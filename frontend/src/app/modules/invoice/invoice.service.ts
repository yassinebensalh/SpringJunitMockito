import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class InvoiceService {
    API_URL = environment.API_URL;
    authz_id = {};
    DATA_IS_BEING_SENT = new BehaviorSubject(false);
    invoice_HAS_BEEN_TRIGGERED$ = new BehaviorSubject<any>(<any>{
        state: false,
        id: '',
        action: ''
    });

    constructor(private http: HttpClient) {}

    addElement(invoice: any) {
        return this.http.post(`${this.API_URL}invoice/`, invoice);
    }

    getElements(): any {
        return this.http.get(`${this.API_URL}invoice`);
    }

    getElement(invoice_id: string): any {
        return this.http.get(`${this.API_URL}/invoice/${invoice_id}`);
    }

    updateElement(invoice_id: string, payload: any) {
        return this.http.put(`${this.API_URL}/invoice/${invoice_id}`, payload);
    }


    deleteElement(invoice_id: string): any {
        return this.http.delete(`${this.API_URL}invoice/${invoice_id}`);
    }
}
