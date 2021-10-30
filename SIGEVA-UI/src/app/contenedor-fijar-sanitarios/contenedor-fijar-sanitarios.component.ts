import { Component, OnInit } from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {CentroSalud} from "../Model/centro-salud";
import {JsonService} from "../Service/json.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-fijar-sanitarios',
  templateUrl: './contenedor-fijar-sanitarios.component.html',
  styleUrls: ['./contenedor-fijar-sanitarios.component.css']
})
export class ContenedorFijarSanitariosComponent implements OnInit {

  sanitarios: Usuario[];
  centros: CentroSalud[]

  constructor(private json: JsonService) {
    this.sanitarios = [];
    this.centros = [];
  }

  ngOnInit(): void {
    this.getCentros();
    this.getParams();
    this.changeCentros();
  }

  changeCentros() {
    let self = this;
    this.centros.forEach(function (centro2: CentroSalud) {
      self.sanitarios.forEach(function (sanitario: Usuario) {
        if (sanitario.centroSalud === centro2.id) {
          sanitario.centroSalud = centro2.nombreCentro;
        }

      });
    })
  }

  getParams() {
    let params = new HttpParams({
      fromObject: {
        rol: "141abdc8-0f85-43c0-8c51-dfdd2c039ef4"
      }
    });
    let self = this;
    this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
      result => {
        this.sanitarios = JSON.parse(result)
        this.centros.forEach(function (centro2: CentroSalud) {
          self.sanitarios.forEach(function (sanitario: Usuario) {
            if (sanitario.centroSalud === centro2.id) {
              sanitario.centroSalud = centro2.nombreCentro;
            }

          });
        })
      }, error => {
        console.log(error);
      });
  }

  getCentros() {
    this.json.getJson("user/getCentros").subscribe(
      result => {
        this.centros = JSON.parse(result);
      }, error => {
        console.log(error);
      });
  }
}
