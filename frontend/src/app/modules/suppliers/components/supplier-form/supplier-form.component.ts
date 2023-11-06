import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CostType } from 'src/app/types/costs.type';
import { SupplierService } from '../../supplier.service';

@Component({
    selector: 'ski-supplier-form',
    templateUrl: './supplier-form.component.html',
    styleUrls: ['./supplier-form.component.scss']
})
export class SupplierFormComponent implements OnInit {
    SUPPLIER_FORM!: FormGroup;
    DATA_IS_BEING_SENT: boolean = false;
    @Output() triggerCloseEvent = new EventEmitter<boolean>();
    SUPPLIER_TYPE = ['ORDINAIRE', 'CONVENTIONNE'];

    constructor(private fb: FormBuilder, private router: Router, private supplierService: SupplierService) {}

    ngOnInit(): void {
        this.buildSupplierForm();
        this.supplierService.DATA_IS_BEING_SENT.subscribe({
            next: (value: boolean) => {
                this.DATA_IS_BEING_SENT = value;
            }
        });
    }

    buildSupplierForm() {
        this.SUPPLIER_FORM = this.fb.group({
            code: [null, Validators.required],
            label: [null, Validators.required],
            category: ['ORDINAIRE', Validators.required]
        });
    }

    saveSupplier() {
        // LOADER
        this.supplierService.DATA_IS_BEING_SENT.next(true);
        // TMP FORM DATA
        const DATA: any = {
            ...this.SUPPLIER_FORM.value
        };
        // DATA SENT TO BACKEND
        this.supplierService.addElement(DATA).subscribe({
            next: (response: any) => {
                if (response) {
                    this.supplierService.DATA_IS_BEING_SENT.next(false);
                    this.supplierService.SUPPLIER_HAS_BEEN_TRIGGERED$.next({
                        state: true,
                        id: '',
                        action: 'add'
                    });
                    this.DATA_IS_BEING_SENT = false;
                    this.triggerCloseEvent.emit(true); // close modal
                }
            }
        });
    }
}
