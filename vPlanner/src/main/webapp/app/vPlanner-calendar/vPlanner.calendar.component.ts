import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';

import { VacationService, VacationPlanService, CustomerService, AuthenticationService } from '../_services/index';

import { VacationPlanValidator, VacationValidator } from '../_validators/index';

import { User } from '../_models/index';

@Component( {
    moduleId: module.id,
    selector: 'vPlanner-calendar',
    templateUrl: 'vPlanner.calendar.component.html',
    styleUrls: ['vPlanner.calendar.component.css']
})
export class VPlannerCalendarComponent implements OnInit {

    currentUser: User;

    events: any[] = [];

    vacationPlans: any[] = [];

    vacations: any[] = [];

    createdOrChangedVacationPlans: any[] = [];

    createdOrChangedVacations: any[] = [];

    height: number = 440;

    header: any;

    date: Date;

    monthNames: String[];

    dialogVisible: boolean = false;

    infoDialogVisible: boolean = false;

    infoType: string;

    infoDialogText: string;

    event: MyEvent;

    idGen: number = -10000;

    currentDisplay: string;
    error: string;

    constructor( private authenticationService: AuthenticationService, private customerService: CustomerService, private vacationService: VacationService, private vacationPlanService: VacationPlanService, private vacationPlanValidator: VacationPlanValidator, private vacationValidator: VacationValidator, private cd: ChangeDetectorRef ) {

        this.currentUser = JSON.parse( localStorage.getItem( 'currentUser' ) );

        this.date = new Date();

        this.monthNames = ["Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"];
    }

    ngOnInit() {
        localStorage.setItem( 'header', 'Calendar' );
        this.vacationPlanService.getVacationPlansByEmployeeId( JSON.parse( localStorage.getItem( 'currentUser' ) ).id ).subscribe( vacationPlans => { this.vacationPlans = vacationPlans; this.events = this.vacationPlans; }, error => {
            if ( error.status == 401 ) { this.authenticationService.logout(); }
        });
        this.vacationService.getVacationsByEmployeeId( JSON.parse( localStorage.getItem( 'currentUser' ) ).id ).subscribe( vacations => { this.vacations = vacations; },
            error => {
                if ( error.status == 401 ) { this.authenticationService.logout(); }
            });

        this.currentDisplay = "Vacation Plans";
        this.events = this.vacationPlans;
        this.header = {
            left: 'title',
            center: false,
            right: 'today'
        };
    }

    goToMonth( fc, index ) {
        this.date.setMonth( index );
        fc.gotoDate( this.date );
    }

    goToOneYearAgo( fc ) {
        this.date.setFullYear( this.date.getFullYear() - 1 );
        fc.gotoDate( this.date );
    }

    goToOneYearForward( fc ) {
        this.date.setFullYear( this.date.getFullYear() + 1 );
        fc.gotoDate( this.date );
    }

    handleDayClick( event ) {
        let eventStartDate: string = event.date.format();

        this.error = '';

        //Сheck the current display
        if ( this.currentDisplay == 'Vacations' ) {
            this.infoType = 'Error';
            this.infoDialogText = 'Sorry, you cannot create vacation. \n\n Vacation will be added automaticly when manager approve vacation plan.';
            this.infoDialogVisible = true;
            return;
        }

        //If start date is not valid, error will be output in the dialog info
        if ( !this.vacationPlanValidator.startDateIsValid( this.vacationPlans, eventStartDate, null ) ) {
            this.infoType = 'Error';
            this.infoDialogText = this.vacationPlanValidator.error;
            this.infoDialogVisible = true;
            return;
        }

        //Create event 
        this.event = new MyEvent();
        this.event.start = eventStartDate;
        this.dialogVisible = true;
        this.cd.detectChanges();
    }

    handleEventClick( e ) {
        this.error = '';

        //If event is editable
        if ( e.calEvent.editable ) {
            this.event = new MyEvent();
            this.event.title = e.calEvent.title;
            let start = e.calEvent.start;
            let end = e.calEvent.end;
            if ( e.view.name === 'month' ) {
                start.stripTime();
            }
            if ( end ) {
                end.stripTime();
                this.event.end = end.format();
            }
            this.event.id = e.calEvent.id;
            this.event.color = e.calEvent.color;
            this.event.start = start.format();
            this.event.allDay = true;
            this.dialogVisible = true;
        }
    }

    saveEvent() {
        if ( this.currentDisplay == "Vacation Plans" ) {
            //If start date is not valid, error will be output in the dialog info
            if ( !this.vacationPlanValidator.startDateIsValid( this.vacationPlans, this.event.start, this.event.id ) ) {
                this.error = this.vacationPlanValidator.error;
                return;
            }

            if ( this.event.end == null ) {
                this.error = 'The date of the vacation end cannot be empty';
                return;
            }
            this.event.title = 'After save vacation plan will be sent to the manager';
            //update
            if ( this.event.id ) {
                this.event.color = null;
                if ( this.event.end == null ) { return; }
                let index: number = this.findEventIndexById( this.event.id );
                let vacationPlanIndex: number = this.findVacationPlanIndexById( this.event.id );
                if ( index >= 0 ) {
                    this.vacationPlans[index] = this.event;
                    if ( vacationPlanIndex >= 0 ) {
                        this.createdOrChangedVacationPlans[vacationPlanIndex] = this.event;
                    } else {
                        this.createdOrChangedVacationPlans.push( this.event );
                    }
                }
            }
            //new
            else {
                this.event.id = this.idGen;
                this.createdOrChangedVacationPlans.push( this.event );
                this.vacationPlans.push( this.event );
                this.event = null;
                this.idGen = this.idGen + 1;
            }
        }

        if ( this.currentDisplay == "Vacations" ) {
            if ( this.vacationValidator.isAfterToday( this.event.start ) ) {
                this.event.title = 'Please, request your customer to approve vacation';
                let index: number = this.findEventIndexById( this.event.id );
                let vacationIndex: number = this.findVacationIndexById( this.event.id );
                if ( index >= 0 ) {
                    this.vacations[index] = this.event;
                    if ( vacationIndex > 0 ) {
                        this.createdOrChangedVacations[vacationIndex] = this.event;
                    } else {
                        this.createdOrChangedVacations.push( this.event );
                    }
                }
            }
        }
        this.dialogVisible = false;
    }

