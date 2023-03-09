import dayjs from 'dayjs';
import { IGoalGroups } from 'app/shared/model/goal-groups.model';
import { IGoals } from 'app/shared/model/goals.model';

export interface IGoalGroupMappings {
  id?: number;
  weightage?: number;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  goalGroup?: IGoalGroups;
  goal?: IGoals;
}

export const defaultValue: Readonly<IGoalGroupMappings> = {};
