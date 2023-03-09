package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypeEscalationRules.
 */
@Entity
@Table(name = "leave_type_escalation_rules")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeEscalationRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "no_of_days")
    private Integer noOfDays;

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
            "leaveescalationapproversLeaveescalationcriteria",
            "leavetypeescalationrulesLeaveescalationcriteria",
            "userattributeescalationrulesLeaveescalationcriteria",
        },
        allowSetters = true
    )
    private LeaveEscalationCriterias leaveEscalationCriteria;

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
    private LeaveStatuses leaveRequestStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leavedetailsLeavetypes",
            "leavetypeapprovalrulesLeavetypes",
            "leavetypeconfigurationsLeavetypes",
            "leavetypeescalationrulesLeavetypes",
            "leavetyperestrictionsLeavetypes",
        },
        allowSetters = true
    )
    private LeaveTypes leaveType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveTypeEscalationRules id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfDays() {
        return this.noOfDays;
    }

    public LeaveTypeEscalationRules noOfDays(Integer noOfDays) {
        this.setNoOfDays(noOfDays);
        return this;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveTypeEscalationRules effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveTypeEscalationRules createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveTypeEscalationRules updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveTypeEscalationRules endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveTypeEscalationRules version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveEscalationCriterias getLeaveEscalationCriteria() {
        return this.leaveEscalationCriteria;
    }

    public void setLeaveEscalationCriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.leaveEscalationCriteria = leaveEscalationCriterias;
    }

    public LeaveTypeEscalationRules leaveEscalationCriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.setLeaveEscalationCriteria(leaveEscalationCriterias);
        return this;
    }

    public LeaveStatuses getLeaveRequestStatus() {
        return this.leaveRequestStatus;
    }

    public void setLeaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.leaveRequestStatus = leaveStatuses;
    }

    public LeaveTypeEscalationRules leaveRequestStatus(LeaveStatuses leaveStatuses) {
        this.setLeaveRequestStatus(leaveStatuses);
        return this;
    }

    public LeaveTypes getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(LeaveTypes leaveTypes) {
        this.leaveType = leaveTypes;
    }

    public LeaveTypeEscalationRules leaveType(LeaveTypes leaveTypes) {
        this.setLeaveType(leaveTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypeEscalationRules)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypeEscalationRules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeEscalationRules{" +
            "id=" + getId() +
            ", noOfDays=" + getNoOfDays() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
