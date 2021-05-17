import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStation } from 'app/shared/model/station.model';
import { StationService } from './station.service';

@Component({
  templateUrl: './station-delete-dialog.component.html',
})
export class StationDeleteDialogComponent {
  station?: IStation;

  constructor(protected stationService: StationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stationListModification');
      this.activeModal.close();
    });
  }
}
