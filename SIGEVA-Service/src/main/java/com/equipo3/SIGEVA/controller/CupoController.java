package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public CupoCitas buscarCupoLibre() {
		return cupoCitasDao.buscarCuposLibres("bf142a13-fb97-4422-9ef6-7536d309cc18", new Date()).get(0);
	}

	@PutMapping("/confirmarCita")
	public void confirmarCita(@RequestBody CupoCitas cupo, @RequestBody Usuario paciente) {
		// TODO
	}

	@SuppressWarnings("deprecation")
	@PostMapping("/prepararCuposCitas")
	public List<CupoCitas> prepararCuposCitas(@RequestBody CentroSalud centroSalud) {

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
			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

			Date fechaFinDiaria = copia(fechaIterada);
			fechaFinDiaria.setHours(fechaFinAbsoluta.getHours()); // Fin del día.
			fechaFinDiaria.setMinutes(fechaFinAbsoluta.getMinutes());

			while (fechaIterada.before(fechaFinDiaria)) {
				momentos.add(new CupoCitas(UUID.randomUUID().toString(), centroSalud, copia(fechaIterada),
						new ArrayList<>()));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

		}

		for (int i = 0; i < momentos.size(); i++) {
			cupoCitasDao.save(momentos.get(i));
		}

		return momentos;
	}

	@SuppressWarnings("deprecation")
	public static Date copia(Date fecha) { // Desacoplado del Paso por Referencia.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

}
