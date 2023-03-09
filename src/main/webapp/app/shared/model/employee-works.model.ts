import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { ICompanies } from 'app/shared/model/companies.model';

export interface IEmployeeWorks {
  id?: number;
  startdate?: string | null;
  enddate?: string | null;
  designation?: string | null;
  leavingreason?: string | null;
  createdat?: string;
  updatedat?: string;
  employee?: IEmployees | null;
  company?: ICompanies | null;
}

export const defaultValue: Readonly<IEmployeeWorks> = {};
