package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.TokenDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;

@SpringBootTest
public class listarCentrosSaludTest {

    @Autowired
    private AdministradorController administradorController;
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
    @Test
    public void getTodosCentros(){
        assertNotNull(administradorController.listarCentros(requestMockAdmin));
    }
    
    @Test
    public void getTodosCentrosPaciente(){
    	try {
    		administradorController.listarCentros(requestMockPa);
    	}catch(Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
    
    @Test
    public void getTodosCentrosSanitario(){
    	try {
    		administradorController.listarCentros(requestMockSan);
    	}catch(Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
}
