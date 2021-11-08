package com.equipo3.SIGEVA.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Usuario;

@Repository
public interface UsuarioDao extends MongoRepository <Usuario, String> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByRol(String rol);

    @Query("{ '_class' : ?0 }")
    List<Usuario> findAllByClass(String className);

    void deleteByUsername(String username);
}
