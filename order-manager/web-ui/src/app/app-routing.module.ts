import {NgModule} from '@angular/core';
import {LoginComponent} from './public/auth/login/login.component';
import {SignupComponent} from './public/auth/signup/signup.component';
import {AuthGuard} from './guards/auth.guard';
import {RouterModule, Routes} from '@angular/router';
import {ConfirmSignupComponent} from './public/auth/confirm-signup/confirm-signup.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'oms',
  },
  {
    path: 'oms',
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'ui',
      },
      {
        path: 'ui',
        loadChildren: './secure/secure.module#SecureModule',
        canActivate: [AuthGuard]
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignupComponent
      },
      {
        path: 'confirm-signup',
        component: ConfirmSignupComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  providers: [AuthGuard],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
