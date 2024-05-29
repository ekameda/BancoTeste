import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Conta } from '../models/conta';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  apiUrl: string = environment.apiUrlConta;
  constructor(private http: HttpClient) {}

  salvarConta(conta: Conta): Observable<Conta> {
    console.log(this.apiUrl);
    return this.http.post<Conta>(this.apiUrl, conta);
  }
}
