package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;
import java.util.WeakHashMap;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	private Utilidades utilidades;

	static CentroSaludDTO centroSaludDTO;
	static CentroSaludDTO newCentroSaludDTO;
	static SanitarioDTO sanitarioDTO;

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
		administradorController.crearCentroSalud(centroSaludDTO);
		sanitarioDTO.setCentroSalud((centroSaludDTO));

		sanitarioDTO.setRol(utilidades.getRolByNombre("Sanitario"));

		administradorController.crearUsuarioSanitario(sanitarioDTO);

		administradorController.crearCentroSalud(newCentroSaludDTO);

		administradorController.fijarPersonal(sanitarioDTO.getUsername(), newCentroSaludDTO.getId());

		UsuarioDTO newSanitarioDTO = administradorController.getUsuarioById(sanitarioDTO.getIdUsuario());

		Assertions.assertEquals(newSanitarioDTO.getCentroSalud().getId(), newCentroSaludDTO.getId());

		utilidades.eliminarCentro(centroSaludDTO.getId());
		utilidades.eliminarUsuario(sanitarioDTO.getUsername());
	}
}
