import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-configuracion-cupos',
  templateUrl: './configuracion-cupos.component.html',
  styleUrls: ['./configuracion-cupos.component.css']
})
export class ConfiguracionCuposComponent implements OnInit {

  duracionJornadaHoras : number;
  duracionJornadaMinutos : number;
  duracionMinutos : number;
  numeroPacientes : number;
  pacientesVacunadosDia : number;
  horaInicio : number;
  minutoInicio : number;
  fechaInicio : string;
  fecha : Date;
  constructor() {
    this.duracionMinutos = 0;
    this.numeroPacientes = 0;
    this.pacientesVacunadosDia = 0;
    this.duracionJornadaHoras = 0;
    this.duracionJornadaMinutos = 0;
    this.fechaInicio = '';
    this.minutoInicio = 0;
    this.horaInicio = 0;
    this.fecha = new Date();
  }

  ngOnInit(): void {
  }

  comprobar(){
    this.fecha = new Date(this.fechaInicio);
    this.fecha.setHours(this.fecha.getHours()+this.duracionJornadaHoras);
    this.fecha.setMinutes(this.fecha.getMinutes()+this.duracionJornadaMinutos);
  }
}
