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

 enviarDatosBack() {
    console.log(this.centroSalud);
   this.generandoCupos = "Generando cupos de citas y centro...";
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
	this.json.postJson("cupo/prepararCupos", this.centroSalud).subscribe(
        result => {
          this.message = "";
         // setTimeout(function(){ window.location.reload(); }, 3000);
        },err=> {
          this.errorMessage = err.error.message;
 		 // setTimeout(function(){ self.errorMessage=""; }, 3000);
        });


  }
}
