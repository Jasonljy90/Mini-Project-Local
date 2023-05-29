import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Inventory } from '../models/shoppingproduct';

@Injectable({
  providedIn: 'root',
})
export class ShoppingService {
  inventory!: Inventory;

  constructor(private http: HttpClient) {}

  getProducts(): Promise<any> {
    return firstValueFrom(
      this.http.get('http://localhost:8080/shopping/search')
    );
  }
}
