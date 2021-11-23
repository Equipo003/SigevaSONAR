package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.utils.ParaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class EliminarUsuarioTest {

    public static CentroSaludDTO centroSaludDTO;
    public static CitaDTO citaDTO;
    public static CupoDTO cupoDTO;
    public static PacienteDTO pacienteDTO;
    public static SanitarioDTO sanitarioDTO;
    public static AdministradorDTO administradorDTO;

    @Autowired
    private AdministradorController administradorController;

    @Autowired
    private CitaController citaController;

    @Autowired
    private CupoController cupoController;
    
    @Autowired
    private ParaTest paraTest;

    @Autowired
    CupoDao cupoDao;

    @Autowired
    CitaDao citaDao;

    @BeforeAll
    static void setUpCita() {
        centroSaludDTO = new CentroSaludDTO();
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

        cupoDTO = new CupoDTO();
        cupoDTO.setCentroSalud(centroSaludDTO);

        citaDTO = new CitaDTO();
        citaDTO.setCupo(cupoDTO);
        citaDTO.setPaciente(pacienteDTO);
        cupoDTO.setCentroSalud(centroSaludDTO);
    }

    @BeforeAll
    static void setUpPaciente(){
        pacienteDTO = new PacienteDTO();
        pacienteDTO.setCentroSalud(centroSaludDTO);
        pacienteDTO.setUsername(UUID.randomUUID().toString());
    }
    @BeforeAll
    static void setUpSanitario(){
        sanitarioDTO = new SanitarioDTO();
        sanitarioDTO.setCentroSalud(centroSaludDTO);
        sanitarioDTO.setUsername(UUID.randomUUID().toString());
    }
    @BeforeAll
    static void setUpAdministrador(){
        administradorDTO = new AdministradorDTO();
        administradorDTO.setCentroSalud(centroSaludDTO);
        administradorDTO.setUsername(UUID.randomUUID().toString());
    }

    @Test
    public void eliminarUsuarioSanitario() {
        try {
            sanitarioDTO.setRol(administradorController.getRolByNombre("Sanitario"));

            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearUsuarioSanitario(sanitarioDTO);
            
            
            administradorController.deleteUsuarioById(sanitarioDTO.getIdUsuario());
        	
        	Assertions.assertNull(paraTest.getUsuarioById(sanitarioDTO.getIdUsuario()));
        	
            administradorController.eliminarUsuario(sanitarioDTO.getUsername());
            administradorController.eliminarCentro(centroSaludDTO.getId());
            
        }
        catch (Exception e) {
        
        }
    }

    @Test
    public void eliminarUsuarioAdministrador() {
        try {
            administradorDTO.setRol(administradorController.getRolByNombre("Administrador"));
            administradorDTO.setCentroSalud(centroSaludDTO);
            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearUsuarioAdministrador(administradorDTO);
            
            
            administradorController.deleteUsuarioById(administradorDTO.getIdUsuario());	
            
        }
        catch (Exception e) {
            Assertions.assertNotNull(e);

            administradorController.eliminarUsuario(administradorDTO.getUsername());
            administradorController.eliminarCentro(centroSaludDTO.getId());
        }
    }
    
    @Test
    public void eliminarUsuarioPacienteSinNada() {
        try {
            pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(0);
            pacienteDTO.setCentroSalud(centroSaludDTO);

            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearUsuarioPaciente(pacienteDTO);
            
            
            administradorController.deleteUsuarioById(pacienteDTO.getIdUsuario());
        	
        	Assertions.assertNull(paraTest.getUsuarioById(pacienteDTO.getIdUsuario()));
        	
            administradorController.eliminarUsuario(pacienteDTO.getUsername());
            administradorController.eliminarCentro(centroSaludDTO.getId());
            
        }
        catch (Exception e) {

        }
    }
    
    @Test
    public void eliminarUsuarioPacienteConDosis() {
        try {
            pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(1);
            pacienteDTO.setCentroSalud(centroSaludDTO);

            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearUsuarioPaciente(pacienteDTO);
                   
            administradorController.deleteUsuarioById(pacienteDTO.getIdUsuario()); 	


            
        }
        catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),"401 UNAUTHORIZED \"No puedes eliminar el usuario porque ya tiene aplicada 1 o más dosis\"");

            administradorController.eliminarUsuario(pacienteDTO.getUsername());
            administradorController.eliminarCentro(centroSaludDTO.getId());
        }
    }
    
    @Test
    public void eliminarUsuarioPacienteConCitasFuturas() {
        try {
        	centroSaludDTO.setNumVacunasDisponibles(55);
            pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(0);
            pacienteDTO.setCentroSalud(centroSaludDTO);
            cupoDTO.setFechaYHoraInicio(new Date(121, 11, 23));
            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearUsuarioPaciente(pacienteDTO);
            cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
            citaDao.save(WrapperDTOtoModel.citaDTOToCita(citaDTO));
                   
            administradorController.deleteUsuarioById(pacienteDTO.getIdUsuario());
            
            Assertions.assertNull(paraTest.getUsuarioById(pacienteDTO.getIdUsuario()));
        	
        	citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
            cupoController.eliminarCupo(cupoDTO.getUuidCupo());
            administradorController.eliminarUsuario(pacienteDTO.getUsername());
            administradorController.eliminarCentro(centroSaludDTO.getId());
            
        }
        catch (Exception e) {
           
        }
    }
    

}
