package com.equipo3.SIGEVA.model;

import com.equipo3.SIGEVA.controller.AdministradorController;


import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfiguracionCuposTest {


    static ConfiguracionCupos configuracionCupos;

    @BeforeAll
    static void start(){
        configuracionCupos = new ConfiguracionCupos(60, 60, 30,
                30, "");
    }

    @Autowired
    private AdministradorController administradorController;

    @Test
    void setDuracionMinutos() {
        this.configuracionCupos.setDuracionMinutos(65);
        assertTrue(this.configuracionCupos.getDuracionMinutos()==65);
    }

    @Test
    void setNumeroPacientes() {
        this.configuracionCupos.setNumeroPacientes(70);
        assertTrue(this.configuracionCupos.getNumeroPacientes()==70);
    }

    @Test
    void getDuracionMinutos() {
        this.configuracionCupos.setDuracionMinutos(10);
        assertTrue(this.configuracionCupos.getDuracionMinutos()==10);
    }

    @Test
    void getNumeroPacientes() {
        this.configuracionCupos.setNumeroPacientes(20);
        assertTrue(this.configuracionCupos.getNumeroPacientes()==20);
    }

    @Test
    void getDuracionJornadaHoras() {
        this.configuracionCupos.setDuracionJornadaHoras(20);
        assertTrue(this.configuracionCupos.getDuracionJornadaHoras()==20);
    }

    @Test
    void setDuracionJornadaHoras() {
        this.configuracionCupos.setDuracionJornadaHoras(50);
        assertTrue(this.configuracionCupos.getDuracionJornadaHoras()==50);
    }

    @Test
    void getDuracionJornadaMinutos() {
        this.configuracionCupos.setDuracionJornadaMinutos(30);
        assertTrue(this.configuracionCupos.getDuracionJornadaMinutos()==30);
    }

    @Test
    void setDuracionJornadaMinutos() {
        this.configuracionCupos.setDuracionJornadaMinutos(45);
        assertTrue(this.configuracionCupos.getDuracionJornadaMinutos()==45);
    }

    @Test
    void getFechaInicio() {
        String fecha = "2021-11-08T07:00";
        this.configuracionCupos.setFechaInicio(fecha);
        assertTrue(this.configuracionCupos.getFechaInicio().equals(fecha));
    }

    @Test
    void getFechaInicioAsDate() {
        String fechaInicio = "2021-11-08T07:00";
        this.configuracionCupos.setFechaInicio("2021-11-08T07:00");
        Date fechaInicioDate = new Date();

        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            fechaInicioDate =  formateador.parse(fechaInicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertTrue(this.configuracionCupos.getFechaInicioAsDate().getTime()==fechaInicioDate.getTime());

    }

    @Test
    void setFechaInicio() {
        String fecha = "fecha de prueba";
        this.configuracionCupos.setFechaInicio(fecha);
        assertTrue(this.configuracionCupos.getFechaInicio().equals(fecha));
    }

    @Test
    void getHoraFin() {
        configuracionCupos.setFechaInicio("2021-11-08T07:00");
        String fechaInicio = "2021-11-08T07:00";
        Date horaFin = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            horaFin =  formateador.parse(fechaInicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        horaFin.setHours(horaFin.getHours()+this.configuracionCupos.getDuracionJornadaHoras());
        horaFin.setMinutes(horaFin.getMinutes()+this.configuracionCupos.getDuracionJornadaMinutos());

        assertTrue(this.configuracionCupos.getHoraFin().getTime()==horaFin.getTime());

    }

    @Test
    void crearConfiguracionCupos(){
        configuracionCupos = new ConfiguracionCupos(60, 60, 50,50,"2021-11-08T07:00" );
        if(this.administradorController.existConfiguracionCupos()==false){
            this.administradorController.crearConfiguracionCupos(configuracionCupos);
            assertTrue(true);
        }else {
            Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()->{
                this.administradorController.crearConfiguracionCupos(configuracionCupos);
            });
        }
    }

}