import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "ng2-ui-auth";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

@Component({
  selector: 'pathsala-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  user: any;
  expiration: Date;
  secret: Observable<string>;

  constructor(private http: HttpClient,
              private auth: AuthService,
              private router: Router) {

  }

  ngOnInit() {
    this.user = this.auth.getPayload();
    this.expiration = this.auth.getExpirationDate();
    this.secret = this.http.get<string>('/secret');
  }

  logout() {
    this.auth.logout()
      .subscribe({
        error: (err: any) => alert(err.message),
        complete: () => this.router.navigateByUrl('login')
      });
  }

  refresh() {
    this.http.get<string>('/auth/refresh')
      .pipe(tap(token => this.auth.setToken(token)))
      .subscribe({
        error: (err: any) => alert(err.message),
        complete: () => this.expiration = this.auth.getExpirationDate()
      });
  }

  link() {
    this.auth.link('google')
      .subscribe({
        error: (err: any) => alert(err.message),
        complete: () => {
          this.expiration = this.auth.getExpirationDate();
          this.user = this.auth.getPayload();
        }
      });
  }
}
