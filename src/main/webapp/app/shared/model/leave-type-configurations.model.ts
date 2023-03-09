import dayjs from 'dayjs';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';

export interface ILeaveTypeConfigurations {
  id?: number;
  noOfLeaves?: number;
  tenureCycle?: string;
  to?: number | null;
  from?: number | null;
  inclusivity?: string | null;
  maxQuota?: number | null;
  isAccured?: boolean | null;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveType?: ILeaveTypes;
}

export const defaultValue: Readonly<ILeaveTypeConfigurations> = {
  isAccured: false,
};
