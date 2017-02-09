import { Component, NgModule } from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';


@NgModule( {
    providers: [{ provide: APP_BASE_HREF, useValue: '/' }]
})

@Component( {
    moduleId: module.id,
    selector: 'app',
    templateUrl: 'app.component.html'
})

export class AppComponent { }