import dayjs from 'dayjs';
import { IGoalGroupMappings } from 'app/shared/model/goal-group-mappings.model';
import { IUserGoals } from 'app/shared/model/user-goals.model';

export interface IGoals {
  id?: number;
  title?: string;
  description?: string | null;
  measurement?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  goalgroupmappingsGoals?: IGoalGroupMappings[] | null;
  usergoalsReferencegoals?: IUserGoals[] | null;
}

export const defaultValue: Readonly<IGoals> = {};
