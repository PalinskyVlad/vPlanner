import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { JWTService } from './jwt.service';

@Injectable()
export class VacationService {

    constructor( private http: Http, private jwtService: JWTService ) { }

    getVacationsByEmployeeId( employeeId ) {
        return this.http.get( '/vPlanner/rest/vacations/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    getVacationForCustomerApprove( employeeId ) {
        return this.http.get( '/vPlanner/rest/vacations/forCustomerApprove/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    saveVacations( events, employeeId ) {
        var vacationDTOs = JSON.stringify( events );
        return this.http.post( '/vPlanner/rest/vacations/' + employeeId, vacationDTOs, this.jwtService.jwt() ).map(( response: Response ) => response );
    }

    create( vacation ) {
        return this.http.post( '/vPlanner/rest/vacations', vacation, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    deleteVacation( id ) {
        return this.http.delete( '/vPlanner/rest/vacations' + id, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }
    
    registerCustomerApprove(customer, vacationId) {
        return this.http.post( '/vPlanner/rest/vacations/approve/' + vacationId, customer, this.jwtService.jwt() ).map(( response: Response ) => response );
    }
        
}