package com.equipo3.SIGEVA.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;

import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Usuario;

import com.equipo3.SIGEVA.dto.*;


@CrossOrigin
@RestController
@RequestMapping("cita")
public class CitaController {

	@Autowired
	CitaDao citaDao;

	@Autowired
	CupoDao cupoDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	CupoController cupoController;
	@Autowired
	AdministradorController adminController;

	@Autowired
	WrapperModelToDTO wrapper;

	@Autowired
	WrapperDTOtoModel wrapperDTOtoModel;

	@Autowired
	CentroSaludDao centroSaludDao;


	@PostMapping("/obtenerCitasFuturasDelPaciente")
	@GetMapping("/buscarYAsignarCitas")
	public List<CitaDTO> buscarYAsignarCitas(HttpServletRequest request,@RequestBody String uuidPaciente) throws UsuarioInvalidoException {
		// TODO PENDIENTE	
		List<CitaDTO> list = new ArrayList<CitaDTO>();
		if (adminController.verificarAutenticidad(request,"Paciente")) {
			return list;
		}else {
			return null;
		}
	}

	@GetMapping("/confirmarCita")
	public CitaDTO confirmarCita(@RequestBody CupoDTO cupoDTO, @RequestBody PacienteDTO pacienteDTO) {
		// TODO PENDIENTE
		return null;
	}

	@SuppressWarnings("deprecation")
	@GetMapping(value = "/obtenerCitasFecha")
	public List<CitaDTO> obtenerCitasFecha(@RequestParam(name = "centroSaludDTO") String centroSaludDTOJson, @RequestParam(name = "fecha") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") String fechaJson) { // Terminado.
		if (!centroSaludDTOJson.isEmpty() && !fechaJson.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			CentroSaludDTO centroSaludDTO = null;
			Date fecha = null;

			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			try{
			 centroSaludDTO = mapper.readValue(centroSaludDTOJson, CentroSaludDTO.class);
			 fecha = mapper.readValue(fechaJson, Date.class);
			}catch (JsonProcessingException j){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Formato de fecha inválido");
			}
			/*
			 * (Posiblemente se exija desde el frontend pasar String y parseo de fecha de
			 * String a Date).
			 */

			// (La hora de la fecha no importa, solamente importa el día)

			List<CupoDTO> cuposDTO = cupoController.buscarTodosCuposFecha(centroSaludDTO, fecha);

			List<Cita> citas = new ArrayList<>();
			for (int i = 0; i < cuposDTO.size(); i++) {
				citas.addAll(citaDao.buscarCitasDelCupo(cuposDTO.get(i).getUuidCupo()));
			}

			List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citas);
			Collections.sort(citasDTO); //

			return citasDTO;

		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Centro de salud o fecha no contemplada en el parámetro.");
		}
	}

	@GetMapping("/obtenerCitasFuturasDelPaciente")
	public List<CitaDTO> obtenerCitasFuturasDelPaciente(@RequestParam String idPaciente)
			throws UsuarioInvalidoException { // Terminado.

		Optional<Usuario> optUsuario = usuarioDao.findById(idPaciente);

		if (optUsuario != null) {
			List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(idPaciente));
			for (int i = 0; i < citasDTO.size(); i++) {
				if (citasDTO.get(i).getCupo().getFechaYHoraInicio().before(new Date())) {
					citasDTO.remove(i--);
				}
			}
			// Parte de pruebas
//			citasDTO.add(citaSimulacion(paciente, 1));
//			citasDTO.add(citaSimulacion(paciente, 2));
			Collections.sort(citasDTO);
			return citasDTO;
		} else {
			throw new UsuarioInvalidoException("Paciente no contemplado en el parámetro.");
		}
	}


	private CitaDTO citaSimulacion(PacienteDTO paciente,int dosis){
		CitaDTO cita = new CitaDTO();
		cita.setPaciente(paciente);
		CupoDTO cupoDTO = new CupoDTO();
		Cupo cupo = this.cupoDao.findAll().get(0);
		cupoDTO.setCentroSalud(this.wrapper.centroSaludToCentroSaludDTO(this.centroSaludDao.findByNombreCentro("Julio Prueba").get()));
		cupoDTO.setFechaYHoraInicio(new Date());
		cita.setCupo(cupoDTO);
		cita.getPaciente().setNumDosisAplicadas(1);
		cita.setDosis(dosis);
		return cita;
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
		eliminarCitas(obtenerCitasFuturasDelPaciente(paciente.getIdUsuario()));
	}

	public void eliminarTodasLasCitasDelPaciente(PacienteDTO pacienteDTO) { // Terminado
		eliminarCitas(wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario())));
	}

	public void eliminarTodasLasCitasDelCupo(String uuidCupo) { // Terminado
		citaDao.deleteAllByUuidCupo(uuidCupo);
		try {
			cupoController.anularTamanoActual(uuidCupo);
		} catch (CupoException e) {
		}
	}

	@GetMapping("/getPacientePrueba")
	public PacienteDTO getPacientePrueba(){
		Optional<Usuario> opt = this.usuarioDao.findByUsername("No borrar prueba citas");
		if(opt.isPresent()) {
			Usuario paciente = opt.get();
			Paciente p = (Paciente) paciente;
		}
		return wrapper.pacienteToPacienteDTO(this.usuarioDao.findByUsername("No borrar prueba citas").get());

	}

	public void eliminarAllCitasPaciente(PacienteDTO pacienteDTO) {
		List<CitaDTO> citasDTO = wrapper.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario()));
		for (int i = 0; i < citasDTO.size(); i++) {
			citaDao.deleteById(citasDTO.get(i).getUuidCita());
		}
	}

	@PutMapping("/modificarCita")
	public CitaDTO modificarCita(@RequestBody CitaDTO citaAntigua, @RequestBody CupoDTO cupoNuevo) { // PENDIENTE
		// TODO PENDIENTE
		return null;
	}


	@PostMapping("/vacunar")
	public void vacunarPaciente(@RequestBody CitaDTO cita){
		try{
			cita.getPaciente().setNumDosisAplicadas(cita.getPaciente().getNumDosisAplicadas() + 1);
			if(cita.getDosis()==cita.getPaciente().getNumDosisAplicadas()) {
				this.usuarioDao.save(this.wrapperDTOtoModel.pacienteDTOToPaciente(cita.getPaciente()));
			}else{
				throw new Exception();
			}
		}catch (Exception e){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Las dosis del paciente no coinciden con la dosis supuesta para la cita");
		}

	}

}
