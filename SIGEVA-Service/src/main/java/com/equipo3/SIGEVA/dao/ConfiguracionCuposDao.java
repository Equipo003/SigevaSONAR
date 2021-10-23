package com.equipo3.SIGEVA.dao;


import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConfiguracionCuposDao extends MongoRepository<ConfiguracionCupos, String> {

    List<ConfiguracionCupos> findAll(); //Si hay más de una se lanza una excepción
}