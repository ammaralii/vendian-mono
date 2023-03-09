import dayjs from 'dayjs';
import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { IDealResourceStatus } from 'app/shared/model/deal-resource-status.model';

export interface IDealResourceEventLogs {
  id?: number;
  comments?: string;
  createdat?: string;
  matchingresource?: IDealRequirementMatchingResources | null;
  resourcestatus?: IDealResourceStatus | null;
  systemstatus?: IDealResourceStatus | null;
}

export const defaultValue: Readonly<IDealResourceEventLogs> = {};
