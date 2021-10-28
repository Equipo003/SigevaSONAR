export interface CentroSalud{
    direccion: string;
	nombreCentro : string;
	numVacunasDisponibles : number;
	id : string;
}

export class CentroSalud {

	direccion: string;
	constructor(direccion: string, nombreCentro : string,numVacunasDisponibles : number){
		this.direccion = direccion;
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;

	}
}
