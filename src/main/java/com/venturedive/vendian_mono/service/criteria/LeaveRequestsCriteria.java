package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveRequests} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveRequestsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private LocalDateFilter requestStartDate;

    private LocalDateFilter requestEndDate;

    private BooleanFilter isHalfDay;

    private InstantFilter statusDate;

    private StringFilter comments;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter leaveDetailId;

    private LongFilter leaveStatusId;

    private LongFilter parentLeaveRequestId;

    private LongFilter leaverequestapproversLeaverequestId;

    private LongFilter leaverequestsParentleaverequestId;

    private Boolean distinct;

    public LeaveRequestsCriteria() {}

    public LeaveRequestsCriteria(LeaveRequestsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.requestStartDate = other.requestStartDate == null ? null : other.requestStartDate.copy();
        this.requestEndDate = other.requestEndDate == null ? null : other.requestEndDate.copy();
        this.isHalfDay = other.isHalfDay == null ? null : other.isHalfDay.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveDetailId = other.leaveDetailId == null ? null : other.leaveDetailId.copy();
        this.leaveStatusId = other.leaveStatusId == null ? null : other.leaveStatusId.copy();
        this.parentLeaveRequestId = other.parentLeaveRequestId == null ? null : other.parentLeaveRequestId.copy();
        this.leaverequestapproversLeaverequestId =
            other.leaverequestapproversLeaverequestId == null ? null : other.leaverequestapproversLeaverequestId.copy();
        this.leaverequestsParentleaverequestId =
            other.leaverequestsParentleaverequestId == null ? null : other.leaverequestsParentleaverequestId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveRequestsCriteria copy() {
        return new LeaveRequestsCriteria(this);
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

    public LocalDateFilter getRequestStartDate() {
        return requestStartDate;
    }

    public LocalDateFilter requestStartDate() {
        if (requestStartDate == null) {
            requestStartDate = new LocalDateFilter();
        }
        return requestStartDate;
    }

    public void setRequestStartDate(LocalDateFilter requestStartDate) {
        this.requestStartDate = requestStartDate;
    }

    public LocalDateFilter getRequestEndDate() {
        return requestEndDate;
    }

    public LocalDateFilter requestEndDate() {
        if (requestEndDate == null) {
            requestEndDate = new LocalDateFilter();
        }
        return requestEndDate;
    }

    public void setRequestEndDate(LocalDateFilter requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public BooleanFilter getIsHalfDay() {
        return isHalfDay;
    }

    public BooleanFilter isHalfDay() {
        if (isHalfDay == null) {
            isHalfDay = new BooleanFilter();
        }
        return isHalfDay;
    }

    public void setIsHalfDay(BooleanFilter isHalfDay) {
        this.isHalfDay = isHalfDay;
    }

    public InstantFilter getStatusDate() {
        return statusDate;
    }

    public InstantFilter statusDate() {
        if (statusDate == null) {
            statusDate = new InstantFilter();
        }
        return statusDate;
    }

    public void setStatusDate(InstantFilter statusDate) {
        this.statusDate = statusDate;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
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

    public LongFilter getLeaveDetailId() {
        return leaveDetailId;
    }

    public LongFilter leaveDetailId() {
        if (leaveDetailId == null) {
            leaveDetailId = new LongFilter();
        }
        return leaveDetailId;
    }

    public void setLeaveDetailId(LongFilter leaveDetailId) {
        this.leaveDetailId = leaveDetailId;
    }

    public LongFilter getLeaveStatusId() {
        return leaveStatusId;
    }

    public LongFilter leaveStatusId() {
        if (leaveStatusId == null) {
            leaveStatusId = new LongFilter();
        }
        return leaveStatusId;
    }

    public void setLeaveStatusId(LongFilter leaveStatusId) {
        this.leaveStatusId = leaveStatusId;
    }

    public LongFilter getParentLeaveRequestId() {
        return parentLeaveRequestId;
    }

    public LongFilter parentLeaveRequestId() {
        if (parentLeaveRequestId == null) {
            parentLeaveRequestId = new LongFilter();
        }
        return parentLeaveRequestId;
    }

    public void setParentLeaveRequestId(LongFilter parentLeaveRequestId) {
        this.parentLeaveRequestId = parentLeaveRequestId;
    }

    public LongFilter getLeaverequestapproversLeaverequestId() {
        return leaverequestapproversLeaverequestId;
    }

    public LongFilter leaverequestapproversLeaverequestId() {
        if (leaverequestapproversLeaverequestId == null) {
            leaverequestapproversLeaverequestId = new LongFilter();
        }
        return leaverequestapproversLeaverequestId;
    }

    public void setLeaverequestapproversLeaverequestId(LongFilter leaverequestapproversLeaverequestId) {
        this.leaverequestapproversLeaverequestId = leaverequestapproversLeaverequestId;
    }

    public LongFilter getLeaverequestsParentleaverequestId() {
        return leaverequestsParentleaverequestId;
    }

    public LongFilter leaverequestsParentleaverequestId() {
        if (leaverequestsParentleaverequestId == null) {
            leaverequestsParentleaverequestId = new LongFilter();
        }
        return leaverequestsParentleaverequestId;
    }

    public void setLeaverequestsParentleaverequestId(LongFilter leaverequestsParentleaverequestId) {
        this.leaverequestsParentleaverequestId = leaverequestsParentleaverequestId;
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
        final LeaveRequestsCriteria that = (LeaveRequestsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(requestStartDate, that.requestStartDate) &&
            Objects.equals(requestEndDate, that.requestEndDate) &&
            Objects.equals(isHalfDay, that.isHalfDay) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveDetailId, that.leaveDetailId) &&
            Objects.equals(leaveStatusId, that.leaveStatusId) &&
            Objects.equals(parentLeaveRequestId, that.parentLeaveRequestId) &&
            Objects.equals(leaverequestapproversLeaverequestId, that.leaverequestapproversLeaverequestId) &&
            Objects.equals(leaverequestsParentleaverequestId, that.leaverequestsParentleaverequestId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdAt,
            requestStartDate,
            requestEndDate,
            isHalfDay,
            statusDate,
            comments,
            updatedAt,
            deletedAt,
            version,
            leaveDetailId,
            leaveStatusId,
            parentLeaveRequestId,
            leaverequestapproversLeaverequestId,
            leaverequestsParentleaverequestId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (requestStartDate != null ? "requestStartDate=" + requestStartDate + ", " : "") +
            (requestEndDate != null ? "requestEndDate=" + requestEndDate + ", " : "") +
            (isHalfDay != null ? "isHalfDay=" + isHalfDay + ", " : "") +
            (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveDetailId != null ? "leaveDetailId=" + leaveDetailId + ", " : "") +
            (leaveStatusId != null ? "leaveStatusId=" + leaveStatusId + ", " : "") +
            (parentLeaveRequestId != null ? "parentLeaveRequestId=" + parentLeaveRequestId + ", " : "") +
            (leaverequestapproversLeaverequestId != null ? "leaverequestapproversLeaverequestId=" + leaverequestapproversLeaverequestId + ", " : "") +
            (leaverequestsParentleaverequestId != null ? "leaverequestsParentleaverequestId=" + leaverequestsParentleaverequestId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
