import { IImplicado } from 'app/shared/model/implicado.model';

export interface ITasaAlcohol {
  id?: number;
  tasaNoEvidencial?: number;
  tasaEvidencial1?: number;
  tasaEvidencial2?: number;
  tasaEnSangre?: number;
  implicado?: IImplicado;
}

export class TasaAlcohol implements ITasaAlcohol {
  constructor(
    public id?: number,
    public tasaNoEvidencial?: number,
    public tasaEvidencial1?: number,
    public tasaEvidencial2?: number,
    public tasaEnSangre?: number,
    public implicado?: IImplicado
  ) {}
}
