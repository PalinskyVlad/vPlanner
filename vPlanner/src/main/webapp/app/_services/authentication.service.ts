import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Router } from '@angular/router';

@Injectable()
export class AuthenticationService {
    constructor(private router: Router,
            private http: Http) { }

    login(userId, password) {
        let body = { userId: userId, password: password };
        let headers = new Headers({ 'Content-Type': 'application/json' });
       
        
        return this.http.post('/vPlanner/rest/authentication', body , {headers: headers})
            .map((response: Response) => {

                console.log('service user');
                console.log(response.json());
                // login successful if there's a jwt token in the response
                var token = response.headers.get('Authorization');
                let user = response.json();
                if (user && token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    localStorage.setItem('token', token);
                }
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        localStorage.removeItem('token');
        this.router.navigate( ['/login'] );
    }
}