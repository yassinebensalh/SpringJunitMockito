import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SupplierService {
    API_URL = environment.API_URL;
    authz_id = {};
    DATA_IS_BEING_SENT = new BehaviorSubject(false);
    SUPPLIER_HAS_BEEN_TRIGGERED$ = new BehaviorSubject<any>(<any>{
        state: false,
        id: '',
        action: ''
    });

    constructor(private http: HttpClient) {}

    addElement(supplier: any) {
        return this.http.post(`${this.API_URL}supplier/`, supplier);
    }

    getElements(): any {
        return this.http.get(`${this.API_URL}supplier`);
    }

    patchExpiryDate(id: string) {
        return this.http.put(`${this.API_URL}gym_members/reniew_membership/${id}`, id);
    }

    patchAssurance(id: string, is_assured: boolean) {
        return this.http.patch(`${this.API_URL}gym_members/assure/${id}`, {
            is_assured
        });
    }

    patchPaymentStatus(id: string, payment_status: string) {
        return this.http.patch(`${this.API_URL}gym_members/payment/${id}`, {
            payment_status
        });
    }
    getAllTimeBalance() {
        return this.http.get(`${this.API_URL}gym_members/all_time_balance`);
    }

    getMonthlyBalance() {
        return this.http.get(`${this.API_URL}gym_members/monthly_balance`);
    }
    getActiveMembersCount() {
        return this.http.get(`${this.API_URL}gym_members/active_members`);
    }

    getSubscriptionSums() {
        return this.http.get(`${this.API_URL}gym_members/stats_subscription_type`);
    }
    getMembersCount() {
        return this.http.get(`${this.API_URL}gym_members/member_count`);
    }

    getGenderSums() {
        return this.http.get(`${this.API_URL}gym_members/gender_sums`);
    }

    getAlltimeDepenses() {
        return this.http.get(`${this.API_URL}statistics/all_time_depenses`);
    }

    getThisMonthDepenses() {
        return this.http.get(`${this.API_URL}statistics/this_month_depenses`);
    }
    addDepense(payload: any) {
        return this.http.post(`${this.API_URL}statistics/depenses`, payload);
    }

    getElement(supplier_id: string): any {
        return this.http.get(`${this.API_URL}/suppliers/${supplier_id}`);
    }

    updateElement(supplier_id: string, payload: any) {
        return this.http.put(`${this.API_URL}/suppliers/${supplier_id}`, payload);
    }

    addFrais(payload: any) {
        return this.http.post(`${this.API_URL}api/frais`, payload);
    }

    deleteElement(supplier_id: string): any {
        return this.http.delete(`${this.API_URL}supplier/${supplier_id}`);
    }
}
