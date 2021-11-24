package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
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

	@Autowired
	private Utilidades utilidades;

	private static CentroSaludDTO csDto;

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
			administradorController.crearCentroSalud(csDto);

			csDto.setDireccion("Modificación dirección test centro Administrador");
			csDto.setNumVacunasDisponibles(30);

			administradorController.modificarCentroSalud(csDto);

			assertEquals(administradorController.getCentroById(csDto.getId()).toString(), csDto.toString());

			utilidades.eliminarCentro(csDto.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
