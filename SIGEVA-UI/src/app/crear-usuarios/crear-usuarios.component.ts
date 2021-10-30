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
  public centros: CentroSalud[];
  public rolSeleccionado: string;
  public centroSeleccionado: String;
  public nombreRol:string;
  public usuario: Usuario;
  public message: string;
  public errorMessage: string;

  constructor(private json: JsonService) {
    this.roles = [];
    this.centros = []
    this.rolSeleccionado = "";
    this.centroSeleccionado = "";
    this.nombreRol = "";

    this.usuario = new Usuario("", "", "", "", "", "",
      "", "", "", "");
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit() {
    this.message = "";
    this.errorMessage = "";
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
        this.rolSeleccionado = this.roles[0].nombre;
      }, error=> {
        console.log(error);
      });

    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
        this.centroSeleccionado = this.centros[0].nombreCentro;
      }, error=> {
        console.log(error);
      });
  };

  capturarFile (event : any) {
    let self = this;
    var file = event.target.files[0];
    var reader = new FileReader();
    reader.onload = function () {
      if (typeof reader.result === "string") {
        self.usuario.imagen = ("data:image/png;base64," + btoa(reader.result));
      }
    }
    reader.readAsBinaryString(file);
  }

  checkRol(){
    let self = this;
    this.roles.forEach(function(rol2 : Rol){
      if (rol2.nombre === self.rolSeleccionado){
        self.usuario.rol = rol2.id;
        self.nombreRol = rol2.nombre;
      }
    });
  }

  checkCentro(){
    let self = this;
    this.centros.forEach(function(centro2 : CentroSalud){
      if (centro2.nombreCentro === self.centroSeleccionado){
        self.usuario.centroSalud = centro2.id
      }
    });
  }

  enviarDatosBack() {
    this.checkRol();
    this.checkCentro();
    this.json.postJson("user/crearUsuario" + this.nombreRol, this.usuario).subscribe(
      result => {
        this.errorMessage = "";
        this.message = "Usuario creado correctamente"
        setTimeout('document.location.reload()',2000);
      },err=> {
        this.errorMessage = err.error.message;
        console.log(err);
    });
  }
}
