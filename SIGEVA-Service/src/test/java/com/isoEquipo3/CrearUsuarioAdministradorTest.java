package com.isoEquipo3;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.server.ResponseStatusException;

import com.isoEquipo3.SIGEVA.App;
import com.isoEquipo3.SIGEVA.Controllers.AdministradorController;
import com.isoEquipo3.SIGEVA.Dao.AdministradorDao;
import com.isoEquipo3.SIGEVA.Model.Administrador;
import com.isoEquipo3.SIGEVA.Model.Usuario;

public class CrearUsuarioAdministradorTest {

	@Autowired
	AdministradorDao administradorDao;
	
    @Test
    public void insercionCorrectaAdministrador() {
    	
        Usuario administrador = new Administrador();
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

        AdministradorController administradorController = new AdministradorController();
		administradorController.registrarUsuario(administrador);
    }

    @Test(expected = ResponseStatusException.class)
    public void administradorYaExiste() {
        Administrador admin1 = new Administrador();
        admin1.setUsername("9234");
        AdministradorController administradorController = new AdministradorController();
		administradorController.registrarUsuario(admin1);

        Administrador admin2 = new Administrador();
        admin2.setUsername("9234");

        administradorController.registrarUsuario(admin2);
    }
}
