export interface IPowerbank {
  id?: number;
  name?: string;
  desc?: string;
  type?: string;
  position?: string;
  isUsing?: boolean;
  enabled?: boolean;
  stationId?: number;
}

export class Powerbank implements IPowerbank {
  constructor(
    public id?: number,
    public name?: string,
    public desc?: string,
    public type?: string,
    public position?: string,
    public isUsing?: boolean,
    public enabled?: boolean,
    public stationId?: number
  ) {
    this.isUsing = this.isUsing || false;
    this.enabled = this.enabled || false;
  }
}
