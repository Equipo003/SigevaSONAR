package com.equipo3.SIGEVA.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CupoSimpleTest {

	static CupoSimple cupoSimple;

	@BeforeAll
	static void start() {
		CentroSalud centroSalud = new CentroSalud();
		Date fechaYHoraInicio = new Date();
		cupoSimple = new CupoSimple(centroSalud, fechaYHoraInicio);
	}

	@Test
	void getUuid() {
		String uuid = UUID.randomUUID().toString();
		cupoSimple.setUuid(uuid);
		assertEquals(uuid, cupoSimple.getUuid());
	}

	@Test
	void setUuid() {
		String uuid = UUID.randomUUID().toString();
		cupoSimple.setUuid(uuid);
		assertEquals(uuid, cupoSimple.getUuid());
	}

	@Test
	void getCentroSalud() {
		CentroSalud centroSalud = new CentroSalud();
		cupoSimple.setCentroSalud(centroSalud);
		assertEquals(centroSalud, cupoSimple.getCentroSalud());
	}

	@Test
	void setCentroSalud() {
		CentroSalud centroSalud = new CentroSalud();
		cupoSimple.setCentroSalud(centroSalud);
		assertEquals(centroSalud, cupoSimple.getCentroSalud());
	}

	@Test
	void getFechaYHoraInicio() {
		Date fechaYHoraInicio = new Date();
		cupoSimple.setFechaYHoraInicio(fechaYHoraInicio);
		assertEquals(fechaYHoraInicio, cupoSimple.getFechaYHoraInicio());
	}

	@Test
	void setFechaYHoraInicio() {
		Date fechaYHoraInicio = new Date();
		cupoSimple.setFechaYHoraInicio(fechaYHoraInicio);
		assertEquals(fechaYHoraInicio, cupoSimple.getFechaYHoraInicio());
	}

	@Test
	void checkToString() {
		assertEquals("CupoSimple [uuid=" + cupoSimple.getUuid() + ", centroSalud=" + cupoSimple.getCentroSalud()
				+ ", fechaYHoraInicio=" + cupoSimple.getFechaYHoraInicio() + "]", cupoSimple.toString());
	}

	@Test
	void checkHashCode() {
		assertNotEquals(cupoSimple.hashCode(), new Integer(0));
	}

	@Test
	void checkEquals() {
		CupoSimple cs2 = new CupoSimple(cupoSimple.getUuid(), cupoSimple.getCentroSalud(),
				cupoSimple.getFechaYHoraInicio());
		assertEquals(cupoSimple, cs2);
	}

	@Test
	void checkCompareTo() {
		CupoSimple cs2 = new CupoSimple(cupoSimple.getUuid(), cupoSimple.getCentroSalud(),
				cupoSimple.getFechaYHoraInicio());
		new CupoSimple();
		assertEquals(cupoSimple.compareTo(cs2), new Integer(0));
	}

}