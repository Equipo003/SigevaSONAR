package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;

@SpringBootTest
class ModificarCitaTest {
	@Autowired
	static CitaController citaController;

	static CupoDTO cupoDTO;
	static CitaDTO citaDTO;
	static PacienteDTO pacienteDTO;
	static CentroSaludDTO centroSaludDTO;

	@BeforeAll
	static void inicializacionObjetos() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion(UUID.randomUUID().toString());
		centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
		
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
		
		cupoDTO = new CupoDTO();
		cupoDTO.setCentroSalud(centroSaludDTO);
		cupoDTO.setTamanoActual(0);
		cupoDTO.setFechaYHoraInicio(new Date());
		
		citaDTO.setCupo(cupoDTO);
		citaDTO.setDosis(1);
		citaDTO.setPaciente(pacienteDTO);
		
	}
	
	

	@Test
	void modificacionCitaNoExistente() {
		try{
			
		}catch() {
			
		}
	}

	@Test
	void modificaionCitaCupoNoExistente() {

	}

	void modificacionCitaCorrecta() {

	}

}
