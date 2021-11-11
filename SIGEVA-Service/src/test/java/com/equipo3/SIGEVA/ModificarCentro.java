package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;

class ModificarCentro {
	@Autowired
    private AdministradorController administradorController;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;
	
	@Autowired 
	CentroSaludDao centrosaludDao;
	@Autowired
	UsuarioDao usuariodao;
	@Autowired 
	VacunaDao vacuna;
	
	
	static UsuarioDTO usuarioDto;
	static Usuario usuario;
	static CentroSaludDTO csDto;
	static CentroSalud cs;
	
	
	@BeforeAll
	static void inicializacion() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test modificación centro");
		csDto.setNombreCentro("CentroTest modificación");
		csDto.setNumVacunasDisponibles(80);
		
		
	}
	
	
	@Test
	void modificarCentroPaciente() {
	
	}
	
	@Test
	void modificarCentroSanitario() {
		
	}
	
	@Test
	void modificarCentroNombre() {
		
	}
	
	@Test
	void modificarCentroCorrecto() {
		
	}

}
