import dayjs from 'dayjs';
import { ILeaveRequestApproverFlows } from 'app/shared/model/leave-request-approver-flows.model';
import { ILeaveRequestApprovers } from 'app/shared/model/leave-request-approvers.model';
import { ILeaveRequests } from 'app/shared/model/leave-requests.model';
import { ILeaveStatusWorkFlows } from 'app/shared/model/leave-status-work-flows.model';
import { ILeaveTypeEscalationRules } from 'app/shared/model/leave-type-escalation-rules.model';
import { IUserAttributeEscalationRules } from 'app/shared/model/user-attribute-escalation-rules.model';

export interface ILeaveStatuses {
  id?: number;
  name?: string;
  leaveGroup?: string;
  systemKey?: string | null;
  isDefault?: boolean;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaverequestapproverflowsApproverstatuses?: ILeaveRequestApproverFlows[] | null;
  leaverequestapproverflowsCurrentleaverequeststatuses?: ILeaveRequestApproverFlows[] | null;
  leaverequestapproverflowsNextleaverequeststatuses?: ILeaveRequestApproverFlows[] | null;
  leaverequestapproversStatuses?: ILeaveRequestApprovers[] | null;
  leaverequestsLeavestatuses?: ILeaveRequests[] | null;
  leavestatusworkflowsCurrentstatuses?: ILeaveStatusWorkFlows[] | null;
  leavestatusworkflowsNextstatuses?: ILeaveStatusWorkFlows[] | null;
  leavestatusworkflowsSkiptostatuses?: ILeaveStatusWorkFlows[] | null;
  leavetypeescalationrulesLeaverequeststatuses?: ILeaveTypeEscalationRules[] | null;
  userattributeescalationrulesApproverstatuses?: IUserAttributeEscalationRules[] | null;
}

export const defaultValue: Readonly<ILeaveStatuses> = {
  isDefault: false,
};
