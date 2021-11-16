import {Component, Input, OnInit} from '@angular/core';
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {CupoCitas} from "../Model/cupo-citas";
import {Paciente} from "../Model/paciente";
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-cita-editar',
  templateUrl: './cita-editar.component.html',
  styleUrls: ['./cita-editar.component.css']
})
export class CitaEditarComponent implements OnInit {

  @Input() cita: CitaConObjetos;
  fechaCita: string = "";
  minDate: Date;
  maxDate: Date;
  editMode: boolean = false;
  daySelected: boolean = false;
  rangosHoras: string[] = [];
  selectedTime: string = "";

  constructor() {
    this.cita = new CitaConObjetos(new CupoCitas("", new CentroSalud("", "", 0, new Vacuna("", 0, 0, ""), ""), new Date()),
      0,
      new Paciente(new Rol("", ""),
                  new CentroSalud("", "", 0, new Vacuna("", 0, 0), ""),
                "", "", "", "", "", "", "", "", 0, ""));
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

    this.fechaCita = String(this.cita.cupo.fechaYHoraInicio).substr(0, 10)
  }

  ngOnInit(): void {
  }

  addEvent(event: MatDatepickerInputEvent<Date>) {
    console.log(event.value);
    this.daySelected = true;
  }

  editarCita() {
    this.editMode = true;
  }

}
