import dayjs from 'dayjs';
import { ILeaveDetails } from 'app/shared/model/leave-details.model';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { ILeaveRequestApprovers } from 'app/shared/model/leave-request-approvers.model';

export interface ILeaveRequests {
  id?: number;
  createdAt?: string;
  requestStartDate?: string;
  requestEndDate?: string;
  isHalfDay?: boolean | null;
  statusDate?: string;
  comments?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  leaveDetail?: ILeaveDetails;
  leaveStatus?: ILeaveStatuses;
  parentLeaveRequest?: ILeaveRequests | null;
  leaverequestapproversLeaverequests?: ILeaveRequestApprovers[] | null;
  leaverequestsParentleaverequests?: ILeaveRequests[] | null;
}

export const defaultValue: Readonly<ILeaveRequests> = {
  isHalfDay: false,
};
