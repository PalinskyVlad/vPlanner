import { Component, OnInit, Input } from '@angular/core';

import { Statement, VacationPlan, Employee, User } from '../_models/index';

import { VacationPlanService, EmployeeService, AlertService, AuthenticationService } from '../_services/index';


@Component({
    moduleId: module.id,
    selector: 'statements-vacation',
    templateUrl: 'statements.vacation.component.html',
    styleUrls: ['statements.vacation.component.css']   
})

export class StatementsVacationComponent implements OnInit {


	vacationPlans: VacationPlan[] = [];

	statements: Statement[] = [];

	employeeIds: number[] = [];

	//employees: Employee[] = [];

	employee: any;

	firstName: string;
	lastName:string;
	id: number;

	employeeId: number;

	infoType: string;

	info: string;

	user: User;

	check: boolean = true;

	constructor(private authenticationService: AuthenticationService, private vacationPlanService: VacationPlanService, private employeeService: EmployeeService, private alertService: AlertService) {
	}
	
	ngOnInit() {
		localStorage.setItem( 'header', 'Statements' );
		this.vacationPlanService.getVacationPlansByManagerId( JSON.parse( localStorage.getItem( 'currentUser' ) ).id ).subscribe( vacationPlans => { this.vacationPlans = vacationPlans; this.createStatements();},
                error => {
                    if ( error.status == 401 ) { this.authenticationService.logout(); }
                });		
	}

	createStatements() {
		for(var i = 0; i < this.vacationPlans.length; i++) {
			for(var j = 0; j < this.statements.length; j++) {
				if (this.employeeIds[j] == this.vacationPlans[i].employeeId) {
					this.statements[j].vacationPlans.push(this.vacationPlans[i]);
					this.check = false;
				}
			}
			if (this.check) {
					let statement = new Statement();
					statement.vacationPlans.push(this.vacationPlans[i]);
					this.employeeIds.push(this.vacationPlans[i].employeeId);
					this.statements.push(statement);
					this.employeeService.getById(this.vacationPlans[i].employeeId).subscribe( employee => { statement.employee = employee;});
					console.log(statement);
					this.check = false;
			}
			this.check = true;
		}
	}

	approveVacationPlans(employeeId) {
		this.vacationPlanService.approveVacationPlans(employeeId) .subscribe(
                data => {
                    this.employeeId = employeeId;
                    this.infoType = 'Success';
                    this.info = 'Your decision was sent to the employee successfully';
                },
                error => {
                    if ( error.status == 401 ) { this.authenticationService.logout(); } else {
                	this.employeeId = employeeId;
                    this.infoType = 'Error';
                	this.info = 'Error sending decision!';
                	this.alertService.error(error);
                    }
            
                });
	}

	rejectVacationPlans(employeeId) {
		this.vacationPlanService.rejectVacationPlans(employeeId).subscribe(
                data => {
                    this.employeeId = employeeId;
                	this.infoType = 'Success';
                    this.info = 'Your decision was sent to the employee successfully';
                },
                error => {
                    if ( error.status == 401 ) { this.authenticationService.logout(); } else {
                	this.employeeId = employeeId;
                	this.infoType = 'Error';
                	this.info = 'Error sending decision!';
                	this.alertService.error(error);
                    }
                });
	}


}