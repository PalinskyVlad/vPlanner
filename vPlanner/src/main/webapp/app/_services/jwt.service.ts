import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

@Injectable()
export class JWTService {

    jwt() {
        // create authorization header with jwt token
        var token = localStorage.getItem( 'token' );
        console.log( token );
        let currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );
        if ( currentUser && token ) {
            let headers = new Headers( { 'Authorization': 'Bearer ' + token, 'Content-Type' : 'application/json' });
            return new RequestOptions( { headers: headers });
        }
    }
}