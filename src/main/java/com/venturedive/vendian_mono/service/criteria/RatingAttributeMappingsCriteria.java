package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.RatingAttributeMappings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RatingAttributeMappingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rating-attribute-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingAttributeMappingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter attributeId;

    private LongFilter ratingAttributeId;

    private LongFilter pcraterattributesRatingattributemappingId;

    private Boolean distinct;

    public RatingAttributeMappingsCriteria() {}

    public RatingAttributeMappingsCriteria(RatingAttributeMappingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.attributeId = other.attributeId == null ? null : other.attributeId.copy();
        this.ratingAttributeId = other.ratingAttributeId == null ? null : other.ratingAttributeId.copy();
        this.pcraterattributesRatingattributemappingId =
            other.pcraterattributesRatingattributemappingId == null ? null : other.pcraterattributesRatingattributemappingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RatingAttributeMappingsCriteria copy() {
        return new RatingAttributeMappingsCriteria(this);
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

    public LongFilter getRatingAttributeId() {
        return ratingAttributeId;
    }

    public LongFilter ratingAttributeId() {
        if (ratingAttributeId == null) {
            ratingAttributeId = new LongFilter();
        }
        return ratingAttributeId;
    }

    public void setRatingAttributeId(LongFilter ratingAttributeId) {
        this.ratingAttributeId = ratingAttributeId;
    }

    public LongFilter getPcraterattributesRatingattributemappingId() {
        return pcraterattributesRatingattributemappingId;
    }

    public LongFilter pcraterattributesRatingattributemappingId() {
        if (pcraterattributesRatingattributemappingId == null) {
            pcraterattributesRatingattributemappingId = new LongFilter();
        }
        return pcraterattributesRatingattributemappingId;
    }

    public void setPcraterattributesRatingattributemappingId(LongFilter pcraterattributesRatingattributemappingId) {
        this.pcraterattributesRatingattributemappingId = pcraterattributesRatingattributemappingId;
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
        final RatingAttributeMappingsCriteria that = (RatingAttributeMappingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(attributeId, that.attributeId) &&
            Objects.equals(ratingAttributeId, that.ratingAttributeId) &&
            Objects.equals(pcraterattributesRatingattributemappingId, that.pcraterattributesRatingattributemappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            attributeId,
            ratingAttributeId,
            pcraterattributesRatingattributemappingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingAttributeMappingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (attributeId != null ? "attributeId=" + attributeId + ", " : "") +
            (ratingAttributeId != null ? "ratingAttributeId=" + ratingAttributeId + ", " : "") +
            (pcraterattributesRatingattributemappingId != null ? "pcraterattributesRatingattributemappingId=" + pcraterattributesRatingattributemappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
