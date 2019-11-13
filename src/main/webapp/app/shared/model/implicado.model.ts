import { Moment } from 'moment';
import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { IAtestado } from 'app/shared/model/atestado.model';
import { EnumTipoDocumentacion } from 'app/shared/model/enumerations/enum-tipo-documentacion.model';
import { EnumTipoImplicado } from 'app/shared/model/enumerations/enum-tipo-implicado.model';

export interface IImplicado {
  id?: number;
  tipoDocumento?: EnumTipoDocumentacion;
  documento?: string;
  nombre?: string;
  apellido1?: string;
  apellido2?: string;
  fechaNacimiento?: Moment;
  telefono?: string;
  calidad?: EnumTipoImplicado;
  direccion?: string;
  municipio?: string;
  codigopostal?: number;
  tasaAlcohol?: ITasaAlcohol;
  atestado?: IAtestado;
}

export class Implicado implements IImplicado {
  constructor(
    public id?: number,
    public tipoDocumento?: EnumTipoDocumentacion,
    public documento?: string,
    public nombre?: string,
    public apellido1?: string,
    public apellido2?: string,
    public fechaNacimiento?: Moment,
    public telefono?: string,
    public calidad?: EnumTipoImplicado,
    public direccion?: string,
    public municipio?: string,
    public codigopostal?: number,
    public tasaAlcohol?: ITasaAlcohol,
    public atestado?: IAtestado
  ) {}
}
