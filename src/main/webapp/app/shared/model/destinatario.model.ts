export interface IDestinatario {
  id?: number;
  idDestinatario?: string;
  nombre?: string;
  descripcion?: string | null;
  direccion?: string | null;
}

export const defaultValue: Readonly<IDestinatario> = {};
