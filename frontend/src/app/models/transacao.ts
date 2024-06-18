import { Conta } from "./conta";

export interface Transacao {
  id?: number;
  valor?: number;
  dataHora?: Date;
  tipo?: String;
  conta?: Conta;
}
