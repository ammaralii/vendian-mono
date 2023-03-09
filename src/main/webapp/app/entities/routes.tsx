import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Addresses from './addresses';
import ApproverFlows from './approver-flows';
import ApproverGroups from './approver-groups';
import Approvers from './approvers';
import AttributePermissions from './attribute-permissions';
import Attributes from './attributes';
import AuditLogs from './audit-logs';
import Benefits from './benefits';
import BloodGroups from './blood-groups';
import BusinessUnits from './business-units';
import ClaimApprovers from './claim-approvers';
import ClaimDetails from './claim-details';
import ClaimImages from './claim-images';
import ClaimRequestViews from './claim-request-views';
import ClaimRequests from './claim-requests';
import ClaimStatus from './claim-status';
import ClaimTypes from './claim-types';
import Companies from './companies';
import CompensationBenefits from './compensation-benefits';
import Competencies from './competencies';
import Configs from './configs';
import Configurations from './configurations';
import DealEvents from './deal-events';
import DealRequirementMatchingResources from './deal-requirement-matching-resources';
import DealRequirements from './deal-requirements';
import DealResourceEventLogs from './deal-resource-event-logs';
import DealResourceSkills from './deal-resource-skills';
import DealResourceStatus from './deal-resource-status';
import DealResources from './deal-resources';
import Deals from './deals';
import Departments from './departments';
import DesignationJobDescriptions from './designation-job-descriptions';
import Designations from './designations';
import Divisions from './divisions';
import Documents from './documents';
import EmployeeAddresses from './employee-addresses';
import EmployeeBirthdays from './employee-birthdays';
import EmployeeCertificates from './employee-certificates';
import EmployeeComments from './employee-comments';
import EmployeeCompensation from './employee-compensation';
import EmployeeContacts from './employee-contacts';
import EmployeeDetails from './employee-details';
import EmployeeDocuments from './employee-documents';
import EmployeeEducation from './employee-education';
import EmployeeEmergencyContacts from './employee-emergency-contacts';
import EmployeeFamilyInfo from './employee-family-info';
import EmployeeJobInfo from './employee-job-info';
import EmployeeProfileHistoryLogs from './employee-profile-history-logs';
import EmployeeProjectRatings from './employee-project-ratings';
import EmployeeProjectRatings20190826 from './employee-project-ratings-20190826';
import EmployeeProjectRoles from './employee-project-roles';
import EmployeeProjects from './employee-projects';
import EmployeeRoles from './employee-roles';
import EmployeeSkills from './employee-skills';
import EmployeeTalents from './employee-talents';
import EmployeeWorks from './employee-works';
import Employees from './employees';
import EmploymentHistory from './employment-history';
import EmploymentStatuses from './employment-statuses';
import EmploymentTypes from './employment-types';
import Events from './events';
import FeedbackEmails from './feedback-emails';
import FeedbackQuestions from './feedback-questions';
import FeedbackRequests from './feedback-requests';
import FeedbackRespondents from './feedback-respondents';
import FeedbackResponses from './feedback-responses';
import GoalGroupMappings from './goal-group-mappings';
import GoalGroups from './goal-groups';
import Goals from './goals';
import HrPerformanceCycles from './hr-performance-cycles';
import Interviews from './interviews';
import LeaveApprovalCriterias from './leave-approval-criterias';
import LeaveApprovers from './leave-approvers';
import LeaveDetailAdjustmentLogs from './leave-detail-adjustment-logs';
import LeaveDetails from './leave-details';
import LeaveEscalationApprovers from './leave-escalation-approvers';
import LeaveEscalationCriterias from './leave-escalation-criterias';
import LeaveRequestApproverFlows from './leave-request-approver-flows';
import LeaveRequestApprovers from './leave-request-approvers';
import LeaveRequests from './leave-requests';
import LeaveRequestsOlds from './leave-requests-olds';
import LeaveStatusWorkFlows from './leave-status-work-flows';
import LeaveStatuses from './leave-statuses';
import LeaveTypeApprovalRules from './leave-type-approval-rules';
import LeaveTypeConfigurations from './leave-type-configurations';
import LeaveTypeEscalationRules from './leave-type-escalation-rules';
import LeaveTypeRestrictions from './leave-type-restrictions';
import LeaveTypes from './leave-types';
import LeaveTypesOlds from './leave-types-olds';
import Leaves from './leaves';
import LeavesCopy from './leaves-copy';
import LeavesOlds from './leaves-olds';
import Locations from './locations';
import MaritalStatuses from './marital-statuses';
import NotificationEvents from './notification-events';
import NotificationMergeFields from './notification-merge-fields';
import NotificationSentEmailLogs from './notification-sent-email-logs';
import NotificationTemplates from './notification-templates';
import PcRaterAttributes from './pc-rater-attributes';
import PcRatingAttributes from './pc-rating-attributes';
import PcRatingAttributesCategories from './pc-rating-attributes-categories';
import PcRatings from './pc-ratings';
import PcRatingsTrainings from './pc-ratings-trainings';
import PcStatuses from './pc-statuses';
import PerformanceCycleEmployeeRating from './performance-cycle-employee-rating';
import PerformanceCycles from './performance-cycles';
import PerformanceCycles20190826 from './performance-cycles-20190826';
import Permissions from './permissions';
import ProjectCycles from './project-cycles';
import ProjectCycles20190826 from './project-cycles-20190826';
import ProjectLeadership from './project-leadership';
import ProjectRoles from './project-roles';
import Projects from './projects';
import QualificationTypes from './qualification-types';
import Questions from './questions';
import QuestionsFrequencyPerClientTrack from './questions-frequency-per-client-track';
import QuestionsFrequencyPerTrack from './questions-frequency-per-track';
import QuestionsProcessingEventLogs from './questions-processing-event-logs';
import RaterAttributeMappings from './rater-attribute-mappings';
import RaterAttributes from './rater-attributes';
import RatingAttributeMappings from './rating-attribute-mappings';
import RatingAttributes from './rating-attributes';
import RatingCategories from './rating-categories';
import RatingOptions from './rating-options';
import Ratings from './ratings';
import Reasons from './reasons';
import Religions from './religions';
import RolePermissions from './role-permissions';
import Roles from './roles';
import Sequelizedata from './sequelizedata';
import Sequelizemeta from './sequelizemeta';
import Skills from './skills';
import Tracks from './tracks';
import Universities from './universities';
import UserAttributeApprovalRules from './user-attribute-approval-rules';
import UserAttributeEscalationRules from './user-attribute-escalation-rules';
import UserAttributes from './user-attributes';
import UserGoalRaterAttributes from './user-goal-rater-attributes';
import UserGoals from './user-goals';
import UserRatings from './user-ratings';
import UserRatingsRemarks from './user-ratings-remarks';
import UserRelationApprovalRules from './user-relation-approval-rules';
import UserRelations from './user-relations';
import WorkLogDetails from './work-log-details';
import WorkLogs from './work-logs';
import ZEmployeeProjects from './z-employee-projects';
import ZLeaveRequests from './z-leave-requests';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="addresses/*" element={<Addresses />} />
        <Route path="approver-flows/*" element={<ApproverFlows />} />
        <Route path="approver-groups/*" element={<ApproverGroups />} />
        <Route path="approvers/*" element={<Approvers />} />
        <Route path="attribute-permissions/*" element={<AttributePermissions />} />
        <Route path="attributes/*" element={<Attributes />} />
        <Route path="audit-logs/*" element={<AuditLogs />} />
        <Route path="benefits/*" element={<Benefits />} />
        <Route path="blood-groups/*" element={<BloodGroups />} />
        <Route path="business-units/*" element={<BusinessUnits />} />
        <Route path="claim-approvers/*" element={<ClaimApprovers />} />
        <Route path="claim-details/*" element={<ClaimDetails />} />
        <Route path="claim-images/*" element={<ClaimImages />} />
        <Route path="claim-request-views/*" element={<ClaimRequestViews />} />
        <Route path="claim-requests/*" element={<ClaimRequests />} />
        <Route path="claim-status/*" element={<ClaimStatus />} />
        <Route path="claim-types/*" element={<ClaimTypes />} />
        <Route path="companies/*" element={<Companies />} />
        <Route path="compensation-benefits/*" element={<CompensationBenefits />} />
        <Route path="competencies/*" element={<Competencies />} />
        <Route path="configs/*" element={<Configs />} />
        <Route path="configurations/*" element={<Configurations />} />
        <Route path="deal-events/*" element={<DealEvents />} />
        <Route path="deal-requirement-matching-resources/*" element={<DealRequirementMatchingResources />} />
        <Route path="deal-requirements/*" element={<DealRequirements />} />
        <Route path="deal-resource-event-logs/*" element={<DealResourceEventLogs />} />
        <Route path="deal-resource-skills/*" element={<DealResourceSkills />} />
        <Route path="deal-resource-status/*" element={<DealResourceStatus />} />
        <Route path="deal-resources/*" element={<DealResources />} />
        <Route path="deals/*" element={<Deals />} />
        <Route path="departments/*" element={<Departments />} />
        <Route path="designation-job-descriptions/*" element={<DesignationJobDescriptions />} />
        <Route path="designations/*" element={<Designations />} />
        <Route path="divisions/*" element={<Divisions />} />
        <Route path="documents/*" element={<Documents />} />
        <Route path="employee-addresses/*" element={<EmployeeAddresses />} />
        <Route path="employee-birthdays/*" element={<EmployeeBirthdays />} />
        <Route path="employee-certificates/*" element={<EmployeeCertificates />} />
        <Route path="employee-comments/*" element={<EmployeeComments />} />
        <Route path="employee-compensation/*" element={<EmployeeCompensation />} />
        <Route path="employee-contacts/*" element={<EmployeeContacts />} />
        <Route path="employee-details/*" element={<EmployeeDetails />} />
        <Route path="employee-documents/*" element={<EmployeeDocuments />} />
        <Route path="employee-education/*" element={<EmployeeEducation />} />
        <Route path="employee-emergency-contacts/*" element={<EmployeeEmergencyContacts />} />
        <Route path="employee-family-info/*" element={<EmployeeFamilyInfo />} />
        <Route path="employee-job-info/*" element={<EmployeeJobInfo />} />
        <Route path="employee-profile-history-logs/*" element={<EmployeeProfileHistoryLogs />} />
        <Route path="employee-project-ratings/*" element={<EmployeeProjectRatings />} />
        <Route path="employee-project-ratings-20190826/*" element={<EmployeeProjectRatings20190826 />} />
        <Route path="employee-project-roles/*" element={<EmployeeProjectRoles />} />
        <Route path="employee-projects/*" element={<EmployeeProjects />} />
        <Route path="employee-roles/*" element={<EmployeeRoles />} />
        <Route path="employee-skills/*" element={<EmployeeSkills />} />
        <Route path="employee-talents/*" element={<EmployeeTalents />} />
        <Route path="employee-works/*" element={<EmployeeWorks />} />
        <Route path="employees/*" element={<Employees />} />
        <Route path="employment-history/*" element={<EmploymentHistory />} />
        <Route path="employment-statuses/*" element={<EmploymentStatuses />} />
        <Route path="employment-types/*" element={<EmploymentTypes />} />
        <Route path="events/*" element={<Events />} />
        <Route path="feedback-emails/*" element={<FeedbackEmails />} />
        <Route path="feedback-questions/*" element={<FeedbackQuestions />} />
        <Route path="feedback-requests/*" element={<FeedbackRequests />} />
        <Route path="feedback-respondents/*" element={<FeedbackRespondents />} />
        <Route path="feedback-responses/*" element={<FeedbackResponses />} />
        <Route path="goal-group-mappings/*" element={<GoalGroupMappings />} />
        <Route path="goal-groups/*" element={<GoalGroups />} />
        <Route path="goals/*" element={<Goals />} />
        <Route path="hr-performance-cycles/*" element={<HrPerformanceCycles />} />
        <Route path="interviews/*" element={<Interviews />} />
        <Route path="leave-approval-criterias/*" element={<LeaveApprovalCriterias />} />
        <Route path="leave-approvers/*" element={<LeaveApprovers />} />
        <Route path="leave-detail-adjustment-logs/*" element={<LeaveDetailAdjustmentLogs />} />
        <Route path="leave-details/*" element={<LeaveDetails />} />
        <Route path="leave-escalation-approvers/*" element={<LeaveEscalationApprovers />} />
        <Route path="leave-escalation-criterias/*" element={<LeaveEscalationCriterias />} />
        <Route path="leave-request-approver-flows/*" element={<LeaveRequestApproverFlows />} />
        <Route path="leave-request-approvers/*" element={<LeaveRequestApprovers />} />
        <Route path="leave-requests/*" element={<LeaveRequests />} />
        <Route path="leave-requests-olds/*" element={<LeaveRequestsOlds />} />
        <Route path="leave-status-work-flows/*" element={<LeaveStatusWorkFlows />} />
        <Route path="leave-statuses/*" element={<LeaveStatuses />} />
        <Route path="leave-type-approval-rules/*" element={<LeaveTypeApprovalRules />} />
        <Route path="leave-type-configurations/*" element={<LeaveTypeConfigurations />} />
        <Route path="leave-type-escalation-rules/*" element={<LeaveTypeEscalationRules />} />
        <Route path="leave-type-restrictions/*" element={<LeaveTypeRestrictions />} />
        <Route path="leave-types/*" element={<LeaveTypes />} />
        <Route path="leave-types-olds/*" element={<LeaveTypesOlds />} />
        <Route path="leaves/*" element={<Leaves />} />
        <Route path="leaves-copy/*" element={<LeavesCopy />} />
        <Route path="leaves-olds/*" element={<LeavesOlds />} />
        <Route path="locations/*" element={<Locations />} />
        <Route path="marital-statuses/*" element={<MaritalStatuses />} />
        <Route path="notification-events/*" element={<NotificationEvents />} />
        <Route path="notification-merge-fields/*" element={<NotificationMergeFields />} />
        <Route path="notification-sent-email-logs/*" element={<NotificationSentEmailLogs />} />
        <Route path="notification-templates/*" element={<NotificationTemplates />} />
        <Route path="pc-rater-attributes/*" element={<PcRaterAttributes />} />
        <Route path="pc-rating-attributes/*" element={<PcRatingAttributes />} />
        <Route path="pc-rating-attributes-categories/*" element={<PcRatingAttributesCategories />} />
        <Route path="pc-ratings/*" element={<PcRatings />} />
        <Route path="pc-ratings-trainings/*" element={<PcRatingsTrainings />} />
        <Route path="pc-statuses/*" element={<PcStatuses />} />
        <Route path="performance-cycle-employee-rating/*" element={<PerformanceCycleEmployeeRating />} />
        <Route path="performance-cycles/*" element={<PerformanceCycles />} />
        <Route path="performance-cycles-20190826/*" element={<PerformanceCycles20190826 />} />
        <Route path="permissions/*" element={<Permissions />} />
        <Route path="project-cycles/*" element={<ProjectCycles />} />
        <Route path="project-cycles-20190826/*" element={<ProjectCycles20190826 />} />
        <Route path="project-leadership/*" element={<ProjectLeadership />} />
        <Route path="project-roles/*" element={<ProjectRoles />} />
        <Route path="projects/*" element={<Projects />} />
        <Route path="qualification-types/*" element={<QualificationTypes />} />
        <Route path="questions/*" element={<Questions />} />
        <Route path="questions-frequency-per-client-track/*" element={<QuestionsFrequencyPerClientTrack />} />
        <Route path="questions-frequency-per-track/*" element={<QuestionsFrequencyPerTrack />} />
        <Route path="questions-processing-event-logs/*" element={<QuestionsProcessingEventLogs />} />
        <Route path="rater-attribute-mappings/*" element={<RaterAttributeMappings />} />
        <Route path="rater-attributes/*" element={<RaterAttributes />} />
        <Route path="rating-attribute-mappings/*" element={<RatingAttributeMappings />} />
        <Route path="rating-attributes/*" element={<RatingAttributes />} />
        <Route path="rating-categories/*" element={<RatingCategories />} />
        <Route path="rating-options/*" element={<RatingOptions />} />
        <Route path="ratings/*" element={<Ratings />} />
        <Route path="reasons/*" element={<Reasons />} />
        <Route path="religions/*" element={<Religions />} />
        <Route path="role-permissions/*" element={<RolePermissions />} />
        <Route path="roles/*" element={<Roles />} />
        <Route path="sequelizedata/*" element={<Sequelizedata />} />
        <Route path="sequelizemeta/*" element={<Sequelizemeta />} />
        <Route path="skills/*" element={<Skills />} />
        <Route path="tracks/*" element={<Tracks />} />
        <Route path="universities/*" element={<Universities />} />
        <Route path="user-attribute-approval-rules/*" element={<UserAttributeApprovalRules />} />
        <Route path="user-attribute-escalation-rules/*" element={<UserAttributeEscalationRules />} />
        <Route path="user-attributes/*" element={<UserAttributes />} />
        <Route path="user-goal-rater-attributes/*" element={<UserGoalRaterAttributes />} />
        <Route path="user-goals/*" element={<UserGoals />} />
        <Route path="user-ratings/*" element={<UserRatings />} />
        <Route path="user-ratings-remarks/*" element={<UserRatingsRemarks />} />
        <Route path="user-relation-approval-rules/*" element={<UserRelationApprovalRules />} />
        <Route path="user-relations/*" element={<UserRelations />} />
        <Route path="work-log-details/*" element={<WorkLogDetails />} />
        <Route path="work-logs/*" element={<WorkLogs />} />
        <Route path="z-employee-projects/*" element={<ZEmployeeProjects />} />
        <Route path="z-leave-requests/*" element={<ZLeaveRequests />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
