import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Conta } from '../../models/conta';
import { ClienteService } from '../../service/cliente.service';
import { ContaService } from '../../service/conta.service';
import { Cliente } from './../../models/cliente';
@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrls: ['./conta.component.css']
})
export class ContaComponent {

  cliente: Cliente = {
    nome: '',
    idade: undefined,
    email: '',
    cpf: '',
    id: 0
  };

  conta: Conta = {
    id: undefined,
    cliente: undefined,
    saldo: 0,
    numero: undefined
  };

  cliId: number = 0;
  displaySuccess: boolean = false;
  displayError: boolean = false;

  constructor(
    private clienteService: ClienteService,
    private contaService: ContaService,
    private router: Router
  ) {}

  validateNumber(event: KeyboardEvent) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
    }
  }

  salvarConta() {
    this.clienteService.buscarClientePorId(this.cliId).subscribe((cliente: Cliente) => {
      this.cliente = cliente;
      this.conta.cliente = this.cliente;

      this.contaService.salvarConta(this.conta).subscribe(response => {
        this.conta.id = response.id;
        this.displaySuccess = true;
        this.router.navigate(['conta']);
      }, error => {
        this.displayError = true;
      });
    }, error => {
      this.displayError = true;
    });
  }

  checkFormValidity() {
    if (this.cliId === 0 || this.conta.numero === undefined) {
      return;
    }
    this.salvarConta();
  }

  resetForm() {
    this.conta = {
      id: undefined,
      cliente: undefined,
      saldo: undefined,
      numero: undefined
    };
    this.displaySuccess = false;
    this.displayError = false;
  }
}
