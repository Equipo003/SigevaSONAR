package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrearUsuariosTest {

	@Autowired
	private AdministradorController administradorController;

	static AdministradorDTO administradorDTO;
	static SanitarioDTO sanitarioDTO;
	static PacienteDTO pacienteDTO;
	static CentroSaludDTO centroSaludDTO;
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
	static void crearCentroSalud() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion(UUID.randomUUID().toString());
		centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
	}

	@BeforeAll
	static void crearAdministrador() {
		administradorDTO = new AdministradorDTO();
		administradorDTO.setRol(new RolDTO());
		administradorDTO.setCentroSalud(new CentroSaludDTO(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
		administradorDTO.setUsername(UUID.randomUUID().toString());
		administradorDTO.setCorreo("micorreo@correo.com");
		administradorDTO.setHashPassword("sdfsdf");
		administradorDTO.setDni("99999999Q");
		administradorDTO.setNombre("Juan");
		administradorDTO.setApellidos("Perez");
		administradorDTO.setFechaNacimiento(new Date());
		administradorDTO.setImagen("912imagen");
	}

	@BeforeAll
	static void crearSanitario() {
		sanitarioDTO = new SanitarioDTO();
		sanitarioDTO.setRol(new RolDTO());
		sanitarioDTO.setCentroSalud(new CentroSaludDTO(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setCorreo("micorreo@correo.com");
		sanitarioDTO.setHashPassword("sdfsdf");
		sanitarioDTO.setDni("99999999Q");
		sanitarioDTO.setNombre("Juan");
		sanitarioDTO.setApellidos("Perez");
		sanitarioDTO.setFechaNacimiento(new Date());
		sanitarioDTO.setImagen("912imagen");
	}

	@BeforeAll
	static void crearPaciente() {
		pacienteDTO = new PacienteDTO();
		pacienteDTO.setRol(new RolDTO());
		pacienteDTO.setCentroSalud(new CentroSaludDTO(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setCorreo("micorreo@correo.com");
		pacienteDTO.setHashPassword("sdfsdf");
		pacienteDTO.setDni("99999999Q");
		pacienteDTO.setNombre("Juan");
		pacienteDTO.setApellidos("Perez");
		pacienteDTO.setFechaNacimiento(new Date());
		pacienteDTO.setImagen("912imagen");
	}

	@Test
	void insercionCorrectaAdministrador() {
		administradorDTO.setRol(administradorController.getRolByNombre("Administrador"));
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		administradorDTO.setCentroSalud((centroSaludDTO));

		administradorController.crearUsuarioAdministrador(requestMockAdmin, administradorDTO);

		assertEquals(administradorController.getUsuarioById(administradorDTO.getIdUsuario()).toString(), administradorDTO.toString());
		administradorController.eliminarUsuario(administradorDTO.getUsername());
		administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
	}

	@Test
	void insercionAdministradorDuplicado() {
		try{
			String uuid = UUID.randomUUID().toString();
			administradorDTO.setUsername(uuid);
			administradorDTO.setRol(administradorController.getRolByNombre("Administrador"));
			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
			administradorDTO.setCentroSalud((centroSaludDTO));

			administradorController.crearUsuarioAdministrador(requestMockAdmin, administradorDTO);
			administradorController.crearUsuarioAdministrador(requestMockAdmin, administradorDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(administradorDTO.getUsername());
			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
			assertNotNull(e);
		}
	}


	@Test
	void insercionCorrectaSanitario() {
		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		sanitarioDTO.setCentroSalud((centroSaludDTO));

		administradorController.crearUsuarioSanitario(requestMockAdmin, sanitarioDTO);

		assertEquals(administradorController.getUsuarioById(sanitarioDTO.getIdUsuario()).toString(), sanitarioDTO.toString());

		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
		administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
	}

	@Test
	void insercionSanitarioDuplicado(){
		try{
			String uuid = UUID.randomUUID().toString();
			sanitarioDTO.setUsername(uuid);
			sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
			sanitarioDTO.setCentroSalud((centroSaludDTO));

			administradorController.crearUsuarioSanitario(requestMockAdmin, sanitarioDTO);
			administradorController.crearUsuarioSanitario(requestMockAdmin, sanitarioDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
			assertNotNull(e);
		}
	}

	@Test
	void insercionCorrectaPaciente() {

		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		pacienteDTO.setCentroSalud((centroSaludDTO));

		administradorController.crearUsuarioPaciente(requestMockAdmin, pacienteDTO);

		assertEquals(administradorController.getPaciente(pacienteDTO.getIdUsuario()).toString(), pacienteDTO.toString());

		administradorController.eliminarUsuario(pacienteDTO.getUsername());
		administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
	}

	@Test
	void insercionPacienteRolSanitario() {

		try {
			pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
			pacienteDTO.setCentroSalud((centroSaludDTO));

			administradorController.crearUsuarioPaciente(requestMockSan, pacienteDTO);

					

		} catch (Exception e) {
			assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
			administradorController.eliminarUsuario(pacienteDTO.getUsername());
			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
		}

	}

	@Test
	void insercionPacienteDuplicado() {
		try {
			String uuid = UUID.randomUUID().toString();
			pacienteDTO.setUsername(uuid);
			pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
			pacienteDTO.setCentroSalud((centroSaludDTO));

			administradorController.crearUsuarioPaciente(requestMockAdmin, pacienteDTO);
			administradorController.crearUsuarioPaciente(requestMockAdmin, pacienteDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(pacienteDTO.getUsername());
			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
			assertNotNull(e);
		}
	}
}
