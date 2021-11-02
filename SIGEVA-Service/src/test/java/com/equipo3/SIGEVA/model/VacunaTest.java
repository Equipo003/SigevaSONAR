package com.equipo3.SIGEVA.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VacunaTest {

	static Vacuna vacuna;

	@BeforeAll
	static void start() {
		vacuna = new Vacuna("Pfizer", 21, 2);
	}

	@Test
	void getUuid() {
		String uuid = UUID.randomUUID().toString();
		vacuna.setUuid(uuid);
		assertEquals(uuid, vacuna.getUuid());
	}

	@Test
	void setUuid() {
		String uuid = UUID.randomUUID().toString();
		vacuna.setUuid(uuid);
		assertEquals(uuid, vacuna.getUuid());
	}

	@Test
	void getNombre() {
		String nombre = "Pfizer";
		vacuna.setNombre(nombre);
		assertEquals(nombre, vacuna.getNombre());
	}

	@Test
	void setNombre() {
		String nombre = "Pfizer";
		vacuna.setNombre(nombre);
		assertEquals(nombre, vacuna.getNombre());
	}

	@Test
	void getDiasEntreDosis() {
		int diasEntreDosis = 21;
		vacuna.setDiasEntreDosis(diasEntreDosis);
		assertEquals(diasEntreDosis, vacuna.getDiasEntreDosis());
	}

	@Test
	void setDiasEntreDosis() {
		int diasEntreDosis = 21;
		vacuna.setDiasEntreDosis(diasEntreDosis);
		assertEquals(diasEntreDosis, vacuna.getDiasEntreDosis());
	}

	@Test
	void getNumDosis() {
		int numDosis = 2;
		vacuna.setNumDosis(numDosis);
		assertEquals(numDosis, vacuna.getNumDosis());
	}

	@Test
	void setNumDosis() {
		int numDosis = 2;
		vacuna.setNumDosis(numDosis);
		assertEquals(numDosis, vacuna.getNumDosis());
	}

	@Test
	void checkToString() {
		assertEquals("Vacuna [nombre=" + vacuna.getNombre() + ", diasEntreDosis=" + vacuna.getDiasEntreDosis()
				+ ", numDosis=" + vacuna.getNumDosis() + "]", vacuna.toString());
	}

	@Test
	void checkHashCode() {
		assertNotEquals(vacuna.hashCode(), new Integer(0));
	}

	@Test
	void checkEquals() {
		Vacuna v2 = new Vacuna(vacuna.getNombre(), vacuna.getDiasEntreDosis(), vacuna.getNumDosis());
		v2.setUuid(vacuna.getUuid());
		assertEquals(vacuna, v2);
	}

}