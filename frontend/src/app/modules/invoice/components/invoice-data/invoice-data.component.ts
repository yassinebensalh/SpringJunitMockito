import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../../invoice.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/modules/shared/shared.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
    selector: 'ski-invoice-data',
    templateUrl: './invoice-data.component.html',
    styleUrls: ['./invoice-data.component.scss']
})
export class InvoiceDataComponent implements OnInit {
    INVOICE_DATA!: any[];

    isFetchingData: boolean = false;
    POST_ACTION_ELEMENT: string = '';

    constructor(private is: InvoiceService, private route: ActivatedRoute, private nzMessageService: NzMessageService) {}

    // startEdit(id: string): void {
    //     this.editId = id;
    // }

    // stopEdit(): void {
    //     this.editId = null;
    // }

    ngOnInit(): void {
        this.checkForPostActionRouting();
        this.fetchData();
        this.checkForUpdates(); // keeps listening for CRUD actions
    }

    checkForPostActionRouting() {
        this.route.queryParams.subscribe({
            next: (value: any) => {
                if (value) this.POST_ACTION_ELEMENT = value.created;
            }
        });
    }

    fetchData() {
        this.isFetchingData = true;
        this.is.getElements().subscribe({
            next: (response: any[]) => {
                this.INVOICE_DATA = response;
                this.isFetchingData = false;
            }
        });
    }

    checkForUpdates() {
        this.is.invoice_HAS_BEEN_TRIGGERED$.subscribe({
            next: (response: any) => {
                if (response.state && response.action === 'del') this.INVOICE_DATA = this.INVOICE_DATA.filter((element) => element.id !== response.id);
                if ((response.state && response.action === 'update') || 'add') this.fetchData();
            }
        });
    }
}
