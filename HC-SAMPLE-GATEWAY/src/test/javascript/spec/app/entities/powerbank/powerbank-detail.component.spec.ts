import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HcSampleTestModule } from '../../../test.module';
import { PowerbankDetailComponent } from 'app/entities/powerbank/powerbank-detail.component';
import { Powerbank } from 'app/shared/model/powerbank.model';

describe('Component Tests', () => {
  describe('Powerbank Management Detail Component', () => {
    let comp: PowerbankDetailComponent;
    let fixture: ComponentFixture<PowerbankDetailComponent>;
    const route = ({ data: of({ powerbank: new Powerbank(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HcSampleTestModule],
        declarations: [PowerbankDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PowerbankDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PowerbankDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load powerbank on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.powerbank).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
