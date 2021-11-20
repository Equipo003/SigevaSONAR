package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.UUID;
import java.util.WeakHashMap;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.CentroSalud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Usuario;

@SpringBootTest
class FijarUsuarioCentroSalud {
	@Autowired
	private AdministradorController administradorController;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private RolDao roldao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;

	static CentroSaludDTO centroSaludDTO;
	static CentroSaludDTO newCentroSaludDTO;
	static SanitarioDTO sanitarioDTO;
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
	@BeforeAll
	static void crearCentroSalud(){
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion(UUID.randomUUID().toString());
		centroSaludDTO.setNumVacunasDisponibles((int)(Math.random()*1000));

		newCentroSaludDTO = new CentroSaludDTO();
		newCentroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		newCentroSaludDTO.setDireccion(UUID.randomUUID().toString());
		newCentroSaludDTO.setNumVacunasDisponibles((int)(Math.random()*1000));
	}

	@BeforeAll
	static void crearSanitario(){
		sanitarioDTO = new SanitarioDTO();
		sanitarioDTO.setNombre(UUID.randomUUID().toString());
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setHashPassword("sdfsdf");
		sanitarioDTO.setCorreo("correo@correo.com");
		sanitarioDTO.setDni("99999999Q");
		sanitarioDTO.setNombre("Juan");
		sanitarioDTO.setApellidos("Perez");
		sanitarioDTO.setFechaNacimiento(new Date());
		sanitarioDTO.setImagen("912imagen");
	}

	@Test
	void AsignarCentroSalud() {
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		sanitarioDTO.setCentroSalud((centroSaludDTO));

		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));

		administradorController.crearUsuarioSanitario(requestMockAdmin,sanitarioDTO);

		administradorController.crearCentroSalud(requestMockAdmin,newCentroSaludDTO);

		administradorController.fijarPersonal(requestMockAdmin,sanitarioDTO.getUsername(), newCentroSaludDTO.getId());

		UsuarioDTO newSanitarioDTO = administradorController.getUsuarioById(sanitarioDTO.getIdUsuario());

		Assertions.assertEquals(newSanitarioDTO.getCentroSalud().getId(), newCentroSaludDTO.getId());

		administradorController.eliminarCentro(centroSaludDTO.getId());
		administradorController.eliminarCentro(newCentroSaludDTO.getId());
		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
	}
	
	@Test
	void AsignarCentroSaludPorPaciente() {
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		sanitarioDTO.setCentroSalud((centroSaludDTO));

		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));

		administradorController.crearUsuarioSanitario(requestMockAdmin,sanitarioDTO);

		administradorController.crearCentroSalud(requestMockAdmin,newCentroSaludDTO);
		try {
			administradorController.fijarPersonal(requestMockPa,sanitarioDTO.getUsername(), newCentroSaludDTO.getId());
		}catch(Exception e) {
			assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
			administradorController.eliminarCentro(centroSaludDTO.getId());
			administradorController.eliminarCentro(newCentroSaludDTO.getId());
			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
		}
	}
	
	@Test
	void AsignarCentroSaludPorSanitario() {
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		sanitarioDTO.setCentroSalud((centroSaludDTO));

		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));

		administradorController.crearUsuarioSanitario(requestMockAdmin,sanitarioDTO);

		administradorController.crearCentroSalud(requestMockAdmin,newCentroSaludDTO);
		try {
			administradorController.fijarPersonal(requestMockSan,sanitarioDTO.getUsername(), newCentroSaludDTO.getId());
		}catch(Exception e) {
			assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
			administradorController.eliminarCentro(centroSaludDTO.getId());
			administradorController.eliminarCentro(newCentroSaludDTO.getId());
			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
		}
	}
}
