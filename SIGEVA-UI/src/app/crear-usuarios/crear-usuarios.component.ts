import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import {Rol} from "../Model/rol";

@Component({
  selector: 'app-crear-usuarios',
  templateUrl: './crear-usuarios.component.html',
  styleUrls: ['./crear-usuarios.component.css'],
  providers: [JsonService],
})
export class CrearUsuariosComponent implements OnInit {

  public roles: Rol[];
  public seleccionado: string = "";

  constructor(private json: JsonService) {
    this.roles = [];
  }

  ngOnInit() {
    this.json.getJson("user/getRoles").subscribe(
      result=> {
        this.roles = JSON.parse(result);
        console.log(this.roles);
      }, error=> {
        console.log(error);
      });
  };
}
