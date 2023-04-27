import { IProducto } from 'app/shared/model/producto.model';
import { IControl } from 'app/shared/model/control.model';

export interface IProductoControles {
  id?: number;
  descripcion?: string | null;
  codigoArancelario?: IProducto;
  idControl?: IControl;
}

export const defaultValue: Readonly<IProductoControles> = {};
