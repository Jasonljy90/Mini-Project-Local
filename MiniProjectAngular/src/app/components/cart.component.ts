import { Component, Input, OnInit } from '@angular/core';
import { Selection } from '../models/shoppingproduct';
import { Router } from '@angular/router';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  @Input()
  contents: Selection[] = [];

  private totalPriceCart: number = 0;

  constructor(private router: Router, private service: SenderService) {}

  ngOnInit(): void {}

  getSum(): number {
    let sum = 0;
    for (let i = 0; i < this.contents.length; i++) {
      sum += this.contents[i].quantity * this.contents[i].unitPrice;
    }
    this.totalPriceCart = sum;
    return sum;
  }

  payment(): void {
    this.service.totalPrice = this.totalPriceCart;
    this.router.navigate(['/shopping/payment']);
  }
}
