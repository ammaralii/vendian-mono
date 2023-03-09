package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypeApprovalRulesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-type-approval-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeApprovalRulesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter leaveApprovalCriteriaId;

    private LongFilter leaveTypeId;

    private Boolean distinct;

    public LeaveTypeApprovalRulesCriteria() {}

    public LeaveTypeApprovalRulesCriteria(LeaveTypeApprovalRulesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveApprovalCriteriaId = other.leaveApprovalCriteriaId == null ? null : other.leaveApprovalCriteriaId.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypeApprovalRulesCriteria copy() {
        return new LeaveTypeApprovalRulesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getEffDate() {
        return effDate;
    }

    public InstantFilter effDate() {
        if (effDate == null) {
            effDate = new InstantFilter();
        }
        return effDate;
    }

    public void setEffDate(InstantFilter effDate) {
        this.effDate = effDate;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            updatedAt = new InstantFilter();
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public InstantFilter deletedAt() {
        if (deletedAt == null) {
            deletedAt = new InstantFilter();
        }
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public IntegerFilter version() {
        if (version == null) {
            version = new IntegerFilter();
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public LongFilter getLeaveApprovalCriteriaId() {
        return leaveApprovalCriteriaId;
    }

    public LongFilter leaveApprovalCriteriaId() {
        if (leaveApprovalCriteriaId == null) {
            leaveApprovalCriteriaId = new LongFilter();
        }
        return leaveApprovalCriteriaId;
    }

    public void setLeaveApprovalCriteriaId(LongFilter leaveApprovalCriteriaId) {
        this.leaveApprovalCriteriaId = leaveApprovalCriteriaId;
    }

    public LongFilter getLeaveTypeId() {
        return leaveTypeId;
    }

    public LongFilter leaveTypeId() {
        if (leaveTypeId == null) {
            leaveTypeId = new LongFilter();
        }
        return leaveTypeId;
    }

    public void setLeaveTypeId(LongFilter leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveTypeApprovalRulesCriteria that = (LeaveTypeApprovalRulesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveApprovalCriteriaId, that.leaveApprovalCriteriaId) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, effDate, createdAt, updatedAt, deletedAt, version, leaveApprovalCriteriaId, leaveTypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeApprovalRulesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveApprovalCriteriaId != null ? "leaveApprovalCriteriaId=" + leaveApprovalCriteriaId + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
