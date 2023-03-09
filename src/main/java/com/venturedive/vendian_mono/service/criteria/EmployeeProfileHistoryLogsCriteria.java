package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeProfileHistoryLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-profile-history-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProfileHistoryLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tablename;

    private IntegerFilter rowid;

    private StringFilter eventtype;

    private IntegerFilter updatedbyid;

    private StringFilter activityid;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter category;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeProfileHistoryLogsCriteria() {}

    public EmployeeProfileHistoryLogsCriteria(EmployeeProfileHistoryLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tablename = other.tablename == null ? null : other.tablename.copy();
        this.rowid = other.rowid == null ? null : other.rowid.copy();
        this.eventtype = other.eventtype == null ? null : other.eventtype.copy();
        this.updatedbyid = other.updatedbyid == null ? null : other.updatedbyid.copy();
        this.activityid = other.activityid == null ? null : other.activityid.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeProfileHistoryLogsCriteria copy() {
        return new EmployeeProfileHistoryLogsCriteria(this);
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

    public StringFilter getTablename() {
        return tablename;
    }

    public StringFilter tablename() {
        if (tablename == null) {
            tablename = new StringFilter();
        }
        return tablename;
    }

    public void setTablename(StringFilter tablename) {
        this.tablename = tablename;
    }

    public IntegerFilter getRowid() {
        return rowid;
    }

    public IntegerFilter rowid() {
        if (rowid == null) {
            rowid = new IntegerFilter();
        }
        return rowid;
    }

    public void setRowid(IntegerFilter rowid) {
        this.rowid = rowid;
    }

    public StringFilter getEventtype() {
        return eventtype;
    }

    public StringFilter eventtype() {
        if (eventtype == null) {
            eventtype = new StringFilter();
        }
        return eventtype;
    }

    public void setEventtype(StringFilter eventtype) {
        this.eventtype = eventtype;
    }

    public IntegerFilter getUpdatedbyid() {
        return updatedbyid;
    }

    public IntegerFilter updatedbyid() {
        if (updatedbyid == null) {
            updatedbyid = new IntegerFilter();
        }
        return updatedbyid;
    }

    public void setUpdatedbyid(IntegerFilter updatedbyid) {
        this.updatedbyid = updatedbyid;
    }

    public StringFilter getActivityid() {
        return activityid;
    }

    public StringFilter activityid() {
        if (activityid == null) {
            activityid = new StringFilter();
        }
        return activityid;
    }

    public void setActivityid(StringFilter activityid) {
        this.activityid = activityid;
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

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
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
        final EmployeeProfileHistoryLogsCriteria that = (EmployeeProfileHistoryLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tablename, that.tablename) &&
            Objects.equals(rowid, that.rowid) &&
            Objects.equals(eventtype, that.eventtype) &&
            Objects.equals(updatedbyid, that.updatedbyid) &&
            Objects.equals(activityid, that.activityid) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(category, that.category) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tablename, rowid, eventtype, updatedbyid, activityid, createdat, updatedat, category, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProfileHistoryLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tablename != null ? "tablename=" + tablename + ", " : "") +
            (rowid != null ? "rowid=" + rowid + ", " : "") +
            (eventtype != null ? "eventtype=" + eventtype + ", " : "") +
            (updatedbyid != null ? "updatedbyid=" + updatedbyid + ", " : "") +
            (activityid != null ? "activityid=" + activityid + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
