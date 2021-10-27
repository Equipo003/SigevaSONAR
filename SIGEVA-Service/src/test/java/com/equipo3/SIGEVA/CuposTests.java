package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.DateWrapper;

@SpringBootTest
public class CuposTests {

	@Autowired
	private CupoController cupoController;
	
	@Autowired
	private CentroSaludDao centroSaludDao;

	@Autowired
	private CupoCitasDao cupoCitasDao;

	@Test
	void prepararCuposYBuscarCuposLibres() { // 100 segundos.

		cupoCitasDao.deleteAll();

		CentroSalud centroSalud = centroSaludDao.findAll().get(0);
		
		System.out.println(centroSalud);
		
		List<CupoCitas> lista1 = cupoController.prepararCuposCitas2(centroSalud);
		
		CupoCitas cupo1 = cupoController.buscarCupoLibre(centroSalud,
				DateWrapper.parseFromStringToDate("07/11/2021 00:00"));

		assertEquals(lista1.get(0), cupo1);

		List<CupoCitas> lista2 = cupoController.buscarCuposLibres(centroSalud,
				DateWrapper.parseFromStringToDate("07/11/2021 00:00"));
		
		assertEquals(lista1, lista2);
		
	}

}
