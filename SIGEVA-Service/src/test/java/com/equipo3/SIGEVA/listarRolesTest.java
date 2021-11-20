package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.TokenDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.mongodb.assertions.Assertions.assertNotNull;

@SpringBootTest
public class listarRolesTest {
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
    public void getTodosUsuarios(){
        assertNotNull(administradorController.listarRoles(requestMockAdmin));
    }
    @Test
    public void getTodosUsuariosPaciente(){
    	try {
    		administradorController.listarRoles(requestMockPa);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
    
    @Test
    public void getTodosUsuariosSanitario(){
    	try {
    		administradorController.listarRoles(requestMockSan);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
}
