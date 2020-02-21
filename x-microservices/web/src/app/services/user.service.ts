import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from './user';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  private BASE_URL = '/user-service/users';

  constructor(private httpClient: HttpClient) {
  }

  create(resource: User): Observable<any> {
    return this.httpClient.post(this.BASE_URL, resource);
  }

  update(resource: User, id: number): Observable<any> {
    return this.httpClient.put(`${this.BASE_URL}/${id}`, resource);
  }

  get(id: number): Observable<any> {
    return this.httpClient.get(`${this.BASE_URL}/${id}`);
  }

  list(page: number, pageSize: number = 10): Observable<any> {
    const params: HttpParams = new HttpParams();
    params.set('page', page.toString());
    params.set('pageSize', page.toString());
    const options = {
      params: params
    };
    return this.httpClient.get(this.BASE_URL, options);
  }

}
