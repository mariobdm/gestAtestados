import { Moment } from 'moment';
import { IDestinatario } from 'app/shared/model/destinatario.model';
import { IRemitente } from 'app/shared/model/remitente.model';
import { IDocumento } from 'app/shared/model/documento.model';
import { IImplicado } from 'app/shared/model/implicado.model';
import { EnumTipoJuicio } from 'app/shared/model/enumerations/enum-tipo-juicio.model';

export interface IAtestado {
  id?: number;
  numero?: string;
  tipojuicio?: EnumTipoJuicio;
  fechaAtestado?: Moment;
  fechaHoraSuceso?: Moment;
  lugar?: string;
  accidente?: boolean;
  permiso?: boolean;
  alcoholemia?: boolean;
  bienes?: boolean;
  velocidad?: boolean;
  lesiones?: boolean;
  fallecido?: boolean;
  instructor?: string;
  secretario?: string;
  destinatario?: IDestinatario;
  remitente?: IRemitente;
  documentos?: IDocumento[];
  implicados?: IImplicado[];
}

export class Atestado implements IAtestado {
  constructor(
    public id?: number,
    public numero?: string,
    public tipojuicio?: EnumTipoJuicio,
    public fechaAtestado?: Moment,
    public fechaHoraSuceso?: Moment,
    public lugar?: string,
    public accidente?: boolean,
    public permiso?: boolean,
    public alcoholemia?: boolean,
    public bienes?: boolean,
    public velocidad?: boolean,
    public lesiones?: boolean,
    public fallecido?: boolean,
    public instructor?: string,
    public secretario?: string,
    public destinatario?: IDestinatario,
    public remitente?: IRemitente,
    public documentos?: IDocumento[],
    public implicados?: IImplicado[]
  ) {
    this.accidente = this.accidente || false;
    this.permiso = this.permiso || false;
    this.alcoholemia = this.alcoholemia || false;
    this.bienes = this.bienes || false;
    this.velocidad = this.velocidad || false;
    this.lesiones = this.lesiones || false;
    this.fallecido = this.fallecido || false;
  }
}
