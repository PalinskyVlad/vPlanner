import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class VacationPlanValidator {

	dateToCheckMonth: Date;

    startDateToCheck: Date;

    endDateToCheck: Date;

    error : string;

    constructor( ) { 
    	this.startDateToCheck = new Date();

        this.endDateToCheck = new Date();

        this.endDateToCheck.setFullYear(this.endDateToCheck.getFullYear() + 1);
        
        this.endDateToCheck.setMonth(0, 1);

        this.dateToCheckMonth = new Date();

        this.dateToCheckMonth.setMonth(this.dateToCheckMonth.getMonth() + 1);
    }
    // Check start date
    startDateIsValid(vacationPlans, startDate, eventId) {
    	let vacationPlanStartDate : Date = new Date(startDate);

    	if (!this.isYearAhead(vacationPlanStartDate)) {
    		this.error = 'Vacation should be planed only for the current year';
            return false;
    	}

    	if (!this.isMonthLater(vacationPlanStartDate)) {
    		this.error = 'Vacation should not start in 1 month after vacation plan created';
            return false;
    	}

        // If event is new
        if (!eventId) {
    	    if (this.vacationOnThisYearAlreadyPlanned(vacationPlans, vacationPlanStartDate)) {
                this.error = 'Vacation should consists of two parts, you cannot add vacation plan';
                return false;
    	    }
        }
        return true;
    }

    //Vacation should not start after one yaer
    isYearAhead( vacationPlanStartDate ) {
    	 if (vacationPlanStartDate < this.endDateToCheck && vacationPlanStartDate > this.startDateToCheck) {
    	 	return true;
    	 }
    	 return false;
    }

    //Vacation should not start in 1 month after vacation plan created
    isMonthLater( vacationPlanStartDate ) {
    	if (vacationPlanStartDate > this.dateToCheckMonth) {
    		return true;
    	}
    	return false;
    }

    //Check vacation plans on this year
    vacationOnThisYearAlreadyPlanned( vacationPlans, vacationPlanStartDate) {
    	let yearOfVacationPlan = vacationPlanStartDate.getFullYear();
    	let vacationCounter : number = 0;
    	for (let i = 0; i < vacationPlans.length; i++) {
    		let vacationStartDate = new Date(vacationPlans[i].start);
    		if (vacationStartDate.getFullYear() == yearOfVacationPlan) {
    			vacationCounter++;
    		}
    	}

    	if (vacationCounter == 2) {
    		return true; 
    	}

    	return false;
    }

    checkVacationPlansLengths( vacationPlans ) {
    	let startDate : Date;
    	let endDate : Date;
    	for(let i = 0; i < vacationPlans.length; i++) {
    		startDate = new Date(vacationPlans[i].start);
    		startDate.setDate(startDate.getDate() + 14);
    		endDate = new Date(vacationPlans[i].end);
    		if(startDate < endDate) {
    			return true;
    		}
    	}
    	this.error = 'At least one vacation part should be at least 14 days long, please change vacation plan and try again';
    	return false;
    }

    vacationPlansAreValid( createdOrChangedVacationPlans ) {

        if (createdOrChangedVacationPlans.length < 2) {
            for(let i = 0; i < createdOrChangedVacationPlans.length; i++) {
                if (createdOrChangedVacationPlans[i].id < 0) {
                    this.error = 'Vacation should consists of two parts, you cannot add vacation plan';
                    return false;
                }
            }
        }

        if (createdOrChangedVacationPlans.length == 2) {
            if (!this.checkVacationPlansLengths( createdOrChangedVacationPlans )) {
                this.error = 'At least one vacation part should be at least 14 days long, please change vacation plan and try again';
                return false;
            }
        }

        return true;

    }

}