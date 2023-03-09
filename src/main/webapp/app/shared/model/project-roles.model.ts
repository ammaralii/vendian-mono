import dayjs from 'dayjs';
import { IEmployeeProjectRoles } from 'app/shared/model/employee-project-roles.model';
import { IProjectLeadership } from 'app/shared/model/project-leadership.model';

export interface IProjectRoles {
  id?: number;
  role?: string | null;
  createdat?: string;
  updatedat?: string;
  isleadership?: boolean | null;
  employeeprojectrolesProjectroles?: IEmployeeProjectRoles[] | null;
  projectleadershipProjectroles?: IProjectLeadership[] | null;
}

export const defaultValue: Readonly<IProjectRoles> = {
  isleadership: false,
};
