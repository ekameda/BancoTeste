import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cliente } from '../../models/cliente';
import { ClienteService } from '../../service/cliente.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})

export class ClienteComponent {
  novoCliente: Cliente;
  displaySuccess: boolean = false;
  displayError: boolean = false;

  constructor(
    private clienteService: ClienteService,
    private router: Router) {
    this.novoCliente = {
      nome: '',
      idade: 0,
      email: '',
      cpf: '',
      id: undefined
    }
  }

  cadastrarCliente() {
    this.clienteService.createCliente(this.novoCliente).subscribe(response => {
      this.novoCliente.id = response.id;
      this.displaySuccess = true;
      this.router.navigate(['cliente']);
    }, error => {
      this.displayError = true;
    });
  }

  checkFormValidity() {
    if (!this.novoCliente.nome || !this.novoCliente.idade || !this.novoCliente.email || !this.novoCliente.cpf) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    if (isNaN(this.novoCliente.idade)) {
      alert('Por favor, insira uma idade válida.');
      return;
    }

    if (this.novoCliente.cpf.length !== 11) {
      alert('Por favor, insira um CPF válido.');
      return;
    }

    this.cadastrarCliente();
  }

  resetForm() {
    this.displaySuccess = false;
    this.displayError = false;
    const form = document.querySelector('form');
    form?.classList.remove('was-validated');
    this.novoCliente = {
      nome: ' ',
      idade: 0,
      email: ' ',
      cpf: ' ',
      id: undefined
    }
  }

  validateNumber(event: KeyboardEvent) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
    }
  }
}
