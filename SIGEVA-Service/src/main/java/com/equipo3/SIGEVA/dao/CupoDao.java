package com.equipo3.SIGEVA.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Cupo;

@Repository
public interface CupoDao extends MongoRepository<Cupo, String> {

	@Query("{ 'uuidCentroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 } , 'tamano' : { '$lt' : ?2 } }")
	public List<Cupo> buscarCuposLibresAPartirDe(String uuidCentroSalud, Date aPartirDeLaFecha, int maximo);

	@Query("{ 'uuidCentroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 }, 'fechaYHoraInicio' : { '$lte' : ?2 } }")
	public List<Cupo> buscarCuposDelTramo(String uuidCentroSalud, Date fechaInicio, Date fechaFin);
	
	@Query("{ 'uuidCentroSalud' : ?0, 'tamanoActual' : { '$gt' : 0 }, 'fechaYHoraInicio' : { '$gt' : ?1 } }")
	public List<Cupo> buscarCuposOcupados(String uuidCentroSalud, Date fecha);

}
