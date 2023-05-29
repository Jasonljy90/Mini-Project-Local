import { Component } from '@angular/core';
import { Selection } from '../models/shoppingproduct';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css'],
})
export class AddToCartComponent {
  contents: Selection[] = [];

  constructor(private service: SenderService) {}

  addItemToCart(selection: Selection) {
    console.info('selection: ', selection);
    const item = this.contents.find((i) => i.item == selection.item);
    if (!!item) item.quantity += selection.quantity;
    else this.contents.push(selection);
    this.service.contents = this.contents;
  }
}
