export interface Rol{
  id : String;
  nombre : String;
}

export class Rol {
  id : String;
  nombre : String;

  constructor(id : String, nombre : String){

    this.id = id;
    this.nombre = nombre;
  }
}
