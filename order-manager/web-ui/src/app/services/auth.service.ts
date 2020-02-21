import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {LoginData} from '../public/auth/login/login.component';
import {AuthenticationDetails, CognitoUser, CognitoUserAttribute, CognitoUserPool} from 'amazon-cognito-identity-js';
import {environment} from '../../environments/environment';
import {SignUpUser} from '../public/auth/signup/signup.component';
import {Router} from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';

export interface SecurityToken {
  access_token: string;
  expiration: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly userPool: CognitoUserPool;
  private currentUser: CognitoUser;
  private tokenName = 'order-manager-token';
  private readonly jwt = new JwtHelperService();

  constructor(private router: Router) {
    this.userPool = new CognitoUserPool({
      ClientId: environment.clientId,
      UserPoolId: environment.userPoolId
    });
  }

  public login(loginData: LoginData): void {
    this.currentUser = this.newCognitoUser(loginData.username);
    this.currentUser
      .authenticateUser(new AuthenticationDetails({
          Username: loginData.username,
          Password: loginData.password,
        }),
        {
          onSuccess: (session, userConfirmationNecessary) => {
            if (userConfirmationNecessary) {
              this.goToConfirmSignup();
            } else {
              this.goToDashboard();
            }
          },
          onFailure: err => {
            if (err.code === '') {
              console.log(err);
            }
            throw err;
          },
          mfaRequired: (challengeName, challengeParameters) => {
            const confirmationCode = prompt('Please input verification code', '');
            this.currentUser.sendMFACode(confirmationCode, {
              onSuccess: session => {
                this.goToDashboard();
              },
              onFailure: err => {
                throw err;
              }
            });
          }
        });
  }

  public authenticate<T = any>(name: string, userData?: any): Observable<T> {
    return Observable.create(o => {
      return true;
    });
  }

  public signUp(user: SignUpUser): void {
    const list = [];
    const authInstance = this;
    list.push(new CognitoUserAttribute({Name: 'name', Value: user.name}));
    list.push(new CognitoUserAttribute({Name: 'email', Value: user.email}));
    list.push(new CognitoUserAttribute({Name: 'phone_number', Value: user.phoneNumber}));
    this.userPool.signUp(user.username, user.password, list, null,
      function (err, result) {
        if (err) {
          throw err;
        }
        if (!result.userConfirmed) {
          authInstance.goToConfirmSignup();
        }
      });
  }

  public isAuthenticated(): boolean {
    if (this.currentUser) {
      const session = this.currentUser.getSignInUserSession();
      if (session) {
        if (session.isValid()) {
          return true;
        }
      }
    } else {
      return !this.jwt.isTokenExpired(this.getToken());
    }
    return false;
  }

  public confirmSignUp(confirmationCode: string) {
    this.currentUser.confirmRegistration(confirmationCode, true, (err, result) => {
      if (err) {
        throw err;
      }
      console.log(result);
    });
  }

  public getToken(): string {
    const token = localStorage.getItem(this.tokenName);
    if (token) {
      const parse: SecurityToken = JSON.parse(token);
      if (parse) {
        return parse.access_token;
      }
    }
    return '';
  }

  private goToDashboard() {
    const accessToken = this.currentUser.getSignInUserSession().getAccessToken();
    const securityToken: SecurityToken = {access_token: accessToken.getJwtToken(), expiration: accessToken.getExpiration()};
    localStorage.setItem(this.tokenName, JSON.stringify(securityToken));
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/oms/ui/dashboard']);
  }

  private goToConfirmSignup() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/oms/confirm-signup']);
  }

  private goToLogin() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/oms/login']);
  }

  private newCognitoUser(username: string) {
    return new CognitoUser({
      Username: username,
      Pool: this.userPool
    });
  }
}
