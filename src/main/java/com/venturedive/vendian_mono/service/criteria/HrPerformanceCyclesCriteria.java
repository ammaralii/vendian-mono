package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.HrPerformanceCycles} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.HrPerformanceCyclesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hr-performance-cycles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HrPerformanceCyclesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private BooleanFilter freeze;

    private LocalDateFilter dueDate;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter performancecycleemployeeratingPerformancecycleId;

    private LongFilter userratingsPerformancecycleId;

    private Boolean distinct;

    public HrPerformanceCyclesCriteria() {}

    public HrPerformanceCyclesCriteria(HrPerformanceCyclesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.freeze = other.freeze == null ? null : other.freeze.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.performancecycleemployeeratingPerformancecycleId =
            other.performancecycleemployeeratingPerformancecycleId == null
                ? null
                : other.performancecycleemployeeratingPerformancecycleId.copy();
        this.userratingsPerformancecycleId =
            other.userratingsPerformancecycleId == null ? null : other.userratingsPerformancecycleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HrPerformanceCyclesCriteria copy() {
        return new HrPerformanceCyclesCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
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

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
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

    public LongFilter getPerformancecycleemployeeratingPerformancecycleId() {
        return performancecycleemployeeratingPerformancecycleId;
    }

    public LongFilter performancecycleemployeeratingPerformancecycleId() {
        if (performancecycleemployeeratingPerformancecycleId == null) {
            performancecycleemployeeratingPerformancecycleId = new LongFilter();
        }
        return performancecycleemployeeratingPerformancecycleId;
    }

    public void setPerformancecycleemployeeratingPerformancecycleId(LongFilter performancecycleemployeeratingPerformancecycleId) {
        this.performancecycleemployeeratingPerformancecycleId = performancecycleemployeeratingPerformancecycleId;
    }

    public LongFilter getUserratingsPerformancecycleId() {
        return userratingsPerformancecycleId;
    }

    public LongFilter userratingsPerformancecycleId() {
        if (userratingsPerformancecycleId == null) {
            userratingsPerformancecycleId = new LongFilter();
        }
        return userratingsPerformancecycleId;
    }

    public void setUserratingsPerformancecycleId(LongFilter userratingsPerformancecycleId) {
        this.userratingsPerformancecycleId = userratingsPerformancecycleId;
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
        final HrPerformanceCyclesCriteria that = (HrPerformanceCyclesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(freeze, that.freeze) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(performancecycleemployeeratingPerformancecycleId, that.performancecycleemployeeratingPerformancecycleId) &&
            Objects.equals(userratingsPerformancecycleId, that.userratingsPerformancecycleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            freeze,
            dueDate,
            startDate,
            endDate,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            performancecycleemployeeratingPerformancecycleId,
            userratingsPerformancecycleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HrPerformanceCyclesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (freeze != null ? "freeze=" + freeze + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (performancecycleemployeeratingPerformancecycleId != null ? "performancecycleemployeeratingPerformancecycleId=" + performancecycleemployeeratingPerformancecycleId + ", " : "") +
            (userratingsPerformancecycleId != null ? "userratingsPerformancecycleId=" + userratingsPerformancecycleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
