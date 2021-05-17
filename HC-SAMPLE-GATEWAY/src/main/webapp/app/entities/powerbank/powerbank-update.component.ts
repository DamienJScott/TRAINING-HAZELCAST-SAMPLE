import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPowerbank, Powerbank } from 'app/shared/model/powerbank.model';
import { PowerbankService } from './powerbank.service';
import { IStation } from 'app/shared/model/station.model';
import { StationService } from 'app/entities/station/station.service';

@Component({
  selector: 'hc-powerbank-update',
  templateUrl: './powerbank-update.component.html',
})
export class PowerbankUpdateComponent implements OnInit {
  isSaving = false;
  stations: IStation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desc: [],
    type: [null, [Validators.required]],
    position: [null, [Validators.required]],
    isUsing: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    stationId: [],
  });

  constructor(
    protected powerbankService: PowerbankService,
    protected stationService: StationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ powerbank }) => {
      this.updateForm(powerbank);

      this.stationService.query().subscribe((res: HttpResponse<IStation[]>) => (this.stations = res.body || []));
    });
  }

  updateForm(powerbank: IPowerbank): void {
    this.editForm.patchValue({
      id: powerbank.id,
      name: powerbank.name,
      desc: powerbank.desc,
      type: powerbank.type,
      position: powerbank.position,
      isUsing: powerbank.isUsing,
      enabled: powerbank.enabled,
      stationId: powerbank.stationId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const powerbank = this.createFromForm();
    if (powerbank.id !== undefined) {
      this.subscribeToSaveResponse(this.powerbankService.update(powerbank));
    } else {
      this.subscribeToSaveResponse(this.powerbankService.create(powerbank));
    }
  }

  private createFromForm(): IPowerbank {
    return {
      ...new Powerbank(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      type: this.editForm.get(['type'])!.value,
      position: this.editForm.get(['position'])!.value,
      isUsing: this.editForm.get(['isUsing'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      stationId: this.editForm.get(['stationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPowerbank>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStation): any {
    return item.id;
  }
}
