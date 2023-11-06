import { Component } from '@angular/core';

@Component({
    selector: 'invoice-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.scss']
})
export class IndexComponent {
    createNewInvoiceModalIsVisible: boolean = false;

    checkUserExisitanceInRemoteDB: boolean = false;
}
