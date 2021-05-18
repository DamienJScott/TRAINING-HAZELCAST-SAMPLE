import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HcSampleSharedModule } from 'app/shared/shared.module';
import { StationComponent } from './station.component';
import { StationDetailComponent } from './station-detail.component';
import { StationUpdateComponent } from './station-update.component';
import { StationDeleteDialogComponent } from './station-delete-dialog.component';
import { stationRoute } from './station.route';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  imports: [HcSampleSharedModule,CardModule, InputTextModule, ButtonModule, DropdownModule, RouterModule.forChild(stationRoute)],
  declarations: [StationComponent, StationDetailComponent, StationUpdateComponent, StationDeleteDialogComponent],
  entryComponents: [StationDeleteDialogComponent],
})
export class HcSampleStationModule {}
