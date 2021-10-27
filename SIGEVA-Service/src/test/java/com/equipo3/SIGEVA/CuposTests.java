package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CupoController;
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
	private CupoCitasDao cupoCitasDao;

	@Test
	void prepararCuposYBuscarCuposLibres() { // 100 segundos.

		cupoCitasDao.deleteAll();

		CentroSalud centroSalud = new CentroSalud("", 0,
				new ConfiguracionCupos(30, 0, DateWrapper.parseFromStringToDate("07/11/2021 00:00"),
						DateWrapper.parseFromStringToDate("31/12/2021 00:00"),
						DateWrapper.parseFromStringToDate("01/01/2000 08:00"),
						DateWrapper.parseFromStringToDate("01/01/2000 20:30")),
				null, null); // 1375 registros.
		
		List<CupoCitas> lista1 = cupoController.prepararCuposCitas2(centroSalud);
		
		CupoCitas cupo1 = cupoController.buscarCupoLibre(centroSalud,
				DateWrapper.parseFromStringToDate("07/11/2021 00:00"));

		assertEquals(lista1.get(0), cupo1);

		List<CupoCitas> lista2 = cupoController.buscarCuposLibres(centroSalud,
				DateWrapper.parseFromStringToDate("07/11/2021 00:00"));
		
		assertEquals(lista1, lista2);
		
	}

}
