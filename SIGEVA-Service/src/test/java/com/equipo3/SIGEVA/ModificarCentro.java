package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;
@SpringBootTest
class ModificarCentro {
	@Autowired
    private static AdministradorController administradorController;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;
	
	static UsuarioDTO usuarioDto;
	static CentroSaludDTO csDto;
	
	
	@BeforeAll
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test modificación centro");
		csDto.setNombreCentro("CentroTest modificación");
		csDto.setNumVacunasDisponibles(80);
		
		administradorController.crearCentroSalud(csDto);
	}
	
	@BeforeAll
	static void creacionUsuario() {
		usuarioDto.setApellidos("modificación prueba");
		usuarioDto.setCentroSalud(csDto);
		usuarioDto.setRol(administradorController.getRolByNombre("Paciente"));
		usuarioDto.setCorreo("correo@gmail.com");
		
		administradorController.crearUsuarioPaciente((PacienteDTO) usuarioDto);
		
	}
	
	@Test
	void modificarCentroPaciente() {
		csDto.setNumVacunasDisponibles(80);
		administradorController.modificarCentroSalud(usuarioDto.getIdUsuario(), csDto);
	}
	
	@Test
	void modificarCentroSanitario() {
		csDto.setDireccion("Modofcación dirección");
		usuarioDto.setRol(administradorController.getRolByNombre("Sanitario"));
		administradorController.modificarCentroSalud(usuarioDto.getIdUsuario(), csDto);
	}
	
	@Test
	void modificarCentroNombre() {
		
	}
	
	@Test
	void modificarCentroCorrecto() {
		
	}

}
