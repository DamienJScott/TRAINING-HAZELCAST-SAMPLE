import { IPhoto } from 'app/shared/model/photo.model';

export interface IAlbum {
  id?: number;
  name?: string;
  desc?: string;
  detail?: string;
  photos?: IPhoto[];
}

export class Album implements IAlbum {
  constructor(public id?: number, public name?: string, public desc?: string, public detail?: string, public photos?: IPhoto[]) {}
}
