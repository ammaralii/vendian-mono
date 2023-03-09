package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.WorkLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.WorkLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private IntegerFilter mood;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private LongFilter worklogdetailsWorklogId;

    private Boolean distinct;

    public WorkLogsCriteria() {}

    public WorkLogsCriteria(WorkLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.mood = other.mood == null ? null : other.mood.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.worklogdetailsWorklogId = other.worklogdetailsWorklogId == null ? null : other.worklogdetailsWorklogId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkLogsCriteria copy() {
        return new WorkLogsCriteria(this);
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

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public IntegerFilter getMood() {
        return mood;
    }

    public IntegerFilter mood() {
        if (mood == null) {
            mood = new IntegerFilter();
        }
        return mood;
    }

    public void setMood(IntegerFilter mood) {
        this.mood = mood;
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

    public LongFilter getWorklogdetailsWorklogId() {
        return worklogdetailsWorklogId;
    }

    public LongFilter worklogdetailsWorklogId() {
        if (worklogdetailsWorklogId == null) {
            worklogdetailsWorklogId = new LongFilter();
        }
        return worklogdetailsWorklogId;
    }

    public void setWorklogdetailsWorklogId(LongFilter worklogdetailsWorklogId) {
        this.worklogdetailsWorklogId = worklogdetailsWorklogId;
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
        final WorkLogsCriteria that = (WorkLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(mood, that.mood) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(worklogdetailsWorklogId, that.worklogdetailsWorklogId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, mood, createdat, updatedat, employeeId, worklogdetailsWorklogId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (mood != null ? "mood=" + mood + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (worklogdetailsWorklogId != null ? "worklogdetailsWorklogId=" + worklogdetailsWorklogId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
