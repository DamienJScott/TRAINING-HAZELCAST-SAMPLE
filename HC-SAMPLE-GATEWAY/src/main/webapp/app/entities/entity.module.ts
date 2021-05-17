import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'album',
        loadChildren: () => import('./album/album.module').then(m => m.HcSampleAlbumModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.HcSamplePhotoModule),
      },
      {
        path: 'powerbank',
        loadChildren: () => import('./powerbank/powerbank.module').then(m => m.HcSamplePowerbankModule),
      },
      {
        path: 'station',
        loadChildren: () => import('./station/station.module').then(m => m.HcSampleStationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HcSampleEntityModule {}
