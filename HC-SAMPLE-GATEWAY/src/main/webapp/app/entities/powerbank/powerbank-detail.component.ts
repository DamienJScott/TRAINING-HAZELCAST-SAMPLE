import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPowerbank } from 'app/shared/model/powerbank.model';

@Component({
  selector: 'hc-powerbank-detail',
  templateUrl: './powerbank-detail.component.html',
})
export class PowerbankDetailComponent implements OnInit {
  powerbank: IPowerbank | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ powerbank }) => (this.powerbank = powerbank));
  }

  previousState(): void {
    window.history.back();
  }
}
