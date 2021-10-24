package com.equipo3.SIGEVA.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.CupoCitas;

@Repository
public interface CupoCitasDao extends MongoRepository<CupoCitas, String> {

	@Query("{ 'centroSalud._id' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 } }")
	List<CupoCitas> buscarCuposLibres(String uuidCentro, Date fecha);

}