import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgZorroModule } from 'src/app/ng-zorro.module';
// CUSTOM IMPORTS
import { SharedModule } from '../shared/shared.module';
import { SupplierRoutingModule } from './supplier-routing.module';
import { IndexComponent } from './views/index/index.component';
import { SupplierFormComponent } from './components/supplier-form/supplier-form.component';
import { SupplierDataComponent } from './components/supplier-data/supplier-data.component';
import { NzI18nModule, NzI18nService, en_US } from 'ng-zorro-antd/i18n';
import { SupplierTableComponent } from './components/supplier-table/supplier-table.component';
@NgModule({
    declarations: [IndexComponent, SupplierFormComponent, SupplierDataComponent, SupplierTableComponent],
    imports: [CommonModule, NgZorroModule, ReactiveFormsModule, SharedModule, SupplierRoutingModule, FormsModule, NzI18nModule]
})
export class SupplierModule {
    constructor(private i18n: NzI18nService) {
        this.i18n.setLocale(en_US);
    }
}
