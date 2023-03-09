import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IGoals } from 'app/shared/model/goals.model';
import { IUserGoalRaterAttributes } from 'app/shared/model/user-goal-rater-attributes.model';

export interface IUserGoals {
  id?: number;
  title?: string;
  description?: string | null;
  measurement?: string | null;
  weightage?: number | null;
  progress?: number | null;
  status?: string | null;
  dueDate?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  user?: IEmployees | null;
  referenceGoal?: IGoals | null;
  usergoalraterattributesUsergoals?: IUserGoalRaterAttributes[] | null;
}

export const defaultValue: Readonly<IUserGoals> = {};
