package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveStatusWorkFlows.
 */
@Entity
@Table(name = "leave_status_work_flows")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveStatusWorkFlows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "if_approvals", nullable = false)
    private Boolean ifApprovals;

    @NotNull
    @Column(name = "approval_required", nullable = false)
    private Boolean approvalRequired;

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
    private LeaveStatuses currentStatus;

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
    private LeaveStatuses nextStatus;

    @ManyToOne
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
    private LeaveStatuses skipToStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveStatusWorkFlows id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIfApprovals() {
        return this.ifApprovals;
    }

    public LeaveStatusWorkFlows ifApprovals(Boolean ifApprovals) {
        this.setIfApprovals(ifApprovals);
        return this;
    }

    public void setIfApprovals(Boolean ifApprovals) {
        this.ifApprovals = ifApprovals;
    }

    public Boolean getApprovalRequired() {
        return this.approvalRequired;
    }

    public LeaveStatusWorkFlows approvalRequired(Boolean approvalRequired) {
        this.setApprovalRequired(approvalRequired);
        return this;
    }

    public void setApprovalRequired(Boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveStatusWorkFlows createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveStatusWorkFlows updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public LeaveStatusWorkFlows deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveStatusWorkFlows version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveStatuses getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(LeaveStatuses leaveStatuses) {
        this.currentStatus = leaveStatuses;
    }

    public LeaveStatusWorkFlows currentStatus(LeaveStatuses leaveStatuses) {
        this.setCurrentStatus(leaveStatuses);
        return this;
    }

    public LeaveStatuses getNextStatus() {
        return this.nextStatus;
    }

    public void setNextStatus(LeaveStatuses leaveStatuses) {
        this.nextStatus = leaveStatuses;
    }

    public LeaveStatusWorkFlows nextStatus(LeaveStatuses leaveStatuses) {
        this.setNextStatus(leaveStatuses);
        return this;
    }

    public LeaveStatuses getSkipToStatus() {
        return this.skipToStatus;
    }

    public void setSkipToStatus(LeaveStatuses leaveStatuses) {
        this.skipToStatus = leaveStatuses;
    }

    public LeaveStatusWorkFlows skipToStatus(LeaveStatuses leaveStatuses) {
        this.setSkipToStatus(leaveStatuses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveStatusWorkFlows)) {
            return false;
        }
        return id != null && id.equals(((LeaveStatusWorkFlows) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveStatusWorkFlows{" +
            "id=" + getId() +
            ", ifApprovals='" + getIfApprovals() + "'" +
            ", approvalRequired='" + getApprovalRequired() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
