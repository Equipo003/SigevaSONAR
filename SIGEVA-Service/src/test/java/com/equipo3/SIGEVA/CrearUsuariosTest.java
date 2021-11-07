package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrearUsuariosTest {

	@Autowired
	private AdministradorController administradorController;

	static AdministradorDTO administradorDTO;
	static SanitarioDTO sanitarioDTO;
	static PacienteDTO pacienteDTO;

	@BeforeAll
	static void crearAdministrador() {
		administradorDTO = new AdministradorDTO();
		administradorDTO.setRol(new Rol(UUID.randomUUID().toString()));
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
		sanitarioDTO.setRol(new Rol(UUID.randomUUID().toString()));
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
		pacienteDTO.setRol(new Rol(UUID.randomUUID().toString()));
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
		administradorDTO.setCentroSalud(administradorController.getCentroById("2g"));

		administradorController.crearUsuarioAdministrador(administradorDTO);

		assertEquals(administradorController.getUsuarioById(administradorDTO.getIdUsuario()).toString(), administradorDTO.toString());
		administradorController.eliminarUsuario(administradorDTO.getUsername());
	}

	@Test
	void insercionAdministradorDuplicado() {
		try{
			String uuid = UUID.randomUUID().toString();
			administradorDTO.setUsername(uuid);
			administradorController.crearUsuarioAdministrador(administradorDTO);
			administradorController.crearUsuarioAdministrador(administradorDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(administradorDTO.getUsername());
			assertNotNull(e);
		}
	}


	@Test
	void insercionCorrectaSanitario() {
		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
		sanitarioDTO.setCentroSalud(administradorController.getCentroById("2g"));

		administradorController.crearUsuarioSanitario(sanitarioDTO);

		assertEquals(administradorController.getUsuarioById(sanitarioDTO.getIdUsuario()).toString(), sanitarioDTO.toString());
		administradorController.eliminarUsuario(sanitarioDTO.getUsername());

	}

	@Test
	void insercionSanitarioDuplicado(){
		try{
			String uuid = UUID.randomUUID().toString();
			sanitarioDTO.setUsername(uuid);
			administradorController.crearUsuarioSanitario(sanitarioDTO);
			administradorController.crearUsuarioSanitario(sanitarioDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
			assertNotNull(e);
		}
	}

	@Test
	void insercionCorrectaPaciente() {
		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
		pacienteDTO.setCentroSalud(administradorController.getCentroById("2g"));

//		administradorController.crearUsuarioPaciente(pacienteDTO);

		assertEquals(administradorController.getUsuarioById(pacienteDTO.getIdUsuario()).toString(), pacienteDTO.toString());
		administradorController.eliminarUsuario(pacienteDTO.getUsername());

	}

	@Test
	void insercionPacienteDuplicado() {
		try {
			String uuid = UUID.randomUUID().toString();
			pacienteDTO.setUsername(uuid);
//			administradorController.crearUsuarioPaciente(pacienteDTO);
//			administradorController.crearUsuarioPaciente(pacienteDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario(pacienteDTO.getUsername());
			assertNotNull(e);
		}

	}
}
