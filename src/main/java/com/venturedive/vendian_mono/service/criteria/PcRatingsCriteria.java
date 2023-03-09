package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PcRatings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PcRatingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pc-ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRatingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter stausDate;

    private LocalDateFilter dueDate;

    private BooleanFilter freeze;

    private BooleanFilter includeInFinalRating;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter statusId;

    private LongFilter pcemployeeratingId;

    private LongFilter employeeId;

    private LongFilter pcraterattributesRatingId;

    private LongFilter pcratingstrainingsRatingId;

    private LongFilter usergoalraterattributesRatingId;

    private Boolean distinct;

    public PcRatingsCriteria() {}

    public PcRatingsCriteria(PcRatingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stausDate = other.stausDate == null ? null : other.stausDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.freeze = other.freeze == null ? null : other.freeze.copy();
        this.includeInFinalRating = other.includeInFinalRating == null ? null : other.includeInFinalRating.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.pcemployeeratingId = other.pcemployeeratingId == null ? null : other.pcemployeeratingId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.pcraterattributesRatingId = other.pcraterattributesRatingId == null ? null : other.pcraterattributesRatingId.copy();
        this.pcratingstrainingsRatingId = other.pcratingstrainingsRatingId == null ? null : other.pcratingstrainingsRatingId.copy();
        this.usergoalraterattributesRatingId =
            other.usergoalraterattributesRatingId == null ? null : other.usergoalraterattributesRatingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PcRatingsCriteria copy() {
        return new PcRatingsCriteria(this);
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

    public InstantFilter getStausDate() {
        return stausDate;
    }

    public InstantFilter stausDate() {
        if (stausDate == null) {
            stausDate = new InstantFilter();
        }
        return stausDate;
    }

    public void setStausDate(InstantFilter stausDate) {
        this.stausDate = stausDate;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public BooleanFilter getFreeze() {
        return freeze;
    }

    public BooleanFilter freeze() {
        if (freeze == null) {
            freeze = new BooleanFilter();
        }
        return freeze;
    }

    public void setFreeze(BooleanFilter freeze) {
        this.freeze = freeze;
    }

    public BooleanFilter getIncludeInFinalRating() {
        return includeInFinalRating;
    }

    public BooleanFilter includeInFinalRating() {
        if (includeInFinalRating == null) {
            includeInFinalRating = new BooleanFilter();
        }
        return includeInFinalRating;
    }

    public void setIncludeInFinalRating(BooleanFilter includeInFinalRating) {
        this.includeInFinalRating = includeInFinalRating;
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

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getPcemployeeratingId() {
        return pcemployeeratingId;
    }

    public LongFilter pcemployeeratingId() {
        if (pcemployeeratingId == null) {
            pcemployeeratingId = new LongFilter();
        }
        return pcemployeeratingId;
    }

    public void setPcemployeeratingId(LongFilter pcemployeeratingId) {
        this.pcemployeeratingId = pcemployeeratingId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getPcraterattributesRatingId() {
        return pcraterattributesRatingId;
    }

    public LongFilter pcraterattributesRatingId() {
        if (pcraterattributesRatingId == null) {
            pcraterattributesRatingId = new LongFilter();
        }
        return pcraterattributesRatingId;
    }

    public void setPcraterattributesRatingId(LongFilter pcraterattributesRatingId) {
        this.pcraterattributesRatingId = pcraterattributesRatingId;
    }

    public LongFilter getPcratingstrainingsRatingId() {
        return pcratingstrainingsRatingId;
    }

    public LongFilter pcratingstrainingsRatingId() {
        if (pcratingstrainingsRatingId == null) {
            pcratingstrainingsRatingId = new LongFilter();
        }
        return pcratingstrainingsRatingId;
    }

    public void setPcratingstrainingsRatingId(LongFilter pcratingstrainingsRatingId) {
        this.pcratingstrainingsRatingId = pcratingstrainingsRatingId;
    }

    public LongFilter getUsergoalraterattributesRatingId() {
        return usergoalraterattributesRatingId;
    }

    public LongFilter usergoalraterattributesRatingId() {
        if (usergoalraterattributesRatingId == null) {
            usergoalraterattributesRatingId = new LongFilter();
        }
        return usergoalraterattributesRatingId;
    }

    public void setUsergoalraterattributesRatingId(LongFilter usergoalraterattributesRatingId) {
        this.usergoalraterattributesRatingId = usergoalraterattributesRatingId;
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
        final PcRatingsCriteria that = (PcRatingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stausDate, that.stausDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(freeze, that.freeze) &&
            Objects.equals(includeInFinalRating, that.includeInFinalRating) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(pcemployeeratingId, that.pcemployeeratingId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(pcraterattributesRatingId, that.pcraterattributesRatingId) &&
            Objects.equals(pcratingstrainingsRatingId, that.pcratingstrainingsRatingId) &&
            Objects.equals(usergoalraterattributesRatingId, that.usergoalraterattributesRatingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            stausDate,
            dueDate,
            freeze,
            includeInFinalRating,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            statusId,
            pcemployeeratingId,
            employeeId,
            pcraterattributesRatingId,
            pcratingstrainingsRatingId,
            usergoalraterattributesRatingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRatingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stausDate != null ? "stausDate=" + stausDate + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (freeze != null ? "freeze=" + freeze + ", " : "") +
            (includeInFinalRating != null ? "includeInFinalRating=" + includeInFinalRating + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (pcemployeeratingId != null ? "pcemployeeratingId=" + pcemployeeratingId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (pcraterattributesRatingId != null ? "pcraterattributesRatingId=" + pcraterattributesRatingId + ", " : "") +
            (pcratingstrainingsRatingId != null ? "pcratingstrainingsRatingId=" + pcratingstrainingsRatingId + ", " : "") +
            (usergoalraterattributesRatingId != null ? "usergoalraterattributesRatingId=" + usergoalraterattributesRatingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
