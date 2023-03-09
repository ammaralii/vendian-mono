import dayjs from 'dayjs';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IProjects } from 'app/shared/model/projects.model';

export interface IBusinessUnits {
  id?: number;
  name?: string;
  createdat?: string;
  updatedat?: string;
  employeejobinfoBusinessunits?: IEmployeeJobInfo[] | null;
  employeesBusinessunits?: IEmployees[] | null;
  projectsBusinessunits?: IProjects[] | null;
}

export const defaultValue: Readonly<IBusinessUnits> = {};
