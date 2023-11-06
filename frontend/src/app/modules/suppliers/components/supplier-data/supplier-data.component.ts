import { Component, OnInit } from '@angular/core';
import { SupplierService } from '../../supplier.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/modules/shared/shared.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
    selector: 'ski-supplier-data',
    templateUrl: './supplier-data.component.html',
    styleUrls: ['./supplier-data.component.scss']
})
export class SupplierDataComponent implements OnInit {
    SUPPLIER_DATA!: any[];

    isFetchingData: boolean = false;
    POST_ACTION_ELEMENT: string = '';

    constructor(private cs: SupplierService, private route: ActivatedRoute, private nzMessageService: NzMessageService) {}

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
        this.cs.getElements().subscribe({
            next: (response: any[]) => {
                this.SUPPLIER_DATA = response;
                this.isFetchingData = false;
            }
        });
    }

    checkForUpdates() {
        this.cs.SUPPLIER_HAS_BEEN_TRIGGERED$.subscribe({
            next: (response: any) => {
                if (response.state && response.action === 'del') this.SUPPLIER_DATA = this.SUPPLIER_DATA.filter((element) => element.id !== response.id);
                if ((response.state && response.action === 'update') || 'add') this.fetchData();
            }
        });
    }
}
