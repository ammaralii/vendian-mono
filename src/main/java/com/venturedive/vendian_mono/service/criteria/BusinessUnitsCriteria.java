package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.BusinessUnits} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.BusinessUnitsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-units?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessUnitsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeejobinfoBusinessunitId;

    private LongFilter employeesBusinessunitId;

    private LongFilter projectsBusinessunitId;

    private Boolean distinct;

    public BusinessUnitsCriteria() {}

    public BusinessUnitsCriteria(BusinessUnitsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeejobinfoBusinessunitId =
            other.employeejobinfoBusinessunitId == null ? null : other.employeejobinfoBusinessunitId.copy();
        this.employeesBusinessunitId = other.employeesBusinessunitId == null ? null : other.employeesBusinessunitId.copy();
        this.projectsBusinessunitId = other.projectsBusinessunitId == null ? null : other.projectsBusinessunitId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BusinessUnitsCriteria copy() {
        return new BusinessUnitsCriteria(this);
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

    public LongFilter getEmployeejobinfoBusinessunitId() {
        return employeejobinfoBusinessunitId;
    }

    public LongFilter employeejobinfoBusinessunitId() {
        if (employeejobinfoBusinessunitId == null) {
            employeejobinfoBusinessunitId = new LongFilter();
        }
        return employeejobinfoBusinessunitId;
    }

    public void setEmployeejobinfoBusinessunitId(LongFilter employeejobinfoBusinessunitId) {
        this.employeejobinfoBusinessunitId = employeejobinfoBusinessunitId;
    }

    public LongFilter getEmployeesBusinessunitId() {
        return employeesBusinessunitId;
    }

    public LongFilter employeesBusinessunitId() {
        if (employeesBusinessunitId == null) {
            employeesBusinessunitId = new LongFilter();
        }
        return employeesBusinessunitId;
    }

    public void setEmployeesBusinessunitId(LongFilter employeesBusinessunitId) {
        this.employeesBusinessunitId = employeesBusinessunitId;
    }

    public LongFilter getProjectsBusinessunitId() {
        return projectsBusinessunitId;
    }

    public LongFilter projectsBusinessunitId() {
        if (projectsBusinessunitId == null) {
            projectsBusinessunitId = new LongFilter();
        }
        return projectsBusinessunitId;
    }

    public void setProjectsBusinessunitId(LongFilter projectsBusinessunitId) {
        this.projectsBusinessunitId = projectsBusinessunitId;
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
        final BusinessUnitsCriteria that = (BusinessUnitsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeejobinfoBusinessunitId, that.employeejobinfoBusinessunitId) &&
            Objects.equals(employeesBusinessunitId, that.employeesBusinessunitId) &&
            Objects.equals(projectsBusinessunitId, that.projectsBusinessunitId) &&
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
            employeejobinfoBusinessunitId,
            employeesBusinessunitId,
            projectsBusinessunitId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnitsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeejobinfoBusinessunitId != null ? "employeejobinfoBusinessunitId=" + employeejobinfoBusinessunitId + ", " : "") +
            (employeesBusinessunitId != null ? "employeesBusinessunitId=" + employeesBusinessunitId + ", " : "") +
            (projectsBusinessunitId != null ? "projectsBusinessunitId=" + projectsBusinessunitId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
