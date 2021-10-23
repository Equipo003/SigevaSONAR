package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.CentroSalud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroSaludDao extends MongoRepository<CentroSalud, String> {
    //public static void crearUsuario(Usuario administrador);
}

