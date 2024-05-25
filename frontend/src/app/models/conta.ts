import { Cliente } from './cliente';
import { Transacao } from './transacao';
export interface Conta{
  id: Number;
  cliente: Cliente[];
  saldo: Number;
  contas: Conta[];
  transacao: Transacao[];
}
