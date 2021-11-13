package com.equipo3.SIGEVA.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

@CrossOrigin
@RestController
@RequestMapping("cita")
public class CitaController {

	@Autowired
	CitaDao citaDao;

	@Autowired
	WrapperModelToDTO wrapper;

	@GetMapping("/obtenerCitasFuturasDelPaciente")
	public List<CitaDTO> obtenerCitasFuturasDelPaciente(@RequestBody PacienteDTO paciente)
			throws UsuarioInvalidoException {
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

	@PutMapping("/eliminarCitasFuturasDelPaciente")
	public void eliminarCitasFuturasDelPaciente(@RequestBody PacienteDTO paciente) throws UsuarioInvalidoException {
		List<CitaDTO> citasDTO = obtenerCitasFuturasDelPaciente(paciente);
		for (int i = 0; i < citasDTO.size(); i++) {
			citaDao.deleteById(citasDTO.get(i).getUuidCita());
		}
	}

	public void eliminarAllCitasPaciente(PacienteDTO pacienteDTO) {
		List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario()));
		for (int i = 0; i < citasDTO.size(); i++) {
			citaDao.deleteById(citasDTO.get(i).getUuidCita());
		}
	}

}
