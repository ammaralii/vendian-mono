import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeContacts {
  id?: number;
  numberContentType?: string;
  number?: string;
  type?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeContacts> = {};
