import dayjs from 'dayjs';
import { IAttributes } from 'app/shared/model/attributes.model';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { ILeaveEscalationCriterias } from 'app/shared/model/leave-escalation-criterias.model';

export interface IUserAttributeEscalationRules {
  id?: number;
  leaveEscalationCriteriaId?: number;
  noOfDays?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  attribute?: IAttributes;
  approverStatus?: ILeaveStatuses;
  leaveescalationcriteria?: ILeaveEscalationCriterias | null;
}

export const defaultValue: Readonly<IUserAttributeEscalationRules> = {};
