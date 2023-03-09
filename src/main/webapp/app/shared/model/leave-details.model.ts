import dayjs from 'dayjs';
import { ILeaves } from 'app/shared/model/leaves.model';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';
import { ILeaveDetailAdjustmentLogs } from 'app/shared/model/leave-detail-adjustment-logs.model';
import { ILeaveRequests } from 'app/shared/model/leave-requests.model';

export interface ILeaveDetails {
  id?: number;
  total?: number | null;
  used?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leave?: ILeaves;
  leaveType?: ILeaveTypes;
  leavedetailadjustmentlogsLeavedetails?: ILeaveDetailAdjustmentLogs[] | null;
  leaverequestsLeavedetails?: ILeaveRequests[] | null;
}

export const defaultValue: Readonly<ILeaveDetails> = {};
