package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.Vacuna;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacunaDao extends MongoRepository<Vacuna, String> {

    Optional<Vacuna> findByNombre(String nombre);
}
