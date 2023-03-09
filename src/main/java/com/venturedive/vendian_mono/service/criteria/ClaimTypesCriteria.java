package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ClaimTypes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ClaimTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claim-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter claimtype;

    private BooleanFilter daterangerequired;

    private BooleanFilter descriptionrequired;

    private IntegerFilter parentid;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter claimdetailsClaimtypeId;

    private Boolean distinct;

    public ClaimTypesCriteria() {}

    public ClaimTypesCriteria(ClaimTypesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.claimtype = other.claimtype == null ? null : other.claimtype.copy();
        this.daterangerequired = other.daterangerequired == null ? null : other.daterangerequired.copy();
        this.descriptionrequired = other.descriptionrequired == null ? null : other.descriptionrequired.copy();
        this.parentid = other.parentid == null ? null : other.parentid.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.claimdetailsClaimtypeId = other.claimdetailsClaimtypeId == null ? null : other.claimdetailsClaimtypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimTypesCriteria copy() {
        return new ClaimTypesCriteria(this);
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

    public StringFilter getClaimtype() {
        return claimtype;
    }

    public StringFilter claimtype() {
        if (claimtype == null) {
            claimtype = new StringFilter();
        }
        return claimtype;
    }

    public void setClaimtype(StringFilter claimtype) {
        this.claimtype = claimtype;
    }

    public BooleanFilter getDaterangerequired() {
        return daterangerequired;
    }

    public BooleanFilter daterangerequired() {
        if (daterangerequired == null) {
            daterangerequired = new BooleanFilter();
        }
        return daterangerequired;
    }

    public void setDaterangerequired(BooleanFilter daterangerequired) {
        this.daterangerequired = daterangerequired;
    }

    public BooleanFilter getDescriptionrequired() {
        return descriptionrequired;
    }

    public BooleanFilter descriptionrequired() {
        if (descriptionrequired == null) {
            descriptionrequired = new BooleanFilter();
        }
        return descriptionrequired;
    }

    public void setDescriptionrequired(BooleanFilter descriptionrequired) {
        this.descriptionrequired = descriptionrequired;
    }

    public IntegerFilter getParentid() {
        return parentid;
    }

    public IntegerFilter parentid() {
        if (parentid == null) {
            parentid = new IntegerFilter();
        }
        return parentid;
    }

    public void setParentid(IntegerFilter parentid) {
        this.parentid = parentid;
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

    public LongFilter getClaimdetailsClaimtypeId() {
        return claimdetailsClaimtypeId;
    }

    public LongFilter claimdetailsClaimtypeId() {
        if (claimdetailsClaimtypeId == null) {
            claimdetailsClaimtypeId = new LongFilter();
        }
        return claimdetailsClaimtypeId;
    }

    public void setClaimdetailsClaimtypeId(LongFilter claimdetailsClaimtypeId) {
        this.claimdetailsClaimtypeId = claimdetailsClaimtypeId;
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
        final ClaimTypesCriteria that = (ClaimTypesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(claimtype, that.claimtype) &&
            Objects.equals(daterangerequired, that.daterangerequired) &&
            Objects.equals(descriptionrequired, that.descriptionrequired) &&
            Objects.equals(parentid, that.parentid) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(claimdetailsClaimtypeId, that.claimdetailsClaimtypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            claimtype,
            daterangerequired,
            descriptionrequired,
            parentid,
            createdat,
            updatedat,
            claimdetailsClaimtypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimTypesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (claimtype != null ? "claimtype=" + claimtype + ", " : "") +
            (daterangerequired != null ? "daterangerequired=" + daterangerequired + ", " : "") +
            (descriptionrequired != null ? "descriptionrequired=" + descriptionrequired + ", " : "") +
            (parentid != null ? "parentid=" + parentid + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (claimdetailsClaimtypeId != null ? "claimdetailsClaimtypeId=" + claimdetailsClaimtypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
