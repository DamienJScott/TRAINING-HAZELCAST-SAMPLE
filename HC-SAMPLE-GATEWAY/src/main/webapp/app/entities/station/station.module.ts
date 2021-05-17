import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HcSampleSharedModule } from 'app/shared/shared.module';
import { StationComponent } from './station.component';
import { StationDetailComponent } from './station-detail.component';
import { StationUpdateComponent } from './station-update.component';
import { StationDeleteDialogComponent } from './station-delete-dialog.component';
import { stationRoute } from './station.route';

@NgModule({
  imports: [HcSampleSharedModule, RouterModule.forChild(stationRoute)],
  declarations: [StationComponent, StationDetailComponent, StationUpdateComponent, StationDeleteDialogComponent],
  entryComponents: [StationDeleteDialogComponent],
})
export class HcSampleStationModule {}
