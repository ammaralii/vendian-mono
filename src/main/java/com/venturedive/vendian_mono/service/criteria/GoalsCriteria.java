package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Goals} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.GoalsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /goals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoalsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter measurement;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter goalgroupmappingsGoalId;

    private LongFilter usergoalsReferencegoalId;

    private Boolean distinct;

    public GoalsCriteria() {}

    public GoalsCriteria(GoalsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.measurement = other.measurement == null ? null : other.measurement.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.goalgroupmappingsGoalId = other.goalgroupmappingsGoalId == null ? null : other.goalgroupmappingsGoalId.copy();
        this.usergoalsReferencegoalId = other.usergoalsReferencegoalId == null ? null : other.usergoalsReferencegoalId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GoalsCriteria copy() {
        return new GoalsCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMeasurement() {
        return measurement;
    }

    public StringFilter measurement() {
        if (measurement == null) {
            measurement = new StringFilter();
        }
        return measurement;
    }

    public void setMeasurement(StringFilter measurement) {
        this.measurement = measurement;
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

    public LongFilter getGoalgroupmappingsGoalId() {
        return goalgroupmappingsGoalId;
    }

    public LongFilter goalgroupmappingsGoalId() {
        if (goalgroupmappingsGoalId == null) {
            goalgroupmappingsGoalId = new LongFilter();
        }
        return goalgroupmappingsGoalId;
    }

    public void setGoalgroupmappingsGoalId(LongFilter goalgroupmappingsGoalId) {
        this.goalgroupmappingsGoalId = goalgroupmappingsGoalId;
    }

    public LongFilter getUsergoalsReferencegoalId() {
        return usergoalsReferencegoalId;
    }

    public LongFilter usergoalsReferencegoalId() {
        if (usergoalsReferencegoalId == null) {
            usergoalsReferencegoalId = new LongFilter();
        }
        return usergoalsReferencegoalId;
    }

    public void setUsergoalsReferencegoalId(LongFilter usergoalsReferencegoalId) {
        this.usergoalsReferencegoalId = usergoalsReferencegoalId;
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
        final GoalsCriteria that = (GoalsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(measurement, that.measurement) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(goalgroupmappingsGoalId, that.goalgroupmappingsGoalId) &&
            Objects.equals(usergoalsReferencegoalId, that.usergoalsReferencegoalId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            description,
            measurement,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            goalgroupmappingsGoalId,
            usergoalsReferencegoalId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (measurement != null ? "measurement=" + measurement + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (goalgroupmappingsGoalId != null ? "goalgroupmappingsGoalId=" + goalgroupmappingsGoalId + ", " : "") +
            (usergoalsReferencegoalId != null ? "usergoalsReferencegoalId=" + usergoalsReferencegoalId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
