import { Injectable } from '@angular/core';

@Injectable()
export class AuthorizationService {
   
    isManager() {
        let currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );

        if ( currentUser ) {
            if ( currentUser.role == 'Manager' ) {
                return true;
            }
        }
        return false;
    }

    isEmployee() {
        let currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );

        if ( currentUser ) {
            if ( currentUser.role == 'Employee' ) {
                return true;
            }
        }
        return false;
    }

    isAuthorize() {
        let currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );

        if ( currentUser ) {
                return true;
        } 
        return false;
    }
    
}