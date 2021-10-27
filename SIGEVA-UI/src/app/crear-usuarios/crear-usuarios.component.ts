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

  constructor(private json: JsonService) {
    this.roles = [];
    this.centros = []
    this.rolSeleccionado = "";
    this.centroSeleccionado = "";
    this.nombreRol = "";
    // this.rol = "";
    this.usuario = new Usuario("", new CentroSalud("", "", 0), "", "", "", "",
      "", "", "", "");
  }

  ngOnInit() {
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
        this.rolSeleccionado = this.roles[0].nombre;
        console.log(this.roles);
      }, error=> {
        console.log(error);
      });

    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
        this.centroSeleccionado = this.centros[0].nombreCentro;
        console.log(this.centros);
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
        self.usuario.centroSalud = centro2
      }
    });
  }

  enviarDatosBack() {
    this.checkRol();
    this.checkCentro();
    console.log(this.usuario);
    this.json.postJson("user/crearUsuario" + this.nombreRol, this.usuario).subscribe((res: any) => {
      console.log(res);
    });
  }
}
