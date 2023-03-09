package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.UserRatings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.UserRatingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserRatingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rating;

    private StringFilter comment;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter userId;

    private LongFilter reviewManagerId;

    private LongFilter performanceCycleId;

    private Boolean distinct;

    public UserRatingsCriteria() {}

    public UserRatingsCriteria(UserRatingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.reviewManagerId = other.reviewManagerId == null ? null : other.reviewManagerId.copy();
        this.performanceCycleId = other.performanceCycleId == null ? null : other.performanceCycleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserRatingsCriteria copy() {
        return new UserRatingsCriteria(this);
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

    public StringFilter getRating() {
        return rating;
    }

    public StringFilter rating() {
        if (rating == null) {
            rating = new StringFilter();
        }
        return rating;
    }

    public void setRating(StringFilter rating) {
        this.rating = rating;
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

    public LongFilter getReviewManagerId() {
        return reviewManagerId;
    }

    public LongFilter reviewManagerId() {
        if (reviewManagerId == null) {
            reviewManagerId = new LongFilter();
        }
        return reviewManagerId;
    }

    public void setReviewManagerId(LongFilter reviewManagerId) {
        this.reviewManagerId = reviewManagerId;
    }

    public LongFilter getPerformanceCycleId() {
        return performanceCycleId;
    }

    public LongFilter performanceCycleId() {
        if (performanceCycleId == null) {
            performanceCycleId = new LongFilter();
        }
        return performanceCycleId;
    }

    public void setPerformanceCycleId(LongFilter performanceCycleId) {
        this.performanceCycleId = performanceCycleId;
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
        final UserRatingsCriteria that = (UserRatingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(reviewManagerId, that.reviewManagerId) &&
            Objects.equals(performanceCycleId, that.performanceCycleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            rating,
            comment,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            userId,
            reviewManagerId,
            performanceCycleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRatingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (reviewManagerId != null ? "reviewManagerId=" + reviewManagerId + ", " : "") +
            (performanceCycleId != null ? "performanceCycleId=" + performanceCycleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
