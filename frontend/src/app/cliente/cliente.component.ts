import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Cliente } from '../models/cliente';
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent {

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private clienteService: ClienteService) { }
  id: string | null = '';

  userForm: FormGroup = new FormGroup({});
  initialaserForm() {
    this.userForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
      idade: ['', [Validators.required, Validators.min(1), Validators.max(110)]],
      email: ['', [Validators.required ]],
      cpf: ['', [Validators.required, Validators.maxLength(145), Validators.minLength(1)]]
    })
  }

  adicionar() {
    const cli: Cliente = {
      cpf: 123,
      idade: 23,
      email: "eduardo@gmail.com",
      nome: "EduardoYK"
    }
    this.clienteService.adicionar(cli)
    .then(cli=>console.log('Adicionado'))
    .catch(error=> console.error(error))
  }

  SubmitForm() {
    if (this.userForm.valid) {
      this.clienteService.postCliente(this.fb.array.arguments.Cliente);
      console.log(this.userForm.value);
    }
  }

}
