import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-otp-email-verify',
  templateUrl: './otp-email-verify.component.html',
  styleUrls: ['./otp-email-verify.component.css'],
})
export class OtpEmailVerifyComponent {
  verifyEmailOtpForm!: FormGroup;
  username?: string;
  otp?: string;
  dataRegister: any = {};

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.verifyEmailOtpForm = this.createForm();
  }

  verifyEmailOtp() {
    const username = this.verifyEmailOtpForm?.value['username'];
    const otp = this.verifyEmailOtpForm?.value['otp'];

    let body = new URLSearchParams();
    body.set('username', username);
    body.set('otp', otp);

    let options = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/x-www-form-urlencoded'
      ),
    };

    this.http
      .post('http://localhost:8080/verifyotp', body.toString(), options)
      .subscribe((response) => {
        this.dataRegister = response;
        // Check if server response json is success is true or false
        if (this.dataRegister.success) {
          alert('Success');
          this.router.navigate(['/login']);
        } else {
          alert('OTP Invalid');
          this.router.navigate(['/signup']);
        }
      });
  }

  createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control('', [Validators.required]),
      otp: this.fb.control('', [Validators.required]),
    });
  }
}
