import dayjs from 'dayjs';
import { IDeals } from 'app/shared/model/deals.model';
import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';

export interface IDealRequirements {
  id?: number;
  dealreqidentifier?: string;
  competencyname?: string;
  skills?: string | null;
  resourcerequired?: number;
  minexperiencelevel?: string | null;
  maxexperiencelevel?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  deletedat?: string | null;
  deal?: IDeals | null;
  dealrequirementmatchingresourcesDealrequirements?: IDealRequirementMatchingResources[] | null;
}

export const defaultValue: Readonly<IDealRequirements> = {};
