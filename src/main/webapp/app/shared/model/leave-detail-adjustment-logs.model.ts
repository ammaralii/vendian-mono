import dayjs from 'dayjs';
import { ILeaveDetails } from 'app/shared/model/leave-details.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface ILeaveDetailAdjustmentLogs {
  id?: number;
  action?: string | null;
  count?: number | null;
  createdAt?: string;
  updatedAt?: string;
  version?: number | null;
  quotaBeforeAdjustment?: number | null;
  quotaAfterAdjustment?: number | null;
  comment?: string | null;
  leaveDetail?: ILeaveDetails | null;
  adjustedBy?: IEmployees | null;
}

export const defaultValue: Readonly<ILeaveDetailAdjustmentLogs> = {};
