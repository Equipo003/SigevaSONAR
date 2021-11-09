import { Component, OnInit } from '@angular/core';
import { Usuario } from '../Model/Usuario';
import { JsonService } from '../Service/json.service';
import { HttpParams } from "@angular/common/http";
import { CentroSalud } from '../Model/centro-salud';
import { Rol } from '../Model/rol';
import { UsuarioConObjetos } from "../Model/Usuario-con-objetos";

@Component({
	selector: 'app-usuarios-sistema',
	templateUrl: './usuarios-sistema.component.html',
	styleUrls: ['./usuarios-sistema.component.css']
})
export class UsuariosSistemaComponent implements OnInit {
	usuarios: UsuarioConObjetos[];
	roles: Rol[];
  	rolMostrado: string;
  	rolSeleccionado: string;

	constructor(private json: JsonService) {
		this.usuarios = [];
		this.roles = [];
    this.rolSeleccionado = "Todos";
    this.rolMostrado = "Todos";
	}
	ngOnInit(): void {
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

  cambiarRolaId(){
    if (this.rolSeleccionado != "Todos"){
      let self = this;
      this.roles.forEach(function (rol: Rol) {
        if (self.rolSeleccionado === rol.nombre) {
          self.rolSeleccionado = rol.id;
        }
      })
    }
  }

	cargarUsuarios() {
    this.rolMostrado = this.rolSeleccionado;
    this.cambiarRolaId();
		let params = new HttpParams({
			fromObject: {
				rol: this.rolSeleccionado
			}
		});
    this.rolSeleccionado = "Todos";
		this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
			result => {
				this.usuarios = JSON.parse(result);
			}, error => {
				console.log(error);
			});
	}
}
