package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Sanitario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CrearUsuariosTest {

	@Autowired
	private AdministradorController administradorController;

	@Test
	void insercionCorrectaAdministrador() {

		Administrador administrador = new Administrador();

		administrador.setRol("6173cecfc5635444ee5469d7");
		administrador.setCentroSalud("1234");
		administrador.setUsername("user55");
		administrador.setCorreo("micorreo@correo.com");
		administrador.setHashPassword("sdfsdf");
		administrador.setDni("99999999Q");
		administrador.setNombre("Juan");
		administrador.setApellidos("Perez");
		administrador.setFechaNacimiento(new Date());
		administrador.setImagen("912imagen");

		administradorController.crearUsuarioAdministrador(administrador);
	}

	@Test
	void eliminarAdministradorCreadoAnteriormente(){
		administradorController.eliminarUsuario("user55");
	}

	@Test
	void insercionAdministradorDuplicado() throws UsuarioInvalidoException {
		Administrador administrador = new Administrador();

		administrador.setRol("6173cecfc5635444ee5469d7");
		administrador.setCentroSalud("1234");
		administrador.setUsername("user55");
		administrador.setCorreo("micorreo@correo.com");
		administrador.setHashPassword("sdfsdf");
		administrador.setDni("99999999Q");
		administrador.setNombre("Juan");
		administrador.setApellidos("Perez");
		administrador.setFechaNacimiento(new Date());
		administrador.setImagen("912imagen");
		try{
			administradorController.crearUsuarioAdministrador(administrador);
			administradorController.crearUsuarioAdministrador(administrador);
		} catch (Exception e){
			administradorController.eliminarUsuario("user55");
			assertNotNull(e);
		}
	}


	@Test
	void insercionCorrectaSanitario() {

		Sanitario sanitario = new Sanitario();

		sanitario.setRol("6173cecfc5635444ee5469d7");
		sanitario.setCentroSalud("1234");
		sanitario.setUsername("sanitario");
		sanitario.setCorreo("micorreo@correo.com");
		sanitario.setHashPassword("sdfsdf");
		sanitario.setDni("99999999Q");
		sanitario.setNombre("Juan");
		sanitario.setApellidos("Perez");
		sanitario.setFechaNacimiento(new Date());
		sanitario.setImagen("912imagen");

		administradorController.crearUsuarioSanitario(sanitario);

	}

	@Test
	void insercionSanitarioDuplicado() throws UsuarioInvalidoException {
		Sanitario sanitario = new Sanitario();

		sanitario.setRol("6173cecfc5635444ee5469d7");
		sanitario.setCentroSalud("1234");
		sanitario.setUsername("sanitario");
		sanitario.setCorreo("micorreo@correo.com");
		sanitario.setHashPassword("sdfsdf");
		sanitario.setDni("99999999Q");
		sanitario.setNombre("Juan");
		sanitario.setApellidos("Perez");
		sanitario.setFechaNacimiento(new Date());
		sanitario.setImagen("912imagen");

		try{
			administradorController.crearUsuarioSanitario(sanitario);
			administradorController.crearUsuarioSanitario(sanitario);
		} catch (Exception e){
			administradorController.eliminarUsuario("sanitario");
			assertNotNull(e);
		}
	}

	@Test
	void eliminarSanitarioCreadoAnteriormente(){
		administradorController.eliminarUsuario("sanitario");
	}

	@Test
	void insercionCorrectaPaciente() {

		Paciente paciente = new Paciente();

		paciente.setRol("6173cecfc5635444ee5469d7");
		paciente.setCentroSalud("1234");
		paciente.setUsername("paciente");
		paciente.setCorreo("micorreo@correo.com");
		paciente.setHashPassword("sdfsdf");
		paciente.setDni("99999999Q");
		paciente.setNombre("Juan");
		paciente.setApellidos("Perez");
		paciente.setFechaNacimiento(new Date());
		paciente.setImagen("912imagen");

		administradorController.crearUsuarioPaciente(paciente);
	}

	@Test
	void insercionPacienteDuplicado() {
		Paciente paciente = new Paciente();

		paciente.setRol("6173cecfc5635444ee5469d7");
		paciente.setCentroSalud("1234");
		paciente.setUsername("paciente");
		paciente.setCorreo("micorreo@correo.com");
		paciente.setHashPassword("sdfsdf");
		paciente.setDni("99999999Q");
		paciente.setNombre("Juan");
		paciente.setApellidos("Perez");
		paciente.setFechaNacimiento(new Date());
		paciente.setImagen("912imagen");
		try {
			administradorController.crearUsuarioPaciente(paciente);
			administradorController.crearUsuarioPaciente(paciente);
		} catch (Exception e){
			administradorController.eliminarUsuario("paciente");
			assertNotNull(e);
		}

	}

	@Test
	void eliminarPacienteCreadoAnteriormente(){
		administradorController.eliminarUsuario("paciente");
	}

//	@Test
//	void addRoles() {
//		Rol rol1 = new Rol("Administrador");
//		Rol rol2 = new Rol("Sanitario");
//		Rol rol3 = new Rol("Paciente");
//
//		administradorController.registrarRol(rol1);
//		administradorController.registrarRol(rol2);
//		administradorController.registrarRol(rol3);
//
//		assertTrue(true);
//	}


}
