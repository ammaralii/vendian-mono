package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Divisions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DivisionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /divisions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DivisionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter departmentsDivisionId;

    private LongFilter employeejobinfoDivisionId;

    private LongFilter employeesDivisionId;

    private Boolean distinct;

    public DivisionsCriteria() {}

    public DivisionsCriteria(DivisionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.departmentsDivisionId = other.departmentsDivisionId == null ? null : other.departmentsDivisionId.copy();
        this.employeejobinfoDivisionId = other.employeejobinfoDivisionId == null ? null : other.employeejobinfoDivisionId.copy();
        this.employeesDivisionId = other.employeesDivisionId == null ? null : other.employeesDivisionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DivisionsCriteria copy() {
        return new DivisionsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public LongFilter getDepartmentsDivisionId() {
        return departmentsDivisionId;
    }

    public LongFilter departmentsDivisionId() {
        if (departmentsDivisionId == null) {
            departmentsDivisionId = new LongFilter();
        }
        return departmentsDivisionId;
    }

    public void setDepartmentsDivisionId(LongFilter departmentsDivisionId) {
        this.departmentsDivisionId = departmentsDivisionId;
    }

    public LongFilter getEmployeejobinfoDivisionId() {
        return employeejobinfoDivisionId;
    }

    public LongFilter employeejobinfoDivisionId() {
        if (employeejobinfoDivisionId == null) {
            employeejobinfoDivisionId = new LongFilter();
        }
        return employeejobinfoDivisionId;
    }

    public void setEmployeejobinfoDivisionId(LongFilter employeejobinfoDivisionId) {
        this.employeejobinfoDivisionId = employeejobinfoDivisionId;
    }

    public LongFilter getEmployeesDivisionId() {
        return employeesDivisionId;
    }

    public LongFilter employeesDivisionId() {
        if (employeesDivisionId == null) {
            employeesDivisionId = new LongFilter();
        }
        return employeesDivisionId;
    }

    public void setEmployeesDivisionId(LongFilter employeesDivisionId) {
        this.employeesDivisionId = employeesDivisionId;
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
        final DivisionsCriteria that = (DivisionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(departmentsDivisionId, that.departmentsDivisionId) &&
            Objects.equals(employeejobinfoDivisionId, that.employeejobinfoDivisionId) &&
            Objects.equals(employeesDivisionId, that.employeesDivisionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            createdat,
            updatedat,
            departmentsDivisionId,
            employeejobinfoDivisionId,
            employeesDivisionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (departmentsDivisionId != null ? "departmentsDivisionId=" + departmentsDivisionId + ", " : "") +
            (employeejobinfoDivisionId != null ? "employeejobinfoDivisionId=" + employeejobinfoDivisionId + ", " : "") +
            (employeesDivisionId != null ? "employeesDivisionId=" + employeesDivisionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
