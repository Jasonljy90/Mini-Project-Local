import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  username?: string;
  email?: string;
  password?: string;
  dataRegister: any = {};

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signupForm = this.createForm();
  }

  signup() {
    const username = this.signupForm?.value['username'];
    const email = this.signupForm?.value['email'];
    const password = this.signupForm?.value['password'];

    let body = new URLSearchParams();
    body.set('username', username);
    body.set('email', email);
    body.set('password', password);

    let options = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/x-www-form-urlencoded'
      ),
    };

    this.http
      .post('http://localhost:8080/signup', body.toString(), options)
      .subscribe((response) => {
        this.dataRegister = response;
        // Check if server response json is success is true or false
        if (this.dataRegister.success.valueType == 'TRUE') {
          alert('Success');
          this.router.navigate(['/otp']);
        } else {
          alert('Fail');
          this.router.navigate(['/account']);
        }
      });
  }

  createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });
  }
}
