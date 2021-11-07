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
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;


	@Test
	void AsignarCentroSaludSanitarioNivelModelo() throws NumVacunasInvalido {
		CentroSaludDTO cs = new CentroSaludDTO();
		cs.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(cs);
		//Centro salud creado

		SanitarioDTO sanitarioDTO = new SanitarioDTO();
		sanitarioDTO.setNombre(UUID.randomUUID().toString());
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		Rol rol = null;
		if(roldao.findAllByNombre("Sanitario").isPresent()) {
			rol = roldao.findAllByNombre("Sanitario").get();

		}
		sanitarioDTO.setRol(rol);
		sanitarioDTO.setCentroSalud(this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(cs));

		administradorController.crearUsuarioSanitario(sanitarioDTO);
		administradorController.fijarPersonal(sanitarioDTO.getUsername(), cs.getId());

		


		Sanitario sanitario = null;
		if(usuarioDao.findByUsername(sanitarioDTO.getUsername()).isPresent()) {
			sanitario = (Sanitario) usuarioDao.findByUsername(sanitarioDTO.getUsername()).get();
		}
		Assertions.assertEquals(sanitarioDTO.getCentroSalud().getId(), sanitario.getCentroSalud());

		administradorController.eliminarCentro(cs.getId());
		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
	}

	@Test
	void AsignarCentroSaludSanitarioNivelBBDD() throws NumVacunasInvalido {
		CentroSaludDTO cs = new CentroSaludDTO();
		cs.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(cs);
		//Centro salud DT creado

		SanitarioDTO sanitarioDTO = new SanitarioDTO();
		Rol rol = null;
		if(roldao.findAllByNombre("Sanitario").isPresent()) {
			rol = roldao.findAllByNombre("Sanitario").get();

		}
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setNombre(UUID.randomUUID().toString());
		sanitarioDTO.setRol(rol);
		sanitarioDTO.setCentroSalud(this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(cs));
		administradorController.crearUsuarioSanitario(sanitarioDTO);


		if(centroSaludDao.findById(cs.getId()).isPresent()) {
			cs = wrapperModelToDTO.centroSaludToCentroSaludDTO(centroSaludDao.findById(cs.getId()).get());
		}

		Sanitario sanitario = null;
		if(usuarioDao.findById(sanitarioDTO.getIdUsuario()).isPresent()) {
			sanitario = (Sanitario)usuarioDao.findByUsername(sanitarioDTO.getUsername()).get();
		}

		administradorController.eliminarCentro(cs.getId());
		administradorController.eliminarUsuario(sanitarioDTO.getUsername());

		Assertions.assertEquals(cs.getId(), sanitario.getCentroSalud());




	}




}
