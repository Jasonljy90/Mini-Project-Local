import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchComponent } from './components/search.component';
import { CountryInfoComponent } from './components/countryinfo.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home.component';
import { SearchHistoryComponent } from './components/search-history.component';
import { CountryService } from './services/country.service';
import { SenderService } from './services/sender.service';
import { SignupComponent } from './components/signup.component';
import { OtpEmailVerifyComponent } from './components/otp-email-verify.component';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { ChangePasswordComponent } from './components/change-password.component';
import { ResetPasswordComponent } from './components/reset-password.component';
import { LiveChatComponent } from './components/live-chat.component';
import { SearchHistoryInfoComponent } from './components/search-history-info.component';
import { ShoppingProductsComponent } from './components/shopping-products.component';
import { StripePaymentComponent } from './components/stripe-payment.component';
import { CartComponent } from './components/cart.component';
import { AddToCartComponent } from './components/add-to-cart.component';
import { OrderHistoryComponent } from './components/order-history.component';

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    CountryInfoComponent,
    HomeComponent,
    SearchHistoryComponent,
    SignupComponent,
    OtpEmailVerifyComponent,
    LoginComponent,
    MainComponent,
    ChangePasswordComponent,
    ResetPasswordComponent,
    LiveChatComponent,
    SignupComponent,
    SearchHistoryInfoComponent,
    ShoppingProductsComponent,
    StripePaymentComponent,
    CartComponent,
    AddToCartComponent,
    OrderHistoryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule,
  ],
  providers: [CountryService, SenderService],
  bootstrap: [AppComponent],
})
export class AppModule {}
