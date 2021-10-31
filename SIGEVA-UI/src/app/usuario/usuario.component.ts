import { Component, OnInit, Input } from '@angular/core';
import { Usuario } from '../Model/Usuario';
import { CentroSalud } from '../Model/centro-salud';
import { JsonService } from '../Service/json.service';

@Component({
	selector: 'app-usuario',
	templateUrl: './usuario.component.html',
	styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
	@Input() usuario: Usuario;
	constructor(private json: JsonService) {
		this.usuario = new Usuario("", "", "", "", "", "", "", "", "", "");

	}
	ngOnInit(): void {
		
	}

}
