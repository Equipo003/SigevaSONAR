package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModificarNumVacunasTest {

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
    static void crearCentroSalud() {
        centroSaludDTO = new CentroSaludDTO();
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        centroSaludDTO.setDireccion(UUID.randomUUID().toString());
        centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
    }

    @Test
    public void modificarNumeroVacunas() {
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);

        int vacunasToAdd = (int) (Math.random() * 1000);
        int newNumVacunas = centroSaludDTO.getNumVacunasDisponibles() + vacunasToAdd;

        administradorController.modificarNumeroVacunasDisponibles(requestMockAdmin,centroSaludDTO.getId(), vacunasToAdd);

        assertEquals(newNumVacunas, administradorController.getCentroById(centroSaludDTO.getId()).getNumVacunasDisponibles());

        administradorController.eliminarCentro(centroSaludDTO.getId());
    }
    
    @Test
    public void modificarNumeroVacunasPorPaciente() {
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);

        int vacunasToAdd = (int) (Math.random() * 1000);
        int newNumVacunas = centroSaludDTO.getNumVacunasDisponibles() + vacunasToAdd;
        try {
        	administradorController.modificarNumeroVacunasDisponibles(requestMockPa,centroSaludDTO.getId(), vacunasToAdd);
        }catch(Exception e) {
        	assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
        }
       

        administradorController.eliminarCentro(centroSaludDTO.getId());
    }
    
    @Test
    public void modificarNumeroVacunasPorSanitario() {
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        administradorController.crearCentroSalud(requestMockAdmin,centroSaludDTO);

        int vacunasToAdd = (int) (Math.random() * 1000);
        int newNumVacunas = centroSaludDTO.getNumVacunasDisponibles() + vacunasToAdd;
        try {
        	administradorController.modificarNumeroVacunasDisponibles(requestMockSan,centroSaludDTO.getId(), vacunasToAdd);
        }catch(Exception e) {
        	assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
        }
       

        administradorController.eliminarCentro(centroSaludDTO.getId());
    }
}
