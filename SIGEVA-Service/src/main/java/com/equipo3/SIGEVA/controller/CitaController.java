package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.model.Cupo;

@CrossOrigin
@RestController
@RequestMapping("cita")
public class CitaController {

	@Autowired
	CitaDao citaDao;

	@Autowired
	CupoDao cupoDao;

	@Autowired
	CupoController cupoController;

	@Autowired
	WrapperModelToDTO wrapper;

	@GetMapping("/buscarYAsignarCitas")
	public List<CitaDTO> buscarYAsignarCitas(@RequestBody String uuidPaciente) {
		// TODO PENDIENTE
		return null;
	}

	@GetMapping("/confirmarCita")
	public CitaDTO confirmarCita(@RequestBody CupoDTO cupoDTO, @RequestBody PacienteDTO pacienteDTO) {
		// TODO PENDIENTE
		return null;
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/obtenerCitasFecha")
	public List<CitaDTO> obtenerCitasFecha(@RequestBody CentroSaludDTO centroSaludDTO, @RequestBody Date fecha) { // Terminado.
		if (centroSaludDTO != null && fecha != null) {

			/*
			 * (Posiblemente se exija desde el frontend pasar String y parseo de fecha de
			 * String a Date).
			 */

			// (La hora de la fecha no importa, solamente importa el día)

			Date fechaInicio = CupoController.copia(fecha);
			fechaInicio.setHours(0);
			fechaInicio.setMinutes(0);
			Date fechaFin = CupoController.copia(fechaInicio);
			fechaFin.setDate(fechaFin.getDate() + 1);
			List<Cupo> cupos = cupoDao.buscarCuposDelTramo(centroSaludDTO.getId(), fechaInicio, fechaFin);

			List<Cita> citas = new ArrayList<>();
			for (int i = 0; i < cupos.size(); i++) {
				citas.addAll(citaDao.buscarCitasDelCupo(cupos.get(i).getUuidCupo()));
			}

			List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citas);
			Collections.sort(citasDTO); // Ordenación

			return citasDTO;

		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Centro de salud o fecha no contemplada en el parámetro.");
		}
	}

	@GetMapping("/obtenerCitasFuturasDelPaciente")
	public List<CitaDTO> obtenerCitasFuturasDelPaciente(@RequestBody PacienteDTO paciente)
			throws UsuarioInvalidoException { // Terminado.
		if (paciente != null) {
			List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(paciente.getIdUsuario()));
			for (int i = 0; i < citasDTO.size(); i++) {
				if (citasDTO.get(i).getCupo().getFechaYHoraInicio().before(new Date())) {
					citasDTO.remove(i--);
				}
			}
			return citasDTO;
		} else {
			throw new UsuarioInvalidoException("Paciente no contemplado en el parámetro.");
		}
	}

	@PutMapping("/eliminarCita")
	public void eliminarCita(@RequestBody CitaDTO citaDTO) { // Terminado
		citaDao.deleteById(citaDTO.getUuidCita());
		try {
			cupoController.decrementarTamanoActualCupo(citaDTO.getCupo().getUuidCupo());
		} catch (CupoException e) {
			// Cupo no existente en la BD.
			e.printStackTrace();
		}
	}

	public void eliminarCitas(List<CitaDTO> citasDTO) { // Terminado
		for (int i = 0; i < citasDTO.size(); i++) {
			eliminarCita(citasDTO.get(i));
		}
	}

	@PutMapping("/eliminarCitasFuturasDelPaciente")
	public void eliminarCitasFuturasDelPaciente(@RequestBody PacienteDTO paciente) throws UsuarioInvalidoException { // Terminado
		eliminarCitas(obtenerCitasFuturasDelPaciente(paciente));
	}

	public void eliminarTodasLasCitasDelPaciente(PacienteDTO pacienteDTO) { // Terminado
		eliminarCitas(wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario())));
	}

	public void eliminarTodasLasCitasDelCupo(String uuidCupo) { // Terminado
		citaDao.deleteAllByUuidCupo(uuidCupo);
		try {
			cupoController.anularTamanoActual(uuidCupo);
		} catch (CupoException e) {
			e.printStackTrace();
		}
	}

	@PutMapping("/modificarCita")
	public CitaDTO modificarCita(@RequestBody CitaDTO citaAntigua, @RequestBody CupoDTO cupoNuevo) { // PENDIENTE
		// TODO PENDIENTE
		return null;
	}

}
