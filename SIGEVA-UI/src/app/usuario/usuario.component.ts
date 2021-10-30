import {Component, Input, OnInit} from '@angular/core';
import {JsonService} from "../Service/json.service";
import {Usuario} from "../Model/Usuario";
import {Rol} from "../Model/rol";

import { HttpParams } from '@angular/common/http';
import {CentroSalud} from "../Model/centro-salud";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  @Input() sanitario: Usuario;
  centros: CentroSalud[]
  newCentroSeleccionado: string;
  errorMessage: string;
  message: string;

  constructor(private json: JsonService) {
    this.centros = [];
    this.sanitario = new Usuario("", "","", "", "", "", "", "", "", "");
    this.newCentroSeleccionado = "";
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {
    this.getCentros();

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

  enviarDatosBack() {
    if (this.newCentroSeleccionado === this.sanitario.centroSalud){
      this.errorMessage = "Elige un centro distinto al ya fijado";
    }
    else {
      this.message = "Usuario creado correctamente";
    }
  }
}
