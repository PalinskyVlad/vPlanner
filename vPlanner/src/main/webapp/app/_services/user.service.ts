import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { JWTService } from './jwt.service';

@Injectable()
export class UserService {
    constructor( private http: Http, private jwtService: JWTService ) { }

    getAll() {
        return this.http.get( '/vPlanner/rest/users', this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    getById( id ) {
        return this.http.get( '/vPlanner/rest/users/' + id, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    create( user ) {
        return this.http.get( '/vPlanner/rest/users/authenticate', this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    update( user ) {
        return this.http.put( '/vPlanner/rest/users/' + user.id, user, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    delete( id ) {
        return this.http.delete( '/vPlanner/rest/users/' + id, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

}