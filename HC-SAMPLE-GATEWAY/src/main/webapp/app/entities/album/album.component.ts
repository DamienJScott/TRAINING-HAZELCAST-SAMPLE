import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlbum } from 'app/shared/model/album.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AlbumService } from './album.service';
import { AlbumDeleteDialogComponent } from './album-delete-dialog.component';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'hc-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.scss']
})
export class AlbumComponent implements OnInit, OnDestroy {
  albums: IAlbum[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  search: any;

  searchForm = this.fb.group({
    name: [],
    desc: [],
  });

  constructor(
    protected albumService: AlbumService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    private fb: FormBuilder
  ) {
    this.albums = [];
    this.search = {};
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
      if (this.search[key]) {
        this.search[key + '.contains'] = this.search[key];
      }
      Reflect.deleteProperty(this.search, key);
    });
    this.reset();
  }

  loadAll(): void {
    this.search['page'] = this.page;
    this.search['size'] = this.itemsPerPage;
    this.search['sort'] = this.sort();
    this.albumService
      .query(this.search)
      .subscribe((res: HttpResponse<IAlbum[]>) => this.paginateAlbums(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.albums = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlbums();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlbum): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlbums(): void {
    this.eventSubscriber = this.eventManager.subscribe('albumListModification', () => this.reset());
  }

  delete(album: IAlbum): void {
    const modalRef = this.modalService.open(AlbumDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.album = album;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAlbums(data: IAlbum[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.albums.push(data[i]);
      }
    }
  }
}
