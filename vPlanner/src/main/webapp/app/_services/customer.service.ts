import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { JWTService } from './jwt.service';

@Injectable()
export class CustomerService {

    constructor( private http: Http, private jwtService: JWTService ) { }

    saveCustomer( employeeId, customer ) {
        return this.http.post( '/vPlanner/rest/customers/' + employeeId, customer, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

}