package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class listarCentrosSaludTest {

    @Autowired
    private AdministradorController administradorController;

    @Test
    public void getTodosCentros(){
        assertNotNull(administradorController.listarCentros());
    }
}
