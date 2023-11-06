import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'ski-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
    routes: any[] = [
        { label: 'suppliers', link: 'suppliers', icon: 'usergroup-add' },
        { label: 'invoices', link: 'invoices', icon: 'usergroup-add' }
    
    ];

    constructor(private router: Router) {}

    navigateToRoute(link: string) {
        this.router.navigate([link]);
    }
}
