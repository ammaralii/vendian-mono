import dayjs from 'dayjs';
import { ILeaveRequests } from 'app/shared/model/leave-requests.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';

export interface ILeaveRequestApprovers {
  id?: number;
  reference?: string;
  as?: string;
  comments?: string | null;
  approverGroup?: string;
  priority?: number;
  statusDate?: string;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  leaveRequest?: ILeaveRequests;
  user?: IEmployees | null;
  status?: ILeaveStatuses;
}

export const defaultValue: Readonly<ILeaveRequestApprovers> = {};
