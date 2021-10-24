export interface CentroSalud{
    direccion: String;
	nombreCentro : String;
	numVacunasDisponibles : Number;
}

export class CentroSalud {
	
	direccion: String;
	constructor(direccion: String, nombreCentro : String,numVacunasDisponibles : Number){
		this.direccion = direccion;
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;
		
	}
}
