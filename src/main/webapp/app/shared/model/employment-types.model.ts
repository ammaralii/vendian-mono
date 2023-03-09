import dayjs from 'dayjs';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmploymentTypes {
  id?: number;
  name?: string;
  createdat?: string | null;
  updatedat?: string | null;
  employeejobinfoEmploymenttypes?: IEmployeeJobInfo[] | null;
  employeesEmploymenttypes?: IEmployees[] | null;
}

export const defaultValue: Readonly<IEmploymentTypes> = {};
