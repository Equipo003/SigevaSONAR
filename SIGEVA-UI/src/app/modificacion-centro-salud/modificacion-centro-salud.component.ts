import { Component, OnInit } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';
import { ActivatedRoute, Params } from "@angular/router";
import { HttpParams } from "@angular/common/http";
import { JsonService } from '../Service/json.service';

@Component({
	selector: 'app-modificacion-centro-salud',
	templateUrl: './modificacion-centro-salud.component.html',
	styleUrls: ['./modificacion-centro-salud.component.css']
})
export class ModificacionCentroSaludComponent implements OnInit {
	public cs: CentroSalud;
	public idCS: string;
	public message: string;
	public errorMessage: string;

	constructor(private json: JsonService, private rutaActu: ActivatedRoute) {
		this.cs = new CentroSalud("", "", 0);
		this.idCS = "",
			this.errorMessage = "";
		this.message = "";
	}

	ngOnInit(): void {
		this.rutaActu.params.subscribe(
			(pr: Params) => {
				this.idCS = pr['idCentroSalud'];
			}
		);
		this.centroSaludById();

	}

	centroSaludById() {
		let params = new HttpParams({
			fromObject: {
				idCentroSalud: this.idCS
			}
		});
		this.json.getJsonP("user/getCentroSaludById", params).subscribe(
			result => {
				this.cs = JSON.parse(result);
			}, error => {
				console.log(error);
			});
	}
	
	modificarCentroSalud() {

	}

}
