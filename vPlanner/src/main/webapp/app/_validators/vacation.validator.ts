import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class VacationValidator {

	currentDate : Date;

    constructor( ) { 
    	this.currentDate = new Date();
    }

	isAfterToday( date ) {
    	let eventStartDate : Date = new Date(date);
    	if (eventStartDate > this.currentDate) {
    		return true;
    	}
    	return false;
    }

    vacationsAreValid( createdOrChangedVacations ) {
        return true;
    }

    checkVacationPlansLengths( vacations ) {
        let startDate : Date;
        let endDate : Date;
        for(let i = 0; i < vacations.length; i++) {
            startDate = new Date(vacations[i].start);
            startDate.setDate(startDate.getDate() + 14);
            endDate = new Date(vacations[i].end);
            if(startDate < endDate) {
                return true;
            }
        }
        return false;
    }
}