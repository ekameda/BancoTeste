import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssociacaoComponent } from './associacao/associacao.component';
import { ClienteComponent } from './cliente/cliente/cliente.component';
import { ContaComponent } from './conta/conta/conta.component';
import { ExtratoComponent } from './conta/extrato/extrato.component';


const routes: Routes = [
  { path: '', redirectTo: '/cliente', pathMatch: 'full' },
  { path: 'cliente',component: ClienteComponent},
  { path: 'conta', component: ContaComponent },
  { path: 'associacao', component: AssociacaoComponent },
  { path: 'extrato', component: ExtratoComponent },
  { path: 'criaConta', component: ContaComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
