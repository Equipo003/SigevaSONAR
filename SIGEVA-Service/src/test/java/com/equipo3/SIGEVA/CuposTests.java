package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.CupoCitas;

@SpringBootTest
class CuposTests {

	@Autowired
	private CupoController cupoController;

	@Autowired
	private CentroSaludDao centroSaludDao;

	@Autowired
	private CupoCitasDao cupoCitasDao;

	@Autowired
	private ConfiguracionCuposDao configuracionCuposDao;

	@Test
	void prepararCuposYBuscarCuposLibres() {

		cupoCitasDao.deleteAll();

		CentroSalud centroSalud = centroSaludDao.findAll().get(0);

		System.out.println(centroSalud);

		List<CupoCitas> lista1 = cupoController.prepararCuposCitas(centroSalud); // ¡TARDA LO SUYO!

		CupoCitas cupo1 = cupoController.buscarCupoLibre(centroSalud,
				configuracionCuposDao.findAll().get(0).getFechaInicioAsDate());

		assertEquals(lista1.get(0), cupo1);

		List<CupoCitas> lista2 = cupoController.buscarCuposLibres(centroSalud,
				configuracionCuposDao.findAll().get(0).getFechaInicioAsDate());

		assertEquals(lista1, lista2);

	}

}
