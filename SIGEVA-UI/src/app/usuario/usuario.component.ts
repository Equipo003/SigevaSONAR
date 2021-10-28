import { Component, OnInit } from '@angular/core';
import {JsonService} from "../Service/json.service";
import {Usuario} from "../Model/Usuario";

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
    this.json.getJson("user/getUsuariosByRol").subscribe(
      result=> {
        this.sanitarios = JSON.parse(result);
        console.log(result);
      }, error=> {
        console.log(error);
      });
  }

}
