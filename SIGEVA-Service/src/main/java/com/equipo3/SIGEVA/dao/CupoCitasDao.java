package com.equipo3.SIGEVA.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.CupoCitas;

@Repository
public interface CupoCitasDao extends MongoRepository<CupoCitas, String> {

//	@Query("{ 'centroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 } , 'tamano' : { '$lt' : ?2 } }")
//	public List<CupoCitas> buscarCuposLibresAPartirDe(CentroSalud centroSalud, Date aPartirDeLaFecha, int maximo);
	
	@Query("{ 'centroSalud._id' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 } , 'tamano' : { '$lt' : ?2 } }")
	public List<CupoCitas> buscarCuposLibresAPartirDe(String idCentroSalud, Date aPartirDeLaFecha, int maximo);

	@Query("{ 'centroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 }, 'fechaYHoraInicio' : { '$lte' : ?2 } }")
	public List<CupoCitas> buscarCuposDelTramo(CentroSalud centro, Date fechaInicio, Date fechaFin);

    List<CupoCitas> findByFechaYHoraInicio(Date fechaJornadaFormated);
}