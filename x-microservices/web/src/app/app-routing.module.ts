import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {OmsConstants} from "./base";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: OmsConstants.BASE_PAGE_URI,
  },
  {
    path: OmsConstants.OMS,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'ui',
      },
      {
        path: OmsConstants.LOGIN,
        component: LoginComponent
      },
      {
        path: OmsConstants.REGISTER,
        component: RegisterComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
