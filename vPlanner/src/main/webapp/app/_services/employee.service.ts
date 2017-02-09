import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { JWTService } from './jwt.service';

@Injectable()
export class EmployeeService {
    constructor( private http: Http, private jwtService: JWTService ) { }

    getByManagerId( managerId ) {
        return this.http.get( '/vPlanner/rest/employees/manager/' + managerId, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    getById( id ) {
        return this.http.get( '/vPlanner/rest/employees/' + id, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

}