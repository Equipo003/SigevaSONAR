package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.CupoSimple;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Usuario;

@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoCitasDao cupoCitasDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	CentroSaludDao centroSaludDao;

	@SuppressWarnings("deprecation")
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/buscarParDeCuposLibresAPartirDeHoy")
	public List<CupoCitas> buscarParDeCuposLibresAPartirDeHoy(@RequestParam String username) {
		System.out.println(username);
		Optional<Usuario> u = usuarioDao.findByUsername(username);
		if (username == null || !u.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente no contemplado.");
		}

		Paciente paciente = (Paciente) u.get();

		Optional<CentroSalud> optCs = this.centroSaludDao.findById(paciente.getCentroSalud());
		if (!optCs.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No existe el centro de salud o el paciente no tiene centro de salud asignado.");
		}
		CentroSalud centroSalud = optCs.get();

		Date maximo = new Date(Condicionamientos.anyoFin() - 1900, Condicionamientos.mesFin() - 1,
				Condicionamientos.diaFin());
		maximo.setDate(maximo.getDate() - centroSalud.getVacuna().getDiasEntreDosis());
		Date hoy = new Date();

		if (!hoy.before(maximo)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desde el " + Condicionamientos.diaFin() + "/"
					+ Condicionamientos.mesFin() + "/" + Condicionamientos.anyoFin() + " ya no se podían pedir citas.");
		}

		List<CupoCitas> lista = new ArrayList<>();

		CupoCitas dosis1 = buscarPrimerCupoLibre(centroSalud, hoy);
		lista.add(dosis1);

		Date fechaDosis2 = copia(dosis1.getFechaYHoraInicio());
		fechaDosis2.setDate(fechaDosis2.getDate() + centroSalud.getVacuna().getDiasEntreDosis());
		CupoCitas dosis2 = buscarPrimerCupoLibre(centroSalud, fechaDosis2);
		lista.add(dosis2);

		confirmarCita(dosis1, paciente);
		confirmarCita(dosis2, paciente);

		if (Condicionamientos.control()) {
			paciente.setAsignado(true);
		}
		usuarioDao.save(paciente);

		return lista;

	}

	public void desasignarCupo(@RequestBody CupoCitas cupo, @RequestBody Paciente paciente) {
		if (cupo == null || paciente == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupo o paciente no contemplado.");
		}

		try {
			cupo.eliminarPaciente(paciente);
			paciente.desasignarCupo(CupoSimple.createCupoSimple(cupo));

			cupoCitasDao.save(cupo);
			usuarioDao.save(paciente);
		} catch (UsuarioInvalidoException | CupoCitasException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	@GetMapping("/buscarCupoLibre")
	public CupoCitas buscarPrimerCupoLibre(@RequestBody CentroSalud centroSalud, @RequestBody Date aPartirDeLaFecha) {
		if (centroSalud != null) {
			int maximo = configuracionCuposDao.findAll().get(0).getNumeroPacientes();
			List<CupoCitas> lista = cupoCitasDao.buscarCuposLibresAPartirDe(centroSalud.getId(), aPartirDeLaFecha, maximo);
			Collections.sort(lista); // Ordenación por paso por referencia.
			return lista.get(0);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Centro de salud no contemplado.");
		}
	}

	@PutMapping("/confirmarCita")
	public void confirmarCita(@RequestBody CupoCitas cupo, @RequestBody Paciente paciente) {

		try {

			if (cupo == null || paciente == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						(cupo == null ? "Cupo" : "Paciente") + " nulo.");
			}

			if ((paciente.isAsignado() || paciente.getNumCitasPendientes() == 2) && Condicionamientos.control()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El paciente ya tenía dos dosis asignadas.");
			}

			if (paciente.isVacunado() && Condicionamientos.control()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El paciente ya está vacunado.");
			}

			List<ConfiguracionCupos> confCupos = this.configuracionCuposDao.findAll();
			if (confCupos.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.PRECONDITION_REQUIRED,
						"Es necesario establecer la configuración de los cupos.");
			}
			ConfiguracionCupos configuracionCupos = confCupos.get(0);

			Optional<CupoCitas> optCupoReal = cupoCitasDao.findById(cupo.getUuid());
			if (!optCupoReal.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Extrañamente el cupo no se contemplaba en BD.");
			}
			CupoCitas cupoReal = optCupoReal.get();

			cupoReal.anadirPaciente(paciente, configuracionCupos); // Doble throws

			paciente.asignarCupo(CupoSimple.createCupoSimple(cupoReal)); // throws

			cupoCitasDao.save(cupoReal);
			usuarioDao.save(paciente);

		} catch (UsuarioInvalidoException | CupoCitasException e) {
			// Paciente ya contenido en este cupo, máximo alcanzado, o paciente ya vacunado.
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno.");
		}
	}

	@GetMapping("/buscarCuposLibres")
	public List<CupoCitas> buscarCuposLibres(@RequestBody CentroSalud centroSalud, @RequestBody Date aPartirDeLaFecha) {
		if (centroSalud != null) {
			int maximo = configuracionCuposDao.findAll().get(0).getNumeroPacientes();
			List<CupoCitas> lista = cupoCitasDao.buscarCuposLibresAPartirDe(centroSalud.getId(), aPartirDeLaFecha, maximo);
			Collections.sort(lista); // Ordenación por paso por referencia.
			return lista;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se estaba considerando centroSalud.");
		}
	}

	@SuppressWarnings("deprecation")
	private List<CupoCitas> calcularCuposCitas(@RequestBody CentroSalud centroSalud) { // No requerirá tiempo.
		List<CupoCitas> momentos = new ArrayList<>();

		ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);

		Date fechaInicio = configuracionCupos.getFechaInicioAsDate();

		Date fechaFinAbsoluta = new Date(Condicionamientos.anyoFin() - 1900, Condicionamientos.mesFin() - 1,
				Condicionamientos.diaFin(), configuracionCupos.getHoraFin().getHours(),
				configuracionCupos.getHoraFin().getMinutes());

		int duracionTramo = configuracionCupos.getDuracionMinutos();

		Date fechaIterada = copia(fechaInicio);

		while (fechaIterada.before(fechaFinAbsoluta)) {

			Date fechaFinDiaria = copia(fechaIterada);
			fechaFinDiaria.setHours(fechaFinAbsoluta.getHours()); // Fin del día.
			fechaFinDiaria.setMinutes(fechaFinAbsoluta.getMinutes());

			while (fechaIterada.before(fechaFinDiaria)) {
				momentos.add(new CupoCitas(centroSalud, copia(fechaIterada), new ArrayList<>()));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

		}

		return momentos;
	}

	@PostMapping("/prepararCuposCitas")
	public List<CupoCitas> prepararCuposCitas(@RequestBody CentroSalud centroSalud) { // ¡Requerirá tiempo!
		if (centroSalud != null) {
			List<CupoCitas> momentos = calcularCuposCitas(centroSalud);

			for (int i = 0; i < momentos.size(); i++) {
				cupoCitasDao.save(momentos.get(i)); // ¡Puede tardar!
			}

			return momentos;
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Centro de salud no contemplado.");
		}
	}

	@SuppressWarnings("deprecation")
	private static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

}
