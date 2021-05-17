import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HcSampleTestModule } from '../../../test.module';
import { PowerbankUpdateComponent } from 'app/entities/powerbank/powerbank-update.component';
import { PowerbankService } from 'app/entities/powerbank/powerbank.service';
import { Powerbank } from 'app/shared/model/powerbank.model';

describe('Component Tests', () => {
  describe('Powerbank Management Update Component', () => {
    let comp: PowerbankUpdateComponent;
    let fixture: ComponentFixture<PowerbankUpdateComponent>;
    let service: PowerbankService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HcSampleTestModule],
        declarations: [PowerbankUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PowerbankUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PowerbankUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PowerbankService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Powerbank(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Powerbank();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
