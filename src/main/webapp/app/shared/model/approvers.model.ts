import dayjs from 'dayjs';
import { IApproverGroups } from 'app/shared/model/approver-groups.model';

export interface IApprovers {
  id?: number;
  userId?: string | null;
  reference?: string;
  as?: string;
  comment?: string | null;
  status?: string;
  stausDate?: string;
  priority?: number;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  approverGroup?: IApproverGroups;
}

export const defaultValue: Readonly<IApprovers> = {};
