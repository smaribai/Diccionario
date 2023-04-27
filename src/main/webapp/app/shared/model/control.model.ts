export interface IControl {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IControl> = {};
