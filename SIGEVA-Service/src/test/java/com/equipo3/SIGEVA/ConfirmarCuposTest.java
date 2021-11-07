package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.Paciente;

@SpringBootTest
class ConfirmarCuposTest {

	@Autowired
	private CupoController cupoController;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private RolDao rolDao;

	@Autowired
	private CupoCitasDao cuposDao;

	@Test
	void buscarYConfirmarCupoLibre() {


	}

}
