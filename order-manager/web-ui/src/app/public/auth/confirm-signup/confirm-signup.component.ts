import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {FormHelperService} from '../../../services/form-helper.service';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'bhuwanupadhyay-confirm-signup',
  templateUrl: './confirm-signup.component.html',
  styleUrls: ['./confirm-signup.component.scss']
})
export class ConfirmSignupComponent implements OnInit {

  form: FormGroup;

  constructor(private auth: AuthService, private fb: FormBuilder,
              public fh: FormHelperService) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      confirmationCode: new FormControl('', [Validators.required]),
    });
  }

  confirmSignUp(confirm: any) {
    this.auth.confirmSignUp(confirm.confirmationCode);
  }

}
