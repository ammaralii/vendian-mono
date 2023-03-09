package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveRequestApprovers.
 */
@Entity
@Table(name = "leave_request_approvers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestApprovers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 17)
    @Column(name = "reference", length = 17, nullable = false)
    private String reference;

    @NotNull
    @Column(name = "jhi_as", nullable = false)
    private String as;

    @Size(max = 65535)
    @Column(name = "comments", length = 65535)
    private String comments;

    @NotNull
    @Size(max = 255)
    @Column(name = "approver_group", length = 255, nullable = false)
    private String approverGroup;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leaveDetail", "leaveStatus", "parentLeaveRequest", "leaverequestapproversLeaverequests", "leaverequestsParentleaverequests",
        },
        allowSetters = true
    )
    private LeaveRequests leaveRequest;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "location",
            "role",
            "manager",
            "leave",
            "department",
            "businessunit",
            "division",
            "competency",
            "employmentstatus",
            "employmenttype",
            "designation",
            "claimrequestviewsEmployees",
            "claimrequestsEmployees",
            "dealresourcesEmployees",
            "employeeaddressesEmployees",
            "employeebirthdaysEmployees",
            "employeecertificatesEmployees",
            "employeecommentsCommenters",
            "employeecommentsEmployees",
            "employeecompensationEmployees",
            "employeecontactsEmployees",
            "employeedetailsEmployees",
            "employeedocumentsEmployees",
            "employeeeducationEmployees",
            "employeeemergencycontactsEmployees",
            "employeefamilyinfoEmployees",
            "employeejobinfoEmployees",
            "employeejobinfoReviewmanagers",
            "employeejobinfoManagers",
            "employeeprofilehistorylogsEmployees",
            "employeeprojectratingsProjectarchitects",
            "employeeprojectratingsProjectmanagers",
            "employeeprojectratingsEmployees",
            "employeeprojectsEmployees",
            "employeeprojectsAssignors",
            "employeeskillsEmployees",
            "employeetalentsEmployees",
            "employeeworksEmployees",
            "employeesManagers",
            "employmenthistoryEmployees",
            "feedbackquestionsEmployees",
            "feedbackrequestsEmployees",
            "feedbackrequestsLinemanagers",
            "feedbackrespondentsEmployees",
            "interviewsEmployees",
            "leavedetailadjustmentlogsAdjustedbies",
            "leaverequestapproversUsers",
            "leaverequestsoldsManagers",
            "leaverequestsoldsEmployees",
            "leavesUsers",
            "notificationsentemaillogsRecipients",
            "pcratingsEmployees",
            "pcratingstrainingsSuccessorfors",
            "performancecycleemployeeratingEmployees",
            "performancecycleemployeeratingManagers",
            "projectcyclesAllowedafterduedatebies",
            "projectcyclesArchitects",
            "projectcyclesProjectmanagers",
            "projectleadershipEmployees",
            "projectsProjectmanagers",
            "ratingsRaters",
            "userattributesUsers",
            "usergoalsUsers",
            "userratingsUsers",
            "userratingsReviewmanagers",
            "userratingsremarksRaters",
            "userrelationsUsers",
            "userrelationsRelatedusers",
            "worklogsEmployees",
            "zemployeeprojectsEmployees",
            "zemployeeprojectsAssignors",
            "zemployeeprojectsProjectmanagers",
        },
        allowSetters = true
    )
    private Employees user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leaverequestapproverflowsApproverstatuses",
            "leaverequestapproverflowsCurrentleaverequeststatuses",
            "leaverequestapproverflowsNextleaverequeststatuses",
            "leaverequestapproversStatuses",
            "leaverequestsLeavestatuses",
            "leavestatusworkflowsCurrentstatuses",
            "leavestatusworkflowsNextstatuses",
            "leavestatusworkflowsSkiptostatuses",
            "leavetypeescalationrulesLeaverequeststatuses",
            "userattributeescalationrulesApproverstatuses",
        },
        allowSetters = true
    )
    private LeaveStatuses status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveRequestApprovers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return this.reference;
    }

    public LeaveRequestApprovers reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAs() {
        return this.as;
    }

    public LeaveRequestApprovers as(String as) {
        this.setAs(as);
        return this;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getComments() {
        return this.comments;
    }

    public LeaveRequestApprovers comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApproverGroup() {
        return this.approverGroup;
    }

    public LeaveRequestApprovers approverGroup(String approverGroup) {
        this.setApproverGroup(approverGroup);
        return this;
    }

    public void setApproverGroup(String approverGroup) {
        this.approverGroup = approverGroup;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LeaveRequestApprovers priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getStatusDate() {
        return this.statusDate;
    }

    public LeaveRequestApprovers statusDate(Instant statusDate) {
        this.setStatusDate(statusDate);
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveRequestApprovers createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveRequestApprovers updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public LeaveRequestApprovers deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveRequestApprovers version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveRequests getLeaveRequest() {
        return this.leaveRequest;
    }

    public void setLeaveRequest(LeaveRequests leaveRequests) {
        this.leaveRequest = leaveRequests;
    }

    public LeaveRequestApprovers leaveRequest(LeaveRequests leaveRequests) {
        this.setLeaveRequest(leaveRequests);
        return this;
    }

    public Employees getUser() {
        return this.user;
    }

    public void setUser(Employees employees) {
        this.user = employees;
    }

    public LeaveRequestApprovers user(Employees employees) {
        this.setUser(employees);
        return this;
    }

    public LeaveStatuses getStatus() {
        return this.status;
    }

    public void setStatus(LeaveStatuses leaveStatuses) {
        this.status = leaveStatuses;
    }

    public LeaveRequestApprovers status(LeaveStatuses leaveStatuses) {
        this.setStatus(leaveStatuses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveRequestApprovers)) {
            return false;
        }
        return id != null && id.equals(((LeaveRequestApprovers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestApprovers{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", as='" + getAs() + "'" +
            ", comments='" + getComments() + "'" +
            ", approverGroup='" + getApproverGroup() + "'" +
            ", priority=" + getPriority() +
            ", statusDate='" + getStatusDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
