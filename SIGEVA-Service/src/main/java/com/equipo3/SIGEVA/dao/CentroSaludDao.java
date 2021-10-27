package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.CentroSalud;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroSaludDao extends MongoRepository<CentroSalud, String> {
	List<CentroSalud> findAll();
	CentroSalud findById(ObjectId id);
}

