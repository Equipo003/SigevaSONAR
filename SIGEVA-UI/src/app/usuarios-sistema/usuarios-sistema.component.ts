import { Component, OnInit } from '@angular/core';
import { Usuario } from '../Model/Usuario';
import { JsonService } from '../Service/json.service';
import {HttpParams} from "@angular/common/http";

@Component({
	selector: 'app-usuarios-sistema',
	templateUrl: './usuarios-sistema.component.html',
	styleUrls: ['./usuarios-sistema.component.css']
})
export class UsuariosSistemaComponent implements OnInit {
	usuarios: Usuario[];

	constructor(private json: JsonService) {
		this.usuarios = [];
	}
	ngOnInit(): void {
		this.cargarUsuarios();
	}

	cargarUsuarios() {
		let params = new HttpParams({
			fromObject: {
				rol: "all"
			}
		});
		let self = this;
		this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
			(res : any)=>{
				this.usuarios = JSON.parse(res);
				console.log(res);
			},
			(error : any)=>{
				console.log(error);
			}
			);
	}
}
