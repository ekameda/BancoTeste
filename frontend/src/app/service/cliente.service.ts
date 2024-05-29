import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  apiCli: string = environment.apiUrlCli;

  constructor(private http: HttpClient) {}

  getAllClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiCli);
  }

  createCliente(cliente: Cliente): Observable<Cliente> {
    console.log('apiCli');
    return this.http.post<Cliente>(this.apiCli, cliente);
  }

  buscarPorCpf(cpf: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiCli}/${cpf}`);
  }


  buscarPorNome(nome: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiCli}?nome=${nome}`);
  }

}
