package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.model.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class listarUsuariosTest {

    @Autowired
    private AdministradorController administradorController;

    @Autowired
    private RolDao rolDao;

    @Test
    public void getTodosUsuarios(){
        assertNotNull(administradorController.getUsuarioByRol("Todos"));
    }

    @Test void getUsuariosAdministradores(){
        Rol rol = rolDao.findAllByNombre("Administrador").get();
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }

    @Test void getUsuariosSanitarios(){
        Rol rol = rolDao.findAllByNombre("Sanitario").get();
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }

    @Test void getUsuariosPacientes(){
        Rol rol = rolDao.findAllByNombre("Paciente").get();
        assertNotNull(administradorController.getUsuarioByRol(rol.getId()));
    }
}
