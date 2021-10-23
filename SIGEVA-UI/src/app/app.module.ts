import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
<<<<<<< HEAD
import { FormsModule} from '@angular/forms'
=======
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

>>>>>>> refs/remotes/origin/develop
import { AppComponent } from './app.component';
<<<<<<< HEAD
import { FormularioCentroSaludComponent } from './formulario-centro-salud/formulario-centro-salud.component';
=======
import { ConfiguracionCuposComponent } from './configuracion-cupos/configuracion-cupos.component';
>>>>>>> refs/remotes/origin/develop

@NgModule({
  declarations: [
    AppComponent,
<<<<<<< HEAD
    FormularioCentroSaludComponent
=======
    ConfiguracionCuposComponent,
>>>>>>> refs/remotes/origin/develop
  ],
  imports: [
    BrowserModule,
<<<<<<< HEAD
	FormsModule
=======
    FormsModule,
    HttpClientModule,
>>>>>>> refs/remotes/origin/develop
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
