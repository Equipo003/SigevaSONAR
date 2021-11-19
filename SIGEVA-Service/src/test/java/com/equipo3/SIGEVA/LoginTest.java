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
import org.springframework.mock.web.MockHttpServletRequest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;
import com.equipo3.SIGEVA.dto.UsuarioLoginDTO;

@SpringBootTest
class LoginTest {
	public static PacienteDTO pacienteDTO;
	public static AdministradorDTO adminDTO;
	public static AdministradorDTO adminDTO2;
	public static SanitarioDTO sanitarioDTO;
	public static CentroSaludDTO csDto;
	public static TokenDTO tokenDto;
	public static MockHttpServletRequest requestMock;
	
    @Autowired
    private AdministradorController administradorController;
    @Autowired
    private CitaController citaController;
    
    @BeforeAll
	static void creacionRequest() {
    	requestMock = new MockHttpServletRequest();
    	//request.addParameter("token", tokenDto.getRol());
	}
    
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
        	administradorController.crearUsuarioAdministradorSinRestricciones(adminDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin,requestMock));
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
        	administradorController.crearUsuarioPacienteSinRestricciones(pacienteDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(pacienteDTO.getUsername());
        	usuarioLogin.setHashPassword(pacienteDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin,requestMock));
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
        	administradorController.crearUsuarioSanitarioSinRestricciones(sanitarioDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(sanitarioDTO.getUsername());
        	usuarioLogin.setHashPassword(sanitarioDTO.getHashPassword());
        	assertNotNull(administradorController.login(usuarioLogin,requestMock));
        	administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarCentro(csDto.getId());
    	}
    	
    }
    
    @Test
    void loginSanitarioMalTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
    		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
    		sanitarioDTO.setCentroSalud(csDto);
        	administradorController.crearUsuarioSanitarioSinRestricciones(sanitarioDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(sanitarioDTO.getUsername());
        	usuarioLogin.setHashPassword("falla");
        	administradorController.login(usuarioLogin,requestMock);
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarCentro(csDto.getId());
    	}
    }
 
    
    @Test
    void loginPacientePedirCitaTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
    		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
        	administradorController.crearUsuarioPacienteSinRestricciones(pacienteDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(pacienteDTO.getUsername());
        	usuarioLogin.setHashPassword(pacienteDTO.getHashPassword());
        	tokenDto= administradorController.login(usuarioLogin,requestMock);
        	assertNotNull(citaController.buscarYAsignarCitas(requestMock,pacienteDTO.getIdUsuario()));
        	administradorController.eliminarUsuario(pacienteDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(pacienteDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}
    }
    
    @Test
    void loginAdminPedirCitaTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
        	adminDTO.setRol(administradorController.getRolByNombre("Administrador"));
        	administradorController.crearUsuarioAdministradorSinRestricciones(adminDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	tokenDto= administradorController.login(usuarioLogin,requestMock);
        	System.out.println(tokenDto.getRol());
        	citaController.buscarYAsignarCitas(requestMock,adminDTO.getIdUsuario());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}
    }
    
    @Test
    void loginAdminCrearUsuarioAdminTest(){
    	try {
    		administradorController.crearCentroSalud(csDto);
        	adminDTO.setRol(administradorController.getRolByNombre("Administrador"));
        	administradorController.crearUsuarioAdministradorSinRestricciones(adminDTO);
        	
        	adminDTO2 = new AdministradorDTO();
            adminDTO2.setCentroSalud(csDto);
            adminDTO2.setUsername("pruebaLoginAdmin");
            adminDTO2.setNombre("Maco");
            adminDTO2.setApellidos("Molo");
            adminDTO2.setRol(new RolDTO());
            adminDTO2.setDni("06145111A");
            adminDTO2.setCorreo("ma@gmail.com");
            adminDTO2.setFechaNacimiento(new Date());
            adminDTO2.setHashPassword("pass");
        	
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	tokenDto= administradorController.login(usuarioLogin,requestMock);
        	administradorController.crearUsuarioAdministrador(requestMock,adminDTO2);
        	assertNotNull(administradorController.conseguirUsuarioAdministrador(adminDTO2));
        	administradorController.eliminarUsuario(adminDTO.getUsername());
        	administradorController.eliminarUsuario(adminDTO2.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarUsuario(adminDTO2.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    		
    	}
    }
    
    @Test
    void loginAdminCrearUsuarioPacienteTest(){
    	try {
        	
        	administradorController.crearCentroSalud(csDto);
    		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
    		adminDTO.setRol(administradorController.getRolByNombre("Administrador"));
    		administradorController.crearUsuarioAdministradorSinRestricciones(adminDTO);
        	
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	tokenDto= administradorController.login(usuarioLogin,requestMock);
        	administradorController.crearUsuarioPaciente(requestMock,pacienteDTO);
        	assertNotNull(administradorController.conseguirUsuarioPaciente(pacienteDTO));
        	administradorController.eliminarUsuario(pacienteDTO.getUsername());
        	administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(pacienteDTO.getUsername());
    		administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    		
    	}
    }
   
    @Test
    void loginAdminCrearUsuarioSanitarioTest(){
    	try {
        	
        	administradorController.crearCentroSalud(csDto);
    		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
    		sanitarioDTO.setCentroSalud(csDto);
    		adminDTO.setRol(administradorController.getRolByNombre("Administrador"));
    		administradorController.crearUsuarioAdministradorSinRestricciones(adminDTO);
        	
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	tokenDto= administradorController.login(usuarioLogin,requestMock);
        	administradorController.crearUsuarioSanitario(requestMock,sanitarioDTO);
        	assertNotNull(administradorController.conseguirUsuarioSanitario(sanitarioDTO));
        	administradorController.eliminarUsuario(sanitarioDTO.getUsername());
        	administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
    		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
    		administradorController.eliminarUsuario(adminDTO.getUsername());
    		administradorController.eliminarCentro(csDto.getId());
    		
    	}
    }
    
   
   
}