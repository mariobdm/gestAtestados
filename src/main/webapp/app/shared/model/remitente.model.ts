import { IAtestado } from 'app/shared/model/atestado.model';
import { EnumTipoRemitente } from 'app/shared/model/enumerations/enum-tipo-remitente.model';

export interface IRemitente {
  id?: number;
  tipo?: EnumTipoRemitente;
  nombreRemitente?: string;
  descRemitente?: string;
  atestado?: IAtestado;
}

export class Remitente implements IRemitente {
  constructor(
    public id?: number,
    public tipo?: EnumTipoRemitente,
    public nombreRemitente?: string,
    public descRemitente?: string,
    public atestado?: IAtestado
  ) {}
}
