package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ClaimDetails} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ClaimDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claim-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter amount;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private StringFilter description;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter claimrequestId;

    private LongFilter claimtypeId;

    private Boolean distinct;

    public ClaimDetailsCriteria() {}

    public ClaimDetailsCriteria(ClaimDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.claimrequestId = other.claimrequestId == null ? null : other.claimrequestId.copy();
        this.claimtypeId = other.claimtypeId == null ? null : other.claimtypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimDetailsCriteria copy() {
        return new ClaimDetailsCriteria(this);
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

    public IntegerFilter getAmount() {
        return amount;
    }

    public IntegerFilter amount() {
        if (amount == null) {
            amount = new IntegerFilter();
        }
        return amount;
    }

    public void setAmount(IntegerFilter amount) {
        this.amount = amount;
    }

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
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

    public LongFilter getClaimtypeId() {
        return claimtypeId;
    }

    public LongFilter claimtypeId() {
        if (claimtypeId == null) {
            claimtypeId = new LongFilter();
        }
        return claimtypeId;
    }

    public void setClaimtypeId(LongFilter claimtypeId) {
        this.claimtypeId = claimtypeId;
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
        final ClaimDetailsCriteria that = (ClaimDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(claimrequestId, that.claimrequestId) &&
            Objects.equals(claimtypeId, that.claimtypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, startdate, enddate, description, createdat, updatedat, claimrequestId, claimtypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (claimrequestId != null ? "claimrequestId=" + claimrequestId + ", " : "") +
            (claimtypeId != null ? "claimtypeId=" + claimtypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
