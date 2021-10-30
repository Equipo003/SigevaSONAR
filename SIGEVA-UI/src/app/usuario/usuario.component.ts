import { Component, OnInit } from '@angular/core';
import {JsonService} from "../Service/json.service";
import {Usuario} from "../Model/Usuario";
import {Rol} from "../Model/rol";

import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  sanitarios: Usuario[];

  constructor(private json: JsonService) {
    this.sanitarios = [];
  }

  ngOnInit(): void {
   this.getParams();
  }

  getParams(){
    let params = new HttpParams({fromObject:{
    rol:"141abdc8-0f85-43c0-8c51-dfdd2c039ef4"}});
        this.json.getJsonP("user/getUsuariosByRol", params).subscribe(
            result=> {
              this.sanitarios = JSON.parse(result)
              console.log(this.sanitarios);
            }, error=> {
              console.log(error);
            });


  }

}
