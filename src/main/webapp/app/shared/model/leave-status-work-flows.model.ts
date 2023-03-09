import dayjs from 'dayjs';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';

export interface ILeaveStatusWorkFlows {
  id?: number;
  ifApprovals?: boolean;
  approvalRequired?: boolean;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  currentStatus?: ILeaveStatuses;
  nextStatus?: ILeaveStatuses;
  skipToStatus?: ILeaveStatuses | null;
}

export const defaultValue: Readonly<ILeaveStatusWorkFlows> = {
  ifApprovals: false,
  approvalRequired: false,
};
