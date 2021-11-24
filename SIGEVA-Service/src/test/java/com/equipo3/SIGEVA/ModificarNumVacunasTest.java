package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModificarNumVacunasTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private CentroController centroController;

    @Autowired
    private Utilidades utilidades;

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
        centroController.crearCentroSalud(centroSaludDTO);

        int vacunasToAdd = (int) (Math.random() * 1000);
        int newNumVacunas = centroSaludDTO.getNumVacunasDisponibles() + vacunasToAdd;

        centroController.modificarNumeroVacunasDisponibles(centroSaludDTO.getId(), vacunasToAdd);

        assertEquals(newNumVacunas, centroController.getCentroById(centroSaludDTO.getId()).getNumVacunasDisponibles());

        utilidades.eliminarCentro(centroSaludDTO.getId());
    }
}
