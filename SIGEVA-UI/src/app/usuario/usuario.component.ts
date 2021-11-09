import { Component, Input } from '@angular/core';
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {CentroSalud} from "../Model/centro-salud";
import {Rol} from "../Model/rol";

@Component({
	selector: 'app-usuario',
	templateUrl: './usuario.component.html',
	styleUrls: ['./usuario.component.css']
})

export class UsuarioComponent {
	@Input() usuario: UsuarioConObjetos;
	constructor() {
		this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("", "",
      0), "", "", "", "", "", "", "",
      "");
	}
}
