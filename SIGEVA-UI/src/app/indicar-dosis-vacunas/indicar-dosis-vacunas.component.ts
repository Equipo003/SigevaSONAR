import { Component, OnInit } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';
import { JsonService } from '../Service/json.service';

@Component({
	selector: 'app-indicar-dosis-vacunas',
	templateUrl: './indicar-dosis-vacunas.component.html',
	styleUrls: ['./indicar-dosis-vacunas.component.css'],
	providers: [JsonService],
})
export class IndicarDosisVacunasComponent implements OnInit {
	public cs: CentroSalud[];
	public centroSeleccionado: String;
	public nVacunasActual : number;
	
	constructor(private json: JsonService) { 
		this.cs = [];
		this.centroSeleccionado = "";
		this.nVacunasActual = 0;
		
	}

	ngOnInit(): void {
		this.listarCentros();
	}

	listarCentros() {
		this.json.getJson('user/listCentroSalud').subscribe(
			(res: any) => {this.cs = JSON.parse(res);
				this.centroSeleccionado = this.cs[0].nombreCentro;
				console.log(res);
			}, error=>{
				console.log(error);
			}); 
	}

}
