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

  centroSalud : CentroSalud;
  public message: string;
  public errorMessage: string;
  public generandoCupos: string;

  constructor(private json: JsonService) {
    this.centroSalud = new CentroSalud("", "", 0);
    this.errorMessage = "";
    this.message = "";
    this.generandoCupos = "";
	}

  ngOnInit(): void {

  }

 enviarDatosBack() { //NO DEBE GUARDAR SI HAY UN CAMPO NULO y controlar número en backend
	//var centroSalud: CentroSalud = new CentroSalud(this.direccion,this.nombreCentro,this.numVacunasDisponibles);
   this.generandoCupos = "Generando cupos de citas...";
	this.json.postJson("user/newCentroSalud",this.centroSalud).subscribe(
    result => {
      this.errorMessage = "";
      this.generandoCupos = "";
      this.message = "Centro creado correctamente";
      setTimeout('document.location.reload()',2000);
      }, err =>{
      this.generandoCupos = "";
      this.errorMessage = err.error.message;
    });
  }
}
