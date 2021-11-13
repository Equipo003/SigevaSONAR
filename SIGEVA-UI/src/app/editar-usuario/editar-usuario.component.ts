import { Component, OnInit } from '@angular/core';
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {JsonService} from "../Service/json.service";
import {ActivatedRoute, Params} from "@angular/router";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit {

  public centros: CentroSalud[];
  public usuario: UsuarioConObjetos;
  public message: string;
  public errorMessage: string;
  public idUsuario: string;

  constructor(private json: JsonService, private rutaActiva: ActivatedRoute) {
    this.centros = []
    this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("", "", 0),
      "", "", "", "", "", "", "", "");
    this.errorMessage = "";
    this.message = "";
    this.idUsuario = "";
  }

  ngOnInit(){
    this.message = "";
    this.errorMessage = "";
    this.getCentros();
    this.rutaActiva.params.subscribe(
      (params: Params) => {
        this.idUsuario = params['idUsuario'];
      }
    );
    this.getUsuarioById();
  }

  getCentros(){
    this.json.getJson("user/getCentros").subscribe(
      result=> {
        this.centros = JSON.parse(result);
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

  onChangeCentro($event: any) {
    this.usuario.centroSalud = $event;
  }

  getUsuarioById(){
    let params = new HttpParams({
      fromObject: {
        idUsuario: this.idUsuario
      }
    });
    this.json.getJsonP("user/getUsuarioById", params).subscribe(
      result=> {
        this.usuario = JSON.parse(result);
        this.usuario.fechaNacimiento = this.usuario.fechaNacimiento.substr(0, 10);
      }, error=> {
        console.log(error);
      });
  }
}
