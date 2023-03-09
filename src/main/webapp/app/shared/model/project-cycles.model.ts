import dayjs from 'dayjs';
import { IProjects } from 'app/shared/model/projects.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IRatings } from 'app/shared/model/ratings.model';
import { IEmployeeProjectRatings } from 'app/shared/model/employee-project-ratings.model';
import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';

export interface IProjectCycles {
  id?: number;
  isactive?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  allowedafterduedateat?: string | null;
  duedate?: string | null;
  auditlogs?: string | null;
  deletedat?: string | null;
  project?: IProjects | null;
  allowedafterduedateby?: IEmployees | null;
  architect?: IEmployees | null;
  projectmanager?: IEmployees | null;
  ratings?: IRatings[] | null;
  employeeprojectratingsProjectcycles?: IEmployeeProjectRatings[] | null;
  performancecycles?: IPerformanceCycles[] | null;
}

export const defaultValue: Readonly<IProjectCycles> = {
  isactive: false,
};
