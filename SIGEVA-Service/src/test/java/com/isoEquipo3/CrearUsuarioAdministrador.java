package com.isoEquipo3;

import com.isoEquipo3.SIGEVA.Model.Administrador;
import com.isoEquipo3.SIGEVA.Model.CentroSalud;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static org.junit.Assert.*;
@TestPropertySource(locations = "classpath:db-test.properties")
public class CrearUsuarioAdministrador {

    @Test
    public void tiposDatosCorrectos() {

        Administrador administrador = new Administrador();
        administrador.setRol("Administrador");
        administrador.setCentroFK("1234");
        administrador.setUsername("user1");
        administrador.setCorreo("micorreo@correo.com");
        administrador.setHashPassword("sdfsdf");
        administrador.setDni("99999999Q");
        administrador.setNombre("Juan");
        administrador.setApellidos("Perez");
        administrador.setFechaNacimiento(new Date());
        administrador.setImagen("912imagen");

        assertNotNull(administrador.getRol());
        assertNotNull(administrador.getCentroFK());
        assertNotNull(administrador.getUsername());
        assertNotNull(administrador.getCorreo());
        assertNotNull(administrador.getHashPassword());
        assertNotNull(administrador.getDni());
        assertNotNull(administrador.getNombre());
        assertNotNull(administrador.getApellidos());
        assertNotNull(administrador.getFechaNacimiento());
        assertNotNull(administrador.getImagen());
    }

    @Test
    public void rolDiferenteDeAdministrador() {
        Administrador administrador = new Administrador();
        administrador.setRol("Paciente");
        assertNotEquals("Administrador", administrador.getRol());
    }

    @Test
    public void sinCamposVacios() {
        Administrador administrador = new Administrador();
        administrador.setRol("Administrador");
        administrador.setCentroFK("1234");
        administrador.setUsername("user123");
        administrador.setCorreo("micorreo@correo.com");
        administrador.setHashPassword("sdfsdf");
        administrador.setDni("99999999Q");
        administrador.setNombre("Juan");
        administrador.setApellidos("Perez");
        administrador.setFechaNacimiento(new Date());
        administrador.setImagen("912imagen");

        assertNotEquals("", administrador.getRol());
        assertNotEquals("", administrador.getCentroFK());
        assertNotEquals("", administrador.getUsername());
        assertNotEquals("", administrador.getCorreo());
        assertNotEquals("", administrador.getHashPassword());
        assertNotEquals("", administrador.getDni());
        assertNotEquals("", administrador.getNombre());
        assertNotEquals("", administrador.getApellidos());
        assertNotEquals("", administrador.getFechaNacimiento());
        assertNotEquals("", administrador.getImagen());
    }

    @Test
    public void administradorYaExiste() {
        Administrador admin1 = new Administrador();
        admin1.setUsername("9234");

        Administrador admin2 = new Administrador();
        admin2.setUsername("9234");

        assertTrue(admin1.getUsername() == admin2.getUsername());
    }
}
