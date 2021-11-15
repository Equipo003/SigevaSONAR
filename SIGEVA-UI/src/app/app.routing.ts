import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";

import {HomeComponent} from "./home/home.component";
import {FormularioCentroSaludComponent} from "./formulario-centro-salud/formulario-centro-salud.component";
import {ConfiguracionCuposComponent} from "./configuracion-cupos/configuracion-cupos.component";
import {CrearUsuariosComponent} from "./crear-usuarios/crear-usuarios.component";
import {IndicarDosisVacunasComponent} from "./indicar-dosis-vacunas/indicar-dosis-vacunas.component"
import {ContenedorFijarSanitariosComponent} from "./contenedor-fijar-sanitarios/contenedor-fijar-sanitarios.component";
import {UsuariosSistemaComponent} from './usuarios-sistema/usuarios-sistema.component';
import {SolicitarCitaComponent} from './solicitar-cita/solicitar-cita.component';
import {CentrosSaludSistemaComponent} from './centros-salud-sistema/centros-salud-sistema.component';
import {EditarUsuarioComponent} from "./editar-usuario/editar-usuario.component";
import {ModificacionCentroSaludComponent} from "./modificacion-centro-salud/modificacion-centro-salud.component";
import {ListadoPacientesComponent} from "./listado-pacientes/listado-pacientes.component";
import {LoginComponent} from "./login/login.component";
import {FuncionalidadesGuardService as guard} from "./guards/funcionalidades-guard.service";

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {
    path: 'crearCS', component: FormularioCentroSaludComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'cnfgCupos', component: ConfiguracionCuposComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'crearUsuarios', component: CrearUsuariosComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'indicarDosisVacunas', component: IndicarDosisVacunasComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'fijarPersonal', component: ContenedorFijarSanitariosComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'usuariosSistema', component: UsuariosSistemaComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'solicitarCita', component: SolicitarCitaComponent,
    canActivate: [guard], data: {expectedRol: ['Paciente', 'SuperAdmin']}
  },
  {
    path: 'centrosSalud', component: CentrosSaludSistemaComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'editarUsuario/:idUsuario', component: EditarUsuarioComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {
    path: 'editarCS/:idCentroSalud', component: ModificacionCentroSaludComponent,
    canActivate: [guard], data: {expectedRol: ['Administrador', 'SuperAdmin']}
  },
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
