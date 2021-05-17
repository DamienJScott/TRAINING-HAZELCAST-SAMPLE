import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HcSampleSharedModule } from 'app/shared/shared.module';
import { PowerbankComponent } from './powerbank.component';
import { PowerbankDetailComponent } from './powerbank-detail.component';
import { PowerbankUpdateComponent } from './powerbank-update.component';
import { PowerbankDeleteDialogComponent } from './powerbank-delete-dialog.component';
import { powerbankRoute } from './powerbank.route';

@NgModule({
  imports: [HcSampleSharedModule, RouterModule.forChild(powerbankRoute)],
  declarations: [PowerbankComponent, PowerbankDetailComponent, PowerbankUpdateComponent, PowerbankDeleteDialogComponent],
  entryComponents: [PowerbankDeleteDialogComponent],
})
export class HcSamplePowerbankModule {}
