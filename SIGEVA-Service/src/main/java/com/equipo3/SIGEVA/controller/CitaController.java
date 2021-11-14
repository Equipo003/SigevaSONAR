package com.equipo3.SIGEVA.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	CupoDao cupoDao;

	@Autowired
	CentroSaludDao centroSaludDao;


	@PostMapping("/obtenerCitasFuturasDelPaciente")
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

	@PostMapping("/obtenerCitasFuturasDelPacienteP")
	public List<CitaDTO> obtenerCitasFuturasDelPacientePracticas(@RequestBody PacienteDTO paciente)
			throws UsuarioInvalidoException {
		if (paciente != null) {
			List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(paciente.getIdUsuario()));
			for (int i = 0; i < citasDTO.size(); i++) {
				if (citasDTO.get(i).getCupo().getFechaYHoraInicio().before(new Date())) {
					citasDTO.remove(i--);
				}
			}


			CitaDTO cita = new CitaDTO();
			cita.setPaciente(paciente);
			CupoDTO cupoDTO = new CupoDTO();
			Cupo cupo = this.cupoDao.findAll().get(0);
			cupoDTO.setCentroSalud(this.wrapper.centroSaludToCentroSaludDTO(this.centroSaludDao.findByNombreCentro("Julio Prueba").get()));
			cita.setCupo(cupoDTO);
			cita.setDosis(1);
			citasDTO.add(cita);
			cita.setDosis(2);
			citasDTO.add(cita);
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


	@GetMapping("/getPacientePrueba")
	public PacienteDTO getPacientePrueba(){
		Optional<Usuario> opt = this.usuarioDao.findByUsername("No borrar prueba citas");
		if(opt.isPresent()) {
			Usuario paciente = opt.get();
			Paciente p = (Paciente) paciente;
			System.out.println(p.getDni());
		}
		return wrapper.pacienteToPacienteDTO(this.usuarioDao.findByUsername("No borrar prueba citas").get());

	}

	public void eliminarAllCitasPaciente(PacienteDTO pacienteDTO) {
		List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario()));
		for (int i = 0; i < citasDTO.size(); i++) {
			citaDao.deleteById(citasDTO.get(i).getUuidCita());
		}
	}

}
