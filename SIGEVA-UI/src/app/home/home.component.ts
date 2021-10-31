import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  mensajeBoton1: string;
  mensajeBoton2: string;
  mensajeBoton3: string;
  mensajeBoton4: string;
  mensajeBoton5: string;
  mensajeBoton6: string;
  constructor() {
	this.mensajeBoton1="Crear Usuarios";
	this.mensajeBoton2="Crear Centro de Salud";
	this.mensajeBoton3="Establecer Cupos";
	this.mensajeBoton4="Ver Usuarios";
	this.mensajeBoton5="Asignar Sanitarios";
	this.mensajeBoton6="Solicitar Cita";
}

  ngOnInit(): void {
  }

}
