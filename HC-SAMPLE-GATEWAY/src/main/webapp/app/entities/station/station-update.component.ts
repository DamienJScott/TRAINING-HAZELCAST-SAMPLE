import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStation, Station } from 'app/shared/model/station.model';
import { StationService } from './station.service';

@Component({
  selector: 'hc-station-update',
  templateUrl: './station-update.component.html',
})
export class StationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desc: [],
    postion: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
  });

  constructor(protected stationService: StationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ station }) => {
      this.updateForm(station);
    });
  }

  updateForm(station: IStation): void {
    this.editForm.patchValue({
      id: station.id,
      name: station.name,
      desc: station.desc,
      postion: station.postion,
      enabled: station.enabled,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const station = this.createFromForm();
    if (station.id !== undefined) {
      this.subscribeToSaveResponse(this.stationService.update(station));
    } else {
      this.subscribeToSaveResponse(this.stationService.create(station));
    }
  }

  private createFromForm(): IStation {
    return {
      ...new Station(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      postion: this.editForm.get(['postion'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStation>>): void {
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
}
