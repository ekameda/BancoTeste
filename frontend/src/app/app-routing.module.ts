import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteComponent } from './cliente/cliente/cliente.component';
import { ContaComponent } from './conta/conta/conta.component';
import { ExtratoComponent } from './conta/extrato/extrato.component';
import { TransacaoComponent } from './conta/transacao/transacao.component';


const routes: Routes = [
  { path: '', redirectTo: 'cliente', pathMatch: 'full' },
  { path: 'cliente',component: ClienteComponent},
  { path: 'conta', component: ContaComponent },
  { path: 'transacao', component: TransacaoComponent },
  { path: 'extrato', component: ExtratoComponent },
  { path: 'criaConta', component: ContaComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
