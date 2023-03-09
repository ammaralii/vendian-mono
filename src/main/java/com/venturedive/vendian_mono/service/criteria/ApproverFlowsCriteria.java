package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ApproverFlows} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ApproverFlowsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approver-flows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverFlowsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter referenceType;

    private StringFilter approverStatus;

    private StringFilter approval;

    private StringFilter currentStatus;

    private StringFilter nextStatus;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private IntegerFilter version;

    private Boolean distinct;

    public ApproverFlowsCriteria() {}

    public ApproverFlowsCriteria(ApproverFlowsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceType = other.referenceType == null ? null : other.referenceType.copy();
        this.approverStatus = other.approverStatus == null ? null : other.approverStatus.copy();
        this.approval = other.approval == null ? null : other.approval.copy();
        this.currentStatus = other.currentStatus == null ? null : other.currentStatus.copy();
        this.nextStatus = other.nextStatus == null ? null : other.nextStatus.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApproverFlowsCriteria copy() {
        return new ApproverFlowsCriteria(this);
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

    public StringFilter getReferenceType() {
        return referenceType;
    }

    public StringFilter referenceType() {
        if (referenceType == null) {
            referenceType = new StringFilter();
        }
        return referenceType;
    }

    public void setReferenceType(StringFilter referenceType) {
        this.referenceType = referenceType;
    }

    public StringFilter getApproverStatus() {
        return approverStatus;
    }

    public StringFilter approverStatus() {
        if (approverStatus == null) {
            approverStatus = new StringFilter();
        }
        return approverStatus;
    }

    public void setApproverStatus(StringFilter approverStatus) {
        this.approverStatus = approverStatus;
    }

    public StringFilter getApproval() {
        return approval;
    }

    public StringFilter approval() {
        if (approval == null) {
            approval = new StringFilter();
        }
        return approval;
    }

    public void setApproval(StringFilter approval) {
        this.approval = approval;
    }

    public StringFilter getCurrentStatus() {
        return currentStatus;
    }

    public StringFilter currentStatus() {
        if (currentStatus == null) {
            currentStatus = new StringFilter();
        }
        return currentStatus;
    }

    public void setCurrentStatus(StringFilter currentStatus) {
        this.currentStatus = currentStatus;
    }

    public StringFilter getNextStatus() {
        return nextStatus;
    }

    public StringFilter nextStatus() {
        if (nextStatus == null) {
            nextStatus = new StringFilter();
        }
        return nextStatus;
    }

    public void setNextStatus(StringFilter nextStatus) {
        this.nextStatus = nextStatus;
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
        final ApproverFlowsCriteria that = (ApproverFlowsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(approverStatus, that.approverStatus) &&
            Objects.equals(approval, that.approval) &&
            Objects.equals(currentStatus, that.currentStatus) &&
            Objects.equals(nextStatus, that.nextStatus) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referenceType,
            approverStatus,
            approval,
            currentStatus,
            nextStatus,
            effDate,
            createdAt,
            updatedAt,
            version,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverFlowsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceType != null ? "referenceType=" + referenceType + ", " : "") +
            (approverStatus != null ? "approverStatus=" + approverStatus + ", " : "") +
            (approval != null ? "approval=" + approval + ", " : "") +
            (currentStatus != null ? "currentStatus=" + currentStatus + ", " : "") +
            (nextStatus != null ? "nextStatus=" + nextStatus + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
