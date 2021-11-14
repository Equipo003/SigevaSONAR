import {CupoCitas} from "./cupo-citas";
import {Paciente} from "./paciente";

export interface CitaConObjetos{
  cupo : CupoCitas;
  dosis : number;
}


export class CitaConObjetos {
  cupo : CupoCitas;
  dosis : number;
  paciente : Paciente;

  constructor(cupo:CupoCitas, dosis:number, paciente:Paciente) {
    this.cupo = cupo;
    this.dosis = dosis;
    this.paciente = paciente;
  }

}
