import { Component, OnInit } from '@angular/core';

import { EmployeeService, AuthenticationService } from '../_services/index';

import { User, Employee } from '../_models/index';

@Component( {
    moduleId: module.id,
    selector: 'employees',
    templateUrl: 'employees.component.html'
})

export class EmployeesComponent implements OnInit {

    currentUser: User;

    employees: Employee[];

    dateString: String;

    constructor( private authenticationService: AuthenticationService, private employeeService: EmployeeService ) {
        this.currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );
    }

    ngOnInit() {
        localStorage.setItem( 'header', 'Employees' );
        this.loadEmployeesByManagerId();
    }

    private loadEmployeesByManagerId() {
        this.employeeService.getByManagerId( this.currentUser.id ).subscribe( employees => { this.employees = employees; },
            error => {
                if ( error.status == 401 ) { this.authenticationService.logout(); }
            });
    }

}