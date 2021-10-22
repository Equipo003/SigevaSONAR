package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorDao extends MongoRepository<Usuario, String> {
    //public static void crearUsuario(Usuario administrador);
}

