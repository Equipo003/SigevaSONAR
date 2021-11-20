package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CrearRolesYVacunasTest {

    @Autowired
    private AdministradorController administradorController;

    static VacunaDTO vacunaDTO;
    static RolDTO rolDTOAdministrador;
    static RolDTO rolDTOPaciente;
    static RolDTO rolDTOSanitario;
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
    public static void crearVacuna() {
        vacunaDTO = new VacunaDTO();
        vacunaDTO.setNumDosis(2);
        vacunaDTO.setNombre("Pfizer");
        vacunaDTO.setDiasEntreDosis(21);
    }

    @BeforeAll
    public static void crearRol() {
        rolDTOAdministrador = new RolDTO();
        rolDTOPaciente = new RolDTO();
        rolDTOSanitario = new RolDTO();
    }

    @Test
    public void addVacuna(){
        administradorController.addVacuna(vacunaDTO);
        administradorController.getVacunaById(vacunaDTO.getId());
        assertEquals(vacunaDTO.toString(), administradorController.getVacunaById(vacunaDTO.getId()).toString());
        administradorController.eliminarVacuna(vacunaDTO.getId());
    }

    @Test void getVacunaById(){
        vacunaDTO.setId(UUID.randomUUID().toString());
        administradorController.addVacuna(vacunaDTO);
        assertNotNull(administradorController.getVacunaById(vacunaDTO.getId()));
        administradorController.eliminarVacuna(vacunaDTO.getId());
    }

    @Test void addRol(){
        rolDTOAdministrador.setNombre("Administrador");
        rolDTOPaciente.setNombre("Paciente");
        rolDTOSanitario.setNombre("Sanitario");

        administradorController.crearRol(rolDTOAdministrador);
        administradorController.crearRol(rolDTOPaciente);
        administradorController.crearRol(rolDTOSanitario);

        administradorController.eliminarRol(rolDTOAdministrador.getId());
        administradorController.eliminarRol(rolDTOPaciente.getId());
        administradorController.eliminarRol(rolDTOSanitario.getId());
    }
}
