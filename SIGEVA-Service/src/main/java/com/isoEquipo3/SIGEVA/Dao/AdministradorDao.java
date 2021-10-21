package com.isoEquipo3.SIGEVA.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.isoEquipo3.SIGEVA.Model.Usuario;

@Repository
public interface AdministradorDao extends MongoRepository<Usuario, String>{
    //public static void crearUsuario(Usuario administrador);
}
