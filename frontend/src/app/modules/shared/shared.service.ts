import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SharedService {
    data_filter: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    setFilter(filter: any) {
        this.data_filter.next(filter);
    }

    getFilter(): Observable<any> {
        return this.data_filter.asObservable();
    }

    constructor() {}
}
