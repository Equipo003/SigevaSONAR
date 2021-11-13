import { Component, OnInit } from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {Rol} from "../Model/rol";
import {HttpParams} from "@angular/common/http";
import {JsonService} from "../Service/json.service";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {Paciente} from "../Model/paciente";
import {CentroSalud} from "../Model/centro-salud";

@Component({
  selector: 'app-listado-pacientes',
  templateUrl: './listado-pacientes.component.html',
  styleUrls: ['./listado-pacientes.component.css']
})
export class ListadoPacientesComponent implements OnInit {
  pacientes : Usuario[];
  roles : Rol[];
  pacienteSeleccionado:Paciente;
  startDate:Date;
  endDate : Date;
  citas : CitaConObjetos[];

  constructor(private json:JsonService) {
    this.pacientes = [];
    this.roles = [];
    this.pacienteSeleccionado = new Paciente(new Rol("1", "Paciente"), new CentroSalud("direccion", "nombre", 1), "vasilesan", "", "", "",
      "", "", "", "", 0);
    this.startDate = new Date();
    this.endDate = new Date();
    this.citas = [];

  }

  ngOnInit(): void {
    //this.getRoles();
    this.getPacientePrueba();
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

  vacunar(paciente : Paciente){
    this.pacienteSeleccionado = paciente;
  }

  mostrar(){
    console.log(this.endDate);
  }

  getPacientePrueba(){
    this.json.getJson("cita/getPacientePrueba").subscribe(
      result => {
        let paciente : Paciente;
        paciente = JSON.parse(result);
        console.log(paciente);
        this.getCitas(paciente);
      }, error => {
        console.log(error);
      });
  }


  getCitas(paciente : Paciente) {
    this.json.postJson("cita/obtenerCitasFuturasDelPaciente", paciente).subscribe(
      result => {
        this.citas = JSON.parse(JSON.stringify(result));
        console.log(result)
      }, error => {
        console.log(error);
      });
  }

}
