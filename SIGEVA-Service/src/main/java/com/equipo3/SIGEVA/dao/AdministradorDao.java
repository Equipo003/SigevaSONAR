package com.equipo3.SIGEVA.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;

import java.util.Optional;

@Repository
public interface AdministradorDao extends MongoRepository <Usuario, ObjectId> {

    Optional<Usuario> findByUsername(String username);
}
