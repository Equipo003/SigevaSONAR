package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfiguracionCuposDao extends MongoRepository<ConfiguracionCupos, String> {
	
}