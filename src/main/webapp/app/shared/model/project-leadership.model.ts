import dayjs from 'dayjs';
import { IProjectRoles } from 'app/shared/model/project-roles.model';
import { IProjects } from 'app/shared/model/projects.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IProjectLeadership {
  id?: number;
  createdat?: string | null;
  updatedat?: string | null;
  projectrole?: IProjectRoles | null;
  project?: IProjects | null;
  employee?: IEmployees | null;
}

export const defaultValue: Readonly<IProjectLeadership> = {};
