import dayjs from 'dayjs';

export interface IConfigs {
  id?: number;
  name?: string | null;
  group?: string | null;
  intvalue?: number | null;
  stringvalue?: string | null;
  decimalvalue?: number | null;
  jsonvalue?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IConfigs> = {};
