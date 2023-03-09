package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveApprovalCriterias} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveApprovalCriteriasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-approval-criterias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApprovalCriteriasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter priority;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveapproversLeaveapprovalcriteriaId;

    private LongFilter leavetypeapprovalrulesLeaveapprovalcriteriaId;

    private LongFilter userattributeapprovalrulesLeaveapprovalcriteriaId;

    private LongFilter userrelationapprovalrulesLeaveapprovalcriteriaId;

    private Boolean distinct;

    public LeaveApprovalCriteriasCriteria() {}

    public LeaveApprovalCriteriasCriteria(LeaveApprovalCriteriasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveapproversLeaveapprovalcriteriaId =
            other.leaveapproversLeaveapprovalcriteriaId == null ? null : other.leaveapproversLeaveapprovalcriteriaId.copy();
        this.leavetypeapprovalrulesLeaveapprovalcriteriaId =
            other.leavetypeapprovalrulesLeaveapprovalcriteriaId == null ? null : other.leavetypeapprovalrulesLeaveapprovalcriteriaId.copy();
        this.userattributeapprovalrulesLeaveapprovalcriteriaId =
            other.userattributeapprovalrulesLeaveapprovalcriteriaId == null
                ? null
                : other.userattributeapprovalrulesLeaveapprovalcriteriaId.copy();
        this.userrelationapprovalrulesLeaveapprovalcriteriaId =
            other.userrelationapprovalrulesLeaveapprovalcriteriaId == null
                ? null
                : other.userrelationapprovalrulesLeaveapprovalcriteriaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveApprovalCriteriasCriteria copy() {
        return new LeaveApprovalCriteriasCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public IntegerFilter priority() {
        if (priority == null) {
            priority = new IntegerFilter();
        }
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
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

    public LongFilter getLeaveapproversLeaveapprovalcriteriaId() {
        return leaveapproversLeaveapprovalcriteriaId;
    }

    public LongFilter leaveapproversLeaveapprovalcriteriaId() {
        if (leaveapproversLeaveapprovalcriteriaId == null) {
            leaveapproversLeaveapprovalcriteriaId = new LongFilter();
        }
        return leaveapproversLeaveapprovalcriteriaId;
    }

    public void setLeaveapproversLeaveapprovalcriteriaId(LongFilter leaveapproversLeaveapprovalcriteriaId) {
        this.leaveapproversLeaveapprovalcriteriaId = leaveapproversLeaveapprovalcriteriaId;
    }

    public LongFilter getLeavetypeapprovalrulesLeaveapprovalcriteriaId() {
        return leavetypeapprovalrulesLeaveapprovalcriteriaId;
    }

    public LongFilter leavetypeapprovalrulesLeaveapprovalcriteriaId() {
        if (leavetypeapprovalrulesLeaveapprovalcriteriaId == null) {
            leavetypeapprovalrulesLeaveapprovalcriteriaId = new LongFilter();
        }
        return leavetypeapprovalrulesLeaveapprovalcriteriaId;
    }

    public void setLeavetypeapprovalrulesLeaveapprovalcriteriaId(LongFilter leavetypeapprovalrulesLeaveapprovalcriteriaId) {
        this.leavetypeapprovalrulesLeaveapprovalcriteriaId = leavetypeapprovalrulesLeaveapprovalcriteriaId;
    }

    public LongFilter getUserattributeapprovalrulesLeaveapprovalcriteriaId() {
        return userattributeapprovalrulesLeaveapprovalcriteriaId;
    }

    public LongFilter userattributeapprovalrulesLeaveapprovalcriteriaId() {
        if (userattributeapprovalrulesLeaveapprovalcriteriaId == null) {
            userattributeapprovalrulesLeaveapprovalcriteriaId = new LongFilter();
        }
        return userattributeapprovalrulesLeaveapprovalcriteriaId;
    }

    public void setUserattributeapprovalrulesLeaveapprovalcriteriaId(LongFilter userattributeapprovalrulesLeaveapprovalcriteriaId) {
        this.userattributeapprovalrulesLeaveapprovalcriteriaId = userattributeapprovalrulesLeaveapprovalcriteriaId;
    }

    public LongFilter getUserrelationapprovalrulesLeaveapprovalcriteriaId() {
        return userrelationapprovalrulesLeaveapprovalcriteriaId;
    }

    public LongFilter userrelationapprovalrulesLeaveapprovalcriteriaId() {
        if (userrelationapprovalrulesLeaveapprovalcriteriaId == null) {
            userrelationapprovalrulesLeaveapprovalcriteriaId = new LongFilter();
        }
        return userrelationapprovalrulesLeaveapprovalcriteriaId;
    }

    public void setUserrelationapprovalrulesLeaveapprovalcriteriaId(LongFilter userrelationapprovalrulesLeaveapprovalcriteriaId) {
        this.userrelationapprovalrulesLeaveapprovalcriteriaId = userrelationapprovalrulesLeaveapprovalcriteriaId;
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
        final LeaveApprovalCriteriasCriteria that = (LeaveApprovalCriteriasCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveapproversLeaveapprovalcriteriaId, that.leaveapproversLeaveapprovalcriteriaId) &&
            Objects.equals(leavetypeapprovalrulesLeaveapprovalcriteriaId, that.leavetypeapprovalrulesLeaveapprovalcriteriaId) &&
            Objects.equals(userattributeapprovalrulesLeaveapprovalcriteriaId, that.userattributeapprovalrulesLeaveapprovalcriteriaId) &&
            Objects.equals(userrelationapprovalrulesLeaveapprovalcriteriaId, that.userrelationapprovalrulesLeaveapprovalcriteriaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            priority,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveapproversLeaveapprovalcriteriaId,
            leavetypeapprovalrulesLeaveapprovalcriteriaId,
            userattributeapprovalrulesLeaveapprovalcriteriaId,
            userrelationapprovalrulesLeaveapprovalcriteriaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApprovalCriteriasCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveapproversLeaveapprovalcriteriaId != null ? "leaveapproversLeaveapprovalcriteriaId=" + leaveapproversLeaveapprovalcriteriaId + ", " : "") +
            (leavetypeapprovalrulesLeaveapprovalcriteriaId != null ? "leavetypeapprovalrulesLeaveapprovalcriteriaId=" + leavetypeapprovalrulesLeaveapprovalcriteriaId + ", " : "") +
            (userattributeapprovalrulesLeaveapprovalcriteriaId != null ? "userattributeapprovalrulesLeaveapprovalcriteriaId=" + userattributeapprovalrulesLeaveapprovalcriteriaId + ", " : "") +
            (userrelationapprovalrulesLeaveapprovalcriteriaId != null ? "userrelationapprovalrulesLeaveapprovalcriteriaId=" + userrelationapprovalrulesLeaveapprovalcriteriaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
