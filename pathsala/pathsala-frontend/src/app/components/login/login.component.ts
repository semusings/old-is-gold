import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "ng2-ui-auth";
import {Router} from "@angular/router";
import {FormHelperService} from "../../services/form-helper.service";

@Component({
  selector: 'pathsala-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  errorOnLogin: boolean = false;

  constructor(private auth: AuthService,
              private router: Router,
              private fb: FormBuilder,
              public fh: FormHelperService) {

  }

  ngOnInit() {
    this.form = this.fb.group({
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    });
  }

  login(loginData: { username: string, password: string }) {
    this.auth.login(loginData)
      .subscribe({
        complete: () => this.router.navigateByUrl('pathsala')
      });
  }

  loginWithGoogle() {
    this.auth.authenticate('google')
      .subscribe({
        complete: () => this.router.navigateByUrl('pathsala')
      });
  }
}
