package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.UsuarioController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mongodb.assertions.Assertions.assertNotNull;

@SpringBootTest
public class listarRolesTest {
    @Autowired
    private UsuarioController usuarioController;

    @Test
    public void getTodosUsuarios(){
        assertNotNull(usuarioController.listarRoles());
    }
}
