import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { SupplierService } from '../../supplier.service';

@Component({
    selector: 'ski-supplier-table',
    templateUrl: './supplier-table.component.html',
    styleUrls: ['./supplier-table.component.scss']
})
export class SupplierTableComponent {
    @Output() ActionEvent: EventEmitter<boolean> = new EventEmitter();
    @Input() data: any;
    listOfColumns: any[] = [
        {
            name: 'Code'
        },
        {
            name: 'Label'
        },
        {
            name: 'Category'
        },
        {
            name: 'Action'
        }
    ];
    constructor(private cs: SupplierService, private route: ActivatedRoute, private nzMessageService: NzMessageService) {}
    delete(): void {
        this.nzMessageService.info('Event has been canceled.');
    }
    deleteSupplier(id: string) {
        this.cs.deleteElement(id).subscribe({
            next: (response: any) => {
                this.ActionEvent.emit(true);
                this.nzMessageService.success('Supplier has been deleted.');
            }
        });
    }
    cancel(): void {
        this.nzMessageService.info('Event has been canceled.');
    }
}
