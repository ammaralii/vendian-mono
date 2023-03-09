package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypeEscalationRulesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-type-escalation-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeEscalationRulesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter noOfDays;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveEscalationCriteriaId;

    private LongFilter leaveRequestStatusId;

    private LongFilter leaveTypeId;

    private Boolean distinct;

    public LeaveTypeEscalationRulesCriteria() {}

    public LeaveTypeEscalationRulesCriteria(LeaveTypeEscalationRulesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noOfDays = other.noOfDays == null ? null : other.noOfDays.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveEscalationCriteriaId = other.leaveEscalationCriteriaId == null ? null : other.leaveEscalationCriteriaId.copy();
        this.leaveRequestStatusId = other.leaveRequestStatusId == null ? null : other.leaveRequestStatusId.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypeEscalationRulesCriteria copy() {
        return new LeaveTypeEscalationRulesCriteria(this);
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

    public IntegerFilter getNoOfDays() {
        return noOfDays;
    }

    public IntegerFilter noOfDays() {
        if (noOfDays == null) {
            noOfDays = new IntegerFilter();
        }
        return noOfDays;
    }

    public void setNoOfDays(IntegerFilter noOfDays) {
        this.noOfDays = noOfDays;
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

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
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

    public LongFilter getLeaveEscalationCriteriaId() {
        return leaveEscalationCriteriaId;
    }

    public LongFilter leaveEscalationCriteriaId() {
        if (leaveEscalationCriteriaId == null) {
            leaveEscalationCriteriaId = new LongFilter();
        }
        return leaveEscalationCriteriaId;
    }

    public void setLeaveEscalationCriteriaId(LongFilter leaveEscalationCriteriaId) {
        this.leaveEscalationCriteriaId = leaveEscalationCriteriaId;
    }

    public LongFilter getLeaveRequestStatusId() {
        return leaveRequestStatusId;
    }

    public LongFilter leaveRequestStatusId() {
        if (leaveRequestStatusId == null) {
            leaveRequestStatusId = new LongFilter();
        }
        return leaveRequestStatusId;
    }

    public void setLeaveRequestStatusId(LongFilter leaveRequestStatusId) {
        this.leaveRequestStatusId = leaveRequestStatusId;
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
        final LeaveTypeEscalationRulesCriteria that = (LeaveTypeEscalationRulesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(noOfDays, that.noOfDays) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveEscalationCriteriaId, that.leaveEscalationCriteriaId) &&
            Objects.equals(leaveRequestStatusId, that.leaveRequestStatusId) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            noOfDays,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveEscalationCriteriaId,
            leaveRequestStatusId,
            leaveTypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeEscalationRulesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (noOfDays != null ? "noOfDays=" + noOfDays + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveEscalationCriteriaId != null ? "leaveEscalationCriteriaId=" + leaveEscalationCriteriaId + ", " : "") +
            (leaveRequestStatusId != null ? "leaveRequestStatusId=" + leaveRequestStatusId + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
