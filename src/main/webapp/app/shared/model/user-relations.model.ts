import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IAttributes } from 'app/shared/model/attributes.model';

export interface IUserRelations {
  id?: number;
  referenceType?: string | null;
  referenceId?: number | null;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  user?: IEmployees;
  attribute?: IAttributes;
  relatedUser?: IEmployees | null;
}

export const defaultValue: Readonly<IUserRelations> = {};
