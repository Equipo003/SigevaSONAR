package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("usuario")
public class AdministradorController {
    @Autowired
    AdministradorDao administradorDao;
    @Autowired
    ConfiguracionCuposDao configDao;

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

    @GetMapping("/hola")
    public String hola(){
        return "adios";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/crearConfCupo")
    public ConfiguracionCupos crearConfiguracionCupos(@RequestBody ConfiguracionCupos conf){
        List<ConfiguracionCupos> configuracionCuposList = configDao.findAll();
        if(configuracionCuposList.size() == 0)
            configDao.save(conf);
        else
            System.out.println("Ya existe una configuracion");
        return conf;
    }
}
