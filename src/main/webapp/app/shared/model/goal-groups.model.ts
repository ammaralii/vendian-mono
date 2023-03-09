import dayjs from 'dayjs';
import { IGoalGroupMappings } from 'app/shared/model/goal-group-mappings.model';

export interface IGoalGroups {
  id?: number;
  title?: string;
  description?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  goalgroupmappingsGoalgroups?: IGoalGroupMappings[] | null;
}

export const defaultValue: Readonly<IGoalGroups> = {};
