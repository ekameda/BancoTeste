import { Conta } from "./conta";

export interface Transacao {
  id: Number;
  valor: DoubleRange;
  dataHora: Date;
  tipo: String;
  Conta: Conta;
}
