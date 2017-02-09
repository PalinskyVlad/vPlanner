import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor( private router: Router ) { }

    canActivate( route: ActivatedRouteSnapshot ) {
        let roles = route.data["roles"] as Array<string>;

        let currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );
        if ( localStorage.getItem( 'currentUser' ) ) {
            if ( roles == null || roles.indexOf( currentUser.role ) != -1 ) {
                return true;
            }
            if ( roles != null ) {
                return false;
            }
        }

        this.router.navigate( ['/login'] );

        return false;
    }

}