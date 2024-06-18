import { ChangeDetectorRef, Component } from '@angular/core';
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
    clientes: undefined,
    saldo: 0,
    contaNumero: 0
  };

  errorMessage: string = '';
  cliId: number = 0;
  displaySuccess: boolean = false;
  displayError: boolean = false;

  constructor(
    private clienteService: ClienteService,
    private contaService: ContaService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  validateNumber(event: KeyboardEvent) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
    }
  }

  checkFormValidity() {
    if (this.cliId === 0 || this.conta.contaNumero === undefined) {
      return;
    }
    this.salvarConta();
  }

  salvarConta() {
    this.clienteService.buscarClientePorId(this.cliId).subscribe((cliente: Cliente) => {
      console.log('Cliente Localizado com sucesso:', cliente);
      this.errorMessage = '';
      this.cliente = cliente;
      this.cdr.detectChanges();

      this.contaService.salvarConta1(cliente.id , this.conta).subscribe(response => {
        console.log('Conta criada com sucesso:', response);
        this.errorMessage = '';
        this.conta.id = response.id;
        this.displaySuccess = true;
        this.cdr.detectChanges();
        this.router.navigate(['conta']);
        this.resetForm();

      },
      error => {
        console.error('Ocorreu um erro:', error);
        this.errorMessage = error;
        this.displayError = true;
      });
    }, error => {
      console.error('Ocorreu um erro:', error);
        this.errorMessage = error;
      this.displayError = true;
    });
  }

  resetForm() {
    this.conta = {
      id: undefined,
      clientes: undefined,
      saldo: undefined,
      contaNumero: 0
    };
    this.displaySuccess = false;
    this.displayError = false;
  }
}
