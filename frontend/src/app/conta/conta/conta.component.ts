import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Conta } from '../../models/conta';
import { ContaService } from '../../service/conta.service';
import { Cliente } from './../../models/cliente';
import { ClienteService } from './../../service/cliente.service';

@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrls: ['./conta.component.css']
})
export class ContaComponent {
  conta: Conta = {};
  cliente: Cliente | undefined;
  clienteNomeCompleto: string | undefined;
  clienteId: number | undefined;
  displaySuccess: boolean = false;
  displayError: boolean = false;

  constructor(private clienteService: ClienteService,
    private contaService: ContaService,
    private router: Router) {}

  buscarClientePorNome(nome: string) {
    this.clienteService.buscarPorNome(nome).subscribe(
      (cliente: Cliente) => {
        this.cliente = cliente;
        this.clienteNomeCompleto = `${cliente.nome}`;
        this.clienteId = cliente.id;
      },
      () => {
        this.cliente = undefined;
        this.clienteNomeCompleto = undefined;
        this.clienteId = undefined;
      }
    );
  }

  validateNumber(event: KeyboardEvent) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
    }
  }

  cadastrarConta() {
    this.contaService.salvarConta(this.conta).subscribe(response => {
      this.conta.id = response.id;
      this.displaySuccess = true;
      this.router.navigate(['conta']);
    }, error => {
      this.displayError = true;
    });
  }

  checkFormValidity() {
    const form = document.querySelector('.needs-validation') as HTMLFormElement;
    if (form.checkValidity() === false) {
      event?.preventDefault();
      event?.stopPropagation();
    }
    form.classList.add('was-validated');
    this.cadastrarConta();
  }

  resetForm() {
    this.conta = {};
    this.cliente = undefined;
    this.clienteNomeCompleto = undefined;
    this.clienteId = undefined;
    this.displaySuccess = false;
    this.displayError = false;
    const form = document.querySelector('.needs-validation') as HTMLFormElement;
    form.classList.remove('was-validated');
  }
}
