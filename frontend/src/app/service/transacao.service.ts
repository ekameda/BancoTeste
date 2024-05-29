import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransacaoService {

  private baseUrl = '/transacoes';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Transacao[]> {
    return this.http.get<Transacao[]>(`${this.baseUrl}`);
  }

  findById(id: number): Observable<Transacao> {
    return this.http.get<Transacao>(`${this.baseUrl}/${id}`);
  }

  save(Transacao: Transacao): Observable<Transacao> {
    return this.http.post<Transacao>(this.baseUrl, Transacao);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
