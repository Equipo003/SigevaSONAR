package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.exception.CitaException;
import com.equipo3.SIGEVA.model.Cita;

@SpringBootTest
class ModificarCitaTest {
	@Autowired
	CitaController citaController = new CitaController();

	@Autowired
	AdministradorController administradorController = new AdministradorController();

	@Autowired
	CupoController cupoController = new CupoController();

	private static CupoDTO cupoDTO;
	private static CitaDTO citaDTO;
	private static PacienteDTO pacienteDTO;
	private static CentroSaludDTO centroSaludDTO;

	@Autowired
	CitaDao citaDao;

	@BeforeAll
	static void inicializacionObjetos() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setRol(new RolDTO());
		pacienteDTO.setCentroSalud(
				new CentroSaludDTO(UUID.randomUUID().toString(), "dirección", (int) Math.random() * 100));
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setCorreo("micorreo@correo.com");
		pacienteDTO.setHashPassword("sdfsdf");
		pacienteDTO.setDni("99999999Q");
		pacienteDTO.setNombre("Juan");
		pacienteDTO.setApellidos("Perez");
		pacienteDTO.setFechaNacimiento(new Date());
		pacienteDTO.setImagen("912imagen");

		cupoDTO = new CupoDTO();
		cupoDTO.setCentroSalud(centroSaludDTO);

		citaDTO = new CitaDTO();
		citaDTO.setPaciente(pacienteDTO);
		citaDTO.setCupo(cupoDTO);
		cupoDTO.setCentroSalud(centroSaludDTO);
	}

	@Test
	public void modificacionCitaNoExistente() {
		try {
			citaController.modificarCita(citaDTO.getUuidCita(), cupoDTO.getUuidCupo());

		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "204 NO_CONTENT \"La cita que se intenta modificar no existe\"");
		}
	}

	@Test
	void modificacionCitaCupoNoExistente() {
		try {
			pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
			administradorController.crearCentroSalud(centroSaludDTO);
			administradorController.crearUsuarioPaciente(pacienteDTO);
			cupoController.crearCupo(cupoDTO);
			citaController.crearCita(citaDTO);

			citaController.modificarCita(citaDTO.getUuidCita(), UUID.randomUUID().toString());
		} catch (Exception e) {
			Assertions.assertEquals(e.getMessage(), "204 NO_CONTENT \"El cupo no existe\"");
			citaDao.deleteById(citaDTO.getUuidCita());
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
			administradorController.eliminarCentro(centroSaludDTO.getId());
			administradorController.eliminarUsuario(pacienteDTO.getUsername());
		}
	}

	@Test
	void modificacionCitaCorrecta() {
		try {
			pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
			administradorController.crearCentroSalud(centroSaludDTO);
			administradorController.crearUsuarioPaciente(pacienteDTO);
			
			CupoDTO newCupo = new CupoDTO();
			newCupo.setCentroSalud(centroSaludDTO);
	        Date fecha = new Date();
	        fecha.setDate(fecha.getDay()+1);
	        newCupo.setFechaYHoraInicio(fecha);
			
			cupoController.crearCupo(cupoDTO);
			cupoController.crearCupo(newCupo);
			citaController.crearCita(citaDTO);
	        
	        citaController.modificarCita(citaDTO.getUuidCita(), newCupo.getUuidCupo());
	        
	        
	        Optional<Cita> citaEncontrado = citaDao.findById(citaDTO.getUuidCita());
	        Cita citaBbdd = null;
	        if(citaEncontrado.isPresent()) {
	        	citaBbdd = citaEncontrado.get();
	        }
	        
	        Assertions.assertEquals(citaBbdd.getUuidCupo(), newCupo.getUuidCupo());
	        
	        citaDao.deleteById(citaDTO.getUuidCita());
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
			cupoController.eliminarCupo(newCupo.getUuidCupo());
			administradorController.eliminarCentro(centroSaludDTO.getId());
			administradorController.eliminarUsuario(pacienteDTO.getUsername());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
