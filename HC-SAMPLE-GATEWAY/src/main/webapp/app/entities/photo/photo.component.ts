import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhoto } from 'app/shared/model/photo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PhotoService } from './photo.service';
import { PhotoDeleteDialogComponent } from './photo-delete-dialog.component';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'hc-photo',
  templateUrl: './photo.component.html',
  styleUrls:['./photo.component.scss']
})
export class PhotoComponent implements OnInit, OnDestroy {
  photos: IPhoto[];
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
    protected photoService: PhotoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected fb: FormBuilder
  ) {
    this.photos = [];
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
    this.photoService
      .query(this.search)
      .subscribe((res: HttpResponse<IPhoto[]>) => this.paginatePhotos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.photos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhotos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhoto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPhotos(): void {
    this.eventSubscriber = this.eventManager.subscribe('photoListModification', () => this.reset());
  }

  delete(photo: IPhoto): void {
    const modalRef = this.modalService.open(PhotoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.photo = photo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePhotos(data: IPhoto[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.photos.push(data[i]);
      }
    }
  }
}
