import dayjs from 'dayjs';
import { ILeaveEscalationCriterias } from 'app/shared/model/leave-escalation-criterias.model';
import { IAttributes } from 'app/shared/model/attributes.model';

export interface ILeaveEscalationApprovers {
  id?: number;
  source?: string;
  minApprovals?: number | null;
  priority?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveEscalationCriteria?: ILeaveEscalationCriterias;
  attribute?: IAttributes;
}

export const defaultValue: Readonly<ILeaveEscalationApprovers> = {};
