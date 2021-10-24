import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule} from '@angular/forms'
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { FormularioCentroSaludComponent } from './formulario-centro-salud/formulario-centro-salud.component';

import { ConfiguracionCuposComponent } from './configuracion-cupos/configuracion-cupos.component';

@NgModule({
  declarations: [
    AppComponent,
    FormularioCentroSaludComponent,
    ConfiguracionCuposComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
