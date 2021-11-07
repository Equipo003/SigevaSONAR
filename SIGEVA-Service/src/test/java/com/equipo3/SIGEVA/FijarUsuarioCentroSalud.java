package com.equipo3.SIGEVA;

import java.util.UUID;
import java.util.WeakHashMap;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.CentroSalud;
import org.junit.jupiter.api.Assertions;
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
	private UsuarioDao usuarioDao;
	@Autowired
	private RolDao roldao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;


	@Test
	void AsignarCentroSaludSanitarioNivelModelo() throws NumVacunasInvalido {
		Usuario sanitario = new Sanitario();
		CentroSaludDTO cs = new CentroSaludDTO();
		cs.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(cs);

		sanitario.setNombre("NombrePrueba");
		sanitario.setUsername(UUID.randomUUID().toString());
		Rol rol = null;

		if(roldao.findAllByNombre("Sanitario").isPresent()) {
			rol = roldao.findAllByNombre("Sanitario").get();
		}

		sanitario.setRol(rol.getId());

		if(sanitario!=null) {
			System.out.println("No es nulo");
			System.out.println(sanitario.getRol());
		}

		administradorController.fijarPersonal(sanitario.getUsername(), cs.getId());

		Usuario san = null;

		//administradorController.crearUsuarioSanitario(sanitario);


		if(usuarioDao.findById(sanitario.getIdUsuario()).isPresent()) {
			san = usuarioDao.findById(sanitario.getIdUsuario()).get();
		}
		Assertions.assertEquals(sanitario.getCentroSalud(), san.getCentroSalud());
	}

	@Test
	void AsignarCentroSaludSanitarioNivelBBDD() throws NumVacunasInvalido {
		SanitarioDTO sanitario = new SanitarioDTO();

		CentroSaludDTO cs = new CentroSaludDTO();
		CentroSalud csSanitario = new CentroSalud();

		Rol rol = new Rol();
		rol.setId("e24bf973-e26e-47b7-b8f4-83fa13968221");
		rol.setNombre("Sanitario");

		cs.setNombreCentro(UUID.randomUUID().toString());
		cs.setNombreCentro(UUID.randomUUID().toString());

		sanitario.setUsername(sanitario.getIdUsuario());
		sanitario.setRol(rol);

		administradorController.crearCentroSalud(cs);
		sanitario.setCentroSalud(csSanitario);

		administradorController.crearUsuarioSanitario(sanitario);


		if(centroSaludDao.findById(cs.getId()).isPresent()) {
			cs = wrapperModelToDTO.centroSaludToCentroSaludDTO(centroSaludDao.findById(cs.getId()).get());
		}

		if(usuarioDao.findById(sanitario.getIdUsuario()).isPresent()) {
			sanitario = (SanitarioDTO)this.wrapperModelToDTO.usuarioToUsuarioDTO(usuarioDao.findById(sanitario.getIdUsuario()).get());
		}
		Assertions.assertEquals(cs.getId(), sanitario.getCentroSalud().getId());
	}

}
