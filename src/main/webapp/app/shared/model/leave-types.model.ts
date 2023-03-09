import dayjs from 'dayjs';
import { ILeaveDetails } from 'app/shared/model/leave-details.model';
import { ILeaveTypeApprovalRules } from 'app/shared/model/leave-type-approval-rules.model';
import { ILeaveTypeConfigurations } from 'app/shared/model/leave-type-configurations.model';
import { ILeaveTypeEscalationRules } from 'app/shared/model/leave-type-escalation-rules.model';
import { ILeaveTypeRestrictions } from 'app/shared/model/leave-type-restrictions.model';

export interface ILeaveTypes {
  id?: number;
  name?: string;
  category?: string;
  cycleType?: string;
  cycleCount?: number;
  maxQuota?: number | null;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leavedetailsLeavetypes?: ILeaveDetails[] | null;
  leavetypeapprovalrulesLeavetypes?: ILeaveTypeApprovalRules[] | null;
  leavetypeconfigurationsLeavetypes?: ILeaveTypeConfigurations[] | null;
  leavetypeescalationrulesLeavetypes?: ILeaveTypeEscalationRules[] | null;
  leavetyperestrictionsLeavetypes?: ILeaveTypeRestrictions[] | null;
}

export const defaultValue: Readonly<ILeaveTypes> = {};
