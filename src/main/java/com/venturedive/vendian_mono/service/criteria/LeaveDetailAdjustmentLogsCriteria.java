package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveDetailAdjustmentLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-detail-adjustment-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDetailAdjustmentLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter action;

    private BigDecimalFilter count;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private IntegerFilter version;

    private BigDecimalFilter quotaBeforeAdjustment;

    private BigDecimalFilter quotaAfterAdjustment;

    private StringFilter comment;

    private LongFilter leaveDetailId;

    private LongFilter adjustedById;

    private Boolean distinct;

    public LeaveDetailAdjustmentLogsCriteria() {}

    public LeaveDetailAdjustmentLogsCriteria(LeaveDetailAdjustmentLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.quotaBeforeAdjustment = other.quotaBeforeAdjustment == null ? null : other.quotaBeforeAdjustment.copy();
        this.quotaAfterAdjustment = other.quotaAfterAdjustment == null ? null : other.quotaAfterAdjustment.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.leaveDetailId = other.leaveDetailId == null ? null : other.leaveDetailId.copy();
        this.adjustedById = other.adjustedById == null ? null : other.adjustedById.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveDetailAdjustmentLogsCriteria copy() {
        return new LeaveDetailAdjustmentLogsCriteria(this);
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

    public StringFilter getAction() {
        return action;
    }

    public StringFilter action() {
        if (action == null) {
            action = new StringFilter();
        }
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
    }

    public BigDecimalFilter getCount() {
        return count;
    }

    public BigDecimalFilter count() {
        if (count == null) {
            count = new BigDecimalFilter();
        }
        return count;
    }

    public void setCount(BigDecimalFilter count) {
        this.count = count;
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

    public BigDecimalFilter getQuotaBeforeAdjustment() {
        return quotaBeforeAdjustment;
    }

    public BigDecimalFilter quotaBeforeAdjustment() {
        if (quotaBeforeAdjustment == null) {
            quotaBeforeAdjustment = new BigDecimalFilter();
        }
        return quotaBeforeAdjustment;
    }

    public void setQuotaBeforeAdjustment(BigDecimalFilter quotaBeforeAdjustment) {
        this.quotaBeforeAdjustment = quotaBeforeAdjustment;
    }

    public BigDecimalFilter getQuotaAfterAdjustment() {
        return quotaAfterAdjustment;
    }

    public BigDecimalFilter quotaAfterAdjustment() {
        if (quotaAfterAdjustment == null) {
            quotaAfterAdjustment = new BigDecimalFilter();
        }
        return quotaAfterAdjustment;
    }

    public void setQuotaAfterAdjustment(BigDecimalFilter quotaAfterAdjustment) {
        this.quotaAfterAdjustment = quotaAfterAdjustment;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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

    public LongFilter getAdjustedById() {
        return adjustedById;
    }

    public LongFilter adjustedById() {
        if (adjustedById == null) {
            adjustedById = new LongFilter();
        }
        return adjustedById;
    }

    public void setAdjustedById(LongFilter adjustedById) {
        this.adjustedById = adjustedById;
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
        final LeaveDetailAdjustmentLogsCriteria that = (LeaveDetailAdjustmentLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(action, that.action) &&
            Objects.equals(count, that.count) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(quotaBeforeAdjustment, that.quotaBeforeAdjustment) &&
            Objects.equals(quotaAfterAdjustment, that.quotaAfterAdjustment) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(leaveDetailId, that.leaveDetailId) &&
            Objects.equals(adjustedById, that.adjustedById) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            action,
            count,
            createdAt,
            updatedAt,
            version,
            quotaBeforeAdjustment,
            quotaAfterAdjustment,
            comment,
            leaveDetailId,
            adjustedById,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveDetailAdjustmentLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (count != null ? "count=" + count + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (quotaBeforeAdjustment != null ? "quotaBeforeAdjustment=" + quotaBeforeAdjustment + ", " : "") +
            (quotaAfterAdjustment != null ? "quotaAfterAdjustment=" + quotaAfterAdjustment + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (leaveDetailId != null ? "leaveDetailId=" + leaveDetailId + ", " : "") +
            (adjustedById != null ? "adjustedById=" + adjustedById + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
