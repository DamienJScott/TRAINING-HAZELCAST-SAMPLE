import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStation } from 'app/shared/model/station.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { StationService } from './station.service';
import { StationDeleteDialogComponent } from './station-delete-dialog.component';

@Component({
  selector: 'hc-station',
  templateUrl: './station.component.html',
})
export class StationComponent implements OnInit, OnDestroy {
  stations: IStation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected stationService: StationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.stations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.stationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
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
