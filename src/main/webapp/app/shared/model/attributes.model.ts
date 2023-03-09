import dayjs from 'dayjs';
import { ILeaveApprovers } from 'app/shared/model/leave-approvers.model';
import { ILeaveEscalationApprovers } from 'app/shared/model/leave-escalation-approvers.model';
import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';
import { IRatingAttributeMappings } from 'app/shared/model/rating-attribute-mappings.model';
import { IUserAttributeApprovalRules } from 'app/shared/model/user-attribute-approval-rules.model';
import { IUserAttributeEscalationRules } from 'app/shared/model/user-attribute-escalation-rules.model';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { IUserRelationApprovalRules } from 'app/shared/model/user-relation-approval-rules.model';
import { IUserRelations } from 'app/shared/model/user-relations.model';

export interface IAttributes {
  id?: number;
  name?: string | null;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number | null;
  effDate?: string | null;
  leaveapproversAttributes?: ILeaveApprovers[] | null;
  leaveescalationapproversAttributes?: ILeaveEscalationApprovers[] | null;
  raterattributemappingsAttributesfors?: IRaterAttributeMappings[] | null;
  raterattributemappingsAttributesbies?: IRaterAttributeMappings[] | null;
  ratingattributemappingsAttributes?: IRatingAttributeMappings[] | null;
  userattributeapprovalrulesAttributes?: IUserAttributeApprovalRules[] | null;
  userattributeescalationrulesAttributes?: IUserAttributeEscalationRules[] | null;
  userattributesAttributes?: IUserAttributes[] | null;
  userrelationapprovalrulesAttributes?: IUserRelationApprovalRules[] | null;
  userrelationsAttributes?: IUserRelations[] | null;
}

export const defaultValue: Readonly<IAttributes> = {};
