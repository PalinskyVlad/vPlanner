import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index';
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { VPlannerCalendarComponent } from './vPlanner-calendar/index';
import { StatementsVacationComponent } from './statements-vacation/index';
import { VacationComponent } from './vacation/index';
import { EmployeesComponent } from './employees/index';
import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'calendar', component: VPlannerCalendarComponent, canActivate: [AuthGuard], data: { roles: ['Employee'] } },
    { path: 'vacation', component: VacationComponent, canActivate: [AuthGuard], data: { roles: ['Employee'] } },
    { path: 'statements', component: StatementsVacationComponent, canActivate: [AuthGuard], data: { roles: ['Manager'] } },
    { path: 'employees', component: EmployeesComponent, canActivate: [AuthGuard], data: { roles: ['Manager'] } },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot( appRoutes );