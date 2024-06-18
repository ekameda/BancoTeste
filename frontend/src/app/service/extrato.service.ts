import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExtratoService {


  private apiUrl: string = environment.apiUrlExtra;

  constructor(private http: HttpClient) {}

  buscarExtrato(contaNumero: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${contaNumero}`);
  }
}
