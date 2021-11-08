package com.equipo3.SIGEVA;

import java.util.UUID;

import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.model.Vacuna;
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

	static CentroSaludDTO centroSaludDTO;

	@BeforeAll
	static void CrearCentroSalud() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion("Calle falsa 123");
		centroSaludDTO.setNumVacunasDisponibles((int)(Math.random()*1000));
		System.out.println(centroSaludDTO.getNumVacunasDisponibles());
	}

    @Test
	void testCrearCentroSaludBien() {
		centroSaludDTO.setId(UUID.randomUUID().toString());
		centroSaludDTO.setVacuna(administradorController.getVacunaByNombre("Pfizer"));
		administradorController.crearCentroSalud(centroSaludDTO);
		assertEquals(administradorController.getCentroById(centroSaludDTO.getId()).toString(), centroSaludDTO.toString());
//		administradorController.eliminarCentro(centroSaludDTO.getId());
	}
    
    @Test
   	void testCrearCentroSaludExistente() {
       	try {
			String uuid = UUID.randomUUID().toString();
			centroSaludDTO.setNombreCentro(uuid);
			centroSaludDTO.setVacuna(administradorController.getVacunaByNombre("Pfizer"));
       		administradorController.crearCentroSalud(centroSaludDTO);
			administradorController.crearCentroSalud(centroSaludDTO);
		   } catch (Exception e) {
				administradorController.eliminarCentro(centroSaludDTO.getId());
				Assertions.assertNotNull(e);
		   }
   	}
    
//    @Test
//    void testnumeroVacunasDisponiblesNegativo() throws NumVacunasInvalido {
//    	CentroSalud centroS = new CentroSalud();
//    	try {
//    		centroS.setNumVacunasDisponibles(-1);
//    	} catch (Exception e){
//            Assertions.assertNotNull(e);
//        }
//
//	}
//
//    @Test
//    void testModificarStockNumVacunas() {
//    	CentroSalud centroS = new CentroSalud();
//    	centroS.modificarStockVacunas(100);
//    	Assertions.assertEquals(100, centroS.getNumVacunasDisponibles());
//    }
}
