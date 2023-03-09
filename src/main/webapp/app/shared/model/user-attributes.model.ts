import dayjs from 'dayjs';
import { IAttributes } from 'app/shared/model/attributes.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IUserAttributes {
  id?: number;
  createdAt?: string;
  updatedAt?: string;
  version?: number;
  endDate?: string | null;
  effDate?: string | null;
  attribute?: IAttributes;
  user?: IEmployees;
}

export const defaultValue: Readonly<IUserAttributes> = {};
