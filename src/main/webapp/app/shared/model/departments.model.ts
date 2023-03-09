import dayjs from 'dayjs';
import { IDivisions } from 'app/shared/model/divisions.model';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IDepartments {
  id?: number;
  name?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  division?: IDivisions | null;
  employeejobinfoDepartments?: IEmployeeJobInfo[] | null;
  employeesDepartments?: IEmployees[] | null;
}

export const defaultValue: Readonly<IDepartments> = {};
