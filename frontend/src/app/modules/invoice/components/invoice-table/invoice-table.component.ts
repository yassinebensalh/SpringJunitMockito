import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { InvoiceService } from '../../invoice.service';

@Component({
    selector: 'ski-invoice-table',
    templateUrl: './invoice-table.component.html',
    styleUrls: ['./invoice-table.component.scss']
})
export class InvoiceTableComponent {
    @Output() ActionEvent: EventEmitter<boolean> = new EventEmitter();
    @Input() data: any;
    listOfColumns: any[] = [
        {
            name: 'discountAMount'
        },
        {
            name: 'invoiceAMount'
        },
        {
            name: 'dateCreation'
        },
        {
            name: 'archived'
        }
    ];
    constructor(private is: InvoiceService, private route: ActivatedRoute, private nzMessageService: NzMessageService) {}
    delete(): void {
        this.nzMessageService.info('Event has been canceled.');
    }
    deleteInvoice(id: string) {
        this.is.deleteElement(id).subscribe({
            next: (response: any) => {
                this.ActionEvent.emit(true);
                this.nzMessageService.success('Invoice has been deleted.');
            }
        });
    }
    cancel(): void {
        this.nzMessageService.info('Event has been canceled.');
    }
}
