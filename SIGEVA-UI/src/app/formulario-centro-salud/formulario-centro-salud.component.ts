import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import { CentroSalud } from '../Model/centro-salud';

@Component({
  selector: 'app-formulario-centro-salud',
  templateUrl: './formulario-centro-salud.component.html',
  styleUrls: ['./formulario-centro-salud.component.css'],
  providers: [JsonService],
})
export class FormularioCentroSaludComponent implements OnInit {

  numVacunasDisponibles:number;
  direccion: string;
  nombreCentro: string;
  constructor(private json: JsonService) {
	this.numVacunasDisponibles=0;
	this.direccion = "";
	this.nombreCentro ="";
	}

  ngOnInit(): void {
  }
 enviarDatosBack() {
	var centroSalud: CentroSalud = new CentroSalud(this.direccion,this.nombreCentro,this.numVacunasDisponibles);
	this.json.postJson("user/newCentroSalud",centroSalud).subscribe((res: any) => {
           console.log(res);
      });
  }

}
