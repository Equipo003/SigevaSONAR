package com.equipo3.SIGEVA.model;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
