import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'bna-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
    constructor() {}

    logout() {
        localStorage.clear();
    }
}
