package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter category;

    private StringFilter cycleType;

    private IntegerFilter cycleCount;

    private IntegerFilter maxQuota;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leavedetailsLeavetypeId;

    private LongFilter leavetypeapprovalrulesLeavetypeId;

    private LongFilter leavetypeconfigurationsLeavetypeId;

    private LongFilter leavetypeescalationrulesLeavetypeId;

    private LongFilter leavetyperestrictionsLeavetypeId;

    private Boolean distinct;

    public LeaveTypesCriteria() {}

    public LeaveTypesCriteria(LeaveTypesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.cycleType = other.cycleType == null ? null : other.cycleType.copy();
        this.cycleCount = other.cycleCount == null ? null : other.cycleCount.copy();
        this.maxQuota = other.maxQuota == null ? null : other.maxQuota.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leavedetailsLeavetypeId = other.leavedetailsLeavetypeId == null ? null : other.leavedetailsLeavetypeId.copy();
        this.leavetypeapprovalrulesLeavetypeId =
            other.leavetypeapprovalrulesLeavetypeId == null ? null : other.leavetypeapprovalrulesLeavetypeId.copy();
        this.leavetypeconfigurationsLeavetypeId =
            other.leavetypeconfigurationsLeavetypeId == null ? null : other.leavetypeconfigurationsLeavetypeId.copy();
        this.leavetypeescalationrulesLeavetypeId =
            other.leavetypeescalationrulesLeavetypeId == null ? null : other.leavetypeescalationrulesLeavetypeId.copy();
        this.leavetyperestrictionsLeavetypeId =
            other.leavetyperestrictionsLeavetypeId == null ? null : other.leavetyperestrictionsLeavetypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypesCriteria copy() {
        return new LeaveTypesCriteria(this);
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

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getCycleType() {
        return cycleType;
    }

    public StringFilter cycleType() {
        if (cycleType == null) {
            cycleType = new StringFilter();
        }
        return cycleType;
    }

    public void setCycleType(StringFilter cycleType) {
        this.cycleType = cycleType;
    }

    public IntegerFilter getCycleCount() {
        return cycleCount;
    }

    public IntegerFilter cycleCount() {
        if (cycleCount == null) {
            cycleCount = new IntegerFilter();
        }
        return cycleCount;
    }

    public void setCycleCount(IntegerFilter cycleCount) {
        this.cycleCount = cycleCount;
    }

    public IntegerFilter getMaxQuota() {
        return maxQuota;
    }

    public IntegerFilter maxQuota() {
        if (maxQuota == null) {
            maxQuota = new IntegerFilter();
        }
        return maxQuota;
    }

    public void setMaxQuota(IntegerFilter maxQuota) {
        this.maxQuota = maxQuota;
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

    public LongFilter getLeavedetailsLeavetypeId() {
        return leavedetailsLeavetypeId;
    }

    public LongFilter leavedetailsLeavetypeId() {
        if (leavedetailsLeavetypeId == null) {
            leavedetailsLeavetypeId = new LongFilter();
        }
        return leavedetailsLeavetypeId;
    }

    public void setLeavedetailsLeavetypeId(LongFilter leavedetailsLeavetypeId) {
        this.leavedetailsLeavetypeId = leavedetailsLeavetypeId;
    }

    public LongFilter getLeavetypeapprovalrulesLeavetypeId() {
        return leavetypeapprovalrulesLeavetypeId;
    }

    public LongFilter leavetypeapprovalrulesLeavetypeId() {
        if (leavetypeapprovalrulesLeavetypeId == null) {
            leavetypeapprovalrulesLeavetypeId = new LongFilter();
        }
        return leavetypeapprovalrulesLeavetypeId;
    }

    public void setLeavetypeapprovalrulesLeavetypeId(LongFilter leavetypeapprovalrulesLeavetypeId) {
        this.leavetypeapprovalrulesLeavetypeId = leavetypeapprovalrulesLeavetypeId;
    }

    public LongFilter getLeavetypeconfigurationsLeavetypeId() {
        return leavetypeconfigurationsLeavetypeId;
    }

    public LongFilter leavetypeconfigurationsLeavetypeId() {
        if (leavetypeconfigurationsLeavetypeId == null) {
            leavetypeconfigurationsLeavetypeId = new LongFilter();
        }
        return leavetypeconfigurationsLeavetypeId;
    }

    public void setLeavetypeconfigurationsLeavetypeId(LongFilter leavetypeconfigurationsLeavetypeId) {
        this.leavetypeconfigurationsLeavetypeId = leavetypeconfigurationsLeavetypeId;
    }

    public LongFilter getLeavetypeescalationrulesLeavetypeId() {
        return leavetypeescalationrulesLeavetypeId;
    }

    public LongFilter leavetypeescalationrulesLeavetypeId() {
        if (leavetypeescalationrulesLeavetypeId == null) {
            leavetypeescalationrulesLeavetypeId = new LongFilter();
        }
        return leavetypeescalationrulesLeavetypeId;
    }

    public void setLeavetypeescalationrulesLeavetypeId(LongFilter leavetypeescalationrulesLeavetypeId) {
        this.leavetypeescalationrulesLeavetypeId = leavetypeescalationrulesLeavetypeId;
    }

    public LongFilter getLeavetyperestrictionsLeavetypeId() {
        return leavetyperestrictionsLeavetypeId;
    }

    public LongFilter leavetyperestrictionsLeavetypeId() {
        if (leavetyperestrictionsLeavetypeId == null) {
            leavetyperestrictionsLeavetypeId = new LongFilter();
        }
        return leavetyperestrictionsLeavetypeId;
    }

    public void setLeavetyperestrictionsLeavetypeId(LongFilter leavetyperestrictionsLeavetypeId) {
        this.leavetyperestrictionsLeavetypeId = leavetyperestrictionsLeavetypeId;
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
        final LeaveTypesCriteria that = (LeaveTypesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(category, that.category) &&
            Objects.equals(cycleType, that.cycleType) &&
            Objects.equals(cycleCount, that.cycleCount) &&
            Objects.equals(maxQuota, that.maxQuota) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leavedetailsLeavetypeId, that.leavedetailsLeavetypeId) &&
            Objects.equals(leavetypeapprovalrulesLeavetypeId, that.leavetypeapprovalrulesLeavetypeId) &&
            Objects.equals(leavetypeconfigurationsLeavetypeId, that.leavetypeconfigurationsLeavetypeId) &&
            Objects.equals(leavetypeescalationrulesLeavetypeId, that.leavetypeescalationrulesLeavetypeId) &&
            Objects.equals(leavetyperestrictionsLeavetypeId, that.leavetyperestrictionsLeavetypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            category,
            cycleType,
            cycleCount,
            maxQuota,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leavedetailsLeavetypeId,
            leavetypeapprovalrulesLeavetypeId,
            leavetypeconfigurationsLeavetypeId,
            leavetypeescalationrulesLeavetypeId,
            leavetyperestrictionsLeavetypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (cycleType != null ? "cycleType=" + cycleType + ", " : "") +
            (cycleCount != null ? "cycleCount=" + cycleCount + ", " : "") +
            (maxQuota != null ? "maxQuota=" + maxQuota + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leavedetailsLeavetypeId != null ? "leavedetailsLeavetypeId=" + leavedetailsLeavetypeId + ", " : "") +
            (leavetypeapprovalrulesLeavetypeId != null ? "leavetypeapprovalrulesLeavetypeId=" + leavetypeapprovalrulesLeavetypeId + ", " : "") +
            (leavetypeconfigurationsLeavetypeId != null ? "leavetypeconfigurationsLeavetypeId=" + leavetypeconfigurationsLeavetypeId + ", " : "") +
            (leavetypeescalationrulesLeavetypeId != null ? "leavetypeescalationrulesLeavetypeId=" + leavetypeescalationrulesLeavetypeId + ", " : "") +
            (leavetyperestrictionsLeavetypeId != null ? "leavetyperestrictionsLeavetypeId=" + leavetyperestrictionsLeavetypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
