package com.equipo3.SIGEVA.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Usuario;

@Repository
public interface UsuarioDao extends MongoRepository <Usuario, String> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByRol(String id);
    
    List<Usuario> findAllByCentroSalud(String centroSalud);
    
    @Query("{ '_class' : ?0 }")
    List<Usuario> findAllByClass(String className);

    void deleteByUsername(String username);
}
