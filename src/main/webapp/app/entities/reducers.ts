import addresses from 'app/entities/addresses/addresses.reducer';
import approverFlows from 'app/entities/approver-flows/approver-flows.reducer';
import approverGroups from 'app/entities/approver-groups/approver-groups.reducer';
import approvers from 'app/entities/approvers/approvers.reducer';
import attributePermissions from 'app/entities/attribute-permissions/attribute-permissions.reducer';
import attributes from 'app/entities/attributes/attributes.reducer';
import auditLogs from 'app/entities/audit-logs/audit-logs.reducer';
import benefits from 'app/entities/benefits/benefits.reducer';
import bloodGroups from 'app/entities/blood-groups/blood-groups.reducer';
import businessUnits from 'app/entities/business-units/business-units.reducer';
import claimApprovers from 'app/entities/claim-approvers/claim-approvers.reducer';
import claimDetails from 'app/entities/claim-details/claim-details.reducer';
import claimImages from 'app/entities/claim-images/claim-images.reducer';
import claimRequestViews from 'app/entities/claim-request-views/claim-request-views.reducer';
import claimRequests from 'app/entities/claim-requests/claim-requests.reducer';
import claimStatus from 'app/entities/claim-status/claim-status.reducer';
import claimTypes from 'app/entities/claim-types/claim-types.reducer';
import companies from 'app/entities/companies/companies.reducer';
import compensationBenefits from 'app/entities/compensation-benefits/compensation-benefits.reducer';
import competencies from 'app/entities/competencies/competencies.reducer';
import configs from 'app/entities/configs/configs.reducer';
import configurations from 'app/entities/configurations/configurations.reducer';
import dealEvents from 'app/entities/deal-events/deal-events.reducer';
import dealRequirementMatchingResources from 'app/entities/deal-requirement-matching-resources/deal-requirement-matching-resources.reducer';
import dealRequirements from 'app/entities/deal-requirements/deal-requirements.reducer';
import dealResourceEventLogs from 'app/entities/deal-resource-event-logs/deal-resource-event-logs.reducer';
import dealResourceSkills from 'app/entities/deal-resource-skills/deal-resource-skills.reducer';
import dealResourceStatus from 'app/entities/deal-resource-status/deal-resource-status.reducer';
import dealResources from 'app/entities/deal-resources/deal-resources.reducer';
import deals from 'app/entities/deals/deals.reducer';
import departments from 'app/entities/departments/departments.reducer';
import designationJobDescriptions from 'app/entities/designation-job-descriptions/designation-job-descriptions.reducer';
import designations from 'app/entities/designations/designations.reducer';
import divisions from 'app/entities/divisions/divisions.reducer';
import documents from 'app/entities/documents/documents.reducer';
import employeeAddresses from 'app/entities/employee-addresses/employee-addresses.reducer';
import employeeBirthdays from 'app/entities/employee-birthdays/employee-birthdays.reducer';
import employeeCertificates from 'app/entities/employee-certificates/employee-certificates.reducer';
import employeeComments from 'app/entities/employee-comments/employee-comments.reducer';
import employeeCompensation from 'app/entities/employee-compensation/employee-compensation.reducer';
import employeeContacts from 'app/entities/employee-contacts/employee-contacts.reducer';
import employeeDetails from 'app/entities/employee-details/employee-details.reducer';
import employeeDocuments from 'app/entities/employee-documents/employee-documents.reducer';
import employeeEducation from 'app/entities/employee-education/employee-education.reducer';
import employeeEmergencyContacts from 'app/entities/employee-emergency-contacts/employee-emergency-contacts.reducer';
import employeeFamilyInfo from 'app/entities/employee-family-info/employee-family-info.reducer';
import employeeJobInfo from 'app/entities/employee-job-info/employee-job-info.reducer';
import employeeProfileHistoryLogs from 'app/entities/employee-profile-history-logs/employee-profile-history-logs.reducer';
import employeeProjectRatings from 'app/entities/employee-project-ratings/employee-project-ratings.reducer';
import employeeProjectRatings20190826 from 'app/entities/employee-project-ratings-20190826/employee-project-ratings-20190826.reducer';
import employeeProjectRoles from 'app/entities/employee-project-roles/employee-project-roles.reducer';
import employeeProjects from 'app/entities/employee-projects/employee-projects.reducer';
import employeeRoles from 'app/entities/employee-roles/employee-roles.reducer';
import employeeSkills from 'app/entities/employee-skills/employee-skills.reducer';
import employeeTalents from 'app/entities/employee-talents/employee-talents.reducer';
import employeeWorks from 'app/entities/employee-works/employee-works.reducer';
import employees from 'app/entities/employees/employees.reducer';
import employmentHistory from 'app/entities/employment-history/employment-history.reducer';
import employmentStatuses from 'app/entities/employment-statuses/employment-statuses.reducer';
import employmentTypes from 'app/entities/employment-types/employment-types.reducer';
import events from 'app/entities/events/events.reducer';
import feedbackEmails from 'app/entities/feedback-emails/feedback-emails.reducer';
import feedbackQuestions from 'app/entities/feedback-questions/feedback-questions.reducer';
import feedbackRequests from 'app/entities/feedback-requests/feedback-requests.reducer';
import feedbackRespondents from 'app/entities/feedback-respondents/feedback-respondents.reducer';
import feedbackResponses from 'app/entities/feedback-responses/feedback-responses.reducer';
import goalGroupMappings from 'app/entities/goal-group-mappings/goal-group-mappings.reducer';
import goalGroups from 'app/entities/goal-groups/goal-groups.reducer';
import goals from 'app/entities/goals/goals.reducer';
import hrPerformanceCycles from 'app/entities/hr-performance-cycles/hr-performance-cycles.reducer';
import interviews from 'app/entities/interviews/interviews.reducer';
import leaveApprovalCriterias from 'app/entities/leave-approval-criterias/leave-approval-criterias.reducer';
import leaveApprovers from 'app/entities/leave-approvers/leave-approvers.reducer';
import leaveDetailAdjustmentLogs from 'app/entities/leave-detail-adjustment-logs/leave-detail-adjustment-logs.reducer';
import leaveDetails from 'app/entities/leave-details/leave-details.reducer';
import leaveEscalationApprovers from 'app/entities/leave-escalation-approvers/leave-escalation-approvers.reducer';
import leaveEscalationCriterias from 'app/entities/leave-escalation-criterias/leave-escalation-criterias.reducer';
import leaveRequestApproverFlows from 'app/entities/leave-request-approver-flows/leave-request-approver-flows.reducer';
import leaveRequestApprovers from 'app/entities/leave-request-approvers/leave-request-approvers.reducer';
import leaveRequests from 'app/entities/leave-requests/leave-requests.reducer';
import leaveRequestsOlds from 'app/entities/leave-requests-olds/leave-requests-olds.reducer';
import leaveStatusWorkFlows from 'app/entities/leave-status-work-flows/leave-status-work-flows.reducer';
import leaveStatuses from 'app/entities/leave-statuses/leave-statuses.reducer';
import leaveTypeApprovalRules from 'app/entities/leave-type-approval-rules/leave-type-approval-rules.reducer';
import leaveTypeConfigurations from 'app/entities/leave-type-configurations/leave-type-configurations.reducer';
import leaveTypeEscalationRules from 'app/entities/leave-type-escalation-rules/leave-type-escalation-rules.reducer';
import leaveTypeRestrictions from 'app/entities/leave-type-restrictions/leave-type-restrictions.reducer';
import leaveTypes from 'app/entities/leave-types/leave-types.reducer';
import leaveTypesOlds from 'app/entities/leave-types-olds/leave-types-olds.reducer';
import leaves from 'app/entities/leaves/leaves.reducer';
import leavesCopy from 'app/entities/leaves-copy/leaves-copy.reducer';
import leavesOlds from 'app/entities/leaves-olds/leaves-olds.reducer';
import locations from 'app/entities/locations/locations.reducer';
import maritalStatuses from 'app/entities/marital-statuses/marital-statuses.reducer';
import notificationEvents from 'app/entities/notification-events/notification-events.reducer';
import notificationMergeFields from 'app/entities/notification-merge-fields/notification-merge-fields.reducer';
import notificationSentEmailLogs from 'app/entities/notification-sent-email-logs/notification-sent-email-logs.reducer';
import notificationTemplates from 'app/entities/notification-templates/notification-templates.reducer';
import pcRaterAttributes from 'app/entities/pc-rater-attributes/pc-rater-attributes.reducer';
import pcRatingAttributes from 'app/entities/pc-rating-attributes/pc-rating-attributes.reducer';
import pcRatingAttributesCategories from 'app/entities/pc-rating-attributes-categories/pc-rating-attributes-categories.reducer';
import pcRatings from 'app/entities/pc-ratings/pc-ratings.reducer';
import pcRatingsTrainings from 'app/entities/pc-ratings-trainings/pc-ratings-trainings.reducer';
import pcStatuses from 'app/entities/pc-statuses/pc-statuses.reducer';
import performanceCycleEmployeeRating from 'app/entities/performance-cycle-employee-rating/performance-cycle-employee-rating.reducer';
import performanceCycles from 'app/entities/performance-cycles/performance-cycles.reducer';
import performanceCycles20190826 from 'app/entities/performance-cycles-20190826/performance-cycles-20190826.reducer';
import permissions from 'app/entities/permissions/permissions.reducer';
import projectCycles from 'app/entities/project-cycles/project-cycles.reducer';
import projectCycles20190826 from 'app/entities/project-cycles-20190826/project-cycles-20190826.reducer';
import projectLeadership from 'app/entities/project-leadership/project-leadership.reducer';
import projectRoles from 'app/entities/project-roles/project-roles.reducer';
import projects from 'app/entities/projects/projects.reducer';
import qualificationTypes from 'app/entities/qualification-types/qualification-types.reducer';
import questions from 'app/entities/questions/questions.reducer';
import questionsFrequencyPerClientTrack from 'app/entities/questions-frequency-per-client-track/questions-frequency-per-client-track.reducer';
import questionsFrequencyPerTrack from 'app/entities/questions-frequency-per-track/questions-frequency-per-track.reducer';
import questionsProcessingEventLogs from 'app/entities/questions-processing-event-logs/questions-processing-event-logs.reducer';
import raterAttributeMappings from 'app/entities/rater-attribute-mappings/rater-attribute-mappings.reducer';
import raterAttributes from 'app/entities/rater-attributes/rater-attributes.reducer';
import ratingAttributeMappings from 'app/entities/rating-attribute-mappings/rating-attribute-mappings.reducer';
import ratingAttributes from 'app/entities/rating-attributes/rating-attributes.reducer';
import ratingCategories from 'app/entities/rating-categories/rating-categories.reducer';
import ratingOptions from 'app/entities/rating-options/rating-options.reducer';
import ratings from 'app/entities/ratings/ratings.reducer';
import reasons from 'app/entities/reasons/reasons.reducer';
import religions from 'app/entities/religions/religions.reducer';
import rolePermissions from 'app/entities/role-permissions/role-permissions.reducer';
import roles from 'app/entities/roles/roles.reducer';
import sequelizedata from 'app/entities/sequelizedata/sequelizedata.reducer';
import sequelizemeta from 'app/entities/sequelizemeta/sequelizemeta.reducer';
import skills from 'app/entities/skills/skills.reducer';
import tracks from 'app/entities/tracks/tracks.reducer';
import universities from 'app/entities/universities/universities.reducer';
import userAttributeApprovalRules from 'app/entities/user-attribute-approval-rules/user-attribute-approval-rules.reducer';
import userAttributeEscalationRules from 'app/entities/user-attribute-escalation-rules/user-attribute-escalation-rules.reducer';
import userAttributes from 'app/entities/user-attributes/user-attributes.reducer';
import userGoalRaterAttributes from 'app/entities/user-goal-rater-attributes/user-goal-rater-attributes.reducer';
import userGoals from 'app/entities/user-goals/user-goals.reducer';
import userRatings from 'app/entities/user-ratings/user-ratings.reducer';
import userRatingsRemarks from 'app/entities/user-ratings-remarks/user-ratings-remarks.reducer';
import userRelationApprovalRules from 'app/entities/user-relation-approval-rules/user-relation-approval-rules.reducer';
import userRelations from 'app/entities/user-relations/user-relations.reducer';
import workLogDetails from 'app/entities/work-log-details/work-log-details.reducer';
import workLogs from 'app/entities/work-logs/work-logs.reducer';
import zEmployeeProjects from 'app/entities/z-employee-projects/z-employee-projects.reducer';
import zLeaveRequests from 'app/entities/z-leave-requests/z-leave-requests.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  addresses,
  approverFlows,
  approverGroups,
  approvers,
  attributePermissions,
  attributes,
  auditLogs,
  benefits,
  bloodGroups,
  businessUnits,
  claimApprovers,
  claimDetails,
  claimImages,
  claimRequestViews,
  claimRequests,
  claimStatus,
  claimTypes,
  companies,
  compensationBenefits,
  competencies,
  configs,
  configurations,
  dealEvents,
  dealRequirementMatchingResources,
  dealRequirements,
  dealResourceEventLogs,
  dealResourceSkills,
  dealResourceStatus,
  dealResources,
  deals,
  departments,
  designationJobDescriptions,
  designations,
  divisions,
  documents,
  employeeAddresses,
  employeeBirthdays,
  employeeCertificates,
  employeeComments,
  employeeCompensation,
  employeeContacts,
  employeeDetails,
  employeeDocuments,
  employeeEducation,
  employeeEmergencyContacts,
  employeeFamilyInfo,
  employeeJobInfo,
  employeeProfileHistoryLogs,
  employeeProjectRatings,
  employeeProjectRatings20190826,
  employeeProjectRoles,
  employeeProjects,
  employeeRoles,
  employeeSkills,
  employeeTalents,
  employeeWorks,
  employees,
  employmentHistory,
  employmentStatuses,
  employmentTypes,
  events,
  feedbackEmails,
  feedbackQuestions,
  feedbackRequests,
  feedbackRespondents,
  feedbackResponses,
  goalGroupMappings,
  goalGroups,
  goals,
  hrPerformanceCycles,
  interviews,
  leaveApprovalCriterias,
  leaveApprovers,
  leaveDetailAdjustmentLogs,
  leaveDetails,
  leaveEscalationApprovers,
  leaveEscalationCriterias,
  leaveRequestApproverFlows,
  leaveRequestApprovers,
  leaveRequests,
  leaveRequestsOlds,
  leaveStatusWorkFlows,
  leaveStatuses,
  leaveTypeApprovalRules,
  leaveTypeConfigurations,
  leaveTypeEscalationRules,
  leaveTypeRestrictions,
  leaveTypes,
  leaveTypesOlds,
  leaves,
  leavesCopy,
  leavesOlds,
  locations,
  maritalStatuses,
  notificationEvents,
  notificationMergeFields,
  notificationSentEmailLogs,
  notificationTemplates,
  pcRaterAttributes,
  pcRatingAttributes,
  pcRatingAttributesCategories,
  pcRatings,
  pcRatingsTrainings,
  pcStatuses,
  performanceCycleEmployeeRating,
  performanceCycles,
  performanceCycles20190826,
  permissions,
  projectCycles,
  projectCycles20190826,
  projectLeadership,
  projectRoles,
  projects,
  qualificationTypes,
  questions,
  questionsFrequencyPerClientTrack,
  questionsFrequencyPerTrack,
  questionsProcessingEventLogs,
  raterAttributeMappings,
  raterAttributes,
  ratingAttributeMappings,
  ratingAttributes,
  ratingCategories,
  ratingOptions,
  ratings,
  reasons,
  religions,
  rolePermissions,
  roles,
  sequelizedata,
  sequelizemeta,
  skills,
  tracks,
  universities,
  userAttributeApprovalRules,
  userAttributeEscalationRules,
  userAttributes,
  userGoalRaterAttributes,
  userGoals,
  userRatings,
  userRatingsRemarks,
  userRelationApprovalRules,
  userRelations,
  workLogDetails,
  workLogs,
  zEmployeeProjects,
  zLeaveRequests,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
