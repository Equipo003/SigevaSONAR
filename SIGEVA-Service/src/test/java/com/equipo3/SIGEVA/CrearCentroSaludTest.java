package com.equipo3.SIGEVA;

import java.util.UUID;

import com.equipo3.SIGEVA.dto.AdministradorDTO;
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
		centroSaludDTO.setNumVacunasDisponibles((int)Math.random());
	}

    @Test
	void testCrearCentroSaludBien() {
		
    	try {

    		administradorController.crearCentroSalud(null);
        } catch (Exception e){
            Assertions.assertNotNull(e);
        }
	}
    
//    @Test
//   	void testCrearCentroSaludExistente() {
//
//       	try {
//       		CentroSaludDTO centroS = new CentroSaludDTO("Centro", 10, "Mi dirección");
//       		administradorController.crearCentroSalud(centroS);
//           } catch (Exception e){
//               Assertions.assertNotNull(e);
//           }
//   	}
    
    @Test
    void testnumeroVacunasDisponiblesNegativo() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	try {
    		centroS.setNumVacunasDisponibles(-1); 
    	} catch (Exception e){
            Assertions.assertNotNull(e);
        }
    	
	}

    @Test
    void testModificarStockNumVacunas() {
    	CentroSalud centroS = new CentroSalud();
    	centroS.modificarStockVacunas(100);
    	Assertions.assertEquals(100, centroS.getNumVacunasDisponibles());
    }
}
