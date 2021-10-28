package com.equipo3.SIGEVA.dao;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Usuario;

@Repository
public interface AdministradorDao extends MongoRepository <Usuario, ObjectId> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByRol(String rol);
}
