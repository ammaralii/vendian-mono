package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ClaimImages} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ClaimImagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claim-images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimImagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter images;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter claimrequestId;

    private Boolean distinct;

    public ClaimImagesCriteria() {}

    public ClaimImagesCriteria(ClaimImagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.images = other.images == null ? null : other.images.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.claimrequestId = other.claimrequestId == null ? null : other.claimrequestId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimImagesCriteria copy() {
        return new ClaimImagesCriteria(this);
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

    public StringFilter getImages() {
        return images;
    }

    public StringFilter images() {
        if (images == null) {
            images = new StringFilter();
        }
        return images;
    }

    public void setImages(StringFilter images) {
        this.images = images;
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

    public LongFilter getClaimrequestId() {
        return claimrequestId;
    }

    public LongFilter claimrequestId() {
        if (claimrequestId == null) {
            claimrequestId = new LongFilter();
        }
        return claimrequestId;
    }

    public void setClaimrequestId(LongFilter claimrequestId) {
        this.claimrequestId = claimrequestId;
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
        final ClaimImagesCriteria that = (ClaimImagesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(images, that.images) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(claimrequestId, that.claimrequestId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, images, createdat, updatedat, claimrequestId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimImagesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (images != null ? "images=" + images + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (claimrequestId != null ? "claimrequestId=" + claimrequestId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
