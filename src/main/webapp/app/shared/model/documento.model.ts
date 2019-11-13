import { IAtestado } from 'app/shared/model/atestado.model';

export interface IDocumento {
  id?: number;
  nombreDoc?: string;
  descDoc?: string;
  archivoContentType?: string;
  archivo?: any;
  archivoPdfContentType?: string;
  archivoPdf?: any;
  atestado?: IAtestado;
}

export class Documento implements IDocumento {
  constructor(
    public id?: number,
    public nombreDoc?: string,
    public descDoc?: string,
    public archivoContentType?: string,
    public archivo?: any,
    public archivoPdfContentType?: string,
    public archivoPdf?: any,
    public atestado?: IAtestado
  ) {}
}
