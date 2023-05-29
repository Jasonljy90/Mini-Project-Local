import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm!: FormGroup;
  email!: string;
  password?: string;
  dataRegister: any = {};
  username!: string;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private service: SenderService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.createForm();
  }

  login() {
    const email = this.loginForm?.value['email'];
    const password = this.loginForm?.value['password'];

    let body = new URLSearchParams();
    body.set('email', email);
    body.set('password', password);

    let options = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/x-www-form-urlencoded'
      ),
    };

    this.http
      .post('http://localhost:8080/login', body.toString(), options)
      .subscribe((response) => {
        this.dataRegister = response;
        this.username = JSON.stringify(this.dataRegister.username.chars);

        console.log('Inside Login Component: ' + this.username);
        // Check if server response json is success is true or false
        if (this.username != null) {
          this.service.username = this.username;
          this.service.email = email;
          alert('Success');
          this.username = this.dataRegister.username.value;
          this.router.navigate(['/main']);
        } else {
          alert('Fail');
          this.router.navigate(['/login']);
        }
      });
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });
  }
}
