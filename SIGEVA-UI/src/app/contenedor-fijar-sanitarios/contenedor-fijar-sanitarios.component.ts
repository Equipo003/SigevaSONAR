import {Component, OnInit} from '@angular/core';
import {JsonService} from "../Service/json.service";
import {HttpParams} from "@angular/common/http";
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {Rol} from "../Model/rol";

@Component({
  selector: 'app-fijar-sanitarios',
  templateUrl: './contenedor-fijar-sanitarios.component.html',
  styleUrls: ['./contenedor-fijar-sanitarios.component.css']
})
export class ContenedorFijarSanitariosComponent implements OnInit {

  sanitarios: UsuarioConObjetos[];
  roles: Rol[];
  id: string;

  constructor(private json: JsonService) {
    this.sanitarios = [];
    this.roles = [new Rol("", "")];
    this.id = "";
  }

  ngOnInit(): void {
    this.getParams();
    this.getRoles();
  }

   getRoles(){
     let self = this;
     this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
       }, error=> {
        console.log(error);
       });
     console.log(this.id);
  }
  
  // setRolSanitario(){
  //   let self = this;
  //   this.roles.forEach(function(rol: Rol){
  //     if (rol.nombre === "Sanitario"){
  //       Object.assign(self.id, rol.id);
  //     }
  //   })
  //   console.log(this.id);
  // }

  getParams() {
    console.log(this.id);
    let params = new HttpParams({
      fromObject: {
        rol: "e24bf973-e26e-47b7-b8f4-83fa13968221"
      }
    });
    this.json.getJsonP("user/getUsuariosByRol/", params).subscribe(
      result => {
        this.sanitarios = JSON.parse(result)
      }, error => {
        console.log(error);
      });
  }
}
