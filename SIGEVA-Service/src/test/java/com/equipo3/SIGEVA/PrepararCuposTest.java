package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.model.Cupo;

@SpringBootTest
class PrepararCuposTest {

	public static CentroSaludDTO centroSaludDTO;

	@Autowired
	private AdministradorController administradorController;

	@Autowired
	private CupoController cupoController;

	@Autowired
	private CupoDao cupoDao;

	@Test
	void prepararCupos() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setNumVacunasDisponibles(50);
		administradorController.crearCentroSalud(centroSaludDTO);

		List<Cupo> listaInicial = cupoDao.findAllByUuidCentroSalud(centroSaludDTO.getId());
		assertEquals(0, listaInicial.size());

		// cupoController.prepararCupos(centroSaludDTO.getId());

		List<Cupo> listaFinal = cupoDao.findAllByUuidCentroSalud(centroSaludDTO.getId());
		// assertTrue(listaFinal.size() > 0);

		cupoController.calcularCupos(centroSaludDTO);
		cupoController.borrarCuposDelCentro(centroSaludDTO);
		administradorController.borrarCentroSalud(centroSaludDTO);
	}

}
