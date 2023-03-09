package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypeRestrictions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypeRestrictionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-type-restrictions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeRestrictionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter restrictionJson;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveTypeId;

    private Boolean distinct;

    public LeaveTypeRestrictionsCriteria() {}

    public LeaveTypeRestrictionsCriteria(LeaveTypeRestrictionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.restrictionJson = other.restrictionJson == null ? null : other.restrictionJson.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypeRestrictionsCriteria copy() {
        return new LeaveTypeRestrictionsCriteria(this);
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

    public StringFilter getRestrictionJson() {
        return restrictionJson;
    }

    public StringFilter restrictionJson() {
        if (restrictionJson == null) {
            restrictionJson = new StringFilter();
        }
        return restrictionJson;
    }

    public void setRestrictionJson(StringFilter restrictionJson) {
        this.restrictionJson = restrictionJson;
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
        final LeaveTypeRestrictionsCriteria that = (LeaveTypeRestrictionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(restrictionJson, that.restrictionJson) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restrictionJson, effDate, createdAt, updatedAt, endDate, version, leaveTypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeRestrictionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (restrictionJson != null ? "restrictionJson=" + restrictionJson + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
