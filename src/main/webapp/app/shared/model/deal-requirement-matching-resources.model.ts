import dayjs from 'dayjs';
import { IDealRequirements } from 'app/shared/model/deal-requirements.model';
import { IDealResources } from 'app/shared/model/deal-resources.model';
import { IDealResourceStatus } from 'app/shared/model/deal-resource-status.model';
import { IDealResourceEventLogs } from 'app/shared/model/deal-resource-event-logs.model';

export interface IDealRequirementMatchingResources {
  id?: number;
  comments?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  deletedat?: string | null;
  dealrequirement?: IDealRequirements | null;
  resource?: IDealResources | null;
  resourcestatus?: IDealResourceStatus | null;
  systemstatus?: IDealResourceStatus | null;
  dealresourceeventlogsMatchingresources?: IDealResourceEventLogs[] | null;
}

export const defaultValue: Readonly<IDealRequirementMatchingResources> = {};
