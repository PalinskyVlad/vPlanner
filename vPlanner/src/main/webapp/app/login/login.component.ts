import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService, AuthenticationService, AuthorizationService } from '../_services/index';

@Component( {
    moduleId: module.id,
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css'],
})

export class LoginComponent implements OnInit {
    model: any = {};
    loading = false;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private authorizationService: AuthorizationService,
        private alertService: AlertService ) { }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();
    }

    login() {
        this.loading = true;
        this.authenticationService.login( this.model.username, this.model.password )
            .subscribe(
            data => {
                if ( this.authorizationService.isManager() ) {
                    this.router.navigate( ['/statements'] );
                }
                if ( this.authorizationService.isEmployee() ) {
                    this.router.navigate( ['/calendar'] );
                }
                console.log('authentication service end');
            },
            error => {
                this.alertService.error( error );
                this.loading = false;
            });
    }
}
