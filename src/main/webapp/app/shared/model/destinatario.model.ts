import { IAtestado } from 'app/shared/model/atestado.model';
import { EnumTipoDestinatario } from 'app/shared/model/enumerations/enum-tipo-destinatario.model';

export interface IDestinatario {
  id?: number;
  tipo?: EnumTipoDestinatario;
  nombreDestinatario?: string;
  descDestinatario?: string;
  atestado?: IAtestado;
}

export class Destinatario implements IDestinatario {
  constructor(
    public id?: number,
    public tipo?: EnumTipoDestinatario,
    public nombreDestinatario?: string,
    public descDestinatario?: string,
    public atestado?: IAtestado
  ) {}
}
