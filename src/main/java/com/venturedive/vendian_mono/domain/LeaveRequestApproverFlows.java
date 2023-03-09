package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveRequestApproverFlows.
 */
@Entity
@Table(name = "leave_request_approver_flows")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestApproverFlows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "approvals", length = 3, nullable = false)
    private String approvals;

    @NotNull
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

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
    private LeaveStatuses approverStatus;

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
    private LeaveStatuses currentLeaveRequestStatus;

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
    private LeaveStatuses nextLeaveRequestStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveRequestApproverFlows id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApprovals() {
        return this.approvals;
    }

    public LeaveRequestApproverFlows approvals(String approvals) {
        this.setApprovals(approvals);
        return this;
    }

    public void setApprovals(String approvals) {
        this.approvals = approvals;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveRequestApproverFlows effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveRequestApproverFlows createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveRequestApproverFlows updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveRequestApproverFlows endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveRequestApproverFlows version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveStatuses getApproverStatus() {
        return this.approverStatus;
    }

    public void setApproverStatus(LeaveStatuses leaveStatuses) {
        this.approverStatus = leaveStatuses;
    }

    public LeaveRequestApproverFlows approverStatus(LeaveStatuses leaveStatuses) {
        this.setApproverStatus(leaveStatuses);
        return this;
    }

    public LeaveStatuses getCurrentLeaveRequestStatus() {
        return this.currentLeaveRequestStatus;
    }

    public void setCurrentLeaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.currentLeaveRequestStatus = leaveStatuses;
    }

    public LeaveRequestApproverFlows currentLeaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.setCurrentLeaveRequestStatus(leaveStatuses);
        return this;
    }

    public LeaveStatuses getNextLeaveRequestStatus() {
        return this.nextLeaveRequestStatus;
    }

    public void setNextLeaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.nextLeaveRequestStatus = leaveStatuses;
    }

    public LeaveRequestApproverFlows nextLeaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.setNextLeaveRequestStatus(leaveStatuses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveRequestApproverFlows)) {
            return false;
        }
        return id != null && id.equals(((LeaveRequestApproverFlows) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestApproverFlows{" +
            "id=" + getId() +
            ", approvals='" + getApprovals() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
