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

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.exception.CupoCitasException;
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
	ConfiguracionCuposDao configuracionCuposDao;

	@SuppressWarnings("deprecation")
	@GetMapping("/buscarParDeCuposLibresAPartirDeHoy")
	public List<CupoCitas> buscarParDeCuposLibresAPartirDeHoy(@RequestBody CentroSalud centroSalud) {
		int distancia = 21;

		Date maximo = new Date(122, 0, 1); // 01/01/2022
		maximo.setDate(maximo.getDate() - distancia); // 11/12/2021

		Date hoy = new Date();
		if (hoy.before(maximo)) {
			if (centroSalud != null) {
				List<CupoCitas> lista = new ArrayList<>();
				CupoCitas dosis1 = buscarCupoLibre(centroSalud, hoy);
				lista.add(dosis1);
				Date fechaDosis2 = copia(dosis1.getFechaYHoraInicio());
				fechaDosis2.setDate(fechaDosis2.getDate() + 21);
				CupoCitas dosis2 = buscarCupoLibre(centroSalud, fechaDosis2);
				lista.add(dosis2);
				return lista;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Centro de salud no contemplado.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Ya no se pueden pedir citas a razón de que en 2022 no se asignarán segundas dosis.");
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
		// ¡No probado!
		try {
			if (cupo != null && paciente != null) {
				Optional<CupoCitas> optCupo = cupoCitasDao.findById(cupo.getUuid());
				if (optCupo.isPresent()) {
					CupoCitas cupoReal = optCupo.get();
					ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);
					cupoReal.anadirPaciente(paciente, configuracionCupos);
					cupoCitasDao.save(cupoReal);
					/*
					 * El .save() debería funcionar por Sustitución al tratarse de la misma PK. En
					 * caso contrario, añadir cupoCitasDao.delete(cupoReal); justo antes.
					 */
				} else {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El cupo no se contemplaba en BD.");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						(cupo == null ? "Cupo" : "Paciente") + " no contemplado correctamente en el parámetro.");
			}
		} catch (UsuarioInvalidoException | CupoCitasException e) {
			// Paciente ya contenido, o máximo alcanzado.
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Autowired
	private CentroSaludDao centroSaludDao;

	@GetMapping("/prueba")
	public void prueba() { // Método borrador.
		CentroSalud centroSalud = centroSaludDao.findAll().get(0);
		List<CupoCitas> lista = buscarParDeCuposLibresAPartirDeHoy(centroSalud);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).toString());
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

		Date fechaFinAbsoluta = new Date(121, 11, 31, configuracionCupos.getHoraFin().getHours(),
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

			// El bucle es sustituible por cupoCitasDao.saveAll(momentos);
			for (int i = 0; i < momentos.size(); i++) {
				//System.out.println(momentos.get(i));
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
