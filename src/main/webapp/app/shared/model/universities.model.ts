import dayjs from 'dayjs';

export interface IUniversities {
  id?: number;
  name?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IUniversities> = {};
