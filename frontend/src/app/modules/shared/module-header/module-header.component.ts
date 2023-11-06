import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SharedService } from '../shared.service';
import { Filter, FilterCriteria } from 'src/app/types/filter.type';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
    selector: 'ski-module-header',
    templateUrl: './module-header.component.html',
    styleUrls: ['./module-header.component.scss']
})
export class ModuleHeaderComponent {
    @Input() moduleName!: string;
    @Input() moduleDescription!: string;
    @Output() triggerFormEvent = new EventEmitter<boolean>();
    date = null;
    filterForm: FormGroup;
    filterValue: any = { name: '', number: null, date: null };
    filterCriteria: any = { name: '', number: null, date: null };

    constructor(private readonly sharedService: SharedService) {
        this.filterForm = new FormGroup({
            name: new FormControl(''),
            number: new FormControl(null),
            date: new FormControl(null)
        });

        this.filterForm.valueChanges.subscribe((values) => {
            this.sharedService.setFilter(values);
        });
    }
    onFilterChange() {
        this.sharedService.setFilter(this.filterCriteria);
    }
}
