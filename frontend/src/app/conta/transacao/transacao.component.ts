import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Conta } from '../../models/conta';
import { ContaService } from '../../service/conta.service';
import { TransacaoService } from '../../service/transacao.service';
import { Transacao } from './../../models/transacao';

@Component({
  selector: 'app-transacao',
  templateUrl: './transacao.component.html',
  styleUrl: './transacao.component.css'
})
export class TransacaoComponent {

  displaySuccess: boolean = false;
  displayError: boolean = false;

  errorMessage: string = '';
  successMessage: string = '';


  transacao: Transacao = {
    id: undefined,
    valor: 0,
    dataHora: undefined,
    tipo: undefined,
    conta: undefined
  }

  conta: Conta = {
    id: undefined,
    saldo: undefined,
    contaNumero: undefined
  };

  constructor(
    private contaService: ContaService,
    private transacaoService: TransacaoService,
    private router: Router
  ) { }


  validateNumber(event: KeyboardEvent) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
    }
  }


  checkFormValidity() {
    if (this.transacao.tipo === undefined || this.conta.contaNumero === undefined || this.transacao.tipo === undefined) {
      return;
    }
    this.contaService.buscarContaPorNumero(this.conta.contaNumero).subscribe((conta: Conta) => {
        this.conta = conta;

        if (this.transacao.tipo === "DEBITO") {

          if (this.conta.saldo !== undefined && this.transacao.valor !== undefined && this.conta.saldo >= this.transacao.valor) {
            this.conta.saldo = this.conta.saldo - this.transacao.valor;
            this.salvar();
          } else {
            this.errorMessage = "Saldo insuficiente";
            this.displayError = true;
          }
        } else {
          this.conta.saldo = (this.conta.saldo || 0) + (this.transacao.valor || 0);
          this.salvar();
        }


    }, error => {
      this.resetForm();
      this.errorMessage = "Conta não encontrada com o numero: " + this.conta.contaNumero;
      this.displayError = true;
    });
  }

  salvar() {
    this.conta.clientes = [];
    this.transacao.dataHora = new Date;
    this.transacao.conta = this.conta;

    this.transacaoService.save(this.transacao).subscribe(respose => {
      this.transacao.id = respose.id;
      this.displaySuccess = true;
      this.successMessage = "Transação Salva com Sucesso. Id: " + respose.id + "Valor Conta :" + this.conta.saldo;
      this.router.navigate(['transacao']);
      this.resetForm();
    }, error => {
      this.errorMessage = "Erro ao inserir Transação";
      this.displayError = true;
      this.resetForm();
    });
  }


  resetForm() {
    this.transacao = {
      id: undefined,
      valor: 0,
      dataHora: undefined,
      tipo: undefined,
      conta: undefined
    }

    this.conta = {
      id: undefined,
      saldo: undefined,
      contaNumero: undefined
    };
    this.displaySuccess = false;
    this.displayError = false;
  }

}
