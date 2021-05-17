export interface IPhoto {
  id?: number;
  name?: string;
  desc?: string;
  src?: string;
  isPrivate?: boolean;
  albumId?: number;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public name?: string,
    public desc?: string,
    public src?: string,
    public isPrivate?: boolean,
    public albumId?: number
  ) {
    this.isPrivate = this.isPrivate || false;
  }
}
