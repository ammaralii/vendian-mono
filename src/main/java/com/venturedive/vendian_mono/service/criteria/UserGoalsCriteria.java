package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.UserGoals} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.UserGoalsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-goals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserGoalsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter measurement;

    private IntegerFilter weightage;

    private IntegerFilter progress;

    private StringFilter status;

    private LocalDateFilter dueDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter userId;

    private LongFilter referenceGoalId;

    private LongFilter usergoalraterattributesUsergoalId;

    private Boolean distinct;

    public UserGoalsCriteria() {}

    public UserGoalsCriteria(UserGoalsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.measurement = other.measurement == null ? null : other.measurement.copy();
        this.weightage = other.weightage == null ? null : other.weightage.copy();
        this.progress = other.progress == null ? null : other.progress.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.referenceGoalId = other.referenceGoalId == null ? null : other.referenceGoalId.copy();
        this.usergoalraterattributesUsergoalId =
            other.usergoalraterattributesUsergoalId == null ? null : other.usergoalraterattributesUsergoalId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserGoalsCriteria copy() {
        return new UserGoalsCriteria(this);
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

    public IntegerFilter getProgress() {
        return progress;
    }

    public IntegerFilter progress() {
        if (progress == null) {
            progress = new IntegerFilter();
        }
        return progress;
    }

    public void setProgress(IntegerFilter progress) {
        this.progress = progress;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
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

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getReferenceGoalId() {
        return referenceGoalId;
    }

    public LongFilter referenceGoalId() {
        if (referenceGoalId == null) {
            referenceGoalId = new LongFilter();
        }
        return referenceGoalId;
    }

    public void setReferenceGoalId(LongFilter referenceGoalId) {
        this.referenceGoalId = referenceGoalId;
    }

    public LongFilter getUsergoalraterattributesUsergoalId() {
        return usergoalraterattributesUsergoalId;
    }

    public LongFilter usergoalraterattributesUsergoalId() {
        if (usergoalraterattributesUsergoalId == null) {
            usergoalraterattributesUsergoalId = new LongFilter();
        }
        return usergoalraterattributesUsergoalId;
    }

    public void setUsergoalraterattributesUsergoalId(LongFilter usergoalraterattributesUsergoalId) {
        this.usergoalraterattributesUsergoalId = usergoalraterattributesUsergoalId;
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
        final UserGoalsCriteria that = (UserGoalsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(measurement, that.measurement) &&
            Objects.equals(weightage, that.weightage) &&
            Objects.equals(progress, that.progress) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(referenceGoalId, that.referenceGoalId) &&
            Objects.equals(usergoalraterattributesUsergoalId, that.usergoalraterattributesUsergoalId) &&
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
            weightage,
            progress,
            status,
            dueDate,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            userId,
            referenceGoalId,
            usergoalraterattributesUsergoalId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGoalsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (measurement != null ? "measurement=" + measurement + ", " : "") +
            (weightage != null ? "weightage=" + weightage + ", " : "") +
            (progress != null ? "progress=" + progress + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (referenceGoalId != null ? "referenceGoalId=" + referenceGoalId + ", " : "") +
            (usergoalraterattributesUsergoalId != null ? "usergoalraterattributesUsergoalId=" + usergoalraterattributesUsergoalId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
