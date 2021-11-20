package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.model.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;

@SpringBootTest
public class listarUsuariosTest {

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
        assertNotNull(administradorController.getUsuarioByRol(requestMockAdmin,"Todos"));
    }

    @Test void getUsuariosAdministradores(){
        RolDTO rolDTO = administradorController.getRolByNombre("Administrador");
        assertNotNull(administradorController.getUsuarioByRol(requestMockAdmin,rolDTO.getId()));
    }

    @Test void getUsuariosSanitarios(){
        RolDTO rolDTO = administradorController.getRolByNombre("Sanitario");
        assertNotNull(administradorController.getUsuarioByRol(requestMockAdmin,rolDTO.getId()));
    }

    @Test void getUsuariosPacientes(){
        RolDTO rolDTO = administradorController.getRolByNombre("Paciente");
        assertNotNull(administradorController.getUsuarioByRol(requestMockAdmin,rolDTO.getId()));
    }

    @Test void getPacientes(){
    	PacienteDTO paciente = new PacienteDTO();
    	paciente.setNombre("Test getPacientes");
    	paciente.setUsername("Test getPacientes");
    	paciente.setRol(administradorController.getRolByNombre("Paciente"));

    	CentroSaludDTO csDto = new CentroSaludDTO();
		csDto.setDireccion("test getPacientes direccion");
		csDto.setNombreCentro("CentroTest getPaciente");
		csDto.setNumVacunasDisponibles(80);

		administradorController.crearCentroSalud(requestMockAdmin,csDto);

		paciente.setCentroSalud(csDto);

    	administradorController.crearUsuarioPaciente(requestMockAdmin,paciente);
        for (PacienteDTO pacienteDTO : administradorController.getPacientes()){
            assertNotNull(pacienteDTO);
            administradorController.eliminarUsuario(paciente.getUsername());
        }
        administradorController.borrarCentroSalud(requestMockAdmin,csDto);


    }
    
    @Test
    public void getTodosUsuariosPaciente(){
    	try {
    		administradorController.getUsuarioByRol(requestMockPa,"Todos");
    	}catch(Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
    
    @Test
    public void getTodosUsuariosSanitario(){
    	try {
    		administradorController.getUsuarioByRol(requestMockSan,"Todos");
    	}catch(Exception e) {
    		assertEquals(e.getMessage(), "409 CONFLICT \"No tiene permisos para realizar esta acción.\"");
    	}
    }
}
