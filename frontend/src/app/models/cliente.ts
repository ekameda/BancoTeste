import { Conta } from "./conta";

export interface Cliente {
  id?: number;
  nome: string;
  idade?: number;
  email: string;
  cpf: string;
  conta?: Conta;
}
