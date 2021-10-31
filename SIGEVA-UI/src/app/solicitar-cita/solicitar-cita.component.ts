import { Component, OnInit } from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {CentroSalud} from "../Model/centro-salud";
import {CupoCitas} from "../Model/cupo-citas";
import { JsonService } from '../Service/json.service';
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-solicitar-cita',
  templateUrl: './solicitar-cita.component.html',
  styleUrls: ['./solicitar-cita.component.css']
})

export class SolicitarCitaComponent implements OnInit {
  paciente : Usuario;
  mensaje: string;
  mensajeError : string;
  cita1 : CupoCitas;
  cita2 : CupoCitas;
  citas : CupoCitas[];
  solicitada : boolean;

  constructor(private json: JsonService) {
    this.paciente = new Usuario("","412cc2c7-2067-4991-9912-53280a87a89a", "tupaciente", "", "", "",
          "", "", "", "", "4da33823-0218-41f2-86a6-65cdafe27e2e");
    this.mensaje = "";
    this.cita1 = new CupoCitas("",new CentroSalud("","",0,""), new Date());
    this.cita2 = new CupoCitas("",new CentroSalud("","",0,""), new Date());
    this.citas = [];
    this.solicitada = false;
    this.mensajeError = "SOLICITAR CITA";

  }

  ngOnInit(): void {

  }

  solicitarCita(){
    let params = new HttpParams({
      fromObject: {
        "username": String(this.paciente.username)
      }
    });
     this.json.getJsonP("cupo/buscarParDeCuposLibresAPartirDeHoy",params).subscribe(
          result => {
            this.citas = JSON.parse(result.toString());
            this.cita1 = this.citas[0];
            this.cita2 = this.citas[1];
            this.mensaje = 'CITA RESERVADA!'
            this.solicitada = true;
          },err=> {
            this.mensajeError = err.error.message;
          });


  }

}
