package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.UserRelations} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.UserRelationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-relations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserRelationsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter referenceType;

    private IntegerFilter referenceId;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter userId;

    private LongFilter attributeId;

    private LongFilter relatedUserId;

    private Boolean distinct;

    public UserRelationsCriteria() {}

    public UserRelationsCriteria(UserRelationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceType = other.referenceType == null ? null : other.referenceType.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.attributeId = other.attributeId == null ? null : other.attributeId.copy();
        this.relatedUserId = other.relatedUserId == null ? null : other.relatedUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserRelationsCriteria copy() {
        return new UserRelationsCriteria(this);
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

    public StringFilter getReferenceType() {
        return referenceType;
    }

    public StringFilter referenceType() {
        if (referenceType == null) {
            referenceType = new StringFilter();
        }
        return referenceType;
    }

    public void setReferenceType(StringFilter referenceType) {
        this.referenceType = referenceType;
    }

    public IntegerFilter getReferenceId() {
        return referenceId;
    }

    public IntegerFilter referenceId() {
        if (referenceId == null) {
            referenceId = new IntegerFilter();
        }
        return referenceId;
    }

    public void setReferenceId(IntegerFilter referenceId) {
        this.referenceId = referenceId;
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

    public LongFilter getAttributeId() {
        return attributeId;
    }

    public LongFilter attributeId() {
        if (attributeId == null) {
            attributeId = new LongFilter();
        }
        return attributeId;
    }

    public void setAttributeId(LongFilter attributeId) {
        this.attributeId = attributeId;
    }

    public LongFilter getRelatedUserId() {
        return relatedUserId;
    }

    public LongFilter relatedUserId() {
        if (relatedUserId == null) {
            relatedUserId = new LongFilter();
        }
        return relatedUserId;
    }

    public void setRelatedUserId(LongFilter relatedUserId) {
        this.relatedUserId = relatedUserId;
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
        final UserRelationsCriteria that = (UserRelationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(attributeId, that.attributeId) &&
            Objects.equals(relatedUserId, that.relatedUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referenceType,
            referenceId,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            userId,
            attributeId,
            relatedUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRelationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceType != null ? "referenceType=" + referenceType + ", " : "") +
            (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (attributeId != null ? "attributeId=" + attributeId + ", " : "") +
            (relatedUserId != null ? "relatedUserId=" + relatedUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
