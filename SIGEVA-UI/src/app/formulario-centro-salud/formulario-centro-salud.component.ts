import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-formulario-centro-salud',
  templateUrl: './formulario-centro-salud.component.html',
  styleUrls: ['./formulario-centro-salud.component.css']
})
export class FormularioCentroSaludComponent implements OnInit {

  a:number;
  nombre: String;
  constructor() {
	this.a=10;
	this.nombre = "";
	}

  ngOnInit(): void {
  }

}
