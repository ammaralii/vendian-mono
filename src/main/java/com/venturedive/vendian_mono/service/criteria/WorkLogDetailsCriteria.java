package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.WorkLogDetails} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.WorkLogDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-log-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkLogDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter percentage;

    private IntegerFilter hours;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter worklogId;

    private LongFilter projectId;

    private Boolean distinct;

    public WorkLogDetailsCriteria() {}

    public WorkLogDetailsCriteria(WorkLogDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.hours = other.hours == null ? null : other.hours.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.worklogId = other.worklogId == null ? null : other.worklogId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkLogDetailsCriteria copy() {
        return new WorkLogDetailsCriteria(this);
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

    public StringFilter getPercentage() {
        return percentage;
    }

    public StringFilter percentage() {
        if (percentage == null) {
            percentage = new StringFilter();
        }
        return percentage;
    }

    public void setPercentage(StringFilter percentage) {
        this.percentage = percentage;
    }

    public IntegerFilter getHours() {
        return hours;
    }

    public IntegerFilter hours() {
        if (hours == null) {
            hours = new IntegerFilter();
        }
        return hours;
    }

    public void setHours(IntegerFilter hours) {
        this.hours = hours;
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

    public LongFilter getWorklogId() {
        return worklogId;
    }

    public LongFilter worklogId() {
        if (worklogId == null) {
            worklogId = new LongFilter();
        }
        return worklogId;
    }

    public void setWorklogId(LongFilter worklogId) {
        this.worklogId = worklogId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
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
        final WorkLogDetailsCriteria that = (WorkLogDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(hours, that.hours) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(worklogId, that.worklogId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentage, hours, createdat, updatedat, worklogId, projectId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkLogDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (hours != null ? "hours=" + hours + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (worklogId != null ? "worklogId=" + worklogId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
