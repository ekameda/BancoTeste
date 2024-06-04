import { Cliente } from './cliente';

export interface Conta {
  id?: Number;
  cliente?: Cliente;
  saldo?: number;
  numero?: number
}
