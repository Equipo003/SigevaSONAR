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
  cita1 : CupoCitas;
  cita2 : CupoCitas;
  citas : CupoCitas[];
  solicitada : boolean;
;

  constructor(private json: JsonService) {
    this.paciente = new Usuario("","8071ef83-2230-40b5-bb4e-9b3167006f8f", "", "", "", "",
          "", "", "", "");
    this.mensaje = "";
    this.cita1 = new CupoCitas("",new CentroSalud("","",0,""), new Date());
    this.cita2 = new CupoCitas("",new CentroSalud("","",0,""), new Date());
    this.citas = [];
    this.solicitada = false;

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
          },err=> {
            this.mensaje = err.error.message;
            console.log(err);
          });
     this.solicitada = true;

  }

}
