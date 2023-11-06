import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgZorroModule } from 'src/app/ng-zorro.module';
// CUSTOM IMPORTS
import { SharedModule } from '../shared/shared.module';
import { InvoiceRoutingModule } from './invoice-routing.module';
import { IndexComponent } from './views/index/index.component';
import { InvoiceFormComponent } from './components/invoice-form/invoice-form.component';
import { InvoiceDataComponent } from './components/invoice-data/invoice-data.component';
import { NzI18nModule, NzI18nService, en_US } from 'ng-zorro-antd/i18n';
import { InvoiceTableComponent } from './components/invoice-table/invoice-table.component';
@NgModule({
    declarations: [IndexComponent, InvoiceFormComponent, InvoiceDataComponent, InvoiceTableComponent],
    imports: [CommonModule, NgZorroModule, ReactiveFormsModule, SharedModule, InvoiceRoutingModule, FormsModule, NzI18nModule]
})
export class InvoiceModule {
    constructor(private i18n: NzI18nService) {
        this.i18n.setLocale(en_US);
    }
}
