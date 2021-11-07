package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
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
        Rol rol = administradorController.getRolByNombre("Administrador");
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }

    @Test void getUsuariosSanitarios(){
        Rol rol = administradorController.getRolByNombre("Sanitario");
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }

    @Test void getUsuariosPacientes(){
        Rol rol = administradorController.getRolByNombre("Paciente");
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }

    @Test void getPacientes(){
        Rol rol = administradorController.getRolByNombre("Paciente");
        for (PacienteDTO pacienteDTO : administradorController.getPacientes(rol.getId())){
            System.out.println(pacienteDTO.toString());
        }
        assertNotNull(administradorController.getPacientes(rol.getId()));
    }
}
