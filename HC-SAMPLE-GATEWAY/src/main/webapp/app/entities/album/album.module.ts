import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HcSampleSharedModule } from 'app/shared/shared.module';
import { AlbumComponent } from './album.component';
import { AlbumDetailComponent } from './album-detail.component';
import { AlbumUpdateComponent } from './album-update.component';
import { AlbumDeleteDialogComponent } from './album-delete-dialog.component';
import { albumRoute } from './album.route';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [HcSampleSharedModule,InputTextModule,CardModule,ButtonModule,RouterModule.forChild(albumRoute)],
  declarations: [AlbumComponent, AlbumDetailComponent, AlbumUpdateComponent, AlbumDeleteDialogComponent],
  entryComponents: [AlbumDeleteDialogComponent],
})
export class HcSampleAlbumModule {}
