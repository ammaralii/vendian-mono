import dayjs from 'dayjs';
import { ILeaveApprovers } from 'app/shared/model/leave-approvers.model';
import { ILeaveTypeApprovalRules } from 'app/shared/model/leave-type-approval-rules.model';
import { IUserAttributeApprovalRules } from 'app/shared/model/user-attribute-approval-rules.model';
import { IUserRelationApprovalRules } from 'app/shared/model/user-relation-approval-rules.model';

export interface ILeaveApprovalCriterias {
  id?: number;
  name?: string;
  priority?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  leaveapproversLeaveapprovalcriteria?: ILeaveApprovers[] | null;
  leavetypeapprovalrulesLeaveapprovalcriteria?: ILeaveTypeApprovalRules[] | null;
  userattributeapprovalrulesLeaveapprovalcriteria?: IUserAttributeApprovalRules[] | null;
  userrelationapprovalrulesLeaveapprovalcriteria?: IUserRelationApprovalRules[] | null;
}

export const defaultValue: Readonly<ILeaveApprovalCriterias> = {};
