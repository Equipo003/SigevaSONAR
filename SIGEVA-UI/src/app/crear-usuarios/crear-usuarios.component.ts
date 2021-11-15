import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {Vacuna} from "../Model/vacuna";

@Component({
  selector: 'app-crear-usuarios',
  templateUrl: './crear-usuarios.component.html',
  styleUrls: ['./crear-usuarios.component.css'],
  providers: [JsonService],
})
export class CrearUsuariosComponent implements OnInit {

  public roles: Rol[];
  public centros: CentroSalud[];
  public usuario: UsuarioConObjetos;
  public message: string;
  public errorMessage: string;

  constructor(private json: JsonService) {
    this.roles = [];
    this.centros = []

    this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""),
      "", "", "", "", "", "", "", "");
    this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(){
    this.message = "";
    this.errorMessage = "";
    this.getRoles();
    this.getCentros();
  }

  getRoles(){
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
        this.usuario.rol = this.roles[0];
      }, error=> {
        console.log(error);
      });
  }

  getCentros(){
    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
        this.usuario.centroSalud = this.centros[0];
      }, error=> {
        console.log(error);
      });
  }

  capturarFile (event : any) {
    let self = this;
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      if (typeof reader.result === "string") {
        self.usuario.imagen = ("data:image/png;base64," + btoa(reader.result));
      }
    }
    reader.readAsBinaryString(file);
  }

  enviarDatosBack() {
    this.json.postJson("user/crearUsuario" + this.usuario.rol.nombre, this.usuario).subscribe(
      result => {
        if (result === null) {
          this.errorMessage = "";
          this.message = "Usuario creado correctamente"
        }
        setTimeout('document.location.reload()',2000);
      },err=> {
        this.errorMessage = err.error.message;
        console.log(err);
    });
  }

  onChangeCentro($event: any) {
    this.usuario.centroSalud = $event;
  }

  onChangeRol($event: any) {
    this.usuario.rol = $event;
  }
}
