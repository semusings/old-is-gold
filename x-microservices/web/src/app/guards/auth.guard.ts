import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {OmsConstants} from "../base";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    // if (this.authService.isAuthenticated()) {
    //   return true;
    // }
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate([OmsConstants.LOGIN_PAGE_URI], {queryParams: next.queryParams});
    return false;
  }
}
