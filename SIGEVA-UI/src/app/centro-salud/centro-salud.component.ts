import { Component, OnInit, Input } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';

@Component({
  selector: 'app-centro-salud',
  templateUrl: './centro-salud.component.html',
  styleUrls: ['./centro-salud.component.css']
})
export class CentroSaludComponent implements OnInit {
  @Input() cs: CentroSalud;

  constructor() {
	this.cs = new CentroSalud("", "", 0);
  }

  ngOnInit(): void {
	
  }

}
