import { Component, OnInit, Input } from '@angular/core';
import { CentroSalud } from '../Model/centro-salud';
import {JsonService} from "../Service/json.service";
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

  constructor(private json: JsonService) {
	this.cs = new CentroSalud("", "", 0);
	this.idCentro="";
	this.errorMessage = "";
    this.message = "";
  }

  ngOnInit(): void {
	
  }
  enviarDatosBack() {
   
      let self = this;
      this.json.postJson("user/deleteCentroSalud", this.cs).subscribe(
        result => {
          this.message = "Centro eliminado correctamente.";
          setTimeout(function(){ window.location.reload(); }, 3000);
        },err=> {
          this.errorMessage = err.error.message;
          console.log(err);
        });
    
  }
}
