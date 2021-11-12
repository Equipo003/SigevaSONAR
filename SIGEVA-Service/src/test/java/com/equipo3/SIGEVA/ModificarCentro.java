package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.DeniedAccessException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;
@SpringBootTest
class ModificarCentro {
	@Autowired
    private AdministradorController administradorController = new AdministradorController();
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;
	
	static CentroSaludDTO csDto;
	
	@BeforeAll
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test modificación centro");
		csDto.setNombreCentro("CentroTest modificación");
		csDto.setNumVacunasDisponibles(80);
	}
	
	@Test
	void modificarCentroPaciente() {
		administradorController.crearCentroSalud(csDto);
		
		PacienteDTO paciente = new PacienteDTO();
		paciente.setRol(administradorController.getRolByNombre("Paciente"));
		paciente.setNombre("Paciente test modificar Paciente");
		paciente.setCentroSalud(csDto);

		administradorController.crearUsuarioPaciente(paciente);
		
		csDto.setNumVacunasDisponibles(80);
		csDto.setDireccion("Modificación centro test paciente");
		
		try {
			administradorController.modificarCentroSalud(paciente.getIdUsuario(), csDto);
		}catch(Exception e) {
			assertEquals("401 UNAUTHORIZED \"No tienes los permisos necesarios, para realizar la operación\"", e.getMessage());
			administradorController.borrarCentroSalud(csDto);
			administradorController.eliminarUsuario(paciente.getUsername());
		}
		
		
	}
	
	@Test
	void modificarCentroSanitario() {
		administradorController.crearCentroSalud(csDto);
		
		SanitarioDTO sanitario = new SanitarioDTO();
		sanitario.setRol(administradorController.getRolByNombre("Sanitario"));
		sanitario.setCentroSalud(csDto);
		sanitario.setUsername("Test sanitario");
		
		administradorController.crearUsuarioSanitario(sanitario);
		
		csDto.setDireccion("Modificación dirección test sanitario");
		
		try {
			administradorController.modificarCentroSalud(sanitario.getIdUsuario(), csDto);
			
		}catch(Exception e) {
			assertEquals("401 UNAUTHORIZED \"No tienes los permisos necesarios, para realizar la operación\"", e.getMessage());
			administradorController.borrarCentroSalud(csDto);
			administradorController.eliminarUsuario(sanitario.getUsername());
		}
		
	}
	
	@Test
	void modificarCentroNombreAdministrador() {
		administradorController.crearCentroSalud(csDto);
		
		AdministradorDTO administrador = new AdministradorDTO();
		administrador.setCentroSalud(csDto);
		administrador.setRol(administradorController.getRolByNombre("Administrador"));
		administrador.setUsername("Test modificación centro  admin");
		
		administradorController.crearUsuarioAdministrador(administrador);
		
		csDto.setNombreCentro("Hospital test");
		
		try {
			administradorController.modificarCentroSalud(administrador.getIdUsuario(), csDto);
		}catch(Exception e) {
			assertEquals("401 UNAUTHORIZED \"No tienes los permisos necesarios, para realizar la operación\"", e.getMessage());
			administradorController.borrarCentroSalud(csDto);
			administradorController.eliminarUsuario(administrador.getUsername());
		}
		
	}
	
	@Test
	void modificarCentroCorrecto() {
		boolean throwe = false;
		
		administradorController.crearCentroSalud(csDto);
		
		AdministradorDTO administrador = new AdministradorDTO();
		administrador.setCentroSalud(csDto);
		administrador.setRol(administradorController.getRolByNombre("Administrador"));
		administrador.setUsername("Test modificación centro  admin");
		
		administradorController.crearUsuarioAdministrador(administrador);
		
		csDto.setDireccion("Modificación dirección test centro Administrador");
		csDto.setNumVacunasDisponibles(30);
		
		try {
			administradorController.modificarCentroSalud(administrador.getIdUsuario(), csDto);
			administradorController.borrarCentroSalud(csDto);
			administradorController.eliminarUsuario(administrador.getUsername());
		}catch(Exception e) {
			throwe = true;
		}
		
		assertFalse(throwe);
		
	}


}
