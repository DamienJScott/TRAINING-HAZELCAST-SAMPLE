import { IPowerbank } from 'app/shared/model/powerbank.model';

export interface IStation {
  id?: number;
  name?: string;
  desc?: string;
  postion?: string;
  enabled?: boolean;
  powerbanks?: IPowerbank[];
}

export class Station implements IStation {
  constructor(
    public id?: number,
    public name?: string,
    public desc?: string,
    public postion?: string,
    public enabled?: boolean,
    public powerbanks?: IPowerbank[]
  ) {
    this.enabled = this.enabled || false;
  }
}
