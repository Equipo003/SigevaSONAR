package com.equipo3.SIGEVA.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Rol;

@Repository
public interface RolDao extends MongoRepository <Rol, String> {
}
