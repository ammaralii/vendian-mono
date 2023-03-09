import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmploymentStatuses {
  id?: number;
  name?: string;
  createdat?: string | null;
  updatedat?: string | null;
  employeesEmploymentstatuses?: IEmployees[] | null;
}

export const defaultValue: Readonly<IEmploymentStatuses> = {};
