package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroSaludDao extends MongoRepository<CentroSalud, String> {
	List<CentroSalud> findAll();
	CentroSalud findById(ObjectId id);
	Optional<CentroSalud> findByNombreCentro(String nombreCentroSalud);
}

