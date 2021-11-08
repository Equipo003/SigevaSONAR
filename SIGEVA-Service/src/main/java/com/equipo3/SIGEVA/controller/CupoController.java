package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Vacuna;

@CrossOrigin
@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoDao cupoDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@SuppressWarnings("deprecation")
	private List<CupoDTO> calcularCuposCitas(@RequestBody CentroSaludDTO centroSalud) { // No requerirá tiempo.
		List<CupoDTO> momentos = new ArrayList<>();

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
				momentos.add(new CupoDTO(centroSalud, copia(fechaIterada), 0));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

		}

		return momentos;
	}

	@PostMapping("/prepararCuposCitas")
	public List<CupoDTO> prepararCuposCitas(@RequestBody CentroSaludDTO centroSaludDTO) { // ¡Requerirá tiempo!
		if (centroSaludDTO != null) {
			List<CupoDTO> cuposDTO = calcularCuposCitas(centroSaludDTO);

			for (int i = 0; i < cuposDTO.size(); i++) {
				cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cuposDTO.get(i))); // ¡Puede tardar!
			}

			return cuposDTO;
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Centro de salud no contemplado.");
		}
	}

	@SuppressWarnings("deprecation")
	private static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

	@Autowired
	VacunaDao vacunaDao;

	@Autowired
	CentroSaludDao centroSaludDao;

	@GetMapping("/prueba")
	public void prueba() {

		// VacunaDTO vacunaDTO = new VacunaDTO("UUIDVACUNA", "Pfizer", 21, 2);
		// System.out.println(vacunaDTO.toString());
		// Vacuna vacuna = WrapperDTOtoModel.vacunaDTOToVacuna(vacunaDTO);
		// System.out.println(vacuna.toString());
		// vacunaDao.save(vacuna);

		// CentroSaludDTO centroSaludDTO = new CentroSaludDTO("UUIDCENTRO","Mi centro",
		// "Mi direccion", 50);

	}

}
