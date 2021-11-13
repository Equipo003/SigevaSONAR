import { Component, Input } from '@angular/core';
import {UsuarioConObjetos} from "../Model/Usuario-con-objetos";
import {CentroSalud} from "../Model/centro-salud";
import {Rol} from "../Model/rol";
import {VentanaEmergenteComponent} from "../ventana-emergente/ventana-emergente.component";
import {MatDialog} from "@angular/material/dialog";
import {JsonService} from "../Service/json.service";
import {Usuario} from "../Model/Usuario";

@Component({
	selector: 'app-usuario',
	templateUrl: './usuario.component.html',
	styleUrls: ['./usuario.component.css']
})

export class UsuarioComponent {
	@Input() usuario: UsuarioConObjetos;
  message: string = "";
  errorMessage: string = "";
	constructor(private json: JsonService, public dialog: MatDialog) {
		this.usuario = new UsuarioConObjetos(new Rol("", ""), new CentroSalud("", "",
      0), "", "", "", "", "", "", "",
      "");
	}

  openDialogEliminar() {
    let self = this;
    const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
      data: {mensaje: '¿SEGURO QUE QUIERES ELIMINAR AL USUARIO: ' + this.usuario.nombre + ' ' + this.usuario.apellidos + '?', titulo: 'Eliminar Usuario'},
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.json.postJson("user/removeUsuario", this.usuario).subscribe(
          result => {
            this.message = "Usuario eliminado correctamente";
            setTimeout(function(){ self.message= ""; self.errorMessage = "" }, 3000);
            this.errorMessage = "";
          }, error => {
            this.errorMessage = error.error.message;
            this.message = "";
          });
      }
    });
  }
}
