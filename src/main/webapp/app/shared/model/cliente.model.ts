import { ITipoCliente } from 'app/shared/model/tipo-cliente.model';
import { IPais } from 'app/shared/model/pais.model';

export interface ICliente {
  id?: number;
  idCliente?: string | null;
  nombre?: string;
  descripcion?: string | null;
  direccion?: string | null;
  tipoCliente?: ITipoCliente;
  pais?: IPais;
}

export const defaultValue: Readonly<ICliente> = {};
