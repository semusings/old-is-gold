import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {FormHelperService} from '../../../services/form-helper.service';
import {AuthService} from '../../../services/auth.service';

export interface LoginData {
  username: string;
  password: string;
}

@Component({
  selector: 'bhuwanupadhyay-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  errorOnLogin = false;

  constructor(private auth: AuthService,
              private router: Router,
              private fb: FormBuilder,
              public fh: FormHelperService) {

  }

  ngOnInit() {
    this.form = this.fb.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login(loginData: LoginData) {
    this.auth.login(loginData);
  }

  loginWithGoogle() {
    this.auth.authenticate('google');
  }
}
