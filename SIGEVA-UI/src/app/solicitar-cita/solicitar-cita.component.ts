import { Component, } from '@angular/core';
import { Usuario } from "../Model/Usuario";
import { CentroSalud  } from "../Model/centro-salud";
import { CupoCitas } from "../Model/cupo-citas";
import { JsonService } from '../Service/json.service';
import { HttpParams } from "@angular/common/http";

@Component({
	selector: 'app-solicitar-cita',
	templateUrl: './solicitar-cita.component.html',
	styleUrls: ['./solicitar-cita.component.css']
})

export class SolicitarCitaComponent {
	paciente: Usuario;
	mensaje: string;
	mensajeError: string;
	cita1: CupoCitas;
	cita2: CupoCitas;
	citas: CupoCitas[];
	solicitada: boolean;

	constructor(private json: JsonService) {
		this.paciente = new Usuario("", "eb972b1a-b1f0-41c9-bae4-ac729b6b967b", "vasilesan", "", "", "",
			"", "", "", "", "25100ecd-136f-43a5-886e-0e7de585d5ea");
		this.cita1 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
		this.cita2 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
		this.citas = [];
		this.solicitada = false;
		this.mensaje = "SOLICITAR CITA";
		this.mensajeError = "";

	}

	solicitarCita() {
		let params = new HttpParams({
			fromObject: {
				"username": String(this.paciente.username)
			}
		});
		this.json.getJsonP("cupo/buscarYAsignarCitas", params).subscribe(
			result => {
				this.citas = JSON.parse(result.toString());
				this.cita1 = this.citas[0];
				this.cita2 = this.citas[1];
				this.mensaje = 'CITA RESERVADA!'
				this.mensajeError = "";
				this.solicitada = true;
			}, err => {
				this.mensaje = "";
				this.mensajeError = JSON.parse(err.error)['message'];

			});


	}

}
