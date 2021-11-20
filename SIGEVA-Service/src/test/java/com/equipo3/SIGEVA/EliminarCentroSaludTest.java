package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.Cupo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CupoDao;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EliminarCentroSaludTest {

	@Autowired
	private AdministradorController administradorController;

	static CentroSaludDTO centroSaludDTO;

	static UsuarioDTO usuarioDTO;

	static AdministradorDTO administradorDTO;

	static SanitarioDTO sanitarioDTO;

	static PacienteDTO pacienteDTO;

	@Autowired
	private CupoDao cupoDao;
	
	public static MockHttpServletRequest requestMockAdmin;
	public static MockHttpServletRequest requestMockSan;
	public static MockHttpServletRequest requestMockPa;
	static TokenDTO tokenDTOAdmin;
	static TokenDTO tokenDTOSan;
	static TokenDTO tokenDTOPa;

	@BeforeAll
	static void creacionRequest() {
		requestMockAdmin = new MockHttpServletRequest();
		tokenDTOAdmin = new TokenDTO("adm", "Administrador");
		requestMockAdmin.getSession().setAttribute("token", tokenDTOAdmin);

		requestMockSan = new MockHttpServletRequest();
		tokenDTOSan = new TokenDTO("san", "Sanitario");
		requestMockSan.getSession().setAttribute("token", tokenDTOSan);
		
		requestMockPa = new MockHttpServletRequest();
		tokenDTOPa = new TokenDTO("pa", "Paciente");
		requestMockPa.getSession().setAttribute("token", tokenDTOPa);
	}

	@Test
	void eliminacionCorrectaTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		String idCentro= centroSaludDTO.getId();
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(requestMockAdmin,centroSaludDTO);
			administradorController.getCentroById(idCentro);
		}catch(Exception e){
			assertEquals(e.getMessage(), "409 CONFLICT \"Identificador Centro Salud "+ idCentro+" no existe\"");
		}
	}

	@Test
	void eliminacionCentroSaludDuplicadoTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(requestMockAdmin,centroSaludDTO);
			administradorController.borrarCentroSalud(requestMockAdmin,centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
		}
		administradorController.eliminarCentro(centroSaludDTO.getId());
	}

	@Test
	void eliminacionCentroSaludConPacienteTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);

		sanitarioDTO = new SanitarioDTO();
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setCentroSalud(centroSaludDTO);
		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));

		administradorDTO = new AdministradorDTO();
		administradorDTO.setUsername(UUID.randomUUID().toString());
		administradorDTO.setCentroSalud(centroSaludDTO);
		administradorDTO.setRol(administradorController.getRolByNombre("Administrador"));

		administradorController.crearUsuarioAdministrador(requestMockAdmin,administradorDTO);
		administradorController.crearUsuarioPaciente(requestMockAdmin,pacienteDTO);
		administradorController.crearUsuarioSanitario(requestMockAdmin,sanitarioDTO);
		try {
			administradorController.borrarCentroSalud(requestMockAdmin,centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
		}

		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
		administradorController.eliminarUsuario(pacienteDTO.getUsername());
		administradorController.eliminarUsuario(administradorDTO.getUsername());
		administradorController.eliminarCentro(centroSaludDTO.getId());
	}

	@Test
	void eliminacionCentroSaludConCitasTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);

		Date fecha = new Date(120,0,1);
		Cupo cupo = new Cupo();
		cupo.setFechaYHoraInicio(fecha);
		cupo.setUuidCentroSalud(centroSaludDTO.getId());
		cupo.setTamanoActual(1);
		cupoDao.save(cupo);

		try {
			administradorController.borrarCentroSalud(requestMockAdmin,centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
		}
		administradorController.eliminarCentro(centroSaludDTO.getId());

	}
	
	@Test
	void eliminacionCentroSaludPacienteTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(requestMockPa,centroSaludDTO);
		}catch(Exception e){
			assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
		}
	}
	
	@Test
	void eliminacionCentroSaludSanitarioTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(requestMockSan,centroSaludDTO);
		}catch(Exception e){
			assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
		}
	}

}
