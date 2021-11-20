package com.equipo3.SIGEVA;

import java.util.UUID;

import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.model.Vacuna;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CrearCentroSaludTest {

	@Autowired
	private AdministradorController administradorController;

	static CentroSaludDTO centroSaludDTO;
	public static MockHttpServletRequest requestMockAdmin;
	public static MockHttpServletRequest requestMockSan;
	public static MockHttpServletRequest requestMockPa;
	static TokenDTO tokenDTOAdmin;
	static TokenDTO tokenDTOSan;
	static TokenDTO tokenDTOPa;

	@BeforeAll
	static void creacionRequest() {
		requestMockAdmin = new MockHttpServletRequest();
		tokenDTOAdmin = new TokenDTO("adm", "Administrador");
		requestMockAdmin.getSession().setAttribute("token", tokenDTOAdmin);

		requestMockSan = new MockHttpServletRequest();
		tokenDTOSan = new TokenDTO("san", "Sanitario");
		requestMockSan.getSession().setAttribute("token", tokenDTOSan);
		
		requestMockPa = new MockHttpServletRequest();
		tokenDTOPa = new TokenDTO("pa", "Paciente");
		requestMockPa.getSession().setAttribute("token", tokenDTOPa);
	}

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
		administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		assertEquals(administradorController.getCentroById(centroSaludDTO.getId()).toString(), centroSaludDTO.toString());
		administradorController.eliminarCentro(centroSaludDTO.getId());
	}
    
    @Test
   	void testCrearCentroSaludPaciente() {
    	try {
    		centroSaludDTO.setId(UUID.randomUUID().toString());
       		administradorController.crearCentroSalud(requestMockPa,centroSaludDTO);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    		administradorController.eliminarCentro(centroSaludDTO.getId());
    	}
   	}
    
    @Test
   	void testCrearCentroSaludSanitario() {
    	try {
    		centroSaludDTO.setId(UUID.randomUUID().toString());
       		administradorController.crearCentroSalud(requestMockSan,centroSaludDTO);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    		administradorController.eliminarCentro(centroSaludDTO.getId());
    	}
   	}
    
    @Test
   	void testCrearCentroSaludExistente() {
       	try {
			String uuid = UUID.randomUUID().toString();
			centroSaludDTO.setNombreCentro(uuid);
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
			administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);
		   } catch (Exception e) {
				administradorController.eliminarCentro(centroSaludDTO.getId());
				Assertions.assertNotNull(e);
		   }
   	}
    

}
