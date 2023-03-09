import dayjs from 'dayjs';

export interface IMaritalStatuses {
  id?: number;
  status?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IMaritalStatuses> = {};
