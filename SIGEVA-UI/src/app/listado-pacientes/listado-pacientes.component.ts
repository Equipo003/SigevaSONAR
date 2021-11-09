import { Component, OnInit } from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {Rol} from "../Model/rol";
import {HttpParams} from "@angular/common/http";
import {JsonService} from "../Service/json.service";

@Component({
  selector: 'app-listado-pacientes',
  templateUrl: './listado-pacientes.component.html',
  styleUrls: ['./listado-pacientes.component.css']
})
export class ListadoPacientesComponent implements OnInit {
  pacientes : Usuario[];
  roles : Rol[];
  pacienteSeleccionado:Usuario;

  constructor(private json:JsonService) {
    this.pacientes = [];
    this.roles = [];
    this.pacienteSeleccionado = new Usuario("", "eb972b1a-b1f0-41c9-bae4-ac729b6b967b", "vasilesan", "", "", "",
      "", "", "", "", "25100ecd-136f-43a5-886e-0e7de585d5ea");
  }

  ngOnInit(): void {
    this.getRoles();
  }

  getRoles() {
    let self = this;
    this.json.getJson("user/getRoles").subscribe(
      result => {
        this.roles = JSON.parse(result);
        this.getRolPaciente();
      }, error => {
        console.log(error);
      });
  }

  getRolPaciente() {
    let self = this;
    this.roles.forEach(function(rol: Rol) {
      if (rol.nombre === "Sanitario") {
        self.getParams(rol);
      }
    })
  }

  getParams(rolAux: Rol) {
    let params = new HttpParams({
      fromObject: {
        rol: rolAux.id,
      }
    });
    this.json.getJsonP("user/getUsuariosByRol/", params).subscribe(
      result => {
        this.pacientes = JSON.parse(result)
      }, error => {
        console.log(error);
      });
  }

  vacunar(paciente : Usuario){
    this.pacienteSeleccionado = paciente;
  }

}
