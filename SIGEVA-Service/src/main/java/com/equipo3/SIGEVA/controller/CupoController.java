package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.DateWrapper;
import com.equipo3.SIGEVA.model.Usuario;

@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoCitasDao cupoCitasDao;

	@GetMapping("/buscarCupoLibre")
	public CupoCitas buscarCupoLibre(@RequestBody CentroSalud centroSalud, @RequestBody Date fecha) {
		if (centroSalud != null) {
			return cupoCitasDao.buscarCuposLibres(centroSalud, fecha).get(0);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se estaba considerando centroSalud.");
		}
	}

	@PutMapping("/confirmarCita")
	public void confirmarCita(@RequestBody CupoCitas cupo, @RequestBody Usuario paciente) {
		// TODO
	}

	@GetMapping("/prueba")
	public void prueba() {
		CentroSalud centroSalud = new CentroSalud("", 0,
				new ConfiguracionCupos(30, 0, DateWrapper.parseFromStringToDate("07/11/2021 00:00"),
						DateWrapper.parseFromStringToDate("31/12/2021 00:00"),
						DateWrapper.parseFromStringToDate("01/01/2000 08:00"),
						DateWrapper.parseFromStringToDate("01/01/2000 20:30")),
				null, null); // 1375 registros.
		List<CupoCitas> lista2 = this.prepararCuposCitas1(centroSalud);
		for (int i = 0; i < lista2.size(); i++) {
			System.out.println(lista2.get(i).toString());
		}
	}

	@GetMapping("/buscarCuposLibres")
	public List<CupoCitas> buscarCuposLibres(@RequestBody CentroSalud centroSalud, @RequestBody Date fecha) {
		if (centroSalud != null) {
			return cupoCitasDao.buscarCuposLibres(centroSalud, fecha);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se estaba considerando centroSalud.");
		}
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/prepararCuposCitas1")
	public List<CupoCitas> prepararCuposCitas1(@RequestBody CentroSalud centroSalud) { // No requerirá tiempo.
		List<CupoCitas> momentos = new ArrayList<>();

		Date fechaInicio = centroSalud.getConfiguracionCupos().getDiaInicio();
		fechaInicio.setHours(centroSalud.getConfiguracionCupos().getHoraInicio().getHours());
		fechaInicio.setMinutes(centroSalud.getConfiguracionCupos().getHoraInicio().getMinutes());
		fechaInicio.setSeconds(0);

		Date fechaFinAbsoluta = new Date(centroSalud.getConfiguracionCupos().getDiaFin().getYear(),
				centroSalud.getConfiguracionCupos().getDiaFin().getMonth(),
				centroSalud.getConfiguracionCupos().getDiaFin().getDate(),
				centroSalud.getConfiguracionCupos().getHoraFin().getHours(),
				centroSalud.getConfiguracionCupos().getHoraFin().getMinutes());

		int duracionTramo = centroSalud.getConfiguracionCupos().getDuracionMinutos();

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

	@PostMapping("/prepararCuposCitas2")
	public List<CupoCitas> prepararCuposCitas2(@RequestBody CentroSalud centroSalud) { // Requerirá tiempo.

		List<CupoCitas> momentos = prepararCuposCitas1(centroSalud);

		for (int i = 0; i < momentos.size(); i++) {
			System.out.println(momentos.get(i));
			cupoCitasDao.save(momentos.get(i)); // ¡TARDA LO SUYO!
		}

		return momentos;

	}

	@SuppressWarnings("deprecation")
	public static Date copia(Date fecha) { // Desacoplado del Paso por Referencia.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

}
