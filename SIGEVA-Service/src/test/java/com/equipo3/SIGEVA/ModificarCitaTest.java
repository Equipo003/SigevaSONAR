package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.exception.CitaException;

@SpringBootTest
class ModificarCitaTest {
	@Autowired
	static CitaController citaController = new CitaController();

	@Autowired
	static AdministradorController administradorController = new AdministradorController();
	
	@Autowired
	static CupoController cupoController = new CupoController();

	private static CupoDTO cupoDTO;
	private static CitaDTO citaDTO;
	private static PacienteDTO pacienteDTO = new PacienteDTO();
	private static CentroSaludDTO centroSaludDTO = new CentroSaludDTO();

	@BeforeAll
	static void inicializacionObjetos() {
		

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setRol(new RolDTO());
		pacienteDTO.setCorreo("micorreo@correo.com");
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setHashPassword("sdfsdf");
		pacienteDTO.setDni("99999999Q");
		pacienteDTO.setNombre("Juan");
		pacienteDTO.setApellidos("Perez");
		pacienteDTO.setFechaNacimiento(new Date());
		pacienteDTO.setImagen("912imagen");

	}

	@Test
	public void modificacionCitaNoExistente() {
		try {
			cupoDTO = new CupoDTO();
			cupoDTO.setCentroSalud(centroSaludDTO);
			cupoDTO.setTamanoActual(0);
			cupoDTO.setFechaYHoraInicio(new Date());
			
			centroSaludDTO = new CentroSaludDTO();
			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
			centroSaludDTO.setDireccion(UUID.randomUUID().toString());
			centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
			
			administradorController.crearCentroSalud(centroSaludDTO);
			
			cupoController.crearCupo(cupoDTO);

			citaDTO = new CitaDTO();
			citaDTO.setCupo(cupoDTO);
			citaDTO.setDosis(1);
			citaDTO.setPaciente(pacienteDTO);
			System.out.println("identificador CITA: " + citaDTO.getUuidCita());
			System.out.println("identificador CUPO: " + cupoDTO.getUuidCupo());

			citaController.modificarCita(citaDTO.getUuidCita(), cupoDTO.getUuidCupo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assertions.assertEquals(e.getMessage(), "204 NO_CONTENT \"La cita que se intenta modificar no existe \"");
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
			administradorController.borrarCentroSalud(centroSaludDTO);
		}
	}

	@Test
	void modificacionCitaCupoNoExistente() {
		try {
			cupoDTO = new CupoDTO();
			cupoDTO.setCentroSalud(centroSaludDTO);
			cupoDTO.setTamanoActual(0);
			cupoDTO.setFechaYHoraInicio(new Date());

			citaDTO = new CitaDTO();
			citaDTO.setCupo(cupoDTO);
			citaDTO.setDosis(1);
			citaDTO.setPaciente(pacienteDTO);
			System.out.println("identificador CITA: " + citaDTO.getUuidCita());
			System.out.println("identificador CUPO: " + cupoDTO.getUuidCupo());

			citaController.modificarCita(citaDTO.getUuidCita(), cupoDTO.getUuidCupo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assertions.assertEquals(e.getMessage(), "204 NO_CONTENT \"La cita que se intenta modificar no existe \"");
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
		}
	}

	void modificacionCitaCorrecta() {

	}

}
