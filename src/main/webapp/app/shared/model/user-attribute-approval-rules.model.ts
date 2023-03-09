import dayjs from 'dayjs';
import { IAttributes } from 'app/shared/model/attributes.model';
import { ILeaveApprovalCriterias } from 'app/shared/model/leave-approval-criterias.model';

export interface IUserAttributeApprovalRules {
  id?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  attribute?: IAttributes;
  leaveApprovalCriteria?: ILeaveApprovalCriterias;
}

export const defaultValue: Readonly<IUserAttributeApprovalRules> = {};
