import {Component, EventEmitter, OnInit} from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {Rol} from "../Model/rol";
import {HttpParams} from "@angular/common/http";
import {JsonService} from "../Service/json.service";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {Paciente} from "../Model/paciente";
import {CentroSalud} from "../Model/centro-salud";
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {Vacuna} from "../Model/vacuna";
import {CupoCitas} from "../Model/cupo-citas";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-listado-pacientes',
  templateUrl: './listado-pacientes.component.html',
  styleUrls: ['./listado-pacientes.component.css']
})
export class ListadoPacientesComponent implements OnInit {
  pacientes : Usuario[];
  roles : Rol[];
  citaSeleccionada:CitaConObjetos;
  citas : CitaConObjetos[];
  pacienteCentroSalud : CentroSalud;
  pacienteSeleccionado:boolean;
  today : FormControl;

  constructor(private json:JsonService) {
    this.pacientes = [];
    this.roles = [];
    this.pacienteCentroSalud = new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), "");
    this.citaSeleccionada = new CitaConObjetos(new CupoCitas("",this.pacienteCentroSalud, new Date() ), 0, new Paciente(new Rol("1", "Paciente"),new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), ""), "vasilesan", "", "", "",
      "", "", "", "", 0));
    this.citas = [];
    this.pacienteSeleccionado = false;
    this.today = new FormControl(new Date());
  }

  ngOnInit(): void {
    //this.getRoles();
    this.getPacientePrueba();
    this.today = new FormControl(new Date());
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

  vacunar(cita : CitaConObjetos){
    this.citaSeleccionada = cita;
    this.pacienteSeleccionado = true;
  }


  getPacientePrueba(){
    this.json.getJson("cita/getPacientePrueba").subscribe(
      result => {
        let paciente : Paciente;
        paciente = JSON.parse(result);
        this.pacienteCentroSalud = paciente.centroSalud;
      }, error => {
        console.log(error);
      });
  }

  dataChangeEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.getPacientesFecha(event);
  }

  getPacientesFecha(event : MatDatepickerInputEvent<Date>){
    let params = new HttpParams({
      fromObject: {
        'centroSaludDTO': JSON.stringify(this.pacienteCentroSalud),
        'fecha' : JSON.stringify(event.value),
      }
    });
    this.json.getJsonP("cita/obtenerCitasFecha", params).subscribe(
      result => {
        this.citas = JSON.parse(result);
        console.log(this.citas[0]);
      }, error => {
        console.log(error);
      });
  }

  getPacientesHoy(){
    let params = new HttpParams({
      fromObject: {
        'centroSaludDTO': JSON.stringify(this.pacienteCentroSalud),
        'fecha' : JSON.stringify(new Date()),
      }
    });
    this.json.getJsonP("cita/obtenerCitasFecha", params).subscribe(
      result => {
        this.citas = JSON.parse(result);
        console.log(this.citas[0]);
      }, error => {
        console.log(error);
      });
  }

  aplicarDosis() {
    this.citaSeleccionada.paciente.numDosisAplicadas = this.citaSeleccionada.paciente.numDosisAplicadas + 1;
    this.pacienteSeleccionado = false;
  }

}
