import { Component } from '@angular/core';
@Component({
    selector: 'ski-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
    constructor() {}
    visible = false;

    open(): void {
        this.visible = true;
    }

    close(): void {
        this.visible = false;
    }

    ngOnInit() {
        this.getAuth0Data();
    }

    getAuth0Data() {}
}
