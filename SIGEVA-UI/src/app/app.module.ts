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
import { UsuarioComponent } from './usuario/usuario.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FormularioCentroSaludComponent,
    ConfiguracionCuposComponent,
    CrearUsuariosComponent,
    IndicarDosisVacunasComponent,
    UsuarioComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routing,
    ReactiveFormsModule
  ],
  providers: [appRoutingProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
