package com.equipo3.SIGEVA;

import java.util.UUID;

import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.model.Vacuna;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CrearCentroSaludTest {

	@Autowired
	private AdministradorController administradorController;

	@Autowired
	private Utilidades utilidades;

	static CentroSaludDTO centroSaludDTO;

	@BeforeAll
	static void CrearCentroSalud() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion("Calle falsa 123");
		centroSaludDTO.setNumVacunasDisponibles((int)(Math.random()*1000));
	}

    @Test
	void testCrearCentroSaludBien() {
		centroSaludDTO.setId(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		assertEquals(administradorController.getCentroById(centroSaludDTO.getId()).toString(), centroSaludDTO.toString());
		utilidades.eliminarCentro(centroSaludDTO.getId());
	}
    
    @Test
   	void testCrearCentroSaludExistente() {
       	try {
			String uuid = UUID.randomUUID().toString();
			centroSaludDTO.setNombreCentro(uuid);
       		administradorController.crearCentroSalud(centroSaludDTO);
			administradorController.crearCentroSalud(centroSaludDTO);
		   } catch (Exception e) {
			utilidades.eliminarCentro(centroSaludDTO.getId());
				Assertions.assertNotNull(e);
		   }
   	}
}
