import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent {
  changePasswordForm!: FormGroup;
  email?: string;
  oldPassword?: string;
  newPassword?: string;
  dataRegister: any = {};

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private service: SenderService
  ) {}

  ngOnInit(): void {
    this.changePasswordForm = this.createForm();
  }

  changePassword() {
    const email = this.service.email;
    const oldPassword = this.changePasswordForm?.value['oldPassword'];
    const newPassword = this.changePasswordForm?.value['newPassword'];

    const data = {
      email: email,
      oldPassword: oldPassword,
      newPassword: newPassword,
    };

    let options = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/json; charset=UTF-8'
      ),
    };

    this.http
      .put('http://localhost:8080/changepassword', data, options)
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

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [Validators.required]),
      oldPassword: this.fb.control('', [Validators.required]),
      newPassword: this.fb.control('', [Validators.required]),
    });
  }
}
