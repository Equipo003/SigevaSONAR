package com.equipo3.SIGEVA.dao;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.CupoCitas;

@Repository
public interface CupoCitasDao extends MongoRepository<CupoCitas, String> {

	@Query("e")
	CupoCitas buscarCupoLibre(String uuidCentro, Date fecha);
	
}