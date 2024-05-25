import { Component } from '@angular/core';

@Component({
  selector: 'app-extrato',
  templateUrl: './extrato.component.html',
  styleUrl: './extrato.component.css'
})
export class ExtratoComponent {
  transacao: String[] = ['Extrato 1', 'Extrato 2', 'Extrato 3', 'Extrato 4', 'Extrato 5'];

}
