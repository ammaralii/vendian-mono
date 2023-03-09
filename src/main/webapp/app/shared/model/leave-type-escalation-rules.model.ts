import dayjs from 'dayjs';
import { ILeaveEscalationCriterias } from 'app/shared/model/leave-escalation-criterias.model';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';

export interface ILeaveTypeEscalationRules {
  id?: number;
  noOfDays?: number | null;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveEscalationCriteria?: ILeaveEscalationCriterias;
  leaveRequestStatus?: ILeaveStatuses;
  leaveType?: ILeaveTypes;
}

export const defaultValue: Readonly<ILeaveTypeEscalationRules> = {};
