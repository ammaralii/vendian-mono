package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveEscalationCriterias} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveEscalationCriteriasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-escalation-criterias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveEscalationCriteriasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter priority;

    private IntegerFilter total;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveescalationapproversLeaveescalationcriteriaId;

    private LongFilter leavetypeescalationrulesLeaveescalationcriteriaId;

    private LongFilter userattributeescalationrulesLeaveescalationcriteriaId;

    private Boolean distinct;

    public LeaveEscalationCriteriasCriteria() {}

    public LeaveEscalationCriteriasCriteria(LeaveEscalationCriteriasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveescalationapproversLeaveescalationcriteriaId =
            other.leaveescalationapproversLeaveescalationcriteriaId == null
                ? null
                : other.leaveescalationapproversLeaveescalationcriteriaId.copy();
        this.leavetypeescalationrulesLeaveescalationcriteriaId =
            other.leavetypeescalationrulesLeaveescalationcriteriaId == null
                ? null
                : other.leavetypeescalationrulesLeaveescalationcriteriaId.copy();
        this.userattributeescalationrulesLeaveescalationcriteriaId =
            other.userattributeescalationrulesLeaveescalationcriteriaId == null
                ? null
                : other.userattributeescalationrulesLeaveescalationcriteriaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveEscalationCriteriasCriteria copy() {
        return new LeaveEscalationCriteriasCriteria(this);
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

    public IntegerFilter getTotal() {
        return total;
    }

    public IntegerFilter total() {
        if (total == null) {
            total = new IntegerFilter();
        }
        return total;
    }

    public void setTotal(IntegerFilter total) {
        this.total = total;
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

    public LongFilter getLeaveescalationapproversLeaveescalationcriteriaId() {
        return leaveescalationapproversLeaveescalationcriteriaId;
    }

    public LongFilter leaveescalationapproversLeaveescalationcriteriaId() {
        if (leaveescalationapproversLeaveescalationcriteriaId == null) {
            leaveescalationapproversLeaveescalationcriteriaId = new LongFilter();
        }
        return leaveescalationapproversLeaveescalationcriteriaId;
    }

    public void setLeaveescalationapproversLeaveescalationcriteriaId(LongFilter leaveescalationapproversLeaveescalationcriteriaId) {
        this.leaveescalationapproversLeaveescalationcriteriaId = leaveescalationapproversLeaveescalationcriteriaId;
    }

    public LongFilter getLeavetypeescalationrulesLeaveescalationcriteriaId() {
        return leavetypeescalationrulesLeaveescalationcriteriaId;
    }

    public LongFilter leavetypeescalationrulesLeaveescalationcriteriaId() {
        if (leavetypeescalationrulesLeaveescalationcriteriaId == null) {
            leavetypeescalationrulesLeaveescalationcriteriaId = new LongFilter();
        }
        return leavetypeescalationrulesLeaveescalationcriteriaId;
    }

    public void setLeavetypeescalationrulesLeaveescalationcriteriaId(LongFilter leavetypeescalationrulesLeaveescalationcriteriaId) {
        this.leavetypeescalationrulesLeaveescalationcriteriaId = leavetypeescalationrulesLeaveescalationcriteriaId;
    }

    public LongFilter getUserattributeescalationrulesLeaveescalationcriteriaId() {
        return userattributeescalationrulesLeaveescalationcriteriaId;
    }

    public LongFilter userattributeescalationrulesLeaveescalationcriteriaId() {
        if (userattributeescalationrulesLeaveescalationcriteriaId == null) {
            userattributeescalationrulesLeaveescalationcriteriaId = new LongFilter();
        }
        return userattributeescalationrulesLeaveescalationcriteriaId;
    }

    public void setUserattributeescalationrulesLeaveescalationcriteriaId(LongFilter userattributeescalationrulesLeaveescalationcriteriaId) {
        this.userattributeescalationrulesLeaveescalationcriteriaId = userattributeescalationrulesLeaveescalationcriteriaId;
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
        final LeaveEscalationCriteriasCriteria that = (LeaveEscalationCriteriasCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(total, that.total) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveescalationapproversLeaveescalationcriteriaId, that.leaveescalationapproversLeaveescalationcriteriaId) &&
            Objects.equals(leavetypeescalationrulesLeaveescalationcriteriaId, that.leavetypeescalationrulesLeaveescalationcriteriaId) &&
            Objects.equals(
                userattributeescalationrulesLeaveescalationcriteriaId,
                that.userattributeescalationrulesLeaveescalationcriteriaId
            ) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            priority,
            total,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveescalationapproversLeaveescalationcriteriaId,
            leavetypeescalationrulesLeaveescalationcriteriaId,
            userattributeescalationrulesLeaveescalationcriteriaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveEscalationCriteriasCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveescalationapproversLeaveescalationcriteriaId != null ? "leaveescalationapproversLeaveescalationcriteriaId=" + leaveescalationapproversLeaveescalationcriteriaId + ", " : "") +
            (leavetypeescalationrulesLeaveescalationcriteriaId != null ? "leavetypeescalationrulesLeaveescalationcriteriaId=" + leavetypeescalationrulesLeaveescalationcriteriaId + ", " : "") +
            (userattributeescalationrulesLeaveescalationcriteriaId != null ? "userattributeescalationrulesLeaveescalationcriteriaId=" + userattributeescalationrulesLeaveescalationcriteriaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
