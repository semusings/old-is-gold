import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ClarityModule} from '@clr/angular';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginComponent} from './public/auth/login/login.component';
import {SignupComponent} from './public/auth/signup/signup.component';
import {AppRoutingModule} from './app-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HttpInterceptorService} from './services/http-interceptor.service';
import {FormHelperService} from './services/form-helper.service';
import {AuthService} from './services/auth.service';
import {ConfirmSignupComponent} from './public/auth/confirm-signup/confirm-signup.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    ConfirmSignupComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    ClarityModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorService,
    multi: true,
  },
    AuthService,
    FormHelperService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
