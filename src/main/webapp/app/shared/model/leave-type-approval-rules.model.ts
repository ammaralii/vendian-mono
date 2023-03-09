import dayjs from 'dayjs';
import { ILeaveApprovalCriterias } from 'app/shared/model/leave-approval-criterias.model';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';

export interface ILeaveTypeApprovalRules {
  id?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  leaveApprovalCriteria?: ILeaveApprovalCriterias;
  leaveType?: ILeaveTypes;
}

export const defaultValue: Readonly<ILeaveTypeApprovalRules> = {};
