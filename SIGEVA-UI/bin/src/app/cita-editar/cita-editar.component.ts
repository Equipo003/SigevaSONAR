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
  rangosHoras: CupoCitas [] = [];
  cupoSeleccionado: CupoCitas;
  message: string = "";
  errorMessage: string = "";
  nombreCentro = "Centro de pruebas";
  rangoFechas = [];

  constructor(private json: JsonService, public dialog: MatDialog) {
    this.cita = new CitaConObjetos(new CupoCitas("", new CentroSalud("", "", 0, new Vacuna("", 0, 0, ""), ""), new Date()),
      0,
      new Paciente(new Rol("", ""),
                  new CentroSalud("", "", 0, new Vacuna("", 0, 0), ""),
                "", "", "", "", "", "", "", "", 0, ""), "");

    this.minDate = new Date();
    this.maxDate = new Date();
    this.cupoSeleccionado = new CupoCitas("", new CentroSalud("", "", 0, new Vacuna("", 0, 0, ""), ""), new Date());
  }

  ngOnInit(): void {
  }

  addEvent(event: MatDatepickerInputEvent<Date>) {
    this.daySelected = true;
    const fecha = new Date(Number(event.value?.getFullYear()), Number(event.value?.getMonth()), Number(event.value?.getDay()));
    const fechaString = fecha.toLocaleDateString();

    let params = new HttpParams({
      fromObject: {
        uuidPaciente: "74467d37-9b85-49fc-b932-06125f80488e",
        // idUsuario: "74467d37-9b85-49fc-b932-06125f80488e",
        fecha: JSON.stringify(event.value)
      }
    });
    this.json.getJsonPJ("cupo/buscarCuposLibresFecha", params).subscribe(
      result => {
        this.rangosHoras = JSON.parse(JSON.stringify(result));
        this.rangosHoras.forEach( function (rango) {
          rango.fechaYHoraInicio = new Date(rango.fechaYHoraInicio);
        });
      }
    );
  }

  openDialogGuardar() {
    let self = this;
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES GUARDAR LA EDICIÓN?', titulo: 'Guardar Edición'},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(this.cita.cupo.uuid);
      if (result) {
        let params = new HttpParams({
          fromObject: {
            idCita: String(this.cita.uuidCita),
            cupoNuevo: this.cita.cupo.uuid
          }
        });
        this.json.getJsonPJ("cita/modificarCita", params).subscribe(
          result => {
            console.log(result);
            self.editMode = false;
            this.message = "Cita editada correctamente";
            setTimeout(function(){ location.reload() }, 2000);
          }
        )
      }
    });
  }

  onChangeHora($event: any){
    console.log($event);
    // this.cupoSeleccionado = $event;
    this.cita.cupo.uuid = $event.uuidCupo;
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
}
