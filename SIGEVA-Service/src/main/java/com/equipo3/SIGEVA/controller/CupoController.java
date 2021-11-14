package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Cupo;

@CrossOrigin
@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoDao cupoDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	CitaController citaController;

	@Autowired
	WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	WrapperDTOtoModel wrapperDTOtoModel;

	@SuppressWarnings("deprecation")
	private List<CupoDTO> calcularCupos(CentroSaludDTO centroSalud) { // Terminado.
		// No requerirá tiempo de ejecución.

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

	@PostMapping("/prepararCupos")
	public List<CupoDTO> prepararCupos(@RequestBody CentroSaludDTO centroSaludDTO) { // TODO PENDIENTE
		// ¡Requerirá tiempo de ejecución!
		return null;
	}

	@GetMapping("/buscarCuposLibres")
	public List<CupoDTO> buscarCuposLibresFecha(@RequestBody CentroSaludDTO centroSaludDTO,
			@RequestBody Date aPartirDeLaFecha) { // TODO PENDIENTE
		// Usado en modificar.
		return null;
	}

	public CupoDTO buscarPrimerCupoLibreFecha(CentroSaludDTO centroSaludDTO, Date aPartirDeLaFecha) {
		return buscarCuposLibresFecha(centroSaludDTO, aPartirDeLaFecha).get(0);
		// (Terminado)
	}

	@SuppressWarnings("static-access")
	public void incrementarTamanoActual(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está lleno.
			cupoDTO.incrementarTamanoActual(configuracionCuposDao.findAll().get(0).getNumeroPacientes());
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	@SuppressWarnings("static-access")
	public void decrementarTamanoActualCupo(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está vacío.
			cupoDTO.decrementarTamanoActual();
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	@SuppressWarnings("static-access")
	public void anularTamanoActual(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());
			cupoDTO.setTamanoActual(0);
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	public void eliminarCupo(String uuidCupo) { // Terminado.
		citaController.eliminarTodasLasCitasDelCupo(uuidCupo);
		cupoDao.deleteById(uuidCupo);
	}

	@SuppressWarnings("deprecation")
	public static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia. // Terminado.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

}
