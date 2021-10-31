import { Component, OnInit } from '@angular/core';
import {Usuario} from "../Model/Usuario";
import {CentroSalud} from "../Model/centro-salud";
import {CupoCitas} from "../Model/cupo-citas";
import { JsonService } from '../Service/json.service';



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
;

  constructor(private json: JsonService) {
    this.paciente = new Usuario("","26254604-7e30-43b4-9d37-529402489f5d", "", "", "", "",
          "", "", "", "");
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

     this.json.postJson("cupo/buscarParDeCuposLibresAPartirDeHoy",this.paciente).subscribe(
          result => {
            this.citas = JSON.parse(result.toString());
            this.cita1 = this.citas[0];
            this.cita2 = this.citas[1];
            this.mensaje = 'CITA RESERVADA!'
            this.solicitada = true;
          },err=> {
            this.mensajeError = "Ha ocurrido un error. Vuelva a intentarlo más tarde"
          });


  }

}
