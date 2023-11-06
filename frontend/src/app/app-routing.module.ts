import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// CUSTOM IMPORTS
import { NotFoundComponent } from './views/not-found/not-found.component';

const routes: Routes = [
    // { path: '', redirectTo: 'suppliers', pathMatch: 'full' },

    {
        path: 'suppliers',
        loadChildren: () => import('src/app/modules/suppliers/supplier.module').then((module) => module.SupplierModule)
    },
    {
        path: 'invoices',
        loadChildren: () => import('src/app/modules/invoice/invoice.module').then((module) => module.InvoiceModule)
    },

    { path: '**', component: NotFoundComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
