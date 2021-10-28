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

  id: string;
  numVacunasDisponibles:number;
  direccion: string;
  nombreCentro: string;
  mensajeRespuesta: string;
  mostrarTexto: boolean;
  positivo: boolean;

  constructor(private json: JsonService) {

	this.numVacunasDisponibles=0;
	this.direccion = "";
	this.nombreCentro ="";
	this.mensajeRespuesta="";
    this.id = "";
	this.mostrarTexto =false;
	this.positivo= false;
	}

  ngOnInit(): void {

  }

 enviarDatosBack() { //NO DEBE GUARDAR SI HAY UN CAMPO NULO
	var centroSalud: CentroSalud = new CentroSalud(this.direccion,this.nombreCentro,this.numVacunasDisponibles);
	this.json.postJson("user/newCentroSalud",centroSalud).subscribe(result => {
		 this.mostrarTexto= true;
		 this.positivo= true;
         this.mensajeRespuesta = "Centro de salud guardado con éxito.";
		 this.timeoutEsconderElementos();
      }, error =>{
		this.mostrarTexto= true;
		this.positivo= false;
		this.mensajeRespuesta = "Centro de salud no guardado."
		this.timeoutEsconderElementos();
});
  }

	vaciarCampos(): void {
	this.numVacunasDisponibles=0;
	this.direccion = "";
	this.nombreCentro ="";
  }

	timeoutEsconderElementos():void{
		this.vaciarCampos();
		setTimeout(() => {
  		this.mostrarTexto =false;
		}, 5000);
	}

}
