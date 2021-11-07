package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.model.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class listarUsuariosTest {

    @Autowired
    private AdministradorController administradorController;

    @Test
    public void getTodosUsuarios(){
        assertNotNull(administradorController.getUsuarioByRol("Todos"));
    }

    @Test void getUsuariosAdministradores(){
        RolDTO rolDTO = administradorController.getRolByNombre("Administrador");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosSanitarios(){
        RolDTO rolDTO = administradorController.getRolByNombre("Sanitario");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosPacientes(){
        RolDTO rolDTO = administradorController.getRolByNombre("Paciente");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getPacientes(){
        RolDTO rolDTO = administradorController.getRolByNombre("Paciente");
        for (PacienteDTO pacienteDTO : administradorController.getPacientes(rolDTO.getId())){
            System.out.println(pacienteDTO.toString());
        }
        assertNotNull(administradorController.getPacientes(rolDTO.getId()));
    }
}
