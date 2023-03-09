package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeBirthdays} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeBirthdaysResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-birthdays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeBirthdaysCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter birthdayDetails;

    private IntegerFilter year;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeBirthdaysCriteria() {}

    public EmployeeBirthdaysCriteria(EmployeeBirthdaysCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.birthdayDetails = other.birthdayDetails == null ? null : other.birthdayDetails.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeBirthdaysCriteria copy() {
        return new EmployeeBirthdaysCriteria(this);
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

    public StringFilter getBirthdayDetails() {
        return birthdayDetails;
    }

    public StringFilter birthdayDetails() {
        if (birthdayDetails == null) {
            birthdayDetails = new StringFilter();
        }
        return birthdayDetails;
    }

    public void setBirthdayDetails(StringFilter birthdayDetails) {
        this.birthdayDetails = birthdayDetails;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public IntegerFilter year() {
        if (year == null) {
            year = new IntegerFilter();
        }
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
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
        final EmployeeBirthdaysCriteria that = (EmployeeBirthdaysCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(birthdayDetails, that.birthdayDetails) &&
            Objects.equals(year, that.year) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthdayDetails, year, createdat, updatedat, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeBirthdaysCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (birthdayDetails != null ? "birthdayDetails=" + birthdayDetails + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
