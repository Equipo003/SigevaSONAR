import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { ConfiguracionCuposComponent } from './configuracion-cupos/configuracion-cupos.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfiguracionCuposComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
