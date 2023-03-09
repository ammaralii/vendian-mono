package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.GoalGroupMappings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.GoalGroupMappingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /goal-group-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoalGroupMappingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter weightage;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter goalGroupId;

    private LongFilter goalId;

    private Boolean distinct;

    public GoalGroupMappingsCriteria() {}

    public GoalGroupMappingsCriteria(GoalGroupMappingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.weightage = other.weightage == null ? null : other.weightage.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.goalGroupId = other.goalGroupId == null ? null : other.goalGroupId.copy();
        this.goalId = other.goalId == null ? null : other.goalId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GoalGroupMappingsCriteria copy() {
        return new GoalGroupMappingsCriteria(this);
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

    public IntegerFilter getWeightage() {
        return weightage;
    }

    public IntegerFilter weightage() {
        if (weightage == null) {
            weightage = new IntegerFilter();
        }
        return weightage;
    }

    public void setWeightage(IntegerFilter weightage) {
        this.weightage = weightage;
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

    public LongFilter getGoalGroupId() {
        return goalGroupId;
    }

    public LongFilter goalGroupId() {
        if (goalGroupId == null) {
            goalGroupId = new LongFilter();
        }
        return goalGroupId;
    }

    public void setGoalGroupId(LongFilter goalGroupId) {
        this.goalGroupId = goalGroupId;
    }

    public LongFilter getGoalId() {
        return goalId;
    }

    public LongFilter goalId() {
        if (goalId == null) {
            goalId = new LongFilter();
        }
        return goalId;
    }

    public void setGoalId(LongFilter goalId) {
        this.goalId = goalId;
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
        final GoalGroupMappingsCriteria that = (GoalGroupMappingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(weightage, that.weightage) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(goalGroupId, that.goalGroupId) &&
            Objects.equals(goalId, that.goalId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weightage, createdAt, updatedAt, deletedAt, version, goalGroupId, goalId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalGroupMappingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (weightage != null ? "weightage=" + weightage + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (goalGroupId != null ? "goalGroupId=" + goalGroupId + ", " : "") +
            (goalId != null ? "goalId=" + goalId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
