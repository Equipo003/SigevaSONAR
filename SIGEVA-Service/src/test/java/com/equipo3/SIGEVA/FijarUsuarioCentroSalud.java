package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.CentroSalud;
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
		if(administradorController.getUsuarioByRol("Sanitario")!=null) {
			Usuario sanitario = administradorController.getUsuarioByRol("141abdc8-0f85-43c0-8c51-dfdd2c039ef4").get(1);
			if(administradorController.listarCentros()!=null) {
				CentroSalud cs = administradorController.listarCentros().get(1);
				sanitario.setCentroSalud(cs.getId());
				Assertions.assertEquals(cs.getId(), sanitario.getIdUsuario());
			}
		}
		
	}

}
