import { Injectable } from '@angular/core';
import { Selection } from '../models/shoppingproduct';

@Injectable({
  providedIn: 'root',
})
export class SenderService {
  public totalPrice!: number;
  public username!: string;
  public email!: string;
  public contents!: Selection[];

  constructor() {}
}
