import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteComponent } from './cliente/cliente.component';
import { ExtratoComponent } from './extrato/extrato.component';
import { HomeComponent } from './home/home.component';
import { BodyComponent } from './shared/body/body.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'extrato/list/:id', component: ExtratoComponent},
  { path: 'clienteCad', component: ClienteComponent},
  { path: 'extrato', component: BodyComponent},
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
