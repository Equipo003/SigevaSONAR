package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.TokenDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.DeniedAccessException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;

/**
 * Test usando la técnica TDD para la modificación de lo centros de salud
 * teniendo en cuenta todos los casos especificados por el ProductOwner
 * 
 * @author Equipo3
 *
 */

@SpringBootTest
class ModificarCentroTest {
	@Autowired
	private AdministradorController administradorController = new AdministradorController();

	private static CentroSaludDTO csDto;
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
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test modificación centro");
		csDto.setNombreCentro("CentroTest modificación");
		csDto.setNumVacunasDisponibles(80);
	}

	/**
	 * Test modificación correta de centro de salud, por parte del administrador, caso de éxito.
	 */
	@Test
	void modificarCentroCorrecto() {
		try {
			administradorController.crearCentroSalud(requestMockAdmin,csDto);

			csDto.setDireccion("Modificación dirección test centro Administrador");
			csDto.setNumVacunasDisponibles(30);

			administradorController.modificarCentroSalud(requestMockAdmin,csDto);

			assertEquals(administradorController.getCentroById(csDto.getId()).toString(), csDto.toString());

			administradorController.eliminarCentro(csDto.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void modificarCentroPorPaciente() {
		try {
			administradorController.crearCentroSalud(requestMockAdmin,csDto);

			csDto.setDireccion("Modificación dirección test centro Administrador");
			csDto.setNumVacunasDisponibles(30);

			administradorController.modificarCentroSalud(requestMockPa,csDto);
			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "401 UNAUTHORIZED \"No tiene permisos para realizar esta acción.\"");
			administradorController.eliminarCentro(csDto.getId());
		}
	}
	
	@Test
	void modificarCentroPorSanitario() {
		try {
			administradorController.crearCentroSalud(requestMockAdmin,csDto);

			csDto.setDireccion("Modificación dirección test centro Administrador");
			csDto.setNumVacunasDisponibles(30);

			administradorController.modificarCentroSalud(requestMockSan,csDto);
			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "401 UNAUTHORIZED \"No tiene permisos para realizar esta acción.\"");
			administradorController.eliminarCentro(csDto.getId());
		}
	}
}
