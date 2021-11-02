package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.CentroSaludDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Usuario;

@SpringBootTest
class FijarUsuarioCentroSalud {
	@Autowired
	private AdministradorController administradorController;
	@Autowired
	private CentroSaludDao centroSDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private RolDao roldao;

	@Test
	void AsignarCentroSaludSanitarioNivelModelo() {
		Usuario sanitario = new Sanitario();
		CentroSalud cs = new CentroSalud();
		sanitario.setNombre("NombrePrueba");
		sanitario.setRol("e24bf973-e26e-47b7-b8f4-83fa13968221");
		sanitario.setCentroSalud(cs.getId());
		Assertions.assertEquals(sanitario.getCentroSalud(), cs.getId());
	}

//	@Test
//	void AsignarCentroSaludSanitarioNivelBBDD() throws NumVacunasInvalido {
//		Usuario sanitario = new Sanitario();
//		CentroSaludDTO cs = new CentroSaludDTO();
//
//		cs.setNombreCentro(UUID.randomUUID().toString());
//
//		sanitario.setUsername(sanitario.getIdUsuario());
//		sanitario.setRol("e24bf973-e26e-47b7-b8f4-83fa13968221");
//
//		administradorController.crearCentroSalud(cs);
//		sanitario.setCentroSalud(UUID.randomUUID().toString());
//		administradorController.crearUsuarioSanitario((Sanitario) sanitario);
//
//
//		if(centroSDao.findById(cs.getId()).isPresent()) {
//			cs = centroSDao.findById(cs.getId()).get();
//		}
//
//		if(usuarioDao.findById(sanitario.getIdUsuario()).isPresent()) {
//			sanitario = usuarioDao.findById(sanitario.getIdUsuario()).get();
//		}
//		Assertions.assertEquals(cs.getId(), sanitario.getCentroSalud());
//	}

}
