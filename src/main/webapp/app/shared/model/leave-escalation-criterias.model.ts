import dayjs from 'dayjs';
import { ILeaveEscalationApprovers } from 'app/shared/model/leave-escalation-approvers.model';
import { ILeaveTypeEscalationRules } from 'app/shared/model/leave-type-escalation-rules.model';
import { IUserAttributeEscalationRules } from 'app/shared/model/user-attribute-escalation-rules.model';

export interface ILeaveEscalationCriterias {
  id?: number;
  name?: string;
  priority?: number;
  total?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveescalationapproversLeaveescalationcriteria?: ILeaveEscalationApprovers[] | null;
  leavetypeescalationrulesLeaveescalationcriteria?: ILeaveTypeEscalationRules[] | null;
  userattributeescalationrulesLeaveescalationcriteria?: IUserAttributeEscalationRules[] | null;
}

export const defaultValue: Readonly<ILeaveEscalationCriterias> = {};
