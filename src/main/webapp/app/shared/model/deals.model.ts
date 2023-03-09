import dayjs from 'dayjs';
import { IDealRequirements } from 'app/shared/model/deal-requirements.model';

export interface IDeals {
  id?: number;
  dealnumber?: string;
  dealname?: string;
  businessunit?: string | null;
  clientname?: string;
  dealowner?: string | null;
  proposaltype?: string | null;
  projectid?: number | null;
  expectedstartdate?: string;
  stage?: string | null;
  probability?: number;
  projectduration?: number;
  type?: string;
  status?: string;
  closedat?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  deletedat?: string | null;
  resourcingenteredinvendians?: boolean | null;
  dealrequirementsDeals?: IDealRequirements[] | null;
}

export const defaultValue: Readonly<IDeals> = {
  resourcingenteredinvendians: false,
};
