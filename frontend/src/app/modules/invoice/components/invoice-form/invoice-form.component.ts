import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CostType } from 'src/app/types/costs.type';
import { InvoiceService } from '../../invoice.service';
import * as moment from 'moment-timezone';

@Component({
    selector: 'ski-invoice-form',
    templateUrl: './invoice-form.component.html',
    styleUrls: ['./invoice-form.component.scss']
})
export class InvoiceFormComponent implements OnInit {
    INVOICE_FORM!: FormGroup;
    DATA_IS_BEING_SENT: boolean = false;
    @Output() triggerCloseEvent = new EventEmitter<boolean>();
    SUPPLIER_TYPE = ['ORDINAIRE', 'CONVENTIONNE'];

    constructor(private fb: FormBuilder, private router: Router, private invoiceService: InvoiceService) {}

    ngOnInit(): void {
        this.buildInvoiceForm();
        this.invoiceService.DATA_IS_BEING_SENT.subscribe({
            next: (value: boolean) => {
                this.DATA_IS_BEING_SENT = value;
            }
        });
    }

    buildInvoiceForm() {
        this.INVOICE_FORM = this.fb.group({
            amountDiscount: [null, Validators.required],
            amountInvoice: [null, Validators.required]
        });
    }

    saveInvoice() {
        // LOADER
        this.invoiceService.DATA_IS_BEING_SENT.next(true);
        // TMP FORM DATA
        const DATA: any = {
            ...this.INVOICE_FORM.value,
            "archived" : false,
            "dateCreationInvoice" : moment(new Date()).format("YYYY-MM-DD") ,
            "dateLastModificationInvoice" : moment(new Date()).format("YYYY-MM-DD")

        };
        // DATA SENT TO BACKEND
        this.invoiceService.addElement(DATA).subscribe({
            next: (response: any) => {
                if (response) {
                    this.invoiceService.DATA_IS_BEING_SENT.next(false);
                    this.invoiceService.invoice_HAS_BEEN_TRIGGERED$.next({
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
