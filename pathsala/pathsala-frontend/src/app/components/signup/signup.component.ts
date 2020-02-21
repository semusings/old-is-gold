import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "ng2-ui-auth";
import {Router} from "@angular/router";
import {FormHelperService} from "../../services/form-helper.service";

@Component({
  selector: 'pathsala-signup',
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

  signup(signupData: any) {
    this.auth.signup({
      username: signupData['username'],
      password: signupData['password-group']['password'],
      name: signupData['name'],
      email: signupData['email'],
      phoneNumber: signupData['phoneNumber']
    })
      .subscribe({
        next: (response) => this.auth.setToken(response.access_token),
        complete: () => this.router.navigateByUrl('pathsala')
      });
  }

}
