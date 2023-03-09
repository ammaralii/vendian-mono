import dayjs from 'dayjs';
import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';
import { IProjectRoles } from 'app/shared/model/project-roles.model';

export interface IEmployeeProjectRoles {
  id?: number;
  status?: boolean | null;
  createdat?: string;
  updatedat?: string;
  employeeproject?: IEmployeeProjects | null;
  projectrole?: IProjectRoles | null;
}

export const defaultValue: Readonly<IEmployeeProjectRoles> = {
  status: false,
};
