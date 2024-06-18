import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AssociacaoComponent } from './associacao/associacao.component';
import { ClienteComponent } from './cliente/cliente/cliente.component';
import { ContaComponent } from './conta/conta/conta.component';
import { ExtratoComponent } from './conta/extrato/extrato.component';
import { BodyComponent } from './shared/body/body.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HeaderComponent } from './shared/header/header.component';
import { TransacaoComponent } from './conta/transacao/transacao.component';

@NgModule({
  declarations: [
    AppComponent,
    ContaComponent,
    ExtratoComponent,
    BodyComponent,
    FooterComponent,
    HeaderComponent,
    ClienteComponent,
    AssociacaoComponent,
    TransacaoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule ,
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: 'cliente', component: ClienteComponent },
      { path: 'conta', component: ContaComponent },
      { path: 'extrato', component: ExtratoComponent }

    ])
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
