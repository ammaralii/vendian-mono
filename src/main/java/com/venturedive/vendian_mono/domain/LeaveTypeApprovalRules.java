package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypeApprovalRules.
 */
@Entity
@Table(name = "leave_type_approval_rules")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeApprovalRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

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
            "leaveapproversLeaveapprovalcriteria",
            "leavetypeapprovalrulesLeaveapprovalcriteria",
            "userattributeapprovalrulesLeaveapprovalcriteria",
            "userrelationapprovalrulesLeaveapprovalcriteria",
        },
        allowSetters = true
    )
    private LeaveApprovalCriterias leaveApprovalCriteria;

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

    public LeaveTypeApprovalRules id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveTypeApprovalRules effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveTypeApprovalRules createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveTypeApprovalRules updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public LeaveTypeApprovalRules deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveTypeApprovalRules version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveApprovalCriterias getLeaveApprovalCriteria() {
        return this.leaveApprovalCriteria;
    }

    public void setLeaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.leaveApprovalCriteria = leaveApprovalCriterias;
    }

    public LeaveTypeApprovalRules leaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return this;
    }

    public LeaveTypes getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(LeaveTypes leaveTypes) {
        this.leaveType = leaveTypes;
    }

    public LeaveTypeApprovalRules leaveType(LeaveTypes leaveTypes) {
        this.setLeaveType(leaveTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypeApprovalRules)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypeApprovalRules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeApprovalRules{" +
            "id=" + getId() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
