import { Component, OnInit } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';
import { JsonService } from '../Service/json.service';

@Component({
  selector: 'app-centros-salud-sistema',
  templateUrl: './centros-salud-sistema.component.html',
  styleUrls: ['./centros-salud-sistema.component.css']
})
export class CentrosSaludSistemaComponent implements OnInit {
  centrosSalud: CentroSalud[];

  constructor(private json: JsonService) { 
	this.centrosSalud = [];
  }

  ngOnInit(): void {
	this.listarCentroSalud();
  }
  
  listarCentroSalud(){
	let self = this;
	this.json.getJson("user/getCentros").subscribe(
		(res : any) => {
			this.centrosSalud = JSON.parse(res);
			console.log(res);
		},
		error => {
			console.log(error);
		}
	);
	
  }

}
