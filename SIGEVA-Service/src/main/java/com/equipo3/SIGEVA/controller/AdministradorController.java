package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("usuario")
public class AdministradorController {
    @Autowired
    AdministradorDao administradorDao;
    @Autowired
    private CentroSaludDao centroSaludDao;
    

    @GetMapping("/newUser")
    public String registrarUsuario() {
        try {
            Usuario administrador = new Administrador();
            administrador.setRol("Administrador");
            administrador.setCentroFK("1234");
            administrador.setUsername("user5");
            administrador.setCorreo("micorreo@correo.com");
            administrador.setHashPassword("sdfsdf");
            administrador.setDni("99999999Q");
            administrador.setNombre("Juan");
            administrador.setApellidos("Perez");
            administrador.setFechaNacimiento(new Date());
            administrador.setImagen("912imagen");
            administradorDao.save(administrador);
            System.out.print("Hola");
            return "hola";

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    
    @GetMapping("/newCentroSalud")
    public String crearCentroSalud() {
        try {
        	CentroSalud centroSalud = new CentroSalud();
        	centroSalud.setDireccion("calle");
    		centroSalud.setHoraApertura(new Date(121,9,20,20,15,5));
    		centroSalud.setHoraCierre(new Date(121,10,10,10,15,5));
    		centroSalud.setNumVacunasDisponibles(2);
    		centroSaludDao.save(centroSalud);
            System.out.print("Hola");
            return "hola";

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/hola")
    public String hola(){
        return "adios";
    }
}
