import { Component, OnInit } from '@angular/core';
import {TokenService} from "../Service/token.service";
import {JsonService} from "../Service/json.service";

@Component({
  selector: 'app-barra-navegacion',
  templateUrl: './barra-navegacion.component.html',
  styleUrls: ['./barra-navegacion.component.css']
})
export class BarraNavegacionComponent implements OnInit {

  superAdmin = false;
  isLogged = false;
  rol = "";
  existeConfiguracion = false;

  constructor(private tokenService: TokenService) { }

  ngOnInit() {
    if (this.tokenService.getToken() != null) {
      this.isLogged = true;
      this.rol = String(this.tokenService.getRol());
    }
    else {
      this.isLogged = false;
    }

    if (this.superAdmin){
      this.isLogged = true;
      this.rol = "SuperAdmin";
    }
  }
}
