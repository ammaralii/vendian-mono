import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IProjects } from 'app/shared/model/projects.model';
import { IEmployeeProjectRoles } from 'app/shared/model/employee-project-roles.model';
import { IZEmployeeProjects } from 'app/shared/model/z-employee-projects.model';

export interface IEmployeeProjects {
  id?: number;
  status?: boolean | null;
  type?: string | null;
  startdate?: string | null;
  enddate?: string | null;
  allocation?: boolean | null;
  billed?: string | null;
  createdat?: string;
  updatedat?: string;
  roleid?: number | null;
  notes?: string | null;
  extendedenddate?: string | null;
  probability?: string | null;
  employee?: IEmployees | null;
  project?: IProjects | null;
  assignor?: IEmployees | null;
  employeeprojectrolesEmployeeprojects?: IEmployeeProjectRoles[] | null;
  zemployeeprojectsEmployeeprojects?: IZEmployeeProjects[] | null;
}

export const defaultValue: Readonly<IEmployeeProjects> = {
  status: false,
  allocation: false,
};
