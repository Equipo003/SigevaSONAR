import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { routing, appRoutingProviders} from "./app.routing";


import { AppComponent } from './app.component';

import { HomeComponent } from './home/home.component';
import { FormularioCentroSaludComponent } from './formulario-centro-salud/formulario-centro-salud.component';

import { ConfiguracionCuposComponent } from './configuracion-cupos/configuracion-cupos.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { CrearUsuariosComponent } from './crear-usuarios/crear-usuarios.component';
import { IndicarDosisVacunasComponent } from './indicar-dosis-vacunas/indicar-dosis-vacunas.component';
import { ContenedorFijarSanitariosComponent } from './contenedor-fijar-sanitarios/contenedor-fijar-sanitarios.component';
import { SanitarioFijarCentroComponent } from './sanitario-fijar-centro/sanitario-fijar-centro.component';
import { UsuariosSistemaComponent } from './usuarios-sistema/usuarios-sistema.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { SolicitarCitaComponent } from './solicitar-cita/solicitar-cita.component';
import { CentroSaludComponent } from './centro-salud/centro-salud.component';
import { CentrosSaludSistemaComponent } from './centros-salud-sistema/centros-salud-sistema.component';
import { EditarUsuarioComponent } from './editar-usuario/editar-usuario.component';
import { VentanaEmergenteComponent } from './ventana-emergente/ventana-emergente.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import { ModificacionCentroSaludComponent } from './modificacion-centro-salud/modificacion-centro-salud.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FormularioCentroSaludComponent,
    ConfiguracionCuposComponent,
    CrearUsuariosComponent,
    IndicarDosisVacunasComponent,
    ContenedorFijarSanitariosComponent,
    SanitarioFijarCentroComponent,
    UsuariosSistemaComponent,
    UsuarioComponent,
    SolicitarCitaComponent,
    CentroSaludComponent,
    CentrosSaludSistemaComponent,
    EditarUsuarioComponent,
    VentanaEmergenteComponent,
    ModificacionCentroSaludComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routing,
    ReactiveFormsModule,
    BrowserAnimationsModule,
	MatButtonModule,
    MatDialogModule
  ],
  providers: [appRoutingProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
