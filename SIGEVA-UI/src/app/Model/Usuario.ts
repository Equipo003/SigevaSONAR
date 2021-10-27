export interface Usuario{
  rol: string;
  centro: string;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;
}

export class Usuario {
  rol: string;
  centro: string;
  username: string;
  correo: string;
  hashPassword:string;
  dni:string;
  nombre:string;
  apellidos:string;
  fechaNacimiento:string;
  imagen:string;

  constructor(rol: string, centro: string, username: string, correo: string, hashPassword:string, dni:string,
              nombre:string, apellidos:string, fechaNacimiento:string, imagen:string){

    this.rol = rol;
    this.centro = centro;
    this.username = username;
    this.correo = correo;
    this.hashPassword = hashPassword;
    this.dni = dni;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.fechaNacimiento = fechaNacimiento;
    this.imagen = imagen;
  }
}
