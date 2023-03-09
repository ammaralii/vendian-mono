package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveDetails} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter total;

    private BigDecimalFilter used;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveId;

    private LongFilter leaveTypeId;

    private LongFilter leavedetailadjustmentlogsLeavedetailId;

    private LongFilter leaverequestsLeavedetailId;

    private Boolean distinct;

    public LeaveDetailsCriteria() {}

    public LeaveDetailsCriteria(LeaveDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.used = other.used == null ? null : other.used.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveId = other.leaveId == null ? null : other.leaveId.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.leavedetailadjustmentlogsLeavedetailId =
            other.leavedetailadjustmentlogsLeavedetailId == null ? null : other.leavedetailadjustmentlogsLeavedetailId.copy();
        this.leaverequestsLeavedetailId = other.leaverequestsLeavedetailId == null ? null : other.leaverequestsLeavedetailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveDetailsCriteria copy() {
        return new LeaveDetailsCriteria(this);
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

    public BigDecimalFilter getTotal() {
        return total;
    }

    public BigDecimalFilter total() {
        if (total == null) {
            total = new BigDecimalFilter();
        }
        return total;
    }

    public void setTotal(BigDecimalFilter total) {
        this.total = total;
    }

    public BigDecimalFilter getUsed() {
        return used;
    }

    public BigDecimalFilter used() {
        if (used == null) {
            used = new BigDecimalFilter();
        }
        return used;
    }

    public void setUsed(BigDecimalFilter used) {
        this.used = used;
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

    public LongFilter getLeaveId() {
        return leaveId;
    }

    public LongFilter leaveId() {
        if (leaveId == null) {
            leaveId = new LongFilter();
        }
        return leaveId;
    }

    public void setLeaveId(LongFilter leaveId) {
        this.leaveId = leaveId;
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

    public LongFilter getLeavedetailadjustmentlogsLeavedetailId() {
        return leavedetailadjustmentlogsLeavedetailId;
    }

    public LongFilter leavedetailadjustmentlogsLeavedetailId() {
        if (leavedetailadjustmentlogsLeavedetailId == null) {
            leavedetailadjustmentlogsLeavedetailId = new LongFilter();
        }
        return leavedetailadjustmentlogsLeavedetailId;
    }

    public void setLeavedetailadjustmentlogsLeavedetailId(LongFilter leavedetailadjustmentlogsLeavedetailId) {
        this.leavedetailadjustmentlogsLeavedetailId = leavedetailadjustmentlogsLeavedetailId;
    }

    public LongFilter getLeaverequestsLeavedetailId() {
        return leaverequestsLeavedetailId;
    }

    public LongFilter leaverequestsLeavedetailId() {
        if (leaverequestsLeavedetailId == null) {
            leaverequestsLeavedetailId = new LongFilter();
        }
        return leaverequestsLeavedetailId;
    }

    public void setLeaverequestsLeavedetailId(LongFilter leaverequestsLeavedetailId) {
        this.leaverequestsLeavedetailId = leaverequestsLeavedetailId;
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
        final LeaveDetailsCriteria that = (LeaveDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(total, that.total) &&
            Objects.equals(used, that.used) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveId, that.leaveId) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(leavedetailadjustmentlogsLeavedetailId, that.leavedetailadjustmentlogsLeavedetailId) &&
            Objects.equals(leaverequestsLeavedetailId, that.leaverequestsLeavedetailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            total,
            used,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveId,
            leaveTypeId,
            leavedetailadjustmentlogsLeavedetailId,
            leaverequestsLeavedetailId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (used != null ? "used=" + used + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveId != null ? "leaveId=" + leaveId + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (leavedetailadjustmentlogsLeavedetailId != null ? "leavedetailadjustmentlogsLeavedetailId=" + leavedetailadjustmentlogsLeavedetailId + ", " : "") +
            (leaverequestsLeavedetailId != null ? "leaverequestsLeavedetailId=" + leaverequestsLeavedetailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
