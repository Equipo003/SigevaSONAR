package com.equipo3.SIGEVA.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;

import java.util.List;

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
