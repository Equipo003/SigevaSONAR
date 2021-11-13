package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModificarNumVacunasTest {

    @Autowired
    private AdministradorController administradorController;

    static CentroSaludDTO centroSaludDTO;

    @BeforeAll
    static void crearCentroSalud() {
        centroSaludDTO = new CentroSaludDTO();
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        centroSaludDTO.setDireccion(UUID.randomUUID().toString());
        centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
    }

    @Test
    public void modificarNumeroVacunas() {
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
        administradorController.crearCentroSalud(centroSaludDTO);

        int vacunasToAdd = (int) (Math.random() * 1000);
        int newNumVacunas = centroSaludDTO.getNumVacunasDisponibles() + vacunasToAdd;

        administradorController.modificarNumeroVacunasDisponibles(centroSaludDTO.getId(), vacunasToAdd);

        assertEquals(newNumVacunas, administradorController.getCentroById(centroSaludDTO.getId()).getNumVacunasDisponibles());

        administradorController.eliminarCentro(centroSaludDTO.getId());
    }
}
