package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;

@SpringBootTest
class ConfirmarCuposTest {

	@Autowired
	private CupoController cupoController;
	
	@Autowired
	private AdministradorController administradorcontroller;
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private RolDao rolDao;

	@Autowired
	private CupoCitasDao cuposDao;

	@Test
	void buscarYConfirmarCupoLibre() throws NumVacunasInvalido {
		Paciente paciente = new Paciente();
		paciente.setUsername(paciente.getIdUsuario());
		CentroSaludDTO cs = new CentroSaludDTO();
		cs.setNombreCentro(UUID.randomUUID().toString());
		paciente.setCentroSalud(UUID.randomUUID().toString());
		
		administradorcontroller.crearUsuarioPaciente(paciente);
		
		administradorcontroller.crearCentroSalud(cs);
		
		

		// Se obvia que el paciente tenga centro, y que este a su vez tenga cupos.

		CupoCitas primerCupoLibre = cuposDao.buscarCuposLibresAPartirDe(paciente.getCentroSalud(), new Date(), 50)
				.get(0);

		boolean estabaContenido = primerCupoLibre.getPacientesCitados().contains(paciente);

		if (estabaContenido) { // Ida a la desnormalidad
			// Descontener
			cupoController.desasignarCupo(primerCupoLibre, paciente);
		}

		assertFalse(primerCupoLibre.pacienteEnlistado(paciente));

		cupoController.confirmarCita(primerCupoLibre, paciente);
		// primerCupoLibre no pasa a contener paciente porque trabaja con cupoReal.

		CupoCitas cupo2 = cuposDao.findById(primerCupoLibre.getUuid()).get();

		assertTrue(cupo2.pacienteEnlistado(paciente));

		if (!estabaContenido) { // Vuelta a la normalidad
			cupoController.desasignarCupo(cupo2, paciente);
		}

	}

}
