import dayjs from 'dayjs';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';

export interface ILeaveRequestApproverFlows {
  id?: number;
  approvals?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  approverStatus?: ILeaveStatuses;
  currentLeaveRequestStatus?: ILeaveStatuses;
  nextLeaveRequestStatus?: ILeaveStatuses;
}

export const defaultValue: Readonly<ILeaveRequestApproverFlows> = {};
