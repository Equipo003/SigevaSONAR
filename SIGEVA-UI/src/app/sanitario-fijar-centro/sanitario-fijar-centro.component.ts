import {Component, Input, OnInit} from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {CentroSalud} from "../Model/centro-salud";
import {JsonService} from "../Service/json.service";

@Component({
  selector: 'app-sanitario-fijar-centro',
  templateUrl: './sanitario-fijar-centro.component.html',
  styleUrls: ['./sanitario-fijar-centro.component.css']
})
export class SanitarioFijarCentroComponent implements OnInit {

  @Input() sanitario: Usuario;
  newSanitario: Usuario;
  centros: CentroSalud[]
  newCentroSeleccionado: string;
  errorMessage: string;
  message: string;

  constructor(private json: JsonService) {
    this.centros = [];
    this.sanitario = new Usuario("", "","", "", "", "", "", "", "", "");
    this.newSanitario = new Usuario("", "","", "", "", "", "", "", "", "");
    this.newCentroSeleccionado = "";
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {
    this.getCentros();
    this.copiaSanitario();
  }

  copiaSanitario(){
    Object.assign(this.newSanitario, this.sanitario)
  }

  getCentros() {
    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
        this.newCentroSeleccionado = this.centros[0].nombreCentro;
      }, error=> {
        console.log(error);
      });
  }

  fijarCentro() {
    let self = this;
    this.centros.forEach(function (centro2: CentroSalud) {
      if (self.newCentroSeleccionado === centro2.nombreCentro) {
        self.newSanitario.centroSalud = centro2.id;
      }
    })
  }

  enviarDatosBack() {
    if (this.newCentroSeleccionado === this.sanitario.centroSalud){
      this.errorMessage = "Elige un centro distinto al ya fijado";
    }
    else {
      this.fijarCentro();
      let self = this;
      this.json.putJsonSanitario("user/fijarCentro", this.newSanitario.username, this.newSanitario.centroSalud).subscribe(
        result => {
          this.sanitario.centroSalud = this.newCentroSeleccionado;
          this.message = "Centro fijado correctamente";
          setTimeout(function(){ self.message = ""; }, 2000);
        },err=> {
          this.errorMessage = err.error.message;
          console.log(err);
        });
    }
  }
}
