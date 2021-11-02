import { Component, OnInit } from '@angular/core';
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

export class SolicitarCitaComponent implements OnInit {
	paciente: Usuario;
	mensaje: string;
	mensajeError: string;
	cita1: CupoCitas;
	cita2: CupoCitas;
	citas: CupoCitas[];
	solicitada: boolean;

	constructor(private json: JsonService) {
		this.paciente = new Usuario("", "152054f2-7948-40cc-ba6a-f260638b4351", "pepe", "", "", "",
			"", "", "", "", "d2b2a043-df28-4c15-9190-9da25a0f6074");
		this.cita1 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
		this.cita2 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
		this.citas = [];
		this.solicitada = false;
		this.mensaje = "SOLICITAR CITA";
		this.mensajeError = "";

	}

	ngOnInit(): void {

	}

	solicitarCita() {
		let params = new HttpParams({
			fromObject: {
				"username": String(this.paciente.username)
			}
		});
		this.json.getJsonP("cupo/buscarParDeCuposLibresAPartirDeHoy", params).subscribe(
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
