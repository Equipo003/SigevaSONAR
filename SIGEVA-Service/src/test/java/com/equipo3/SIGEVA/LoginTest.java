package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.PrintStream;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.UsuarioLoginDTO;

@SpringBootTest
class LoginTest {
	public static PacienteDTO pacienteDTO;
	public static AdministradorDTO adminDTO;
	public static SanitarioDTO sanitarioDTO;
	public static CentroSaludDTO csDto;
	
    @Autowired
    private AdministradorController administradorController;
    
    @BeforeAll
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test login centro");
		csDto.setNombreCentro("LoginTest");
		csDto.setNumVacunasDisponibles(40);
	}
    
    @BeforeAll
    static void crearPaciente(){
        pacienteDTO = new PacienteDTO();
        pacienteDTO.setCentroSalud(csDto);
        pacienteDTO.setUsername("pruebaLogin");
        pacienteDTO.setNombre("Rodolfo");
        pacienteDTO.setApellidos("Martínez Pocholo");
        pacienteDTO.setRol(new RolDTO());
        pacienteDTO.setDni("06222222A");
        pacienteDTO.setNumDosisAplicadas(0);
        pacienteDTO.setCorreo("rodolfo@gmail.com");
        pacienteDTO.setFechaNacimiento(new Date());
        pacienteDTO.setHashPassword("pass");
    }
    
    @BeforeAll
    static void crearAdmin(){
        adminDTO = new AdministradorDTO();
        adminDTO.setCentroSalud(csDto);
        adminDTO.setUsername("pruebaLogin2");
        adminDTO.setNombre("Maca");
        adminDTO.setApellidos("Mora Palo");
        adminDTO.setRol(new RolDTO());
        adminDTO.setDni("06111111A");
        adminDTO.setCorreo("maca@gmail.com");
        adminDTO.setFechaNacimiento(new Date());
        adminDTO.setHashPassword("pass");
    }
    
    @BeforeAll
    static void crearSanitario(){
        sanitarioDTO = new SanitarioDTO();
        sanitarioDTO.setCentroSalud(csDto);
        sanitarioDTO.setUsername("pruebaLogin3");
        sanitarioDTO.setNombre("Moncho");
        sanitarioDTO.setApellidos("Paul Mor");
        sanitarioDTO.setRol(new RolDTO());
        sanitarioDTO.setDni("06000000A");
        sanitarioDTO.setCorreo("moncho@gmail.com");
        sanitarioDTO.setFechaNacimiento(new Date());
        sanitarioDTO.setHashPassword("pass");
    }

    @Test
    void loginAdminTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
        	adminDTO.setRol(administradorController.getRolByNombre("Administrador"));
        	administradorController.crearUsuarioAdministrador(adminDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin));
        	administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}
    
    	
    	
    }
    @Test
    void loginPacienteTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
    		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
        	administradorController.crearUsuarioPaciente(pacienteDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(pacienteDTO.getUsername());
        	usuarioLogin.setHashPassword(pacienteDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin));
        	administradorController.eliminarUsuario(pacienteDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e) {
    		assertNotNull(e);
    		administradorController.eliminarUsuario(pacienteDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}
    }
    
    @Test
    void loginSanitarioTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
    		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
    		sanitarioDTO.setCentroSalud(csDto);
        	administradorController.crearUsuarioSanitario(sanitarioDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(sanitarioDTO.getUsername());
        	usuarioLogin.setHashPassword(sanitarioDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin));
        	administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarCentro(csDto.getId());
    	}
    	
    }
   
}