package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.controller.AdministradorController;

import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;

@SpringBootTest
class ConfiguracionCuposTest {


    static ConfiguracionCuposDTO configuracionCuposDTO;
    static ConfiguracionCuposDTO existConfiguracionCuposDTO;

    @BeforeAll
    static void start(){
        configuracionCuposDTO = new ConfiguracionCuposDTO(60, 5, 13,
                0, "2021-11-08T07:00");
        existConfiguracionCuposDTO = new ConfiguracionCuposDTO(60, 3, 13,
                0, "2021-11-08T07:00");
    }

    @Autowired
    private AdministradorController administradorController;

//    @Test
//    void crearConfiguracionCupos(){
//        if(this.administradorController.existConfiguracionCupos()==false){
//            this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
//            assertEquals(this.administradorController.getConfiguracionCupos().toString(), configuracionCuposDTO.toString());
//            this.administradorController.eliminarConfiguracionCupos();
//
//        } else {
//            existConfiguracionCuposDTO = administradorController.getConfiguracionCupos();
//            this.administradorController.eliminarConfiguracionCupos();
//
//            this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
//            assertEquals(this.administradorController.getConfiguracionCupos().toString(), configuracionCuposDTO.toString());
//
//            this.administradorController.eliminarConfiguracionCupos();
//            this.administradorController.crearConfiguracionCupos(existConfiguracionCuposDTO);
//        }
//    }
//
//    @Test
//    void crearConfiguracionCuposYaExistente(){
//        boolean configuracionExistente = true;
//        try {
//            configuracionCuposDTO.setId(UUID.randomUUID().toString());
//            if (this.administradorController.existConfiguracionCupos()==false) {
//                this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
//                configuracionExistente = false;
//                this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
//            }
//            else {
//                this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
//            }
//        } catch (Exception e) {
//            if (configuracionExistente==false){
//                this.administradorController.eliminarConfiguracionCupos();
//            }
//            assertEquals(e.getMessage(), "208 ALREADY_REPORTED \"Ya existe una configuración de cupos\"");
//
//        }
//    }

//    @Test
//    void getConfiguracionCupos(){
//        if(this.administradorController.existConfiguracionCupos()==false){
//            Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()->{
//                this.administradorController.getConfiguracionCupos();
//            });
//        }else{
//            ConfiguracionCuposDTO configuracionCuposDTO = this.administradorController.getConfiguracionCupos();
//            assertTrue(configuracionCuposDTO!=null);
//        }
//    }
}