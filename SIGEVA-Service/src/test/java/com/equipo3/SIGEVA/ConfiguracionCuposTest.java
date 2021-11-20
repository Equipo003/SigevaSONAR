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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.controller.AdministradorController;

import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;

@SpringBootTest
class ConfiguracionCuposTest {


    static ConfiguracionCuposDTO configuracionCuposDTO;
    static ConfiguracionCuposDTO existConfiguracionCuposDTO;
    public static MockHttpServletRequest requestMockAdmin;
	public static MockHttpServletRequest requestMockSan;
	public static MockHttpServletRequest requestMockPa;
	static TokenDTO tokenDTOAdmin;
	static TokenDTO tokenDTOSan;
	static TokenDTO tokenDTOPa;

	@BeforeAll
	static void creacionRequest() {
		requestMockAdmin = new MockHttpServletRequest();
		tokenDTOAdmin = new TokenDTO("adm", "Administrador");
		requestMockAdmin.getSession().setAttribute("token", tokenDTOAdmin);

		requestMockSan = new MockHttpServletRequest();
		tokenDTOSan = new TokenDTO("san", "Sanitario");
		requestMockSan.getSession().setAttribute("token", tokenDTOSan);
		
		requestMockPa = new MockHttpServletRequest();
		tokenDTOPa = new TokenDTO("pa", "Paciente");
		requestMockPa.getSession().setAttribute("token", tokenDTOPa);
	}
    @BeforeAll
    static void start(){
        configuracionCuposDTO = new ConfiguracionCuposDTO(60, 5, 13,
                0, "2021-11-08T07:00");
        existConfiguracionCuposDTO = new ConfiguracionCuposDTO(60, 3, 13,
                0, "2021-11-08T07:00");
    }

    @Autowired
    private AdministradorController administradorController;

    @Test
    void getConfiguracionCuposPorPaciente() {
    	try {
    		this.administradorController.existConfiguracionCupos(requestMockPa);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "500 INTERNAL_SERVER_ERROR \"No tiene permisos para realizar esta acción.\"");
    	}
    }
    @Test
    void getConfiguracionCuposPorSanitario() {
    	try {
    		this.administradorController.existConfiguracionCupos(requestMockSan);
    	}catch (Exception e) {
    		assertEquals(e.getMessage(), "500 INTERNAL_SERVER_ERROR \"No tiene permisos para realizar esta acción.\"");
    	}
    }
    @Test
    void crearConfiguracionCupos(){
        if(this.administradorController.existConfiguracionCupos(requestMockAdmin)==false){
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            assertEquals(this.administradorController.getConfiguracionCupos(requestMockAdmin).toString(), configuracionCuposDTO.toString());
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);

        } else {
            existConfiguracionCuposDTO = administradorController.getConfiguracionCupos(requestMockAdmin);
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);

            this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            assertEquals(this.administradorController.getConfiguracionCupos(requestMockAdmin).toString(), configuracionCuposDTO.toString());

            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,existConfiguracionCuposDTO);
        }
    }
    
    @Test
    void crearConfiguracionCuposPorPaciente(){
        if(this.administradorController.existConfiguracionCupos(requestMockAdmin)==false){
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            assertEquals(this.administradorController.getConfiguracionCupos(requestMockAdmin).toString(), configuracionCuposDTO.toString());
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,existConfiguracionCuposDTO);

        } else {
            existConfiguracionCuposDTO = administradorController.getConfiguracionCupos(requestMockAdmin);
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            try {
            	this.administradorController.crearConfiguracionCupos(requestMockPa,configuracionCuposDTO);
            }catch(Exception e) {
            	assertEquals(e.getMessage(), "208 ALREADY_REPORTED \"No tiene permisos para realizar esta acción.\"");
            }
            
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,existConfiguracionCuposDTO);
        }
    }
    
    @Test
    void crearConfiguracionCuposPorSanitario(){
        if(this.administradorController.existConfiguracionCupos(requestMockAdmin)==false){
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            assertEquals(this.administradorController.getConfiguracionCupos(requestMockAdmin).toString(), configuracionCuposDTO.toString());
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,existConfiguracionCuposDTO);

        } else {
            existConfiguracionCuposDTO = administradorController.getConfiguracionCupos(requestMockAdmin);
            this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            try {
            	this.administradorController.crearConfiguracionCupos(requestMockSan,configuracionCuposDTO);
            }catch(Exception e) {
            	assertEquals(e.getMessage(), "208 ALREADY_REPORTED \"No tiene permisos para realizar esta acción.\"");
            }
            
            this.administradorController.crearConfiguracionCupos(requestMockAdmin,existConfiguracionCuposDTO);
        }
    }

    @Test
    void crearConfiguracionCuposYaExistente(){
        boolean configuracionExistente = true;
        try {
            configuracionCuposDTO.setId(UUID.randomUUID().toString());
            if (this.administradorController.existConfiguracionCupos(requestMockAdmin)==false) {
                this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
                configuracionExistente = false;
                this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            }
            else {
                this.administradorController.crearConfiguracionCupos(requestMockAdmin,configuracionCuposDTO);
            }
        } catch (Exception e) {
            if (configuracionExistente==false){
                this.administradorController.eliminarConfiguracionCupos(requestMockAdmin);
            }
            assertEquals(e.getMessage(), "208 ALREADY_REPORTED \"Ya existe una configuración de cupos\"");

        }
    }

    @Test
    void getConfiguracionCupos(){
        if(this.administradorController.existConfiguracionCupos(requestMockAdmin)==false){
            Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()->{
                this.administradorController.getConfiguracionCupos(requestMockAdmin);
            });
        }else{
            ConfiguracionCuposDTO configuracionCuposDTO = this.administradorController.getConfiguracionCupos(requestMockAdmin);
            assertTrue(configuracionCuposDTO!=null);
        }
    }
}