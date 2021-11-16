import { Component, OnInit } from '@angular/core';
import {CupoCitas} from "../Model/cupo-citas";
import {JsonService} from "../Service/json.service";
import {LoginUsuario} from "../Model/loginUsuario";
import {TokenService} from "../Service/token.service";
import {HttpParams} from "@angular/common/http";
import {CentroSalud} from "../Model/centro-salud";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";

@Component({
  selector: 'app-contenedor-citas',
  templateUrl: './contenedor-citas.component.html',
  styleUrls: ['./contenedor-citas.component.css']
})
export class ContenedorCitasComponent implements OnInit {

  citas: CitaConObjetos[] = [];
  idUsuario: string;
  fechaCita: string = "";
  minDate: Date;
  maxDate: Date;
  editMode: boolean = false;
  daySelected: boolean = false;
  rangosHoras: string[] = [];
  selectedTime: string = "";

  constructor(private json: JsonService, private tokenService: TokenService) {
    this.idUsuario = String(tokenService.getIdUsuario());
    this.rangosHoras.push("08:00");
    this.rangosHoras.push("09:00");
    this.rangosHoras.push("10:00");
    this.rangosHoras.push("11:00");

    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = today.getMonth();
    const currentDay = today.getDate();

    this.minDate = new Date(currentYear, currentMonth, currentDay);
    this.maxDate = new Date(currentYear + 1, 0, 31);
  }

  addEvent(event: MatDatepickerInputEvent<Date>) {
    console.log(event.value);
    this.daySelected = true;
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
        this.citas.push(this.citas[0]);
      }
    );
  }

  editarCita(){
    this.editMode = true;
  }


}
