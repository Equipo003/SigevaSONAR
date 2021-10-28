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
	public centroSeleccionado: CentroSalud;
	public nVacunasActual : number;
	public vacunasAanadir : number;
	
	constructor(private json: JsonService) { 
		this.cs = [];
		this.centroSeleccionado = new CentroSalud("","",0);
		this.nVacunasActual = 0;
		this.vacunasAanadir = 0;
		
	}

	ngOnInit(): void {
		this.listarCentros();
	}

	listarCentros() {
		this.json.getJson('user/listCentroSalud').subscribe(
			(res: any) => {this.cs = JSON.parse(res);
				
				console.log(res);
			}, error=>{
				console.log(error);
			}); 
	}
	

}
