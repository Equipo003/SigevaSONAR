import { Component, OnInit } from '@angular/core';
import {CupoCitas} from "../Model/cupo-citas";
import {JsonService} from "../Service/json.service";
import {LoginUsuario} from "../Model/loginUsuario";
import {TokenService} from "../Service/token.service";
import {HttpParams} from "@angular/common/http";
import {CentroSalud} from "../Model/centro-salud";

@Component({
  selector: 'app-contenedor-citas',
  templateUrl: './contenedor-citas.component.html',
  styleUrls: ['./contenedor-citas.component.css']
})
export class ContenedorCitasComponent implements OnInit {

  citas: CupoCitas[];
  cita1: CupoCitas;
  cita2: CupoCitas;
  idUsuario: string;

  constructor(private json: JsonService, private tokenService: TokenService) {
    this.citas = [];
    this.idUsuario = String(tokenService.getIdUsuario());
    this.cita1 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
    this.cita2 = new CupoCitas("", new CentroSalud("", "", 0, ""), new Date());
  }

  ngOnInit(): void {
    this.getCitas();
  }

  getCitas(){
    let params = new HttpParams({
      fromObject: {
        idPaciente: "ca4cbb02-6325-4e6f-8edf-af3b4de6eedc",
      }
    });
    this.json.getJsonPJ('/cita/obtenerCitasFuturasDelPaciente', params).subscribe(
      data => {
        // console.log(data);
        this.citas = data;
        this.cita1 = this.citas[0];

        console.log(this.cita1)
      }
    );
  }

}
