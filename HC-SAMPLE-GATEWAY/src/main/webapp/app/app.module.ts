import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import './vendor';
import { HcSampleSharedModule } from 'app/shared/shared.module';
import { HcSampleCoreModule } from 'app/core/core.module';
import { HcSampleAppRoutingModule } from './app-routing.module';
import { HcSampleHomeModule } from './home/home.module';
import { HcSampleEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    HcSampleSharedModule,
    HcSampleCoreModule,
    HcSampleHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HcSampleEntityModule,
    HcSampleAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class HcSampleAppModule {}
