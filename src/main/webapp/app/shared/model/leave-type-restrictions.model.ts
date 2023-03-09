import dayjs from 'dayjs';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';

export interface ILeaveTypeRestrictions {
  id?: number;
  restrictionJson?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveType?: ILeaveTypes;
}

export const defaultValue: Readonly<ILeaveTypeRestrictions> = {};
