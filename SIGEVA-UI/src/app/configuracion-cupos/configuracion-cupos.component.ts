import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';
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
      aceptarEstadisticas : boolean;
      mensaje : string;
      solicitada :boolean;
      configuracionExistente : boolean;

  constructor(private json: JsonService) {
    this.duracionMinutos = 0;
    this.numeroPacientes = 0;
    this.pacientesVacunadosDia = 0;
    this.duracionJornadaHoras=0;
    this.duracionJornadaMinutos = 0;
    this.fechaInicio = '';
    this.fechaCreada = false;
    this.fecha = new Date();
    this.aceptarEstadisticas = false;
    this.mensaje = '';
    this.solicitada = false;
    this.configuracionExistente = false;
  }

  ngOnInit(): void {
      this.json.getJson('user/existConfCupos').subscribe((res: any) => {
               this.configuracionExistente = JSON.parse(res);
          },err=> {
              this.mensaje = 'Ha ocurrido un error :( Vuelva a intentarlo más tarde'
              console.log(err);
          });
  }

  calcularHoraFin(){
      if(this.fechaInicio != ''){
        this.fecha = new Date(this.fechaInicio);
            this.fecha.setHours(this.fecha.getHours()+this.duracionJornadaHoras);
            this.fecha.setMinutes(this.fecha.getMinutes()+this.duracionJornadaMinutos);
            console.log(this.fecha);
        this.fechaCreada = true;
      }
  }

  crearConfiguracionCupos(){
      let confCupo : ConfiguracionCupos = new ConfiguracionCupos(this.duracionMinutos, this.numeroPacientes, this.duracionJornadaHoras,
      this.duracionJornadaMinutos, this.fechaInicio)

      this.json.postJson('user/crearConfCupos',confCupo).subscribe((res: any) => {
           this.mensaje = 'Configuración guardada correctamente!'
           this.configuracionExistente = true;
      },err=> {
          this.mensaje = 'Ha ocurrido un error :( Vuelva a intentarlo más tarde'
          console.log(err);
      });

      this.solicitada = true;
  }

  aceptarEstadisticasF(){
    this.aceptarEstadisticas = true;
  }

}
