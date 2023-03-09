import dayjs from 'dayjs';

export interface IReligions {
  id?: number;
  name?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IReligions> = {};
