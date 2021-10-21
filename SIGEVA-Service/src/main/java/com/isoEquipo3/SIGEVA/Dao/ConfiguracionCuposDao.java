package com.isoEquipo3.SIGEVA.Dao;

import com.isoEquipo3.SIGEVA.Model.ConfiguracionCupos;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConfiguracionCuposDao extends MongoRepository<ConfiguracionCupos, String> {

    List<ConfiguracionCupos> findAll(); //Si hay más de una se lanza una excepción
}