import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {FormHelperService} from '../../../services/form-helper.service';
import {AuthService} from '../../../services/auth.service';

export interface SignUpUser {
  username: string;
  password: string;
  phoneNumber: string;
  email: string;
  name: string;
}

@Component({
  selector: 'bhuwanupadhyay-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  form: FormGroup;

  constructor(private auth: AuthService,
              private router: Router,
              private fb: FormBuilder,
              public fh: FormHelperService) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      'username': new FormControl('', [Validators.required, Validators.minLength(3)]),
      'name': new FormControl('', [Validators.required, Validators.minLength(3)]),
      'email': new FormControl('', [Validators.required, Validators.minLength(3)]),
      'phoneNumber': new FormControl('', [Validators.required, Validators.minLength(3)]),
      'password-group': new FormGroup(
        {
          'password': new FormControl('', [Validators.required, Validators.minLength(6)]),
          'confirm-password': new FormControl('')
        },
        (c: FormGroup) =>
          c.value['password'] === c.value['confirm-password'] ?
            null :
            {'pass-confirm': true}
      )
    });
  }

  register(signUpData: any) {
    this.auth.signUp({
      username: signUpData['username'],
      password: signUpData['password-group']['password'],
      name: signUpData['name'],
      email: signUpData['email'],
      phoneNumber: signUpData['phoneNumber']
    });
  }

}
