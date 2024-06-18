import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Transacao } from '../models/transacao';

@Injectable({
  providedIn: 'root'
})
export class TransacaoService {

  private apiUrl: string = environment.apiUrlTrans;

  constructor(private http: HttpClient) {}

  findAll(): Observable<Transacao[]> {
    return this.http.get<Transacao[]>(`${this.apiUrl}`);
  }

  findById(id: number): Observable<Transacao> {
    return this.http.get<Transacao>(`${this.apiUrl}/${id}`);
  }

  save(transacao: Transacao): Observable<Transacao> {
    return this.http.post<Transacao>(this.apiUrl, transacao);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  findTransacaoByConta(idConta: number): Observable<Transacao>{
    return this.http.get<Transacao>(`${this.apiUrl}/conta/${idConta}`);
  }
}
