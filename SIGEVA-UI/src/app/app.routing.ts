import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";

import { HomeComponent} from "./home/home.component";
import { FormularioCentroSaludComponent} from "./formulario-centro-salud/formulario-centro-salud.component";
import { ConfiguracionCuposComponent} from "./configuracion-cupos/configuracion-cupos.component";
import { CrearUsuariosComponent} from "./crear-usuarios/crear-usuarios.component";
import {IndicarDosisVacunasComponent} from "./indicar-dosis-vacunas/indicar-dosis-vacunas.component"
import {ContenedorFijarSanitariosComponent} from "./contenedor-fijar-sanitarios/contenedor-fijar-sanitarios.component";
import { UsuariosSistemaComponent } from './usuarios-sistema/usuarios-sistema.component';
import { SolicitarCitaComponent } from './solicitar-cita/solicitar-cita.component';
import { CentrosSaludSistemaComponent } from './centros-salud-sistema/centros-salud-sistema.component';
import {EditarUsuarioComponent} from "./editar-usuario/editar-usuario.component";
import {ListadoPacientesComponent} from "./listado-pacientes/listado-pacientes.component";

const  appRoutes: Routes = [
  {path: '', component :HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'crearCS', component: FormularioCentroSaludComponent},
  {path: 'cnfgCupos', component: ConfiguracionCuposComponent},
  {path: 'crearUsuarios', component: CrearUsuariosComponent},
  {path: 'indicarDosisVacunas', component: IndicarDosisVacunasComponent},
  {path: 'fijarPersonal', component: ContenedorFijarSanitariosComponent},
  {path: 'usuariosSistema', component: UsuariosSistemaComponent},
  {path: 'solicitarCita', component: SolicitarCitaComponent},
  {path: 'centrosSalud', component: CentrosSaludSistemaComponent},
  {path: 'editarUsuario/:idUsuario', component: EditarUsuarioComponent},
  {path: 'listadoUsuarios', component: ListadoPacientesComponent}


]

export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
