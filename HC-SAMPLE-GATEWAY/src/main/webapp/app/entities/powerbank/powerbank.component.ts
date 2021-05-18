import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPowerbank } from 'app/shared/model/powerbank.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PowerbankService } from './powerbank.service';
import { PowerbankDeleteDialogComponent } from './powerbank-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { ENABLED } from '../station/station.data';

@Component({
  selector: 'hc-powerbank',
  templateUrl: './powerbank.component.html',
  styleUrls: ['./powerbank.component.scss']
})
export class PowerbankComponent implements OnInit, OnDestroy {
  powerbanks: IPowerbank[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  search: any;
  enableds: any[];

  searchForm = this.fb.group({
    name: [],
    desc: [],
    position: [],
    enabled: [],
  });

  constructor(
    protected powerbankService: PowerbankService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected fb: FormBuilder
  ) {
    this.powerbanks = [];
    this.search = {};
    this.enableds = ENABLED;
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.search['page'] = this.page;
    this.search['size'] = this.itemsPerPage;
    this.search['sort'] = this.sort();
    this.powerbankService
      .query(this.search)
      .subscribe((res: HttpResponse<IPowerbank[]>) => this.paginatePowerbanks(res.body, res.headers));
  }

  resetCondition(): void {
    this.searchForm.reset();
    this.search = {};
    this.reset();
  }

  searching(): void {
    this.search = Object.assign({}, this.searchForm.value);
    Object.keys(this.search).forEach(key => {
      if (this.search[key] || this.search[key] === false || this.search[key] === 0) {
        if (key === 'enabled') {
          this.search[key + '.equals'] = this.search[key];
        } else {
          this.search[key + '.contains'] = this.search[key];
        }
      }
      Reflect.deleteProperty(this.search, key);
    });
    this.reset();
  }

  reset(): void {
    this.page = 0;
    this.powerbanks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPowerbanks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPowerbank): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPowerbanks(): void {
    this.eventSubscriber = this.eventManager.subscribe('powerbankListModification', () => this.reset());
  }

  delete(powerbank: IPowerbank): void {
    const modalRef = this.modalService.open(PowerbankDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.powerbank = powerbank;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePowerbanks(data: IPowerbank[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.powerbanks.push(data[i]);
      }
    }
  }
}
