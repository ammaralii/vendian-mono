package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypeConfigurations} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypeConfigurationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-type-configurations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeConfigurationsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter noOfLeaves;

    private StringFilter tenureCycle;

    private IntegerFilter to;

    private IntegerFilter from;

    private StringFilter inclusivity;

    private IntegerFilter maxQuota;

    private BooleanFilter isAccured;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaveTypeId;

    private Boolean distinct;

    public LeaveTypeConfigurationsCriteria() {}

    public LeaveTypeConfigurationsCriteria(LeaveTypeConfigurationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noOfLeaves = other.noOfLeaves == null ? null : other.noOfLeaves.copy();
        this.tenureCycle = other.tenureCycle == null ? null : other.tenureCycle.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.inclusivity = other.inclusivity == null ? null : other.inclusivity.copy();
        this.maxQuota = other.maxQuota == null ? null : other.maxQuota.copy();
        this.isAccured = other.isAccured == null ? null : other.isAccured.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypeConfigurationsCriteria copy() {
        return new LeaveTypeConfigurationsCriteria(this);
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

    public BigDecimalFilter getNoOfLeaves() {
        return noOfLeaves;
    }

    public BigDecimalFilter noOfLeaves() {
        if (noOfLeaves == null) {
            noOfLeaves = new BigDecimalFilter();
        }
        return noOfLeaves;
    }

    public void setNoOfLeaves(BigDecimalFilter noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public StringFilter getTenureCycle() {
        return tenureCycle;
    }

    public StringFilter tenureCycle() {
        if (tenureCycle == null) {
            tenureCycle = new StringFilter();
        }
        return tenureCycle;
    }

    public void setTenureCycle(StringFilter tenureCycle) {
        this.tenureCycle = tenureCycle;
    }

    public IntegerFilter getTo() {
        return to;
    }

    public IntegerFilter to() {
        if (to == null) {
            to = new IntegerFilter();
        }
        return to;
    }

    public void setTo(IntegerFilter to) {
        this.to = to;
    }

    public IntegerFilter getFrom() {
        return from;
    }

    public IntegerFilter from() {
        if (from == null) {
            from = new IntegerFilter();
        }
        return from;
    }

    public void setFrom(IntegerFilter from) {
        this.from = from;
    }

    public StringFilter getInclusivity() {
        return inclusivity;
    }

    public StringFilter inclusivity() {
        if (inclusivity == null) {
            inclusivity = new StringFilter();
        }
        return inclusivity;
    }

    public void setInclusivity(StringFilter inclusivity) {
        this.inclusivity = inclusivity;
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

    public BooleanFilter getIsAccured() {
        return isAccured;
    }

    public BooleanFilter isAccured() {
        if (isAccured == null) {
            isAccured = new BooleanFilter();
        }
        return isAccured;
    }

    public void setIsAccured(BooleanFilter isAccured) {
        this.isAccured = isAccured;
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
        final LeaveTypeConfigurationsCriteria that = (LeaveTypeConfigurationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(noOfLeaves, that.noOfLeaves) &&
            Objects.equals(tenureCycle, that.tenureCycle) &&
            Objects.equals(to, that.to) &&
            Objects.equals(from, that.from) &&
            Objects.equals(inclusivity, that.inclusivity) &&
            Objects.equals(maxQuota, that.maxQuota) &&
            Objects.equals(isAccured, that.isAccured) &&
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
        return Objects.hash(
            id,
            noOfLeaves,
            tenureCycle,
            to,
            from,
            inclusivity,
            maxQuota,
            isAccured,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaveTypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeConfigurationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (noOfLeaves != null ? "noOfLeaves=" + noOfLeaves + ", " : "") +
            (tenureCycle != null ? "tenureCycle=" + tenureCycle + ", " : "") +
            (to != null ? "to=" + to + ", " : "") +
            (from != null ? "from=" + from + ", " : "") +
            (inclusivity != null ? "inclusivity=" + inclusivity + ", " : "") +
            (maxQuota != null ? "maxQuota=" + maxQuota + ", " : "") +
            (isAccured != null ? "isAccured=" + isAccured + ", " : "") +
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
