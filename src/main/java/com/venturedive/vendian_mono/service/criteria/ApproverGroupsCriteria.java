package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ApproverGroups} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ApproverGroupsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approver-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverGroupsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter referenceId;

    private StringFilter referenceType;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter approversApprovergroupId;

    private Boolean distinct;

    public ApproverGroupsCriteria() {}

    public ApproverGroupsCriteria(ApproverGroupsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.referenceType = other.referenceType == null ? null : other.referenceType.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.approversApprovergroupId = other.approversApprovergroupId == null ? null : other.approversApprovergroupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApproverGroupsCriteria copy() {
        return new ApproverGroupsCriteria(this);
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

    public LongFilter getApproversApprovergroupId() {
        return approversApprovergroupId;
    }

    public LongFilter approversApprovergroupId() {
        if (approversApprovergroupId == null) {
            approversApprovergroupId = new LongFilter();
        }
        return approversApprovergroupId;
    }

    public void setApproversApprovergroupId(LongFilter approversApprovergroupId) {
        this.approversApprovergroupId = approversApprovergroupId;
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
        final ApproverGroupsCriteria that = (ApproverGroupsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(approversApprovergroupId, that.approversApprovergroupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceId, referenceType, createdAt, updatedAt, deletedAt, version, approversApprovergroupId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverGroupsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
            (referenceType != null ? "referenceType=" + referenceType + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (approversApprovergroupId != null ? "approversApprovergroupId=" + approversApprovergroupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
