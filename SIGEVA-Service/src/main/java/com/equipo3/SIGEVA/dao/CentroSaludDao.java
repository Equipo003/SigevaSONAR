package com.equipo3.SIGEVA.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.CentroSalud;

@Repository
public interface CentroSaludDao extends MongoRepository<CentroSalud, String> {

	List<CentroSalud> findAll();

	Optional<CentroSalud> findById(String id);

	Optional<CentroSalud> findByNombreCentro(String nombreCentroSalud);

	 void deleteById(String uuidCentroSalud);
}
