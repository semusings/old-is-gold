import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ClarityModule} from '@clr/angular';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginComponent} from './components/login/login.component';
import {SignupComponent} from './components/signup/signup.component';
import {Ng2UiAuthModule} from 'ng2-ui-auth';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {FormHelperService} from './services/form-helper.service';
import {PathsalaHttpInterceptorService} from './services/pathsala-http-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    ClarityModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    Ng2UiAuthModule.forRoot({
      baseUrl: 'https://zxscy6aw9g.execute-api.us-east-1.amazonaws.com/dev',
      providers: {
        google: {
          clientId: 'REPLACE ME',
        }
      }
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: PathsalaHttpInterceptorService,
      multi: true,
    }
    , FormHelperService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
