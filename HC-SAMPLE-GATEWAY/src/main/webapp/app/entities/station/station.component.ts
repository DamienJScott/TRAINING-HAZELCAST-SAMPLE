import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStation } from 'app/shared/model/station.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { StationService } from './station.service';
import { StationDeleteDialogComponent } from './station-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { ENABLED } from './station.data';

@Component({
  selector: 'hc-station',
  templateUrl: './station.component.html',
  styleUrls: ['./station.component.scss']
})
export class StationComponent implements OnInit, OnDestroy {
  stations: IStation[];
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
    postion: [],
    enabled: [],
  });

  constructor(
    protected stationService: StationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected fb: FormBuilder
  ) {
    this.stations = [];
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

  loadAll(): void {
    this.search['page'] = this.page;
    this.search['size'] = this.itemsPerPage;
    this.search['sort'] = this.sort();
    this.stationService
      .query(this.search)
      .subscribe((res: HttpResponse<IStation[]>) => this.paginateStations(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.stations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStations(): void {
    this.eventSubscriber = this.eventManager.subscribe('stationListModification', () => this.reset());
  }

  delete(station: IStation): void {
    const modalRef = this.modalService.open(StationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.station = station;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateStations(data: IStation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.stations.push(data[i]);
      }
    }
  }
}
