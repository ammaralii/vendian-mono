package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PcRaterAttributes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PcRaterAttributesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pc-rater-attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRaterAttributesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter ratingAttributeMappingId;

    private LongFilter ratingOptionId;

    private LongFilter ratingId;

    private Boolean distinct;

    public PcRaterAttributesCriteria() {}

    public PcRaterAttributesCriteria(PcRaterAttributesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.ratingAttributeMappingId = other.ratingAttributeMappingId == null ? null : other.ratingAttributeMappingId.copy();
        this.ratingOptionId = other.ratingOptionId == null ? null : other.ratingOptionId.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PcRaterAttributesCriteria copy() {
        return new PcRaterAttributesCriteria(this);
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

    public LongFilter getRatingAttributeMappingId() {
        return ratingAttributeMappingId;
    }

    public LongFilter ratingAttributeMappingId() {
        if (ratingAttributeMappingId == null) {
            ratingAttributeMappingId = new LongFilter();
        }
        return ratingAttributeMappingId;
    }

    public void setRatingAttributeMappingId(LongFilter ratingAttributeMappingId) {
        this.ratingAttributeMappingId = ratingAttributeMappingId;
    }

    public LongFilter getRatingOptionId() {
        return ratingOptionId;
    }

    public LongFilter ratingOptionId() {
        if (ratingOptionId == null) {
            ratingOptionId = new LongFilter();
        }
        return ratingOptionId;
    }

    public void setRatingOptionId(LongFilter ratingOptionId) {
        this.ratingOptionId = ratingOptionId;
    }

    public LongFilter getRatingId() {
        return ratingId;
    }

    public LongFilter ratingId() {
        if (ratingId == null) {
            ratingId = new LongFilter();
        }
        return ratingId;
    }

    public void setRatingId(LongFilter ratingId) {
        this.ratingId = ratingId;
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
        final PcRaterAttributesCriteria that = (PcRaterAttributesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(ratingAttributeMappingId, that.ratingAttributeMappingId) &&
            Objects.equals(ratingOptionId, that.ratingOptionId) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, deletedAt, version, ratingAttributeMappingId, ratingOptionId, ratingId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRaterAttributesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (ratingAttributeMappingId != null ? "ratingAttributeMappingId=" + ratingAttributeMappingId + ", " : "") +
            (ratingOptionId != null ? "ratingOptionId=" + ratingOptionId + ", " : "") +
            (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
