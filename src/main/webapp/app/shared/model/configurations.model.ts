import dayjs from 'dayjs';

export interface IConfigurations {
  id?: number;
  name?: string;
  group?: string;
  intValue?: number | null;
  stringValue?: string | null;
  doubleValue?: number | null;
  dateValue?: string | null;
  jsonValue?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
}

export const defaultValue: Readonly<IConfigurations> = {};
