import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import {Rol} from "../Model/rol";
import {Usuario} from "../Model/Usuario";
import {CentroSalud} from "../Model/centro-salud";

@Component({
  selector: 'app-crear-usuarios',
  templateUrl: './crear-usuarios.component.html',
  styleUrls: ['./crear-usuarios.component.css'],
  providers: [JsonService],
})
export class CrearUsuariosComponent implements OnInit {

  public roles: Rol[];
  public seleccionado: string;
  rol: string;
  centro: string;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;

  constructor(private json: JsonService) {
    this.roles = [];
    this.seleccionado = "";
    this.rol = "";
    this.centro = "";
    this.username = "";
    this.correo = "";
    this.hashPassword = "";
    this.dni = "";
    this.nombre = "";
    this.apellidos = "";
    this.fechaNacimiento = "";
    this.imagen = "";
  }

  ngOnInit() {
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
        this.seleccionado = this.roles[0].nombre;
      }, error=> {
        console.log(error);
      });
  };

  checkRol(){
    let self = this;
    this.roles.forEach(function(rol2 : Rol){
      if (rol2.nombre === self.seleccionado){
        self.rol = rol2.id;
      }
    });
  }

  enviarDatosBack() {
    this.checkRol();
    console.log(this.rol);
    var usuario: Usuario = new Usuario(this.rol, this.centro, this.username, this.correo, this.hashPassword, this.dni,
      this.nombre, this.apellidos, this.fechaNacimiento, this.imagen);
    this.json.postJson("user/crearUsuario",usuario).subscribe((res: any) => {
      console.log(res);
    });
  }
}
