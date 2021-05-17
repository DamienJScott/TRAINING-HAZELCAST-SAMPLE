import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPowerbank } from 'app/shared/model/powerbank.model';
import { PowerbankService } from './powerbank.service';

@Component({
  templateUrl: './powerbank-delete-dialog.component.html',
})
export class PowerbankDeleteDialogComponent {
  powerbank?: IPowerbank;

  constructor(protected powerbankService: PowerbankService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.powerbankService.delete(id).subscribe(() => {
      this.eventManager.broadcast('powerbankListModification');
      this.activeModal.close();
    });
  }
}
