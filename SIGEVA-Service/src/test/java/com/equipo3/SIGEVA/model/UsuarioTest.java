package com.equipo3.SIGEVA.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UsuarioTest {

    static Usuario usuario;

    @BeforeAll
    static void start() {
        usuario = new Usuario();
    }

    @Test
    void getUuid() {
        String uuid = UUID.randomUUID().toString();
        usuario.setIdUsuario(uuid);
        assertEquals(uuid, usuario.getIdUsuario());
    }

    @Test
    void getRol() {
        String rol = "Paciente";
        usuario.setRol(rol);
        assertEquals(rol, usuario.getRol());
    }

    @Test
    void getCentroSalud() {
        String centro = "324534534";
        usuario.setCentroSalud(centro);
        assertEquals(centro, usuario.getCentroSalud());
    }

    @Test
    void getUsername() {
        String username = "pepe";
        usuario.setUsername(username);
        assertEquals(username, usuario.getUsername());
    }

    @Test
    void getCorreo() {
        String correo = "correo@correo";
        usuario.setCorreo(correo);
        assertEquals(correo, usuario.getCorreo());
    }

    @Test
    void getHashPassword() {
        String pass = "234o";
        usuario.setHashPassword(pass);
        assertEquals(pass, usuario.getHashPassword());
    }

    @Test
    void getDNI() {
        String dni = "77777777Q";
        usuario.setDni(dni);
        assertEquals(dni, usuario.getDni());
    }

    @Test
    void getNombre() {
        String nombre = "manuel";
        usuario.setNombre(nombre);
        assertEquals(nombre, usuario.getNombre());
    }

    @Test
    void getApellidos() {
        String apellido = "diaz";
        usuario.setApellidos(apellido);
        assertEquals(apellido, usuario.getApellidos());
    }

    @Test
    void getFecha() {
        Date fecha = new Date();
        usuario.setFechaNacimiento(fecha);
        assertEquals(fecha, usuario.getFechaNacimiento());
    }

    @Test
    void getImagen() {
        String imagen = "sdfsdfasdfsdfasdf";
        usuario.setImagen(imagen);
        assertEquals(imagen, usuario.getImagen());
    }

    @Test
    void checkToString() {
        assertEquals("Usuario [idUsuario=" + usuario.getIdUsuario() + ", rol=" + usuario.getRol() + ", centroSalud=" +
                usuario.getCentroSalud() + ", username=" + usuario.getUsername() + ", correo=" + usuario.getCorreo() +
                ", hashPassword=" + usuario.getHashPassword() + ", dni=" + usuario.getDni() + ", nombre="
                + usuario.getNombre() + ", apellidos=" + usuario.getApellidos() + ", fechaNacimiento=" +
                usuario.getFechaNacimiento() + ", imagen=" + usuario.getImagen()
                + "]", usuario.toString());
    }

    @Test
    void checkHashCode() {
        assertNotEquals(usuario.hashCode(), new Integer(0));
    }

//    @Test
//    void checkEquals() {
//        Usuario u2 = new Usuario(usuario.getRol(), usuario.getCentroSalud(), usuario.getUsername(), usuario.getCorreo(),
//                usuario.getHashPassword(), usuario.getDni(), usuario.getNombre(),
//                usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getImagen());
//        u2.setIdUsuario(usuario.getIdUsuario());
//        assertEquals(usuario, u2);
//    }

}
