import { Component, OnInit } from '@angular/core';
import {TokenService} from "../Service/token.service";

@Component({
  selector: 'app-barra-navegacion',
  templateUrl: './barra-navegacion.component.html',
  styleUrls: ['./barra-navegacion.component.css']
})
export class BarraNavegacionComponent implements OnInit {

  isLogged = false;

  constructor(private tokenService: TokenService) { }

  ngOnInit() {
    if (this.tokenService.getToken() != null) {
      this.isLogged = true;
    }
    else {
      this.isLogged = false;
    }
  }

}
