import { Component } from '@angular/core';

@Component({
    selector: 'supplier-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.scss']
})
export class IndexComponent {
    createNewSupplierModalIsVisible: boolean = false;

    checkUserExisitanceInRemoteDB: boolean = false;
}
