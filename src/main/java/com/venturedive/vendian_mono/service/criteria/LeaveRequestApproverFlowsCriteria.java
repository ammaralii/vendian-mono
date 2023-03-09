package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveRequestApproverFlowsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-request-approver-flows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestApproverFlowsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter approvals;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter approverStatusId;

    private LongFilter currentLeaveRequestStatusId;

    private LongFilter nextLeaveRequestStatusId;

    private Boolean distinct;

    public LeaveRequestApproverFlowsCriteria() {}

    public LeaveRequestApproverFlowsCriteria(LeaveRequestApproverFlowsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.approvals = other.approvals == null ? null : other.approvals.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.approverStatusId = other.approverStatusId == null ? null : other.approverStatusId.copy();
        this.currentLeaveRequestStatusId = other.currentLeaveRequestStatusId == null ? null : other.currentLeaveRequestStatusId.copy();
        this.nextLeaveRequestStatusId = other.nextLeaveRequestStatusId == null ? null : other.nextLeaveRequestStatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveRequestApproverFlowsCriteria copy() {
        return new LeaveRequestApproverFlowsCriteria(this);
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

    public StringFilter getApprovals() {
        return approvals;
    }

    public StringFilter approvals() {
        if (approvals == null) {
            approvals = new StringFilter();
        }
        return approvals;
    }

    public void setApprovals(StringFilter approvals) {
        this.approvals = approvals;
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

    public LongFilter getApproverStatusId() {
        return approverStatusId;
    }

    public LongFilter approverStatusId() {
        if (approverStatusId == null) {
            approverStatusId = new LongFilter();
        }
        return approverStatusId;
    }

    public void setApproverStatusId(LongFilter approverStatusId) {
        this.approverStatusId = approverStatusId;
    }

    public LongFilter getCurrentLeaveRequestStatusId() {
        return currentLeaveRequestStatusId;
    }

    public LongFilter currentLeaveRequestStatusId() {
        if (currentLeaveRequestStatusId == null) {
            currentLeaveRequestStatusId = new LongFilter();
        }
        return currentLeaveRequestStatusId;
    }

    public void setCurrentLeaveRequestStatusId(LongFilter currentLeaveRequestStatusId) {
        this.currentLeaveRequestStatusId = currentLeaveRequestStatusId;
    }

    public LongFilter getNextLeaveRequestStatusId() {
        return nextLeaveRequestStatusId;
    }

    public LongFilter nextLeaveRequestStatusId() {
        if (nextLeaveRequestStatusId == null) {
            nextLeaveRequestStatusId = new LongFilter();
        }
        return nextLeaveRequestStatusId;
    }

    public void setNextLeaveRequestStatusId(LongFilter nextLeaveRequestStatusId) {
        this.nextLeaveRequestStatusId = nextLeaveRequestStatusId;
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
        final LeaveRequestApproverFlowsCriteria that = (LeaveRequestApproverFlowsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(approvals, that.approvals) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(approverStatusId, that.approverStatusId) &&
            Objects.equals(currentLeaveRequestStatusId, that.currentLeaveRequestStatusId) &&
            Objects.equals(nextLeaveRequestStatusId, that.nextLeaveRequestStatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            approvals,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            approverStatusId,
            currentLeaveRequestStatusId,
            nextLeaveRequestStatusId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestApproverFlowsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (approvals != null ? "approvals=" + approvals + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (approverStatusId != null ? "approverStatusId=" + approverStatusId + ", " : "") +
            (currentLeaveRequestStatusId != null ? "currentLeaveRequestStatusId=" + currentLeaveRequestStatusId + ", " : "") +
            (nextLeaveRequestStatusId != null ? "nextLeaveRequestStatusId=" + nextLeaveRequestStatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
