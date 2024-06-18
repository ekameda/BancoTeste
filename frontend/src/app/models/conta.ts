import { Cliente } from "./cliente";

export interface Conta {
  id?: number;
  clientes?: Cliente [];
  saldo?: any;
  contaNumero?: number;
}
