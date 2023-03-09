package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveDetailAdjustmentLogs.
 */
@Entity
@Table(name = "leave_detail_adjustment_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDetailAdjustmentLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "action", length = 255)
    private String action;

    @Column(name = "count", precision = 21, scale = 2)
    private BigDecimal count;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "version")
    private Integer version;

    @Column(name = "quota_before_adjustment", precision = 21, scale = 2)
    private BigDecimal quotaBeforeAdjustment;

    @Column(name = "quota_after_adjustment", precision = 21, scale = 2)
    private BigDecimal quotaAfterAdjustment;

    @Size(max = 65535)
    @Column(name = "comment", length = 65535)
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "leave", "leaveType", "leavedetailadjustmentlogsLeavedetails", "leaverequestsLeavedetails" },
        allowSetters = true
    )
    private LeaveDetails leaveDetail;

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
    private Employees adjustedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveDetailAdjustmentLogs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public LeaveDetailAdjustmentLogs action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BigDecimal getCount() {
        return this.count;
    }

    public LeaveDetailAdjustmentLogs count(BigDecimal count) {
        this.setCount(count);
        return this;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveDetailAdjustmentLogs createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveDetailAdjustmentLogs updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveDetailAdjustmentLogs version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getQuotaBeforeAdjustment() {
        return this.quotaBeforeAdjustment;
    }

    public LeaveDetailAdjustmentLogs quotaBeforeAdjustment(BigDecimal quotaBeforeAdjustment) {
        this.setQuotaBeforeAdjustment(quotaBeforeAdjustment);
        return this;
    }

    public void setQuotaBeforeAdjustment(BigDecimal quotaBeforeAdjustment) {
        this.quotaBeforeAdjustment = quotaBeforeAdjustment;
    }

    public BigDecimal getQuotaAfterAdjustment() {
        return this.quotaAfterAdjustment;
    }

    public LeaveDetailAdjustmentLogs quotaAfterAdjustment(BigDecimal quotaAfterAdjustment) {
        this.setQuotaAfterAdjustment(quotaAfterAdjustment);
        return this;
    }

    public void setQuotaAfterAdjustment(BigDecimal quotaAfterAdjustment) {
        this.quotaAfterAdjustment = quotaAfterAdjustment;
    }

    public String getComment() {
        return this.comment;
    }

    public LeaveDetailAdjustmentLogs comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LeaveDetails getLeaveDetail() {
        return this.leaveDetail;
    }

    public void setLeaveDetail(LeaveDetails leaveDetails) {
        this.leaveDetail = leaveDetails;
    }

    public LeaveDetailAdjustmentLogs leaveDetail(LeaveDetails leaveDetails) {
        this.setLeaveDetail(leaveDetails);
        return this;
    }

    public Employees getAdjustedBy() {
        return this.adjustedBy;
    }

    public void setAdjustedBy(Employees employees) {
        this.adjustedBy = employees;
    }

    public LeaveDetailAdjustmentLogs adjustedBy(Employees employees) {
        this.setAdjustedBy(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveDetailAdjustmentLogs)) {
            return false;
        }
        return id != null && id.equals(((LeaveDetailAdjustmentLogs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveDetailAdjustmentLogs{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", count=" + getCount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", version=" + getVersion() +
            ", quotaBeforeAdjustment=" + getQuotaBeforeAdjustment() +
            ", quotaAfterAdjustment=" + getQuotaAfterAdjustment() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
