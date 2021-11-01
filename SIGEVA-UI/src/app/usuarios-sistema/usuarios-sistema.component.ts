import { Component, OnInit } from '@angular/core';
import { Usuario } from '../Model/Usuario';
import { JsonService } from '../Service/json.service';
import { HttpParams } from "@angular/common/http";
import { CentroSalud } from '../Model/centro-salud';
import { Rol } from '../Model/rol';

@Component({
	selector: 'app-usuarios-sistema',
	templateUrl: './usuarios-sistema.component.html',
	styleUrls: ['./usuarios-sistema.component.css']
})
export class UsuariosSistemaComponent implements OnInit {
	usuarios: Usuario[];
	cs: CentroSalud[];
	roles: Rol[];

	constructor(private json: JsonService) {
		this.usuarios = [];
		this.cs = [];
		this.roles = [];
	}
	ngOnInit(): void {
		this.cargarCentros();
		this.cargarRoles();
		this.cargarUsuarios();
	}


	cargarRoles() {
		this.json.getJson("user/getRoles").subscribe(
			(res: any) => {
				this.roles = JSON.parse(res);
			},
			error => {
				console.log(error);
			}
		);
	}
	cargarCentros() {
		this.json.getJson('user/getCentros').subscribe(
			(res: any) => {
				this.cs = JSON.parse(res);
			}, error => {
				console.log(error);
			});
	}

	cargarUsuarios() {
		let params = new HttpParams({
			fromObject: {
				rol: "all"
			}
		});
		let self = this;
		this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
			(res: any) => {
				this.usuarios = JSON.parse(res);
				this.cs.forEach(function(centro2: CentroSalud) {
					self.usuarios.forEach(function(usuario: Usuario) {
						self.roles.forEach(function(rol: Rol) {
							if (usuario.centroSalud === centro2.id) {
								usuario.centroSalud = centro2.nombreCentro;
							};
							if (usuario.rol === rol.id) {
								usuario.rol = rol.nombre;
							}
						});
					});
				});
			}, error => {
				console.log(error);
			});
	}
}
