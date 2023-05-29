import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { loadStripe, Stripe, StripeElements } from '@stripe/stripe-js';
import { SenderService } from '../services/sender.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stripe-payment',
  templateUrl: './stripe-payment.component.html',
  styleUrls: ['./stripe-payment.component.css'],
})
export class StripePaymentComponent implements AfterViewInit {
  @ViewChild('cardElement') cardElement!: ElementRef;
  stripe: Stripe | null = null;
  elements!: StripeElements;
  card: any;
  dataRegister: any = {};

  constructor(
    private http: HttpClient,
    private service: SenderService,
    private router: Router
  ) {}

  ngAfterViewInit(): void {
    loadStripe(
      'pk_test_51IzvABIch0MObKLPgKKVufgPZs1oF0djkpvt3f2wZ6Qy8K5NkpWClT06CmXOuTExGGcwfgtmj764COueQM73wz5H00xLoM6zMi'
    ).then((stripe) => {
      this.stripe = stripe;
      this.elements = stripe!.elements();
      this.setupCardElement();
    });
  }

  setupCardElement() {
    const cardElementOptions = {
      style: {
        base: {
          fontSize: '16px',
          color: '#32325d',
        },
      },
    };
    this.card = this.elements.create('card', cardElementOptions);
    this.card.mount(this.cardElement.nativeElement);
  }

  async createToken() {
    if (!this.stripe) {
      console.error('Stripe.js not loaded.');
      return;
    }

    const { token, error } = await this.stripe.createToken(this.card!);

    if (error) {
      console.error(error);
    } else {
      console.log(token);
      this.sendTokenToServer(token.id);
    }
  }

  sendTokenToServer(token: any) {
    // Send the token to your server for further processing
    // You can use Angular's HttpClient or any other method to make an HTTP request
    // console.log(history.state);

    const totalPrice = this.service.totalPrice * 100;
    console.log(JSON.stringify(this.service.contents));
    const data = {
      username: this.service.username,
      totalPrice: totalPrice.toString(),
      stripeToken: token,
      orders: JSON.stringify(this.service.contents),
    };
    let options = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/json; charset=UTF-8'
      ),
    };

    this.http
      .post('http://localhost:8080/charge', data, options)
      .subscribe((response) => {
        this.dataRegister = response;
        // Check if server response json is success is true or false
        if (this.dataRegister.success.valueType == 'TRUE') {
          alert('Success');
          this.router.navigate(['/main']);
        } else {
          alert('Fail');
          this.router.navigate(['/login']);
        }
      });
  }

  // sendTokenToServer(token: any) {
  //   // Send the token to your server for further processing
  //   // You can use Angular's HttpClient or any other method to make an HTTP request
  //   // console.log(history.state);
  //   //const jsonArray = JSON.stringify(this.service.contents);
  //   console.log(this.service.contents);
  //   const totalPrice = this.service.totalPrice * 100;
  //   let body = new URLSearchParams();
  //   body.set('username', this.service.username);
  //   body.set('totalPrice', totalPrice.toString()); // Stripe count price in cents so to convert dollar and cents to cents multiply by 100
  //   body.set('stripeToken', token);
  //   body.set('orders', JSON.stringify(this.service.contents));

  //   let options = {
  //     headers: new HttpHeaders().set(
  //       'Content-Type',
  //       'application/x-www-form-urlencoded'
  //     ),
  //   };

  //   this.http
  //     .post('http://localhost:8080/charge', body.toString(), options)
  //     .subscribe((response) => {
  //       this.dataRegister = response;
  //       console.log(this.dataRegister);
  //     });
  // }
}
