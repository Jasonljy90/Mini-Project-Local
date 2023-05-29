import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  email?: string;
  dataRegister: any = {};

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.resetPasswordForm = this.createForm();
  }

  resetPassword() {
    const emailUser = this.resetPasswordForm?.value['email'];

    this.httpClient
      .get('http://localhost:8080/reset', {
        params: {
          email: emailUser,
        },
        observe: 'response',
      })
      .toPromise()
      .then((response) => {
        alert('Please Check Your Email');
        this.router.navigate(['/login']);
      })
      .catch(console.log);
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [Validators.required]),
    });
  }
}
