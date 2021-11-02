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
  roles2: Rol[];
  rolMostrado: string;
  rolSeleccionado: string;

	constructor(private json: JsonService) {
		this.usuarios = [];
		this.cs = [];
		this.roles = [];
    this.roles2 = [];
    this.rolSeleccionado = "Todos";
    this.rolMostrado = "Todos";
	}
	ngOnInit(): void {
		this.cargarCentros();
		this.cargarRoles();
		this.cargarUsuarios();
    this.cargarRoles2();
	}

  cargarRoles2(){
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles2 = JSON.parse(result);
      }, error=> {
        console.log(error);
      });
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

  cambiarRolaId(){
    if (this.rolSeleccionado != "Todos"){
      let self = this;
      this.roles2.forEach(function (rol: Rol) {
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
		let self = this;
		this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
			(res: any) => {
				this.usuarios = JSON.parse(res);
				this.cs.forEach(function(centro2: CentroSalud) {
					self.usuarios.forEach(function(usuario: Usuario) {
						self.roles.forEach(function(rol: Rol) {
							if (usuario.centroSalud === centro2.id) usuario.centroSalud = centro2.nombreCentro;
							if (usuario.rol === rol.id)	usuario.rol = rol.nombre;
						});
					});
				});
			}, error => {
				console.log(error);
			});
	}
}
