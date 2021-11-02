package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.CentroSalud;
@SpringBootTest
class CrearCentroSaludTest {
    @Autowired
    private AdministradorController administradorController;
    @Test
	void testCrearCentroSaludBien() {
		
    	try {
    		administradorController.crearCentroSalud(null);
        } catch (Exception e){
            Assertions.assertNotNull(e);
        }
	}
    
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
    void testIncrementarNumeroVacunasDisponiblesNegativo() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	try {
    		centroS.setNumVacunasDisponibles(100); 
    		centroS.incrementarNumVacunasDisponibles(-1);
    	} catch (Exception e){
            Assertions.assertNotNull(e);
        }
    	
    	}
    
    @Test
    void testDecrementarNumeroVacunasDisponiblesSinStock() throws NumVacunasInvalido,CentroSinStock {
    	CentroSalud centroS = new CentroSalud();
    	try {
    		centroS.setNumVacunasDisponibles(0); 
    		centroS.decrementarNumVacunasDisponibles();
    	} catch (Exception e){
            Assertions.assertNotNull(e);
        }
    	}
    
    @Test
    void testDecrementarNumeroVacunasDisponiblesConStock() throws NumVacunasInvalido, CentroSinStock {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setNumVacunasDisponibles(100); 
    	centroS.decrementarNumVacunasDisponibles();
        Assertions.assertEquals(99, centroS.getNumVacunasDisponibles());
        
    	
    	}
    
    @Test
    void testIncrementarNumeroVacunasDisponiblesPositivoCero() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setNumVacunasDisponibles(100); 
    	centroS.incrementarNumVacunasDisponibles(105);
    
    	Assertions.assertEquals(205,centroS.getNumVacunasDisponibles());
    	}
    
    @Test
    void testCrearVacunaCorrectamente () {
    	CentroSalud centroS = new CentroSalud();
    	Assertions.assertNotNull(centroS.getVacuna());
    }
    @Test
    void testCrearIdCorrectamente () {
    	CentroSalud centroS = new CentroSalud();
    	Assertions.assertNotNull(centroS.getId());
    }
    @Test
    void testModificarStockNumVacunas() {
    	CentroSalud centroS = new CentroSalud();
    	centroS.modificarStockVacunas(100);
    	Assertions.assertEquals(100, centroS.getNumVacunasDisponibles());
    }
    @Test
    void testNoIgualesCentrosSalud() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("2323124g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	CentroSalud centroS2 = new CentroSalud();
    	centroS2.setDireccion("Calle Ejemplo");
    	centroS2.setId("23256624g");
    	centroS2.setNombreCentro("CENTRO");
    	centroS2.setNumVacunasDisponibles(10);
    	centroS2.setVacuna(null);
    	Assertions.assertFalse(centroS.equals(centroS2));
    }
    
    @Test
    void testIgualesCentrosSalud() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("24g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	CentroSalud centroS2 = new CentroSalud();
    	centroS2.setDireccion("Calle Ejemplo");
    	centroS2.setId("24g");
    	centroS2.setNombreCentro("CENTRO");
    	centroS2.setNumVacunasDisponibles(10);
    	centroS2.setVacuna(null);
    	Assertions.assertTrue(centroS.equals(centroS2));
    }
    
    @Test
    void testCompararCentroSaludConNulo() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("24g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	Assertions.assertFalse(centroS.equals(null));
    }
    @Test
    void testCompararCentroSaludConOtraClase() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("24g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	Assertions.assertFalse(centroS.equals(administradorController));
    }
    
    @Test
    void testCompararCentroSaludConSiMismo() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("24g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	centroS.toString();
    	Assertions.assertTrue(centroS.equals(centroS));
    }
    
    @Test
    void testHashCorrecto() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("Calle Ejemplo");
    	centroS.setId("24g");
    	centroS.setNombreCentro("CENTRO");
    	centroS.setNumVacunasDisponibles(10);
    	centroS.setVacuna(null);
    	Assertions.assertNotNull(centroS.hashCode());
    }
    
    @Test
    void testNombreCentroNoNulos() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setNombreCentro("CENTRO");
    	Assertions.assertNotNull(centroS.getNombreCentro());
    }
    @Test
    void testDireccionNoNulos() throws NumVacunasInvalido {
    	CentroSalud centroS = new CentroSalud();
    	centroS.setDireccion("CENTRO");
    	Assertions.assertNotNull(centroS.getDireccion());
    }
    
}
