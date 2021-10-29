package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.Paciente;

@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoCitasDao cupoCitasDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	public static final int DIA_FIN = 31;
	public static final int MES_FIN = 1;
	public static final int ANYO_FIN = 2022;

	@SuppressWarnings("deprecation")
	@GetMapping("/buscarParDeCuposLibresAPartirDeHoy")
	public List<CupoCitas> buscarParDeCuposLibresAPartirDeHoy(@RequestBody CentroSalud centroSalud) {

		if (centroSalud != null) {
			Date maximo = new Date(ANYO_FIN - 1900, MES_FIN - 1, DIA_FIN);
			maximo.setDate(maximo.getDate() - centroSalud.getVacuna().getDiasEntreDosis());

			Date hoy = new Date();
			if (hoy.before(maximo)) {

				List<CupoCitas> lista = new ArrayList<>();
				CupoCitas dosis1 = buscarCupoLibre(centroSalud, hoy);
				lista.add(dosis1);

				Date fechaDosis2 = copia(dosis1.getFechaYHoraInicio());
				fechaDosis2.setDate(fechaDosis2.getDate() + centroSalud.getVacuna().getDiasEntreDosis());
				CupoCitas dosis2 = buscarCupoLibre(centroSalud, fechaDosis2);
				lista.add(dosis2);

				return lista;

			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Desde el " + DIA_FIN + "/" + MES_FIN + "/" + ANYO_FIN + " ya no se podían pedir citas.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Centro de Salud no contemplado.");
		}

	}

	@GetMapping("/buscarCupoLibre")
	public CupoCitas buscarCupoLibre(@RequestBody CentroSalud centroSalud, @RequestBody Date aPartirDeLaFecha) {
		if (centroSalud != null) {
			int maximo = configuracionCuposDao.findAll().get(0).getNumeroPacientes();
			List<CupoCitas> lista = cupoCitasDao.buscarCuposLibresAPartirDe(centroSalud, aPartirDeLaFecha, maximo);
			Collections.sort(lista); // Ordenación por paso por referencia.
			return lista.get(0);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Centro de salud no contemplado.");
		}
	}

	@PutMapping("/confirmarCita")
	public void confirmarCita(@RequestBody CupoCitas cupo, @RequestBody Paciente paciente) {

		// ¡¡¡ NO PROBADO !!!. ¡¡¡ PUEDE FALLAR (no alarmarse) !!!.

		try {
			if (cupo != null && paciente != null) {

				Optional<CupoCitas> optCupoReal = cupoCitasDao.findById(cupo.getUuid());

				/*
				 * Consideración a valorar: Trabajar con el cupo real de la BD puede
				 * considerarse innecesario ya que ya se tiene mediante el parámetro; salvo que
				 * se llenase el cupo entre medias con rapidez, sobretodo en /modificarCita
				 * donde en la interfaz/frontend se espera a que el paciente escoja entre varios
				 * propuestos.
				 */

				if (optCupoReal.isPresent()) {

					CupoCitas cupoReal = optCupoReal.get();

					ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);
					/*
					 * Prog: Puede provocar ArrayIndexOutOfBoundsException en caso de no existir
					 * configuración en la BD. Considerar medir y throwear mediante if isEmpty().
					 */

					cupoReal.anadirPaciente(paciente, configuracionCupos); // Doble throws

					paciente.incrementarNumVacunas(); // throws

					// No se restará stock hasta que no se confirme como vacunado por un sanitario.

					cupoCitasDao.save(cupoReal);
					usuarioDao.save(paciente);

					/*
					 * Prog: Ambos .save() deberían funcionar por Sustitución al tratarse de la
					 * misma PK. En caso contrario, añadir cupoCitasDao.delete(cupoReal); justo
					 * antes.
					 */

				} else {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cupo no se contemplaba en BD.");
				}

			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						(cupo == null ? "Cupo" : "Paciente") + " nulo.");
			}

		} catch (UsuarioInvalidoException | CupoCitasException | PacienteYaVacunadoException e) {
			// Paciente ya contenido en este cupo, máximo alcanzado, o paciente ya vacunado.
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@GetMapping("/buscarCuposLibres")
	public List<CupoCitas> buscarCuposLibres(@RequestBody CentroSalud centroSalud, @RequestBody Date aPartirDeLaFecha) {
		if (centroSalud != null) {
			int maximo = configuracionCuposDao.findAll().get(0).getNumeroPacientes();
			List<CupoCitas> lista = cupoCitasDao.buscarCuposLibresAPartirDe(centroSalud, aPartirDeLaFecha, maximo);
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

		Date fechaFinAbsoluta = new Date(ANYO_FIN - 1900, MES_FIN - 1, DIA_FIN,
				configuracionCupos.getHoraFin().getHours(), configuracionCupos.getHoraFin().getMinutes());

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
				System.out.println(momentos.get(i));
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
