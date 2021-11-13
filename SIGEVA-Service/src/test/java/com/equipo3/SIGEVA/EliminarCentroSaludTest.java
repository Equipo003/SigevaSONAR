package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EliminarCentroSaludTest {

	@Autowired
	private AdministradorController administradorController;

	static CentroSaludDTO centroSaludDTO;
	
	static UsuarioDTO usuarioDTO;
	
	static AdministradorDTO administradorDTO;
	
	static SanitarioDTO sanitarioDTO;
	
	static PacienteDTO pacienteDTO;
	
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;


	@Test
	void eliminacionCorrectaTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		String idCentro= centroSaludDTO.getId();
		administradorController.crearCentroSalud(centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNull(e);
		}
	}

	@Test
	void eliminacionCentroSaludDuplicadoTest() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			assertNotNull(e);
		}
	}

	@Test
	void eliminacionCentroSaludConPacienteTest() {
		Exception e2 =null;
		sanitarioDTO = new SanitarioDTO();
		pacienteDTO = new PacienteDTO();
		administradorDTO = new AdministradorDTO();
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		administradorController.crearCentroSalud(centroSaludDTO);
		sanitarioDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		administradorDTO.setUsername(UUID.randomUUID().toString());
		sanitarioDTO.setCentroSalud(centroSaludDTO);
		sanitarioDTO.setApellidos(UUID.randomUUID().toString());
		sanitarioDTO.setCorreo(UUID.randomUUID().toString());
		sanitarioDTO.setDni(UUID.randomUUID().toString());
		sanitarioDTO.setFechaNacimiento(new Date());
		sanitarioDTO.setIdUsuario(UUID.randomUUID().toString());
		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
		administradorDTO.setCentroSalud(centroSaludDTO);
		administradorDTO.setRol(administradorController.getRolByNombre("Administrador"));
		
//		wrapperDTOtoModel.administradorDTOtoAdministrador(administradorDTO);
//		wrapperDTOtoModel.pacienteDTOtoPaciente(pacienteDTO);
//		wrapperDTOtoModel.sanitarioDTOtoSanitario(sanitarioDTO);
		administradorController.crearUsuarioAdministrador(administradorDTO);
		administradorController.crearUsuarioPaciente(pacienteDTO);
		administradorController.crearUsuarioSanitario(sanitarioDTO);
		try {
			administradorController.borrarCentroSalud(centroSaludDTO);
		}catch(Exception e){
			//e2 = e;
			//e2 = new Exception();
//			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
//			administradorController.eliminarUsuario(pacienteDTO.getUsername());
//			administradorController.eliminarUsuario(administradorDTO.getUsername());
			assertNotNull(e);
			
		}
		//assertNotNull(e2);
		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
		administradorController.eliminarUsuario(pacienteDTO.getUsername());
		administradorController.eliminarUsuario(administradorDTO.getUsername());
	}

//	@Test
//	void insercionCorrectaSanitario() {
//		sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
//		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
//		administradorController.crearCentroSalud(centroSaludDTO);
//		sanitarioDTO.setCentroSalud((centroSaludDTO));
//
//		administradorController.crearUsuarioSanitario(sanitarioDTO);
//
//		assertEquals(administradorController.getUsuarioById(sanitarioDTO.getIdUsuario()).toString(), sanitarioDTO.toString());
//
//		administradorController.eliminarUsuario(sanitarioDTO.getUsername());
//		administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
//	}
//
//	@Test
//	void insercionSanitarioDuplicado(){
//		try{
//			String uuid = UUID.randomUUID().toString();
//			sanitarioDTO.setUsername(uuid);
//			sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));
//			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
//			administradorController.crearCentroSalud(centroSaludDTO);
//			sanitarioDTO.setCentroSalud((centroSaludDTO));
//
//			administradorController.crearUsuarioSanitario(sanitarioDTO);
//			administradorController.crearUsuarioSanitario(sanitarioDTO);
//		} catch (Exception e){
//			administradorController.eliminarUsuario(sanitarioDTO.getUsername());
//			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
//			assertNotNull(e);
//		}
//	}
//
//	@Test
//	void insercionCorrectaPaciente() {
//		pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
//		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
//		administradorController.crearCentroSalud(centroSaludDTO);
//		pacienteDTO.setCentroSalud((centroSaludDTO));
//
//		administradorController.crearUsuarioPaciente(pacienteDTO);
//
//		assertEquals(administradorController.getPaciente(pacienteDTO.getIdUsuario()).toString(), pacienteDTO.toString());
//
//		administradorController.eliminarUsuario(pacienteDTO.getUsername());
//		administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
//	}
//
//	@Test
//	void insercionPacienteDuplicado() {
//		try {
//			String uuid = UUID.randomUUID().toString();
//			pacienteDTO.setUsername(uuid);
//			pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
//			centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
//			administradorController.crearCentroSalud(centroSaludDTO);
//			pacienteDTO.setCentroSalud((centroSaludDTO));
//
//			administradorController.crearUsuarioPaciente(pacienteDTO);
//			administradorController.crearUsuarioPaciente(pacienteDTO);
//		} catch (Exception e){
//			administradorController.eliminarUsuario(pacienteDTO.getUsername());
//			administradorController.eliminarCentro(administradorDTO.getCentroSalud().getId());
//			assertNotNull(e);
//		}
//	}
}
