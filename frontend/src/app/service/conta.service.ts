import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente } from '../models/cliente';
import { Conta } from '../models/conta';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  apiUrl: string = environment.apiUrlConta;
  constructor(private http: HttpClient) {}

  salvarConta(conta: Conta): Observable<Conta> {
    return this.http.post<Conta>(this.apiUrl, conta).pipe(
      catchError(this.handleError)
    );
  }

  salvarConta1(clienteId: number, conta: Conta): Observable<Conta> {
    const url = `${this.apiUrl}/clientes/${clienteId}/contas`;
    return this.http.post<Conta>(url, conta).pipe(
      catchError(this.handleError)
    );
  }

  buscarContaPorNumero(contaNumero: number): Observable<Conta>{
    return this.http.get<Conta>(`${this.apiUrl}/numero/${contaNumero}`).pipe(
      catchError(this.handleError)
    );
  }

  buscarClientePorConta(contaId: Number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.apiUrl}/cliente/${contaId}`).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 400:
          errorMessage = 'Erro de validação:\n' + this.formatValidationErrors(error.error);
          break;
        case 404:
          errorMessage = 'Recurso não encontrado: ' + error.error;
          break;
        case 409:
          errorMessage = 'Conflito: ' + error.error;
          break;
        default:
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }
    return throwError(errorMessage);
  }

  private formatValidationErrors(errors: any): string {
    return Object.keys(errors).map(field => `${field}: ${errors[field]}`).join('\n');
  }

}
