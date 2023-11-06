import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgZorroModule } from 'src/app/ng-zorro.module';
// CUSTOM IMPORTSQ
import { ConcatclassesPipe } from 'src/app/utils/concatclasses.pipe';
import { CopyingComponent } from './copying/copying.component';
import { ModuleHeaderComponent } from './module-header/module-header.component';
import { FormsModule } from '@angular/forms';
import { NzI18nModule, en_US } from 'ng-zorro-antd/i18n';
import { NzI18nService } from 'ng-zorro-antd/i18n';
import { DataFilterPipe } from 'src/app/utils/data-filter.pipe';

@NgModule({
    declarations: [
        ConcatclassesPipe,
        CopyingComponent,
        ModuleHeaderComponent,
        DataFilterPipe,
    ],
    imports: [CommonModule, RouterModule, NgZorroModule, FormsModule],
    exports: [
        ConcatclassesPipe,
        CopyingComponent,
        ModuleHeaderComponent,
        DataFilterPipe,
    ],
})
export class SharedModule {}
