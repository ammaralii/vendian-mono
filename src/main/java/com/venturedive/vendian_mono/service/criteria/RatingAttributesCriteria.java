package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.RatingAttributes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RatingAttributesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rating-attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingAttributesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter ratingId;

    private LongFilter raterattributemappingId;

    private Boolean distinct;

    public RatingAttributesCriteria() {}

    public RatingAttributesCriteria(RatingAttributesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.raterattributemappingId = other.raterattributemappingId == null ? null : other.raterattributemappingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RatingAttributesCriteria copy() {
        return new RatingAttributesCriteria(this);
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

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
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

    public LongFilter getRaterattributemappingId() {
        return raterattributemappingId;
    }

    public LongFilter raterattributemappingId() {
        if (raterattributemappingId == null) {
            raterattributemappingId = new LongFilter();
        }
        return raterattributemappingId;
    }

    public void setRaterattributemappingId(LongFilter raterattributemappingId) {
        this.raterattributemappingId = raterattributemappingId;
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
        final RatingAttributesCriteria that = (RatingAttributesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(raterattributemappingId, that.raterattributemappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, ratingId, raterattributemappingId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingAttributesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
            (raterattributemappingId != null ? "raterattributemappingId=" + raterattributemappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
