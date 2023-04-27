export interface IRemitente {
  id?: number;
  idRemitente?: string;
  nombre?: string;
  descripcion?: string | null;
  direccion?: string | null;
}

export const defaultValue: Readonly<IRemitente> = {};
