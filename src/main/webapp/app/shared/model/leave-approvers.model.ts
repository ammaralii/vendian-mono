import dayjs from 'dayjs';
import { ILeaveApprovalCriterias } from 'app/shared/model/leave-approval-criterias.model';
import { IAttributes } from 'app/shared/model/attributes.model';

export interface ILeaveApprovers {
  id?: number;
  source?: string;
  minApprovals?: number | null;
  priority?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveApprovalCriteria?: ILeaveApprovalCriterias;
  attribute?: IAttributes;
}

export const defaultValue: Readonly<ILeaveApprovers> = {};
