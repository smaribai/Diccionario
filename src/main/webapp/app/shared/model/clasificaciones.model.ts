import { IPais } from 'app/shared/model/pais.model';
import { IDivisa } from 'app/shared/model/divisa.model';
import { IIdioma } from 'app/shared/model/idioma.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IProvincia } from 'app/shared/model/provincia.model';
import { IRemitente } from 'app/shared/model/remitente.model';
import { IDestinatario } from 'app/shared/model/destinatario.model';

export interface IClasificaciones {
  id?: number;
  descripcion?: string;
  cliente?: string | null;
  remitente?: string | null;
  destinatario?: string | null;
  proveedor?: string | null;
  codigoArancelarioOrigen?: string | null;
  importe?: number | null;
  uds?: number | null;
  peso?: number | null;
  codigoArancelarioObtenido?: string | null;
  paisOrigen?: IPais;
  paisDestino?: IPais;
  divisa?: IDivisa | null;
  idioma?: IIdioma | null;
  refCliente?: ICliente | null;
  provinciaDestino?: IProvincia | null;
  idRemitente?: IRemitente | null;
  idDestinatario?: IDestinatario | null;
}

export const defaultValue: Readonly<IClasificaciones> = {};
