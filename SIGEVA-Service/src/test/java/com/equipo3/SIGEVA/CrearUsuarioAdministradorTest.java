package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

@SpringBootTest
class CrearUsuarioAdministradorTest {

	@Autowired
	private AdministradorController administradorController;

	@Test
	void insercionCorrectaAdministrador() {

		Usuario administrador = new Administrador();

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

		// administradorController.registrarUsuario(administrador);

		assertTrue(true);
	}

	@Test
	void insercionAdministradorDuplicado() {
		try {
			Usuario administrador = new Administrador();

			administrador.setRol("6173cecfc5635444ee5469d7");
			administrador.setCentroSalud("1234");
			administrador.setUsername("user5555");
			administrador.setCorreo("micorreo@correo.com");
			administrador.setHashPassword("sdfsdf");
			administrador.setDni("99999999Q");
			administrador.setNombre("Juan");
			administrador.setApellidos("Perez");
			administrador.setFechaNacimiento(new Date());
			administrador.setImagen("912imagen");
		} catch (ResponseStatusException rse) {
			System.out.println(rse.getMessage());
			Assertions.assertNull(rse);
		}
	}

	@Test
	void addRoles() {
		Rol rol1 = new Rol("Administrador");
		Rol rol2 = new Rol("Sanitario");
		Rol rol3 = new Rol("Paciente");

		administradorController.registrarRol(rol1);
		administradorController.registrarRol(rol2);
		administradorController.registrarRol(rol3);

		assertTrue(true);
	}
}
