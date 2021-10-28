export interface CentroSalud{
  id: string
  direccion: string;
	nombreCentro : string;
	numVacunasDisponibles : number;
}

export class CentroSalud {

	constructor(direccion: string, nombreCentro : string,numVacunasDisponibles : number, id?: string){
    if (id != null) {
      this.id = id;
    }
    this.direccion = direccion;
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;

	}
}
