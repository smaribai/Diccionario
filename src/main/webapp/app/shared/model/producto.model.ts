export interface IProducto {
  id?: number;
  codigoArancelario?: string;
  nivel?: number | null;
  cNCode?: string | null;
  longitudCNCode?: number | null;
  descripcion?: string;
  parent?: IProducto | null;
}

export const defaultValue: Readonly<IProducto> = {};
