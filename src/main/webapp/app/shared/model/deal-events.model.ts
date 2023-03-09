import dayjs from 'dayjs';

export interface IDealEvents {
  id?: number;
  eventtype?: string;
  createdat?: string;
}

export const defaultValue: Readonly<IDealEvents> = {};
