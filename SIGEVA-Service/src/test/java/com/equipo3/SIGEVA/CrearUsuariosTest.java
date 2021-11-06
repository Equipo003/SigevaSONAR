package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

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
		administradorDTO.setCentroSalud(new CentroSalud(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
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
		sanitarioDTO.setCentroSalud(new CentroSalud(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
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
		pacienteDTO.setCentroSalud(new CentroSalud(UUID.randomUUID().toString(), "dirección", (int)Math.random()*100));
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
		administradorDTO.setUsername(UUID.randomUUID().toString());
		administradorController.crearUsuarioAdministrador(administradorDTO);
	}

	@Test
	void insercionAdministradorDuplicado() {
		try{
			String uuid = UUID.randomUUID().toString();
			administradorDTO.setUsername(uuid);
			administradorController.crearUsuarioAdministrador(administradorDTO);
			administradorController.crearUsuarioAdministrador(administradorDTO);
		} catch (Exception e){
			assertNotNull(e);
		}
	}


	@Test
	void insercionCorrectaSanitario() {
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		administradorController.crearUsuarioSanitario(sanitarioDTO);
	}

	@Test
	void insercionSanitarioDuplicado(){
		try{
			String uuid = UUID.randomUUID().toString();
			sanitarioDTO.setUsername(uuid);
			administradorController.crearUsuarioSanitario(sanitarioDTO);
			administradorController.crearUsuarioSanitario(sanitarioDTO);
		} catch (Exception e){
			assertNotNull(e);
		}
	}

	@Test
	void insercionCorrectaPaciente() {
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		administradorController.crearUsuarioPaciente(pacienteDTO);
	}

	@Test
	void insercionPacienteDuplicado() {
		try {
			String uuid = UUID.randomUUID().toString();
			pacienteDTO.setUsername(uuid);
			administradorController.crearUsuarioPaciente(pacienteDTO);
			administradorController.crearUsuarioPaciente(pacienteDTO);
		} catch (Exception e){
			administradorController.eliminarUsuario("paciente");
			assertNotNull(e);
		}

	}

//	@Test
//	void addRoles() {
//		RolDTO rol1 = new RolDTO("Administrador");
//		RolDTO rol2 = new RolDTO("Sanitario");
//		RolDTO rol3 = new RolDTO("Paciente");
//
//		administradorController.registrarRol(rol1);
//		administradorController.registrarRol(rol2);
//		administradorController.registrarRol(rol3);
//
//	}


}
