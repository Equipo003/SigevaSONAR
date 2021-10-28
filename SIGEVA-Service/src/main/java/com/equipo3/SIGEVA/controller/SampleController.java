package com.equipo3.SIGEVA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;

@RestController
@RequestMapping(path = "/home")
public class SampleController
{
    @Autowired
    ConfiguracionCuposDao configDao;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/hi")
    public void healthCheck(){
        List<ConfiguracionCupos> configuracionCuposList = configDao.findAll();
        if(configuracionCuposList.size() >=1){
            System.out.println("Ya configurado");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/envio")
    public ConfiguracionCupos crearConfiguracionCupos(@RequestBody ConfiguracionCupos conf){
        List<ConfiguracionCupos> configuracionCuposList = configDao.findAll();
        if(configuracionCuposList.size() == 0)
            configDao.save(conf);
        else
            System.out.println("Ya existe una configuracion");
        return conf;
    }
}
