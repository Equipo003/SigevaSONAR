import { Component, OnInit } from '@angular/core';
import { JsonService } from '../Service/json.service';

@Component({
  selector: 'app-crear-usuarios',
  templateUrl: './crear-usuarios.component.html',
  styleUrls: ['./crear-usuarios.component.css'],
  providers: [JsonService],
})
export class CrearUsuariosComponent implements OnInit {

  id : String;
  nombre : String;

  constructor(private json: JsonService) {
    this.id = "";
    this.nombre = "";
  }

  ngOnInit(): void {
  }

}
