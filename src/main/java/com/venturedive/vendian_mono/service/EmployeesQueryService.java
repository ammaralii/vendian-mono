package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeesCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Employees} entities in the database.
 * The main input is a {@link EmployeesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Employees} or a {@link Page} of {@link Employees} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeesQueryService extends QueryService<Employees> {

    private final Logger log = LoggerFactory.getLogger(EmployeesQueryService.class);

    private final EmployeesRepository employeesRepository;

    public EmployeesQueryService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    /**
     * Return a {@link List} of {@link Employees} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Employees> findByCriteria(EmployeesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Employees} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Employees> findByCriteria(EmployeesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employees> createSpecification(EmployeesCriteria criteria) {
        Specification<Employees> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employees_.id));
            }
            if (criteria.getFirstname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstname(), Employees_.firstname));
            }
            if (criteria.getLastname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastname(), Employees_.lastname));
            }
            if (criteria.getPhonenumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhonenumber(), Employees_.phonenumber));
            }
            if (criteria.getDateofbirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateofbirth(), Employees_.dateofbirth));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employees_.email));
            }
            if (criteria.getSkype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkype(), Employees_.skype));
            }
            if (criteria.getEmployeeDesignation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmployeeDesignation(), Employees_.employeeDesignation));
            }
            if (criteria.getJoiningdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoiningdate(), Employees_.joiningdate));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), Employees_.area));
            }
            if (criteria.getLeavingdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeavingdate(), Employees_.leavingdate));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Employees_.notes));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Employees_.isactive));
            }
            if (criteria.getGoogleid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoogleid(), Employees_.googleid));
            }
            if (criteria.getOracleid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOracleid(), Employees_.oracleid));
            }
            if (criteria.getDeptt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeptt(), Employees_.deptt));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Employees_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Employees_.updatedat));
            }
            if (criteria.getGenderid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenderid(), Employees_.genderid));
            }
            if (criteria.getOnprobation() != null) {
                specification = specification.and(buildSpecification(criteria.getOnprobation(), Employees_.onprobation));
            }
            if (criteria.getEmployeeCompetency() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmployeeCompetency(), Employees_.employeeCompetency));
            }
            if (criteria.getResourcetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourcetype(), Employees_.resourcetype));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), Employees_.grade));
            }
            if (criteria.getSubgrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubgrade(), Employees_.subgrade));
            }
            if (criteria.getImageurl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageurl(), Employees_.imageurl));
            }
            if (criteria.getResignationdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResignationdate(), Employees_.resignationdate));
            }
            if (criteria.getGraduationdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraduationdate(), Employees_.graduationdate));
            }
            if (criteria.getCareerstartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCareerstartdate(), Employees_.careerstartdate));
            }
            if (criteria.getExternalexpyears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExternalexpyears(), Employees_.externalexpyears));
            }
            if (criteria.getExternalexpmonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExternalexpmonths(), Employees_.externalexpmonths));
            }
            if (criteria.getPlaceofbirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaceofbirth(), Employees_.placeofbirth));
            }
            if (criteria.getHireddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHireddate(), Employees_.hireddate));
            }
            if (criteria.getLasttrackingupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLasttrackingupdate(), Employees_.lasttrackingupdate));
            }
            if (criteria.getMiddlename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddlename(), Employees_.middlename));
            }
            if (criteria.getMobilenumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobilenumber(), Employees_.mobilenumber));
            }
            if (criteria.getResignationtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResignationtype(), Employees_.resignationtype));
            }
            if (criteria.getPrimaryreasonforleaving() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrimaryreasonforleaving(), Employees_.primaryreasonforleaving));
            }
            if (criteria.getSecondaryreasonforleaving() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSecondaryreasonforleaving(), Employees_.secondaryreasonforleaving)
                    );
            }
            if (criteria.getNoticeperiodduration() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNoticeperiodduration(), Employees_.noticeperiodduration));
            }
            if (criteria.getNoticeperiodserved() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoticeperiodserved(), Employees_.noticeperiodserved));
            }
            if (criteria.getProbationperiodduration() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProbationperiodduration(), Employees_.probationperiodduration));
            }
            if (criteria.getLocationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLocationId(),
                            root -> root.join(Employees_.location, JoinType.LEFT).get(Locations_.id)
                        )
                    );
            }
            if (criteria.getRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoleId(), root -> root.join(Employees_.role, JoinType.LEFT).get(Roles_.id))
                    );
            }
            if (criteria.getManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getManagerId(), root -> root.join(Employees_.manager, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getLeaveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLeaveId(), root -> root.join(Employees_.leave, JoinType.LEFT).get(LeavesOlds_.id))
                    );
            }
            if (criteria.getDepartmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDepartmentId(),
                            root -> root.join(Employees_.department, JoinType.LEFT).get(Departments_.id)
                        )
                    );
            }
            if (criteria.getBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessunitId(),
                            root -> root.join(Employees_.businessunit, JoinType.LEFT).get(BusinessUnits_.id)
                        )
                    );
            }
            if (criteria.getDivisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDivisionId(),
                            root -> root.join(Employees_.division, JoinType.LEFT).get(Divisions_.id)
                        )
                    );
            }
            if (criteria.getCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompetencyId(),
                            root -> root.join(Employees_.competency, JoinType.LEFT).get(Competencies_.id)
                        )
                    );
            }
            if (criteria.getEmploymentstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmploymentstatusId(),
                            root -> root.join(Employees_.employmentstatus, JoinType.LEFT).get(EmploymentStatuses_.id)
                        )
                    );
            }
            if (criteria.getEmploymenttypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmploymenttypeId(),
                            root -> root.join(Employees_.employmenttype, JoinType.LEFT).get(EmploymentTypes_.id)
                        )
                    );
            }
            if (criteria.getDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationId(),
                            root -> root.join(Employees_.designation, JoinType.LEFT).get(Designations_.id)
                        )
                    );
            }
            if (criteria.getClaimrequestviewsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimrequestviewsEmployeeId(),
                            root -> root.join(Employees_.claimrequestviewsEmployees, JoinType.LEFT).get(ClaimRequestViews_.id)
                        )
                    );
            }
            if (criteria.getClaimrequestsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimrequestsEmployeeId(),
                            root -> root.join(Employees_.claimrequestsEmployees, JoinType.LEFT).get(ClaimRequests_.id)
                        )
                    );
            }
            if (criteria.getDealresourcesEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourcesEmployeeId(),
                            root -> root.join(Employees_.dealresourcesEmployees, JoinType.LEFT).get(DealResources_.id)
                        )
                    );
            }
            if (criteria.getEmployeeaddressesEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeaddressesEmployeeId(),
                            root -> root.join(Employees_.employeeaddressesEmployees, JoinType.LEFT).get(EmployeeAddresses_.id)
                        )
                    );
            }
            if (criteria.getEmployeebirthdaysEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeebirthdaysEmployeeId(),
                            root -> root.join(Employees_.employeebirthdaysEmployees, JoinType.LEFT).get(EmployeeBirthdays_.id)
                        )
                    );
            }
            if (criteria.getEmployeecertificatesEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecertificatesEmployeeId(),
                            root -> root.join(Employees_.employeecertificatesEmployees, JoinType.LEFT).get(EmployeeCertificates_.id)
                        )
                    );
            }
            if (criteria.getEmployeecommentsCommenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecommentsCommenterId(),
                            root -> root.join(Employees_.employeecommentsCommenters, JoinType.LEFT).get(EmployeeComments_.id)
                        )
                    );
            }
            if (criteria.getEmployeecommentsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecommentsEmployeeId(),
                            root -> root.join(Employees_.employeecommentsEmployees, JoinType.LEFT).get(EmployeeComments_.id)
                        )
                    );
            }
            if (criteria.getEmployeecompensationEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecompensationEmployeeId(),
                            root -> root.join(Employees_.employeecompensationEmployees, JoinType.LEFT).get(EmployeeCompensation_.id)
                        )
                    );
            }
            if (criteria.getEmployeecontactsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecontactsEmployeeId(),
                            root -> root.join(Employees_.employeecontactsEmployees, JoinType.LEFT).get(EmployeeContacts_.id)
                        )
                    );
            }
            if (criteria.getEmployeedetailsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeedetailsEmployeeId(),
                            root -> root.join(Employees_.employeedetailsEmployees, JoinType.LEFT).get(EmployeeDetails_.id)
                        )
                    );
            }
            if (criteria.getEmployeedocumentsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeedocumentsEmployeeId(),
                            root -> root.join(Employees_.employeedocumentsEmployees, JoinType.LEFT).get(EmployeeDocuments_.id)
                        )
                    );
            }
            if (criteria.getEmployeeeducationEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeeducationEmployeeId(),
                            root -> root.join(Employees_.employeeeducationEmployees, JoinType.LEFT).get(EmployeeEducation_.id)
                        )
                    );
            }
            if (criteria.getEmployeeemergencycontactsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeemergencycontactsEmployeeId(),
                            root ->
                                root.join(Employees_.employeeemergencycontactsEmployees, JoinType.LEFT).get(EmployeeEmergencyContacts_.id)
                        )
                    );
            }
            if (criteria.getEmployeefamilyinfoEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeefamilyinfoEmployeeId(),
                            root -> root.join(Employees_.employeefamilyinfoEmployees, JoinType.LEFT).get(EmployeeFamilyInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoEmployeeId(),
                            root -> root.join(Employees_.employeejobinfoEmployees, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoReviewmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoReviewmanagerId(),
                            root -> root.join(Employees_.employeejobinfoReviewmanagers, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoManagerId(),
                            root -> root.join(Employees_.employeejobinfoManagers, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprofilehistorylogsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprofilehistorylogsEmployeeId(),
                            root ->
                                root.join(Employees_.employeeprofilehistorylogsEmployees, JoinType.LEFT).get(EmployeeProfileHistoryLogs_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectratingsProjectarchitectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectratingsProjectarchitectId(),
                            root ->
                                root.join(Employees_.employeeprojectratingsProjectarchitects, JoinType.LEFT).get(EmployeeProjectRatings_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectratingsProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectratingsProjectmanagerId(),
                            root ->
                                root.join(Employees_.employeeprojectratingsProjectmanagers, JoinType.LEFT).get(EmployeeProjectRatings_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectratingsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectratingsEmployeeId(),
                            root -> root.join(Employees_.employeeprojectratingsEmployees, JoinType.LEFT).get(EmployeeProjectRatings_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectsEmployeeId(),
                            root -> root.join(Employees_.employeeprojectsEmployees, JoinType.LEFT).get(EmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectsAssignorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectsAssignorId(),
                            root -> root.join(Employees_.employeeprojectsAssignors, JoinType.LEFT).get(EmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getEmployeeskillsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeskillsEmployeeId(),
                            root -> root.join(Employees_.employeeskillsEmployees, JoinType.LEFT).get(EmployeeSkills_.id)
                        )
                    );
            }
            if (criteria.getEmployeetalentsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeetalentsEmployeeId(),
                            root -> root.join(Employees_.employeetalentsEmployees, JoinType.LEFT).get(EmployeeTalents_.id)
                        )
                    );
            }
            if (criteria.getEmployeeworksEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeworksEmployeeId(),
                            root -> root.join(Employees_.employeeworksEmployees, JoinType.LEFT).get(EmployeeWorks_.id)
                        )
                    );
            }
            if (criteria.getEmployeesManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesManagerId(),
                            root -> root.join(Employees_.employeesManagers, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getEmploymenthistoryEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmploymenthistoryEmployeeId(),
                            root -> root.join(Employees_.employmenthistoryEmployees, JoinType.LEFT).get(EmploymentHistory_.id)
                        )
                    );
            }
            if (criteria.getFeedbackquestionsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackquestionsEmployeeId(),
                            root -> root.join(Employees_.feedbackquestionsEmployees, JoinType.LEFT).get(FeedbackQuestions_.id)
                        )
                    );
            }
            if (criteria.getFeedbackrequestsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackrequestsEmployeeId(),
                            root -> root.join(Employees_.feedbackrequestsEmployees, JoinType.LEFT).get(FeedbackRequests_.id)
                        )
                    );
            }
            if (criteria.getFeedbackrequestsLinemanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackrequestsLinemanagerId(),
                            root -> root.join(Employees_.feedbackrequestsLinemanagers, JoinType.LEFT).get(FeedbackRequests_.id)
                        )
                    );
            }
            if (criteria.getFeedbackrespondentsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackrespondentsEmployeeId(),
                            root -> root.join(Employees_.feedbackrespondentsEmployees, JoinType.LEFT).get(FeedbackRespondents_.id)
                        )
                    );
            }
            if (criteria.getInterviewsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewsEmployeeId(),
                            root -> root.join(Employees_.interviewsEmployees, JoinType.LEFT).get(Interviews_.id)
                        )
                    );
            }
            if (criteria.getLeavedetailadjustmentlogsAdjustedbyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavedetailadjustmentlogsAdjustedbyId(),
                            root ->
                                root
                                    .join(Employees_.leavedetailadjustmentlogsAdjustedbies, JoinType.LEFT)
                                    .get(LeaveDetailAdjustmentLogs_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestapproversUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproversUserId(),
                            root -> root.join(Employees_.leaverequestapproversUsers, JoinType.LEFT).get(LeaveRequestApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestsoldsManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsoldsManagerId(),
                            root -> root.join(Employees_.leaverequestsoldsManagers, JoinType.LEFT).get(LeaveRequestsOlds_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestsoldsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsoldsEmployeeId(),
                            root -> root.join(Employees_.leaverequestsoldsEmployees, JoinType.LEFT).get(LeaveRequestsOlds_.id)
                        )
                    );
            }
            if (criteria.getLeavesUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavesUserId(),
                            root -> root.join(Employees_.leavesUsers, JoinType.LEFT).get(Leaves_.id)
                        )
                    );
            }
            if (criteria.getNotificationsentemaillogsRecipientId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationsentemaillogsRecipientId(),
                            root ->
                                root.join(Employees_.notificationsentemaillogsRecipients, JoinType.LEFT).get(NotificationSentEmailLogs_.id)
                        )
                    );
            }
            if (criteria.getPcratingsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingsEmployeeId(),
                            root -> root.join(Employees_.pcratingsEmployees, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
            if (criteria.getPcratingstrainingsSuccessorforId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingstrainingsSuccessorforId(),
                            root -> root.join(Employees_.pcratingstrainingsSuccessorfors, JoinType.LEFT).get(PcRatingsTrainings_.id)
                        )
                    );
            }
            if (criteria.getPerformancecycleemployeeratingEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleemployeeratingEmployeeId(),
                            root ->
                                root
                                    .join(Employees_.performancecycleemployeeratingEmployees, JoinType.LEFT)
                                    .get(PerformanceCycleEmployeeRating_.id)
                        )
                    );
            }
            if (criteria.getPerformancecycleemployeeratingManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleemployeeratingManagerId(),
                            root ->
                                root
                                    .join(Employees_.performancecycleemployeeratingManagers, JoinType.LEFT)
                                    .get(PerformanceCycleEmployeeRating_.id)
                        )
                    );
            }
            if (criteria.getProjectcyclesAllowedafterduedatebyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcyclesAllowedafterduedatebyId(),
                            root -> root.join(Employees_.projectcyclesAllowedafterduedatebies, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
            if (criteria.getProjectcyclesArchitectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcyclesArchitectId(),
                            root -> root.join(Employees_.projectcyclesArchitects, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
            if (criteria.getProjectcyclesProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcyclesProjectmanagerId(),
                            root -> root.join(Employees_.projectcyclesProjectmanagers, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
            if (criteria.getProjectleadershipEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectleadershipEmployeeId(),
                            root -> root.join(Employees_.projectleadershipEmployees, JoinType.LEFT).get(ProjectLeadership_.id)
                        )
                    );
            }
            if (criteria.getProjectsProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectsProjectmanagerId(),
                            root -> root.join(Employees_.projectsProjectmanagers, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getRatingsRaterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingsRaterId(),
                            root -> root.join(Employees_.ratingsRaters, JoinType.LEFT).get(Ratings_.id)
                        )
                    );
            }
            if (criteria.getUserattributesUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributesUserId(),
                            root -> root.join(Employees_.userattributesUsers, JoinType.LEFT).get(UserAttributes_.id)
                        )
                    );
            }
            if (criteria.getUsergoalsUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsergoalsUserId(),
                            root -> root.join(Employees_.usergoalsUsers, JoinType.LEFT).get(UserGoals_.id)
                        )
                    );
            }
            if (criteria.getUserratingsUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsUserId(),
                            root -> root.join(Employees_.userratingsUsers, JoinType.LEFT).get(UserRatings_.id)
                        )
                    );
            }
            if (criteria.getUserratingsReviewmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsReviewmanagerId(),
                            root -> root.join(Employees_.userratingsReviewmanagers, JoinType.LEFT).get(UserRatings_.id)
                        )
                    );
            }
            if (criteria.getUserratingsremarksRaterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsremarksRaterId(),
                            root -> root.join(Employees_.userratingsremarksRaters, JoinType.LEFT).get(UserRatingsRemarks_.id)
                        )
                    );
            }
            if (criteria.getUserrelationsUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserrelationsUserId(),
                            root -> root.join(Employees_.userrelationsUsers, JoinType.LEFT).get(UserRelations_.id)
                        )
                    );
            }
            if (criteria.getUserrelationsRelateduserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserrelationsRelateduserId(),
                            root -> root.join(Employees_.userrelationsRelatedusers, JoinType.LEFT).get(UserRelations_.id)
                        )
                    );
            }
            if (criteria.getWorklogsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorklogsEmployeeId(),
                            root -> root.join(Employees_.worklogsEmployees, JoinType.LEFT).get(WorkLogs_.id)
                        )
                    );
            }
            if (criteria.getZemployeeprojectsEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsEmployeeId(),
                            root -> root.join(Employees_.zemployeeprojectsEmployees, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getZemployeeprojectsAssignorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsAssignorId(),
                            root -> root.join(Employees_.zemployeeprojectsAssignors, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getZemployeeprojectsProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsProjectmanagerId(),
                            root -> root.join(Employees_.zemployeeprojectsProjectmanagers, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
