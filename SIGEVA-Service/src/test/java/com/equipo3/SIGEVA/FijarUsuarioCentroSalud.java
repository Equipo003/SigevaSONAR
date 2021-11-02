package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Rol;
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
		cs.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(cs);
		
		sanitario.setNombre("NombrePrueba");
		sanitario.setUsername(UUID.randomUUID().toString());
		Rol rol = null;
		
		if(roldao.findAllByNombre("Sanitario").isPresent()) {
			rol = roldao.findAllByNombre("Sanitario").get();
		}
		
		sanitario.setRol(rol.getId());
		System.out.println("Rol: " +rol.getId());
		
		if(sanitario!=null) {
			System.out.println("No es nulo");
			System.out.println(sanitario.getRol());
		}
		
		administradorController.fijarPersonal(sanitario.getUsername(), cs.getId());
		
		Usuario san = null;
		
		administradorController.crearUsuarioSanitario((Sanitario) sanitario);
		
		System.out.println("Id Sanitario "+sanitario.getIdUsuario());
		
		if(usuarioDao.findById(sanitario.getIdUsuario()).isPresent()) {
			san = usuarioDao.findById(sanitario.getIdUsuario()).get();
			System.out.println("Esta presente");
		}
		Assertions.assertEquals(sanitario.getCentroSalud(), san.getCentroSalud());
	}

	@Test
	void AsignarCentroSaludSanitarioNivelBBDD() {
		Usuario sanitario = new Sanitario();
		CentroSalud cs = new CentroSalud();
		
		cs.setNombreCentro(cs.getId());
		
		sanitario.setUsername(sanitario.getIdUsuario());
		sanitario.setRol("e24bf973-e26e-47b7-b8f4-83fa13968221");
		
		administradorController.crearCentroSalud(cs);
		sanitario.setCentroSalud(cs.getId());
		administradorController.crearUsuarioSanitario((Sanitario) sanitario);
		
		
		if(centroSDao.findById(cs.getId()).isPresent()) {
			cs = centroSDao.findById(cs.getId()).get();
		}
		
		if(usuarioDao.findById(sanitario.getIdUsuario()).isPresent()) {
			sanitario = usuarioDao.findById(sanitario.getIdUsuario()).get();
		}
		Assertions.assertEquals(cs.getId(), sanitario.getCentroSalud());
	}

}