import dayjs from 'dayjs';
import { IApprovers } from 'app/shared/model/approvers.model';

export interface IApproverGroups {
  id?: number;
  referenceId?: number | null;
  referenceType?: string;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  approversApprovergroups?: IApprovers[] | null;
}

export const defaultValue: Readonly<IApproverGroups> = {};
