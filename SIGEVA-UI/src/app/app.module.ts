import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule} from '@angular/forms'
import { AppComponent } from './app.component';
import { FormularioCentroSaludComponent } from './formulario-centro-salud/formulario-centro-salud.component';

@NgModule({
  declarations: [
    AppComponent,
    FormularioCentroSaludComponent
  ],
  imports: [
    BrowserModule,
	FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
