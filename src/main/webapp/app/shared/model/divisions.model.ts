import dayjs from 'dayjs';
import { IDepartments } from 'app/shared/model/departments.model';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IDivisions {
  id?: number;
  name?: string;
  createdat?: string;
  updatedat?: string;
  departmentsDivisions?: IDepartments[] | null;
  employeejobinfoDivisions?: IEmployeeJobInfo[] | null;
  employeesDivisions?: IEmployees[] | null;
}

export const defaultValue: Readonly<IDivisions> = {};
