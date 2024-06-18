import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { Transacao } from '../../models/transacao';
import { ContaService } from '../../service/conta.service';
import { TransacaoService } from '../../service/transacao.service';
import { Cliente } from './../../models/cliente';
import { Conta } from './../../models/conta';
import { ExtratoService } from './../../service/extrato.service';

@Component({
  selector: 'app-extrato',
  templateUrl: './extrato.component.html',
  styleUrls: ['./extrato.component.css'] // Corrigido o nome da propriedade
})
export class ExtratoComponent {

  constructor(
    private contaService: ContaService,
    private extratoService: ExtratoService,
    private transacaoService: TransacaoService,
    private router: Router,
    private cdr: ChangeDetectorRef // Injetando ChangeDetectorRef
  ) { }

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
    contaNumero: 0
  };

  cliente: Cliente = {
    nome: '',
    idade: undefined,
    email: '',
    cpf: '',
    id: 0
  }

  contemCliente: boolean = false;

  displayError: boolean = false;
  displaySuccess: boolean = false;
  successMessage: string = "";
  errorMessage: string = "";
  contaNumero: number = 0;
  extrato: any[] = [];

  checkFormValidity() {
    this.contemCliente = true;
  }

  resetForm() {
    // Lógica para resetar o formulário
  }

  validateNumber(event: KeyboardEvent) {
    this.contemCliente = true;
    const pattern = /[0-9]/;
    const inputChar = String.fromCharCode(event.charCode);
    if (!pattern.test(inputChar) && event.charCode !== 0) {
      event.preventDefault();
    }
  }

  buscarExtrato() {
    if (this.conta.contaNumero) {
      this.contaService.buscarContaPorNumero(this.conta.contaNumero)
        .subscribe(
          contaResponse => {
            if (contaResponse && contaResponse.id) {
              this.conta = contaResponse;
              const contaId = contaResponse.id;
              this.contaService.buscarClientePorConta(contaId)
                .subscribe(
                  clienteResponse => {
                    this.cliente = clienteResponse;
                    this.contemCliente = true;
                    this.extratoService.buscarExtrato(contaId)
                      .subscribe(
                        transacaoResponse => {
                          if (Array.isArray(transacaoResponse)) {
                            let saldo = 0;
                            for (let transacao of transacaoResponse) {
                              if (transacao.tipo === 'DEBITO') {
                                saldo -= transacao.valor;
                              } else {
                                saldo += transacao.valor;
                              }
                            }
                            transacaoResponse.push({ id: 'Saldo', dataHora: '', tipo: '', valor: saldo });
                            this.extrato = transacaoResponse;
                            this.cdr.detectChanges(); // Forçar a detecção de mudanças
                          } else {
                            alert('Erro ao buscar o extrato: resposta de transações inválida.');
                          }
                        },
                        error => {
                          alert('Erro ao buscar as transações.');
                        }
                      );
                  },
                  error => {
                    alert('Erro ao buscar o cliente.');
                  }
                );
            } else {
              alert('Erro ao buscar a conta: resposta inválida.');
            }
          },
          error => {
            alert('Erro ao buscar a conta.');
          }
        );
    } else {
      alert('Informe o número da conta.');
    }
  }
}
