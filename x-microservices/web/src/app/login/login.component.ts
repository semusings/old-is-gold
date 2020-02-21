import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {OmsConstants} from "../base";

@Component({
  selector: 'oms-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  login(): void {
    if (this.username == 'admin' && this.password == 'admin') {
      // noinspection JSIgnoredPromiseFromCall
      this.router.navigate(["user"]);
    } else {
      alert("Invalid credentials");
    }
  }

  signInByGoogle() {

  }

  signUp() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate([OmsConstants.REGISTER_PAGE_URI]);
  }
}
