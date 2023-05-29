import { Component } from '@angular/core';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css'],
})
export class OrderHistoryComponent {
  originalString!: string;
  modifiedString!: string;

  constructor(private service: SenderService) {}

  ngOnInit(): void {
    this.originalString = this.service.username;
    this.modifiedString = this.originalString.replace(/"/g, '');
    window.location.href =
      'http://localhost:8080/shopping/order/history/' + this.modifiedString;
  }
}
