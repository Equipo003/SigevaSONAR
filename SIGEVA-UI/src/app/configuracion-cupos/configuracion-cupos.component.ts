import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { ConfiguracionCupos } from '../Model/configuracion-cupos';

@Component({
  selector: 'app-configuracion-cupos',
  templateUrl: './configuracion-cupos.component.html',
  styleUrls: ['./configuracion-cupos.component.css'],
  providers: [JsonService],
})
export class ConfiguracionCuposComponent implements OnInit {

      duracionMinutos : number;
      numeroPacientes : number;
      duracionJornadaHoras : number;
      duracionJornadaMinutos : number;
      fecha : Date;
      pacientesVacunadosDia : number;
      fechaInicio : string;
      fechaCreada : boolean;

  constructor(private json: JsonService) {
    this.duracionMinutos = 0;
    this.numeroPacientes = 0;
    this.pacientesVacunadosDia = 0;
    this.duracionJornadaHoras = 0;
    this.duracionJornadaMinutos = 0;
    this.fechaInicio = '';
    this.fechaCreada = false;
    this.fecha = new Date();
  }

  ngOnInit(): void {
    //let body : ConfiguracionCupos = new ConfiguracionCupos(16,8);
    //this.json.postJson('http://localhost:8080/home/envio', body).subscribe((res: any) => {
     // console.log(res.numeroPacientes);
    //});
  }

  calcularHoraFin(){
      this.fecha = new Date(this.fechaInicio);
            this.fecha.setHours(this.fecha.getHours()+this.duracionJornadaHoras);
            this.fecha.setMinutes(this.fecha.getMinutes()+this.duracionJornadaMinutos);
            console.log(this.fecha);
      this.fechaCreada = true;
  }

  crearConfiguracionCupos(){
      let confCupo : ConfiguracionCupos = new ConfiguracionCupos(this.duracionMinutos, this.numeroPacientes, this.duracionJornadaHoras,
      this.duracionJornadaMinutos, this.fechaInicio)

      this.json.postJson('user/crearConfCupos',confCupo).subscribe((res: any) => {
           console.log(res);
      });
  }

}
