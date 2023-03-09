import dayjs from 'dayjs';
import { IEmployeeWorks } from 'app/shared/model/employee-works.model';

export interface ICompanies {
  id?: number;
  name?: string | null;
  createdat?: string;
  updatedat?: string;
  employeeworksCompanies?: IEmployeeWorks[] | null;
}

export const defaultValue: Readonly<ICompanies> = {};
