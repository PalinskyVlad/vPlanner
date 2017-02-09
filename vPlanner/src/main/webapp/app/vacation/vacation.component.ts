import { Component, OnInit } from '@angular/core';

import { VacationService, AuthenticationService } from '../_services/index';

import { User } from '../_models/index';

@Component( {
    moduleId: module.id,
    selector: 'vacations',
    templateUrl: 'vacation.component.html'
})

export class VacationComponent implements OnInit {

    currentUser: User;

    vacations: any[] = [];

    customer: Customer;

    vacationId: number;

    customerApproveDialogVisible: boolean = false;

    infoDialogVisible: boolean = false;

    infoType: string;

    infoDialogText: string;

    constructor( private authenticationService: AuthenticationService, private vacationService: VacationService ) {
        this.currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );
    }

    ngOnInit() {
        this.vacationService.getVacationForCustomerApprove( this.currentUser.id ).subscribe( vacations => { this.vacations = vacations; },
            error => {
                if ( error.status == 401 ) { this.authenticationService.logout(); }
            });
    }

    openCustomerApproveDialog( vacationId ) {
        this.customer = new Customer();
        this.vacationId = vacationId;
        this.customerApproveDialogVisible = true;
    }

    registerCustomerApprove() {
        this.vacationService.registerCustomerApprove( this.customer, this.vacationId ).subscribe(
            data => {
                this.infoType = 'Success';
                this.infoDialogText = 'Customer approve registered successfully';
                this.infoDialogVisible = true;
                return;
            },
            error => {
                if ( error.status == 401 ) { this.authenticationService.logout(); }
                else {
                    this.infoType = 'Error';
                    this.infoDialogText = 'Register error';
                    this.infoDialogVisible = true;
                    return;
                }
            });
        this.customerApproveDialogVisible = false;
    }

    closeCustomerApprove() {
        this.customerApproveDialogVisible = false;
    }
    
    closeInfoDialog() {
        this.infoDialogVisible = false;
    }
}

export class Customer {
    lastName: string;
    firstName: string;
    email: string;
    response: string;
}