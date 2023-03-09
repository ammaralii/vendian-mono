package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Ratings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RatingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rateeid;

    private StringFilter rateetype;

    private InstantFilter duedate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter raterId;

    private LongFilter ratingattributesRatingId;

    private LongFilter performancecycleId;

    private LongFilter projectcycleId;

    private Boolean distinct;

    public RatingsCriteria() {}

    public RatingsCriteria(RatingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rateeid = other.rateeid == null ? null : other.rateeid.copy();
        this.rateetype = other.rateetype == null ? null : other.rateetype.copy();
        this.duedate = other.duedate == null ? null : other.duedate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.raterId = other.raterId == null ? null : other.raterId.copy();
        this.ratingattributesRatingId = other.ratingattributesRatingId == null ? null : other.ratingattributesRatingId.copy();
        this.performancecycleId = other.performancecycleId == null ? null : other.performancecycleId.copy();
        this.projectcycleId = other.projectcycleId == null ? null : other.projectcycleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RatingsCriteria copy() {
        return new RatingsCriteria(this);
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

    public IntegerFilter getRateeid() {
        return rateeid;
    }

    public IntegerFilter rateeid() {
        if (rateeid == null) {
            rateeid = new IntegerFilter();
        }
        return rateeid;
    }

    public void setRateeid(IntegerFilter rateeid) {
        this.rateeid = rateeid;
    }

    public StringFilter getRateetype() {
        return rateetype;
    }

    public StringFilter rateetype() {
        if (rateetype == null) {
            rateetype = new StringFilter();
        }
        return rateetype;
    }

    public void setRateetype(StringFilter rateetype) {
        this.rateetype = rateetype;
    }

    public InstantFilter getDuedate() {
        return duedate;
    }

    public InstantFilter duedate() {
        if (duedate == null) {
            duedate = new InstantFilter();
        }
        return duedate;
    }

    public void setDuedate(InstantFilter duedate) {
        this.duedate = duedate;
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

    public InstantFilter getDeletedat() {
        return deletedat;
    }

    public InstantFilter deletedat() {
        if (deletedat == null) {
            deletedat = new InstantFilter();
        }
        return deletedat;
    }

    public void setDeletedat(InstantFilter deletedat) {
        this.deletedat = deletedat;
    }

    public LongFilter getRaterId() {
        return raterId;
    }

    public LongFilter raterId() {
        if (raterId == null) {
            raterId = new LongFilter();
        }
        return raterId;
    }

    public void setRaterId(LongFilter raterId) {
        this.raterId = raterId;
    }

    public LongFilter getRatingattributesRatingId() {
        return ratingattributesRatingId;
    }

    public LongFilter ratingattributesRatingId() {
        if (ratingattributesRatingId == null) {
            ratingattributesRatingId = new LongFilter();
        }
        return ratingattributesRatingId;
    }

    public void setRatingattributesRatingId(LongFilter ratingattributesRatingId) {
        this.ratingattributesRatingId = ratingattributesRatingId;
    }

    public LongFilter getPerformancecycleId() {
        return performancecycleId;
    }

    public LongFilter performancecycleId() {
        if (performancecycleId == null) {
            performancecycleId = new LongFilter();
        }
        return performancecycleId;
    }

    public void setPerformancecycleId(LongFilter performancecycleId) {
        this.performancecycleId = performancecycleId;
    }

    public LongFilter getProjectcycleId() {
        return projectcycleId;
    }

    public LongFilter projectcycleId() {
        if (projectcycleId == null) {
            projectcycleId = new LongFilter();
        }
        return projectcycleId;
    }

    public void setProjectcycleId(LongFilter projectcycleId) {
        this.projectcycleId = projectcycleId;
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
        final RatingsCriteria that = (RatingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rateeid, that.rateeid) &&
            Objects.equals(rateetype, that.rateetype) &&
            Objects.equals(duedate, that.duedate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(raterId, that.raterId) &&
            Objects.equals(ratingattributesRatingId, that.ratingattributesRatingId) &&
            Objects.equals(performancecycleId, that.performancecycleId) &&
            Objects.equals(projectcycleId, that.projectcycleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            rateeid,
            rateetype,
            duedate,
            createdat,
            updatedat,
            deletedat,
            raterId,
            ratingattributesRatingId,
            performancecycleId,
            projectcycleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rateeid != null ? "rateeid=" + rateeid + ", " : "") +
            (rateetype != null ? "rateetype=" + rateetype + ", " : "") +
            (duedate != null ? "duedate=" + duedate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (raterId != null ? "raterId=" + raterId + ", " : "") +
            (ratingattributesRatingId != null ? "ratingattributesRatingId=" + ratingattributesRatingId + ", " : "") +
            (performancecycleId != null ? "performancecycleId=" + performancecycleId + ", " : "") +
            (projectcycleId != null ? "projectcycleId=" + projectcycleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
