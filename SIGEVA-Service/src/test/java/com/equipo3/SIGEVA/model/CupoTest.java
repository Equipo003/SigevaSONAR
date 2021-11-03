package com.equipo3.SIGEVA.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CupoTest {
	static CupoCitas cc;
	
	@BeforeAll
	static void start() {
		CentroSalud cs = new CentroSalud();
		cc = new CupoCitas(cs, new Date(), null);
	}
	@Test
	void setUUIDTest() {
		String id = UUID.randomUUID().toString();
		cc.setUuid(id);
		assertEquals(id, cc.getUuid());
	}
	
	@Test 
	void setCentroSaludTest() {
		CentroSalud cs = new CentroSalud();
		cc.setCentroSalud(cs);
		assertEquals(cs, cc.getCentroSalud());
	}
	
	@Test
	void setFechaYHoraInicioTest() {
		Date ahora = new Date();
		cc.setFechaYHoraInicio(ahora);
		assertEquals(ahora, cc.getFechaYHoraInicio());
	}
	
	@Test
	void setPacientesCitadosTest() {
		Paciente p1 = new Paciente();
		Paciente p2 = new Paciente();
		List<Paciente> pacientes = new ArrayList<>();
		pacientes.add(p1);
		pacientes.add(p2);
		
		cc.setPacientesCitados(pacientes);
		
		assertEquals(pacientes, cc.getPacientesCitados());
	}
	
	@Test
	void getTamanoTest() {
		List<Paciente> pacientes = new ArrayList<>();
		
		cc.setPacientesCitados(pacientes);
		
		int numero = cc.getPacientesCitados().size();
		System.out.println();
		cc.setTamano(numero);
		assertEquals(cc.getTamano(), numero);
	}
	
	@Test
	void setTamano() {
		int numero = cc.getPacientesCitados().size();
		cc.setTamano(numero);
		assertEquals(numero, cc.getTamano());
	}
	
	@Test
	void checkHashCode() {
		assertNotEquals(cc.hashCode(), new Integer(0));
	}
	
	

}
