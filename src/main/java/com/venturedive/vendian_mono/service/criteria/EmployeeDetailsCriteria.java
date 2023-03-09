package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeDetails} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter religion;

    private StringFilter maritalstatus;

    private StringFilter bloodgroup;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter totaltenure;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeDetailsCriteria() {}

    public EmployeeDetailsCriteria(EmployeeDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.maritalstatus = other.maritalstatus == null ? null : other.maritalstatus.copy();
        this.bloodgroup = other.bloodgroup == null ? null : other.bloodgroup.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.totaltenure = other.totaltenure == null ? null : other.totaltenure.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeDetailsCriteria copy() {
        return new EmployeeDetailsCriteria(this);
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

    public StringFilter getReligion() {
        return religion;
    }

    public StringFilter religion() {
        if (religion == null) {
            religion = new StringFilter();
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public StringFilter getMaritalstatus() {
        return maritalstatus;
    }

    public StringFilter maritalstatus() {
        if (maritalstatus == null) {
            maritalstatus = new StringFilter();
        }
        return maritalstatus;
    }

    public void setMaritalstatus(StringFilter maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public StringFilter getBloodgroup() {
        return bloodgroup;
    }

    public StringFilter bloodgroup() {
        if (bloodgroup == null) {
            bloodgroup = new StringFilter();
        }
        return bloodgroup;
    }

    public void setBloodgroup(StringFilter bloodgroup) {
        this.bloodgroup = bloodgroup;
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

    public StringFilter getTotaltenure() {
        return totaltenure;
    }

    public StringFilter totaltenure() {
        if (totaltenure == null) {
            totaltenure = new StringFilter();
        }
        return totaltenure;
    }

    public void setTotaltenure(StringFilter totaltenure) {
        this.totaltenure = totaltenure;
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
        final EmployeeDetailsCriteria that = (EmployeeDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(maritalstatus, that.maritalstatus) &&
            Objects.equals(bloodgroup, that.bloodgroup) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(totaltenure, that.totaltenure) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, religion, maritalstatus, bloodgroup, createdat, updatedat, totaltenure, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (religion != null ? "religion=" + religion + ", " : "") +
            (maritalstatus != null ? "maritalstatus=" + maritalstatus + ", " : "") +
            (bloodgroup != null ? "bloodgroup=" + bloodgroup + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (totaltenure != null ? "totaltenure=" + totaltenure + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
