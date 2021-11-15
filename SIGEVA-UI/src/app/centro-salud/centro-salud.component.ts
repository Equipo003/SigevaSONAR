import { Component, OnInit, Input } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import { CentroSalud } from '../Model/centro-salud';
import {JsonService} from "../Service/json.service";
import { VentanaEmergenteComponent } from '../ventana-emergente/ventana-emergente.component';
import {Vacuna} from "../Model/vacuna";
@Component({
  selector: 'app-centro-salud',
  templateUrl: './centro-salud.component.html',
  styleUrls: ['./centro-salud.component.css']
})
export class CentroSaludComponent implements OnInit {
  @Input() cs: CentroSalud;
  idCentro: string;
  public message: string;
  public errorMessage: string;

  constructor( public dialog: MatDialog,private json: JsonService) {
	this.cs = new CentroSalud("direccion", "nombre",1, new Vacuna("vacuna", 3, 15), "");
	this.idCentro="";
	this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {

  }
   openDialog() {
	   let self = this;
       const dialogRef = this.dialog.open(VentanaEmergenteComponent, {
         data: { mensaje: '¿SEGURO QUE QUIERES ELIMINAR EL CENTRO?', titulo: 'Eliminar centro de salud'},
       });
       dialogRef.afterClosed().subscribe(result => {
		 if(result){
			this.json.postJson("user/deleteCentroSalud", this.cs).subscribe(
        result => {
          this.message = "Centro eliminado correctamente.";
          setTimeout(function(){ window.location.reload(); }, 3000);
        },err=> {
          this.errorMessage = err.error.message;
 		  setTimeout(function(){ self.errorMessage=""; }, 3000);
        });


		}
       });
     }
  enviarDatosBack() {

      let self = this;
	  this.openDialog();
  }
}
