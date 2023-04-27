import { IIdioma } from 'app/shared/model/idioma.model';

export interface ITraduccion {
  id?: number;
  textoOrigen?: string;
  textoDestino?: string;
  idiomaOrigen?: IIdioma;
  idiomaDestino?: IIdioma;
}

export const defaultValue: Readonly<ITraduccion> = {};
