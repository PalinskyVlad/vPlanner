import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { ScheduleModule } from 'primeng/primeng';
import { DialogModule } from 'primeng/primeng';
import { ButtonModule } from 'primeng/primeng';
import { InputTextModule } from 'primeng/primeng';
import { InputMaskModule } from 'primeng/primeng';

import { AppComponent } from './app.component';
import { routing } from './app.routing';

import { AlertComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import { AlertService, AuthenticationService, AuthorizationService,
         UserService, EmployeeService, VacationService, VacationPlanService, JWTService, CustomerService } from './_services/index';

import { VacationPlanValidator } from './_validators/index';
import { VacationValidator } from './_validators/index';

import { HeaderComponent } from './header/index';
import { LeftSideMenuComponent } from './left-side-menu/index';
import { FooterComponent } from './footer/index';

import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';

import { HomeComponent } from './home/index';

import { VPlannerCalendarComponent } from './vPlanner-calendar/index';
import { VacationComponent } from './vacation/index';

import { StatementsVacationComponent } from './statements-vacation/index';
import { EmployeesComponent } from './employees/index';

@NgModule( {
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        ScheduleModule,
        DialogModule,
        ButtonModule,
        InputTextModule,
        InputMaskModule,
        routing
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        HeaderComponent,
        FooterComponent,
        LeftSideMenuComponent,
        VPlannerCalendarComponent,
        StatementsVacationComponent,
        VacationComponent,
        EmployeesComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        AuthorizationService,
        UserService,
        EmployeeService, 
        VacationService, 
        VacationPlanService,
        CustomerService,
        JWTService,
        VacationPlanValidator,
        VacationValidator
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }