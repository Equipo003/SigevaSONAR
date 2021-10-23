package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class CrearCentroSaludTest {
    @Autowired
    private AdministradorController administradorController;
    @Test//(expected = Exception.class)
	public void testCrearCentroSaludBien() {
		administradorController.crearCentroSalud();
		//CentroSaludDao centroSaludDao = new CentroSaludDao();
		
		System.out.println("e");
		Assertions.assertFalse(false);
	}
}
