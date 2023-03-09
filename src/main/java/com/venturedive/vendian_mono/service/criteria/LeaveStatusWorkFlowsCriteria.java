package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveStatusWorkFlowsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-status-work-flows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveStatusWorkFlowsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter ifApprovals;

    private BooleanFilter approvalRequired;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter currentStatusId;

    private LongFilter nextStatusId;

    private LongFilter skipToStatusId;

    private Boolean distinct;

    public LeaveStatusWorkFlowsCriteria() {}

    public LeaveStatusWorkFlowsCriteria(LeaveStatusWorkFlowsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ifApprovals = other.ifApprovals == null ? null : other.ifApprovals.copy();
        this.approvalRequired = other.approvalRequired == null ? null : other.approvalRequired.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.currentStatusId = other.currentStatusId == null ? null : other.currentStatusId.copy();
        this.nextStatusId = other.nextStatusId == null ? null : other.nextStatusId.copy();
        this.skipToStatusId = other.skipToStatusId == null ? null : other.skipToStatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveStatusWorkFlowsCriteria copy() {
        return new LeaveStatusWorkFlowsCriteria(this);
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

    public BooleanFilter getIfApprovals() {
        return ifApprovals;
    }

    public BooleanFilter ifApprovals() {
        if (ifApprovals == null) {
            ifApprovals = new BooleanFilter();
        }
        return ifApprovals;
    }

    public void setIfApprovals(BooleanFilter ifApprovals) {
        this.ifApprovals = ifApprovals;
    }

    public BooleanFilter getApprovalRequired() {
        return approvalRequired;
    }

    public BooleanFilter approvalRequired() {
        if (approvalRequired == null) {
            approvalRequired = new BooleanFilter();
        }
        return approvalRequired;
    }

    public void setApprovalRequired(BooleanFilter approvalRequired) {
        this.approvalRequired = approvalRequired;
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

    public LongFilter getCurrentStatusId() {
        return currentStatusId;
    }

    public LongFilter currentStatusId() {
        if (currentStatusId == null) {
            currentStatusId = new LongFilter();
        }
        return currentStatusId;
    }

    public void setCurrentStatusId(LongFilter currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    public LongFilter getNextStatusId() {
        return nextStatusId;
    }

    public LongFilter nextStatusId() {
        if (nextStatusId == null) {
            nextStatusId = new LongFilter();
        }
        return nextStatusId;
    }

    public void setNextStatusId(LongFilter nextStatusId) {
        this.nextStatusId = nextStatusId;
    }

    public LongFilter getSkipToStatusId() {
        return skipToStatusId;
    }

    public LongFilter skipToStatusId() {
        if (skipToStatusId == null) {
            skipToStatusId = new LongFilter();
        }
        return skipToStatusId;
    }

    public void setSkipToStatusId(LongFilter skipToStatusId) {
        this.skipToStatusId = skipToStatusId;
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
        final LeaveStatusWorkFlowsCriteria that = (LeaveStatusWorkFlowsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ifApprovals, that.ifApprovals) &&
            Objects.equals(approvalRequired, that.approvalRequired) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(currentStatusId, that.currentStatusId) &&
            Objects.equals(nextStatusId, that.nextStatusId) &&
            Objects.equals(skipToStatusId, that.skipToStatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            ifApprovals,
            approvalRequired,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            currentStatusId,
            nextStatusId,
            skipToStatusId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveStatusWorkFlowsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ifApprovals != null ? "ifApprovals=" + ifApprovals + ", " : "") +
            (approvalRequired != null ? "approvalRequired=" + approvalRequired + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (currentStatusId != null ? "currentStatusId=" + currentStatusId + ", " : "") +
            (nextStatusId != null ? "nextStatusId=" + nextStatusId + ", " : "") +
            (skipToStatusId != null ? "skipToStatusId=" + skipToStatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
