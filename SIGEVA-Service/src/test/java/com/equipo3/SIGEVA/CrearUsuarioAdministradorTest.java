package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CrearUsuarioAdministradorTest {

    @Autowired
    private AdministradorController administradorController;

    @Test
    public void insercionCorrectaAdministrador() {
        administradorController.registrarUsuario();
    }
}
