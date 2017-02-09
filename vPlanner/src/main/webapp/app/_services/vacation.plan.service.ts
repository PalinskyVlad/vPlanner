import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { JWTService } from './jwt.service';

import { AuthenticationService} from './authentication.service';

@Injectable()
export class VacationPlanService {

    constructor( private http: Http, private jwtService: JWTService, private authenticationService: AuthenticationService ) { }

    getVacationPlansByEmployeeId( employeeId ) {
        return this.http.get( 'http://localhost:7001/vPlanner/rest/vacationPlans/employee/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response.json());
    }

    approveVacationPlans( employeeId ) {
        return this.http.put( 'http://localhost:7001/vPlanner/rest/vacationPlans/approve/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response);
    }

    rejectVacationPlans( employeeId ) {
        return this.http.put( 'http://localhost:7001/vPlanner/rest/vacationPlans/reject/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response);
    }

    getVacationPlansByManagerId( employeeId ) {
        return this.http.get( 'http://localhost:7001/vPlanner/rest/vacationPlans/manager/' + employeeId, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    saveVacationPlans( events, employeeId ) {
        var vacationPlanDTOs = JSON.stringify(events);
        return this.http.post( 'http://localhost:7001/vPlanner/rest/vacationPlans/employee/' + employeeId, vacationPlanDTOs, this.jwtService.jwt() ).map(( response: Response ) => response );
    }

    create( vacation ) {
        return this.http.post( 'http://localhost:7001/vPlanner/rest/vacationPlans', vacation, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }

    deleteVacation( id ) {
        return this.http.delete( 'http://localhost:7001/vPlanner/rest/vacationPlans' + id, this.jwtService.jwt() ).map(( response: Response ) => response.json() );
    }
}