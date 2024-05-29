import { Conta } from "./conta";

export interface cliente {
  id?: Number;
  valor?: Number;
  dataHora: Date;
  tipo: String;
  conta?: Conta;
}
