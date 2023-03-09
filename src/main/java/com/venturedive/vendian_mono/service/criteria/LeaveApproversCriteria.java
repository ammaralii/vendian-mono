package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveApprovers} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveApproversResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-approvers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApproversCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter source;

    private IntegerFilter minApprovals;

    private IntegerFilter priority;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveApprovalCriteriaId;

    private LongFilter attributeId;

    private Boolean distinct;

    public LeaveApproversCriteria() {}

    public LeaveApproversCriteria(LeaveApproversCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.minApprovals = other.minApprovals == null ? null : other.minApprovals.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveApprovalCriteriaId = other.leaveApprovalCriteriaId == null ? null : other.leaveApprovalCriteriaId.copy();
        this.attributeId = other.attributeId == null ? null : other.attributeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveApproversCriteria copy() {
        return new LeaveApproversCriteria(this);
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

    public StringFilter getSource() {
        return source;
    }

    public StringFilter source() {
        if (source == null) {
            source = new StringFilter();
        }
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public IntegerFilter getMinApprovals() {
        return minApprovals;
    }

    public IntegerFilter minApprovals() {
        if (minApprovals == null) {
            minApprovals = new IntegerFilter();
        }
        return minApprovals;
    }

    public void setMinApprovals(IntegerFilter minApprovals) {
        this.minApprovals = minApprovals;
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

    public LongFilter getAttributeId() {
        return attributeId;
    }

    public LongFilter attributeId() {
        if (attributeId == null) {
            attributeId = new LongFilter();
        }
        return attributeId;
    }

    public void setAttributeId(LongFilter attributeId) {
        this.attributeId = attributeId;
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
        final LeaveApproversCriteria that = (LeaveApproversCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(source, that.source) &&
            Objects.equals(minApprovals, that.minApprovals) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveApprovalCriteriaId, that.leaveApprovalCriteriaId) &&
            Objects.equals(attributeId, that.attributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            source,
            minApprovals,
            priority,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveApprovalCriteriaId,
            attributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApproversCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (source != null ? "source=" + source + ", " : "") +
            (minApprovals != null ? "minApprovals=" + minApprovals + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveApprovalCriteriaId != null ? "leaveApprovalCriteriaId=" + leaveApprovalCriteriaId + ", " : "") +
            (attributeId != null ? "attributeId=" + attributeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
