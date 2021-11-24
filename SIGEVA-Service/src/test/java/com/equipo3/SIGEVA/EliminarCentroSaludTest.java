package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.Cupo;

import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CupoDao;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EliminarCentroSaludTest {

	@Autowired
	private AdministradorController administradorController;

	@Autowired
	private Utilidades utilidades;

	static CentroSaludDTO centroSaludDTO;
	
	static UsuarioDTO usuarioDTO;
	
	static AdministradorDTO administradorDTO;
	
	static SanitarioDTO sanitarioDTO;
	
	static PacienteDTO pacienteDTO;
	
	@Autowired
	private CupoDao cupoDao;

	@Test
	void eliminacionCorrectaTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		String idCentro= centroSaludDTO.getId();
		administradorController.crearCentroSalud(centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNull(e);
		}
	}

	@Test
	void eliminacionCentroSaludDuplicadoTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
			utilidades.eliminarCentro(centroSaludDTO.getId());
		}
	}

	@Test
	void eliminacionCentroSaludConPacienteTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		
		sanitarioDTO = new SanitarioDTO();
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setCentroSalud(centroSaludDTO);
		sanitarioDTO.setRol(utilidades.getRolByNombre("Sanitario"));
		
		pacienteDTO = new PacienteDTO();
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
		
		administradorDTO = new AdministradorDTO();
		administradorDTO.setUsername(UUID.randomUUID().toString());
		administradorDTO.setCentroSalud(centroSaludDTO);
		administradorDTO.setRol(utilidades.getRolByNombre("Administrador"));
		
		administradorController.crearUsuarioAdministrador(administradorDTO);
		administradorController.crearUsuarioPaciente(pacienteDTO);
		administradorController.crearUsuarioSanitario(sanitarioDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
			utilidades.eliminarUsuario(sanitarioDTO.getUsername());
			utilidades.eliminarUsuario(pacienteDTO.getUsername());
			utilidades.eliminarUsuario(administradorDTO.getUsername());
			utilidades.eliminarCentro(centroSaludDTO.getId());
		}
	}
	
	@Test
	void eliminacionCentroSaludConCitasTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		
		Date fecha = new Date(120,0,1);
		Cupo cupo = new Cupo();
		cupo.setFechaYHoraInicio(fecha);
		cupo.setUuidCentroSalud(centroSaludDTO.getId());
		cupo.setTamanoActual(1);
		cupoDao.save(cupo);
	
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
			utilidades.eliminarCentro(centroSaludDTO.getId());
		}
	}
}
