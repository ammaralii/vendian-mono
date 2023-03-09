import dayjs from 'dayjs';
import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { IDealResourceEventLogs } from 'app/shared/model/deal-resource-event-logs.model';

export interface IDealResourceStatus {
  id?: number;
  displayname?: string;
  group?: string;
  systemKey?: string;
  effectivedate?: string | null;
  enddate?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  dealrequirementmatchingresourcesResourcestatuses?: IDealRequirementMatchingResources[] | null;
  dealrequirementmatchingresourcesSystemstatuses?: IDealRequirementMatchingResources[] | null;
  dealresourceeventlogsResourcestatuses?: IDealResourceEventLogs[] | null;
  dealresourceeventlogsSystemstatuses?: IDealResourceEventLogs[] | null;
}

export const defaultValue: Readonly<IDealResourceStatus> = {};
