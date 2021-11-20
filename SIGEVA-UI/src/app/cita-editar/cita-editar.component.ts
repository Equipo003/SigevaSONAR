import {Component, Input, OnInit} from '@angular/core';
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import {CitaConObjetos} from "../Model/cita-con-objetos";
import {CupoCitas} from "../Model/cupo-citas";
import {Paciente} from "../Model/paciente";
import {Rol} from "../Model/rol";
import {CentroSalud} from "../Model/centro-salud";
import {Vacuna} from "../Model/vacuna";
import {FormControl} from "@angular/forms";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {enc, SHA256} from "crypto-js";
import {JsonService} from "../Service/json.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-cita-editar',
  templateUrl: './cita-editar.component.html',
  styleUrls: ['./cita-editar.component.css']
})
export class CitaEditarComponent implements OnInit {

  @Input() cita: CitaConObjetos;
  fechaCita: string = "";
  minDate: Date;
  maxDate: Date;
  editMode: boolean = false;
  daySelected: boolean = false;
  rangosHoras: string[] = [];
  horaSeleccionada: string = "";
  message: string = "";
  errorMessage: string = "";
  fecha = "2021-12-22T23:00:00.000+00:00";
  nombreCentro = "Centro de pruebas";
  rangoFechas = [];

  constructor(private json: JsonService, public dialog: MatDialog) {
    this.cita = new CitaConObjetos(new CupoCitas("", new CentroSalud("", "", 0, new Vacuna("", 0, 0, ""), ""), new Date()),
      0,
      new Paciente(new Rol("", ""),
                  new CentroSalud("", "", 0, new Vacuna("", 0, 0), ""),
                "", "", "", "", "", "", "", "", 0, ""), "");

    this.rangosHoras.push("08:00");
    this.rangosHoras.push("09:00");
    this.rangosHoras.push("10:00");
    this.rangosHoras.push("11:00");

    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = today.getMonth();
    const currentDay = today.getDate();

    this.minDate = new Date(currentYear, currentMonth, currentDay);
    this.maxDate = new Date(currentYear + 1, 0, 31);

    this.fechaCita = String(this.cita.cupo.fechaYHoraInicio).substr(0, 10)
  }

  ngOnInit(): void {
  }

  addEvent(event: MatDatepickerInputEvent<Date>) {
    console.log(event.value);
    this.daySelected = true;
    // let params = new HttpParams({
    //   fromObject: {
    //     // uuidPaciente: "74467d37-9b85-49fc-b932-06125f80488e",
    //     idUsuario: "74467d37-9b85-49fc-b932-06125f80488e",
    //     fecha: String(event.value)
    //   }
    // });
    // this.json.getJsonPJ("cupo/freeDatesDay", params).subscribe(
    //   result => {
    //     console.log(result);
    //     this.rangosHoras = result[0];
    //   }
    // );
  }

  editarCita() {
    this.editMode = true;
    let params = new HttpParams({
      fromObject: {
        uuidCita: String(this.cita.uuidCita)
      }
    });
    this.json.getJsonPJ("cita/buscarDiasModificacionCita", params).subscribe(
      result => {
        console.log(result);
        this.minDate = result[0];
        this.maxDate = result[1];
      }
    )
  }

  openDialogCancelar() {
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES CANCELAR LA EDICIÓN?', titulo: 'Cancelar Edición'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.editMode = false;
      }
    });
  }

  openDialogEliminar() {
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES ELIMINAR LA CITA?', titulo: 'Eliminar Cita'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.json.deleteJson("cita/eliminarCita", String(this.cita.uuidCita)).subscribe(
          result => {
            this.message = "Cita eliminada correctamente";
            setTimeout(function(){ window.location.reload() }, 2000);
            this.errorMessage = "";
          }, error => {
            this.errorMessage = "Error al eliminar la cita";
            this.message = "";
          });
      }
    });
  }

  openDialogGuardar() {
    let self = this;
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES GUARDAR LA EDICIÓN?', titulo: 'Guardar Edición'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        self.editMode = false;
        this.message = "Cita editada correctamente";
        setTimeout(function(){ self.message = "" }, 3000);
        // this.json.postJson("user/updateUsuario", this.usuario).subscribe(
        //   result => {
        //     this.message = "Usuario editado correctamente";
        //     setTimeout(function(){ self.router.navigate(['usuariosSistema']); }, 3000);
        //     this.errorMessage = "";
        //   }, error => {
        //     this.errorMessage = "Error al editar el usuario";
        //     this.message = "";
        //   });
      }
    });
  }

}
