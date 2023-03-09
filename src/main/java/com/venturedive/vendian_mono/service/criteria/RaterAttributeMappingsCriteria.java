package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.RaterAttributeMappings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RaterAttributeMappingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rater-attribute-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RaterAttributeMappingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter effdate;

    private InstantFilter enddate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter raterattributeId;

    private LongFilter attributesForId;

    private LongFilter attributesById;

    private LongFilter ratingattributesRaterattributemappingId;

    private Boolean distinct;

    public RaterAttributeMappingsCriteria() {}

    public RaterAttributeMappingsCriteria(RaterAttributeMappingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.effdate = other.effdate == null ? null : other.effdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.raterattributeId = other.raterattributeId == null ? null : other.raterattributeId.copy();
        this.attributesForId = other.attributesForId == null ? null : other.attributesForId.copy();
        this.attributesById = other.attributesById == null ? null : other.attributesById.copy();
        this.ratingattributesRaterattributemappingId =
            other.ratingattributesRaterattributemappingId == null ? null : other.ratingattributesRaterattributemappingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RaterAttributeMappingsCriteria copy() {
        return new RaterAttributeMappingsCriteria(this);
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

    public InstantFilter getEffdate() {
        return effdate;
    }

    public InstantFilter effdate() {
        if (effdate == null) {
            effdate = new InstantFilter();
        }
        return effdate;
    }

    public void setEffdate(InstantFilter effdate) {
        this.effdate = effdate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
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

    public LongFilter getRaterattributeId() {
        return raterattributeId;
    }

    public LongFilter raterattributeId() {
        if (raterattributeId == null) {
            raterattributeId = new LongFilter();
        }
        return raterattributeId;
    }

    public void setRaterattributeId(LongFilter raterattributeId) {
        this.raterattributeId = raterattributeId;
    }

    public LongFilter getAttributesForId() {
        return attributesForId;
    }

    public LongFilter attributesForId() {
        if (attributesForId == null) {
            attributesForId = new LongFilter();
        }
        return attributesForId;
    }

    public void setAttributesForId(LongFilter attributesForId) {
        this.attributesForId = attributesForId;
    }

    public LongFilter getAttributesById() {
        return attributesById;
    }

    public LongFilter attributesById() {
        if (attributesById == null) {
            attributesById = new LongFilter();
        }
        return attributesById;
    }

    public void setAttributesById(LongFilter attributesById) {
        this.attributesById = attributesById;
    }

    public LongFilter getRatingattributesRaterattributemappingId() {
        return ratingattributesRaterattributemappingId;
    }

    public LongFilter ratingattributesRaterattributemappingId() {
        if (ratingattributesRaterattributemappingId == null) {
            ratingattributesRaterattributemappingId = new LongFilter();
        }
        return ratingattributesRaterattributemappingId;
    }

    public void setRatingattributesRaterattributemappingId(LongFilter ratingattributesRaterattributemappingId) {
        this.ratingattributesRaterattributemappingId = ratingattributesRaterattributemappingId;
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
        final RaterAttributeMappingsCriteria that = (RaterAttributeMappingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(effdate, that.effdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(raterattributeId, that.raterattributeId) &&
            Objects.equals(attributesForId, that.attributesForId) &&
            Objects.equals(attributesById, that.attributesById) &&
            Objects.equals(ratingattributesRaterattributemappingId, that.ratingattributesRaterattributemappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            effdate,
            enddate,
            createdat,
            updatedat,
            raterattributeId,
            attributesForId,
            attributesById,
            ratingattributesRaterattributemappingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaterAttributeMappingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (effdate != null ? "effdate=" + effdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (raterattributeId != null ? "raterattributeId=" + raterattributeId + ", " : "") +
            (attributesForId != null ? "attributesForId=" + attributesForId + ", " : "") +
            (attributesById != null ? "attributesById=" + attributesById + ", " : "") +
            (ratingattributesRaterattributemappingId != null ? "ratingattributesRaterattributemappingId=" + ratingattributesRaterattributemappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
