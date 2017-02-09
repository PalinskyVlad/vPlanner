import { Component } from '@angular/core';

import { AuthorizationService, VacationService } from '../_services/index';

@Component( {
    moduleId: module.id,
    selector: 'vPlanner-left-side-menu',
    templateUrl: 'left.side.menu.component.html',
    styleUrls: ['left.side.menu.component.css']
})

export class LeftSideMenuComponent {
    
    constructor( private authorizationService: AuthorizationService, private vacationService: VacationService ) { }

}