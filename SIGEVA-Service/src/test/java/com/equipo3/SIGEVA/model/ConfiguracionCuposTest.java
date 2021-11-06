package com.equipo3.SIGEVA.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.controller.AdministradorController;

import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;

@SuppressWarnings("static-access")
@SpringBootTest
class ConfiguracionCuposTest {


    static ConfiguracionCuposDTO configuracionCuposDTO;
    static ConfiguracionCupos configuracionCupos;

    @BeforeAll
    static void start(){
        configuracionCuposDTO = new ConfiguracionCuposDTO(60, 60, 30,
                30, "");
        configuracionCupos = new ConfiguracionCupos(60, 60, 30,
                30, "");
    }

    @Autowired
    private AdministradorController administradorController;


    void setDuracionMinutos() {
        this.configuracionCuposDTO.setDuracionMinutos(65);
        assertTrue(this.configuracionCuposDTO.getDuracionMinutos()==65);
    }



    void getDuracionMinutos() {
        this.configuracionCuposDTO.setDuracionMinutos(10);
        assertTrue(this.configuracionCuposDTO.getDuracionMinutos()==10);
    }


    void getNumeroPacientes() {
        this.configuracionCuposDTO.setNumeroPacientes(20);
        assertTrue(this.configuracionCuposDTO.getNumeroPacientes()==20);
    }


    void getDuracionJornadaHoras() {
        this.configuracionCuposDTO.setDuracionJornadaHoras(20);
        assertTrue(this.configuracionCuposDTO.getDuracionJornadaHoras()==20);
    }


    void setDuracionJornadaHoras() {
        this.configuracionCuposDTO.setDuracionJornadaHoras(50);
        assertTrue(this.configuracionCuposDTO.getDuracionJornadaHoras()==50);
    }


    void getDuracionJornadaMinutos() {
        this.configuracionCuposDTO.setDuracionJornadaMinutos(30);
        assertTrue(this.configuracionCuposDTO.getDuracionJornadaMinutos()==30);
    }


    void setDuracionJornadaMinutos() {
        this.configuracionCuposDTO.setDuracionJornadaMinutos(45);
        assertTrue(this.configuracionCuposDTO.getDuracionJornadaMinutos()==45);
    }


    void getFechaInicio() {
        String fecha = "2021-11-08T07:00";
        this.configuracionCuposDTO.setFechaInicio(fecha);
        assertTrue(this.configuracionCuposDTO.getFechaInicio().equals(fecha));
    }

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

        assertTrue(configuracionCupos.getFechaInicioAsDate().toString().equals(fechaInicioDate.toString()));
    }

    void getFechaAsDateException(){
        Assertions.assertThatExceptionOfType(ParseException.class).isThrownBy(()->{
           this.getFechaAsDateFunctionCopy();
        });
    }

    void getFechaAsDateFunctionCopy() throws ParseException{
        String fechaInicio = "Wrong Date format stringified";
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        formateador.parse(fechaInicio);
    }

    void setFechaInicio() {
        String fecha = "fecha de prueba";
        this.configuracionCuposDTO.setFechaInicio(fecha);
        assertTrue(this.configuracionCuposDTO.getFechaInicio().equals(fecha));
    }


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

        assertTrue(configuracionCupos.getHoraFin().toString().equals(horaFin.toString()));




    }


    void crearConfiguracionCupos(){
        configuracionCuposDTO = new ConfiguracionCuposDTO(60, 60, 50,50,"2021-11-08T07:00" );
        if(this.administradorController.existConfiguracionCupos()==false){
            this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
            assertTrue(true);
        }else {
            Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()->{
                this.administradorController.crearConfiguracionCupos(configuracionCuposDTO);
            });
        }
    }


    void getConfiguracionCupos(){
        if(this.administradorController.existConfiguracionCupos()==false){
            Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()->{
                this.administradorController.getConfiguracionCupos();
            });
        }else{
            ConfiguracionCuposDTO configuracionCuposDTO = this.administradorController.getConfiguracionCupos();
            assertTrue(configuracionCuposDTO!=null);
        }
    }

    @Test
    void configuracionCuposTesting(){
        this.setDuracionMinutos();
        this.getDuracionMinutos();
        this.getNumeroPacientes();
        this.getDuracionJornadaHoras();
        this.setDuracionJornadaHoras();
        this.getDuracionJornadaMinutos();
        this.setDuracionJornadaMinutos();
        this.getFechaInicio();
        this.getFechaInicioAsDate();
        this.setFechaInicio();
        this.getHoraFin();
        this.crearConfiguracionCupos();
        this.getConfiguracionCupos();

        this.getFechaAsDateException();


    }

}