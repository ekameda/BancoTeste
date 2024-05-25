import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  baseUrl: string = environment.apiCadCliente;

  constructor(private http: HttpClient) { }

  postCliente(cliente: Cliente) {
    this.http.post(this.baseUrl,cliente).pipe(
      map((response: any) => {
        return(response);
      })
    )
  }

  adicionar(cliente: Cliente){
    return this.http.post<Cliente>('${thi.baseUrl}',cliente).toPromise();
  }


}
