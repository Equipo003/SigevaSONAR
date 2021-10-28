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
	public vacunasAanadir : number;
	public idCentro : String;
	public mensaje : String;
	
	constructor(private json: JsonService) { 
		this.cs = [];
		this.centroSeleccionado = "";
		this.nVacunasActual = 0;
		this.vacunasAanadir = 0;
		this.idCentro = "";
		this.mensaje = "";
	}

	ngOnInit(): void {
		this.listarCentros();
	}

	listarCentros() {
		this.json.getJson('user/getCentros').subscribe(
			(res: any) => {this.cs = JSON.parse(res);
				this.centroSeleccionado = this.cs[0].nombreCentro;
				this.nVacunasActual = this.cs[0].numVacunasDisponibles;
				console.log(res);
			}, error=>{
				console.log(error);
			}); 
	}
	
	centroSelect(){
		let self = this;
		this.cs.forEach(function(centro2 : CentroSalud){
			if(centro2.nombreCentro === self.centroSeleccionado){
				self.nVacunasActual = centro2.numVacunasDisponibles;
				self.idCentro = centro2.id;
			}
		});
	}
	
	putBackData(){
		this.json.putJsonVacunas("user/modificarDosisDisponibles",this.idCentro, this.vacunasAanadir).subscribe(
			(res : any) => {
				console.log("Hola "+res);
				this.mensaje = "Modificación correcta";
			}
		);
		this.mensaje = "Modificación correcta";
		location.reload();
		
	}

}