    deleteEvent() {
        let createdOrChangedVacationPlanIndex: number = this.findVacationPlanIndexById( this.event.id );
        if ( createdOrChangedVacationPlanIndex >= 0 ) {
            this.createdOrChangedVacationPlans.splice( createdOrChangedVacationPlanIndex, 1 );
        }
        let index: number = this.findEventIndexById( this.event.id );
        if ( index >= 0 ) {
            this.events.splice( index, 1 );
        }
        this.dialogVisible = false;
    }

    findVacationPlanIndexById( id: number ) {
        let index = -1;
        for ( let i = 0; i < this.createdOrChangedVacationPlans.length; i++ ) {
            if ( id == this.createdOrChangedVacationPlans[i].id ) {
                index = i;
                break;
            }
        }
        return index;
    }

    findVacationIndexById( id: number ) {
        let index = -1;
        for ( let i = 0; i < this.createdOrChangedVacations.length; i++ ) {
            if ( id == this.createdOrChangedVacations[i].id ) {
                index = i;
                break;
            }
        }
        return index;
    }

    findEventIndexById( id: number ) {
        let index = -1;
        for ( let i = 0; i < this.events.length; i++ ) {
            if ( id == this.events[i].id ) {
                index = i;
                break;
            }
        }
        return index;
    }

    saveVacations() {
        
        if (this.createdOrChangedVacationPlans.length == 0 && this.createdOrChangedVacations.length == 0) {
            this.infoType = 'Error';
            this.infoDialogText = 'No changes for save';
            this.infoDialogVisible = true;
            return;
        }

        // checking for changes
        if ( this.createdOrChangedVacationPlans.length > 0 ) {
            // if vacation plans are rejected and changed one of this vacation plans
            if ( this.createdOrChangedVacationPlans.length < 2 ) {
                for ( let i = 0; i < this.vacationPlans.length; i++ ) {
                    if ( this.findVacationPlanIndexById( this.vacationPlans[i].id ) < 0 ) {
                        if ( this.vacationPlans[i].editable ) {
                            this.vacationPlans[i].editable = false;
                            this.createdOrChangedVacationPlans.push( this.vacationPlans[i] );
                        }
                    }
                }
            }

            if ( ( this.vacationPlanValidator.vacationPlansAreValid( this.createdOrChangedVacationPlans ) ) ) {
                this.vacationPlanService.saveVacationPlans( this.createdOrChangedVacationPlans, JSON.parse( localStorage.getItem( 'currentUser' ) ).id ).subscribe( data => {
                    this.infoType = 'Success';
                    this.infoDialogText = 'Vacation plans save successfully';
                    this.infoDialogVisible = true;
                    this.createdOrChangedVacationPlans = [];
                    return;
                },
                    error => {
                        if ( error.status == 401 ) { this.authenticationService.logout(); }
                        else {
                            this.infoType = 'Error';
                            this.infoDialogText = 'Save error';
                            this.infoDialogVisible = true;
                        }
                        return;
                    });
            } else {
                this.infoType = 'Error';
                this.infoDialogText = this.vacationPlanValidator.error;
                this.infoDialogVisible = true;
                return;
            }
        }

        // checking for changes
        if ( this.createdOrChangedVacations.length > 0 ) {
            if ( this.vacationValidator.vacationsAreValid( this.createdOrChangedVacations ) ) {
                this.vacationService.saveVacations( this.createdOrChangedVacations, JSON.parse( localStorage.getItem( 'currentUser' ) ).id ).subscribe( data => {
                    this.infoType = 'Success';
                    this.infoDialogText = 'Vacations save successfully';
                    this.infoDialogVisible = true;
                    this.createdOrChangedVacations = [];
                    return;
                },
                    error => {
                        if ( error.status == 401 ) { this.authenticationService.logout(); }
                        else {
                            this.infoType = 'Error';
                            this.infoDialogText = 'Save error';
                            this.infoDialogVisible = true;
                            return;
                        }
                    });
                return;
            } else {
                this.infoType = 'Error';
                this.infoDialogText = this.vacationPlanValidator.error;
                this.infoDialogVisible = true;
                return;
            }
        }
    }

    getVacationsPlans() {
        this.events = this.vacationPlans;
        this.currentDisplay = "Vacation Plans";
    }

    getVacations() {
        this.events = this.vacations;
        this.currentDisplay = "Vacations";
    }

    closeInfoDialog() {
        this.infoDialogVisible = false;
    }

}

export class MyEvent {
    id: number;
    title: string;
    start: string;
    end: string;
    allDay: boolean = true;
    editable: boolean = true;
    color: string;
    description: string;
}