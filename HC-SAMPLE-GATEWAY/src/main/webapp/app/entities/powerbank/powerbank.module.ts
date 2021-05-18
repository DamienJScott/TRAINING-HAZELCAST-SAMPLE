import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HcSampleSharedModule } from 'app/shared/shared.module';
import { PowerbankComponent } from './powerbank.component';
import { PowerbankDetailComponent } from './powerbank-detail.component';
import { PowerbankUpdateComponent } from './powerbank-update.component';
import { PowerbankDeleteDialogComponent } from './powerbank-delete-dialog.component';
import { powerbankRoute } from './powerbank.route';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';

@NgModule({
  imports: [HcSampleSharedModule, CardModule, InputTextModule, ButtonModule, DropdownModule, RouterModule.forChild(powerbankRoute)],
  declarations: [PowerbankComponent, PowerbankDetailComponent, PowerbankUpdateComponent, PowerbankDeleteDialogComponent],
  entryComponents: [PowerbankDeleteDialogComponent],
})
export class HcSamplePowerbankModule { }
