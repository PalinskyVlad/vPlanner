import { Component , OnInit } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'vPlanner-header',
    templateUrl: 'header.component.html'
})

export class HeaderComponent implements OnInit {

	private header: string;
	
	constructor() {
		this.header = "Header";
	}

	ngOnInit() {
		if (localStorage.getItem('header')) {
		this.header = localStorage.getItem('header');
		}
	}
}