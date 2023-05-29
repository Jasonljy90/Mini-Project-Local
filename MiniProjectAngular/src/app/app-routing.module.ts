import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { CountryInfoComponent } from './components/countryinfo.component';
import { HomeComponent } from './components/home.component';
import { SignupComponent } from './components/signup.component';
import { ChangePasswordComponent } from './components/change-password.component';
import { OtpEmailVerifyComponent } from './components/otp-email-verify.component';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { ResetPasswordComponent } from './components/reset-password.component';
import { LiveChatComponent } from './components/live-chat.component';
import { SearchHistoryInfoComponent } from './components/search-history-info.component';
import { SearchHistoryComponent } from './components/search-history.component';
import { StripePaymentComponent } from './components/stripe-payment.component';
import { AddToCartComponent } from './components/add-to-cart.component';
import { OrderHistoryComponent } from './components/order-history.component';

const routes: Routes = [
  { path: '', component: HomeComponent }, // Home page
  { path: 'search', component: SearchComponent }, // Search Country By Name
  { path: 'searchhistory', component: SearchHistoryComponent }, // Search Country By Name
  { path: 'otp', component: OtpEmailVerifyComponent }, // OTP Verification
  { path: 'login', component: LoginComponent }, // Login
  { path: 'countryinfo/:countryName', component: CountryInfoComponent }, // Display County Info from search result
  { path: 'reset/password', component: ResetPasswordComponent },
  { path: 'signup', component: SignupComponent }, // signup component
  { path: 'change/password', component: ChangePasswordComponent },
  { path: 'delete/account', component: SignupComponent },
  { path: 'main', component: MainComponent }, // Main component
  { path: 'livechat', component: LiveChatComponent }, // Live Chat component
  { path: 'searchhistory/:username', component: SearchHistoryInfoComponent }, // Search History component
  { path: 'shopping/search', component: AddToCartComponent }, // Shopping Products component
  { path: 'shopping/payment', component: StripePaymentComponent }, // Live Chat component
  { path: 'shopping/order/history', component: OrderHistoryComponent }, // Order History component
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
