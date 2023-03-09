import dayjs from 'dayjs';

export interface IBloodGroups {
  id?: number;
  name?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IBloodGroups> = {};
