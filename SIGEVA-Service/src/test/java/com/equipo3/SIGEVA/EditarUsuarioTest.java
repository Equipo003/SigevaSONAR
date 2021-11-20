package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class EditarUsuarioTest {

    public static CentroSaludDTO centroSaludDTO;
    public static CentroSaludDTO newCentroSaludDTO;
    public static CitaDTO citaDTO;
    public static CupoDTO cupoDTO;
    public static PacienteDTO pacienteDTO;

    @Autowired
    private AdministradorController administradorController;

    @Autowired
    private CitaController citaController;

    @Autowired
    private CupoController cupoController;

    @Autowired
    CupoDao cupoDao;

    @Autowired
    CitaDao citaDao;

    @BeforeAll
    static void setUpCita() {
        centroSaludDTO = new CentroSaludDTO();
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

        newCentroSaludDTO = new CentroSaludDTO();
        newCentroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

        cupoDTO = new CupoDTO();
        cupoDTO.setCentroSalud(centroSaludDTO);

        citaDTO = new CitaDTO();
        citaDTO.setPaciente(pacienteDTO);
        citaDTO.setCupo(cupoDTO);
        cupoDTO.setCentroSalud(centroSaludDTO);
    }

    @BeforeAll
    static void setUpPaciente(){
        pacienteDTO = new PacienteDTO();
        pacienteDTO.setCentroSalud(centroSaludDTO);
        pacienteDTO.setUsername(UUID.randomUUID().toString());
    }

    @Test
    public void usuarioSinDosisPuestaEdicionCorrecta() {
        try {
            pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(0);
            cupoDTO.setFechaYHoraInicio(new Date(121, 11, 23));

            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearCentroSalud(newCentroSaludDTO);
            pacienteDTO.setCentroSalud(newCentroSaludDTO);
            administradorController.crearUsuarioPaciente(pacienteDTO);
            System.out.println(cupoDao.toString());
            cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
            citaDao.save(WrapperDTOtoModel.citaDTOToCita(citaDTO));

            administradorController.editarUsuario(pacienteDTO);

            Assertions.assertEquals(administradorController.getPaciente(pacienteDTO.getIdUsuario()).toString(), pacienteDTO.toString());

            citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
            cupoController.eliminarCupo(cupoDTO.getUuidCupo());
            administradorController.eliminarUsuario(pacienteDTO.getUsername());
            administradorController.eliminarCentro(newCentroSaludDTO.getId());
            administradorController.eliminarCentro(centroSaludDTO.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void usuarioConDosisPuestaEdicionIncorrecta() {
        try {
            pacienteDTO.setRol(administradorController.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(1);
            cupoDTO.setFechaYHoraInicio(new Date(121, 1, 23));

            administradorController.crearCentroSalud(centroSaludDTO);
            administradorController.crearCentroSalud(newCentroSaludDTO);
            pacienteDTO.setCentroSalud(newCentroSaludDTO);
            administradorController.crearUsuarioPaciente(pacienteDTO);
            cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
            citaDao.save(WrapperDTOtoModel.citaDTOToCita(citaDTO));

            administradorController.editarUsuario(pacienteDTO);
        }
        catch (Exception e) {
            citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
            cupoController.eliminarCupo(cupoDTO.getUuidCupo());
            administradorController.eliminarUsuario(pacienteDTO.getUsername());
            administradorController.eliminarCentro(newCentroSaludDTO.getId());
            administradorController.eliminarCentro(centroSaludDTO.getId());
            Assertions.assertEquals(e.getMessage(), "401 UNAUTHORIZED \"No puedes modificar el centro de un usuario que ya ha aplicado una dosis\"");
        }
    }
}
