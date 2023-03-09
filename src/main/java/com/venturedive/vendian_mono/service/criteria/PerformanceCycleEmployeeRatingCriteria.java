package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PerformanceCycleEmployeeRatingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-cycle-employee-ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceCycleEmployeeRatingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter performancecycleId;

    private LongFilter employeeId;

    private LongFilter managerId;

    private LongFilter pcratingsPcemployeeratingId;

    private LongFilter userratingsremarksPcemployeeratingId;

    private Boolean distinct;

    public PerformanceCycleEmployeeRatingCriteria() {}

    public PerformanceCycleEmployeeRatingCriteria(PerformanceCycleEmployeeRatingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.performancecycleId = other.performancecycleId == null ? null : other.performancecycleId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.pcratingsPcemployeeratingId = other.pcratingsPcemployeeratingId == null ? null : other.pcratingsPcemployeeratingId.copy();
        this.userratingsremarksPcemployeeratingId =
            other.userratingsremarksPcemployeeratingId == null ? null : other.userratingsremarksPcemployeeratingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceCycleEmployeeRatingCriteria copy() {
        return new PerformanceCycleEmployeeRatingCriteria(this);
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

    public LongFilter getPerformancecycleId() {
        return performancecycleId;
    }

    public LongFilter performancecycleId() {
        if (performancecycleId == null) {
            performancecycleId = new LongFilter();
        }
        return performancecycleId;
    }

    public void setPerformancecycleId(LongFilter performancecycleId) {
        this.performancecycleId = performancecycleId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getPcratingsPcemployeeratingId() {
        return pcratingsPcemployeeratingId;
    }

    public LongFilter pcratingsPcemployeeratingId() {
        if (pcratingsPcemployeeratingId == null) {
            pcratingsPcemployeeratingId = new LongFilter();
        }
        return pcratingsPcemployeeratingId;
    }

    public void setPcratingsPcemployeeratingId(LongFilter pcratingsPcemployeeratingId) {
        this.pcratingsPcemployeeratingId = pcratingsPcemployeeratingId;
    }

    public LongFilter getUserratingsremarksPcemployeeratingId() {
        return userratingsremarksPcemployeeratingId;
    }

    public LongFilter userratingsremarksPcemployeeratingId() {
        if (userratingsremarksPcemployeeratingId == null) {
            userratingsremarksPcemployeeratingId = new LongFilter();
        }
        return userratingsremarksPcemployeeratingId;
    }

    public void setUserratingsremarksPcemployeeratingId(LongFilter userratingsremarksPcemployeeratingId) {
        this.userratingsremarksPcemployeeratingId = userratingsremarksPcemployeeratingId;
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
        final PerformanceCycleEmployeeRatingCriteria that = (PerformanceCycleEmployeeRatingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(performancecycleId, that.performancecycleId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(pcratingsPcemployeeratingId, that.pcratingsPcemployeeratingId) &&
            Objects.equals(userratingsremarksPcemployeeratingId, that.userratingsremarksPcemployeeratingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            performancecycleId,
            employeeId,
            managerId,
            pcratingsPcemployeeratingId,
            userratingsremarksPcemployeeratingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceCycleEmployeeRatingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (performancecycleId != null ? "performancecycleId=" + performancecycleId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (pcratingsPcemployeeratingId != null ? "pcratingsPcemployeeratingId=" + pcratingsPcemployeeratingId + ", " : "") +
            (userratingsremarksPcemployeeratingId != null ? "userratingsremarksPcemployeeratingId=" + userratingsremarksPcemployeeratingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
