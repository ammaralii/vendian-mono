package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeProjectRoles} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeProjectRolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-project-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter status;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeprojectId;

    private LongFilter projectroleId;

    private Boolean distinct;

    public EmployeeProjectRolesCriteria() {}

    public EmployeeProjectRolesCriteria(EmployeeProjectRolesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeprojectId = other.employeeprojectId == null ? null : other.employeeprojectId.copy();
        this.projectroleId = other.projectroleId == null ? null : other.projectroleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeProjectRolesCriteria copy() {
        return new EmployeeProjectRolesCriteria(this);
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

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
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

    public LongFilter getEmployeeprojectId() {
        return employeeprojectId;
    }

    public LongFilter employeeprojectId() {
        if (employeeprojectId == null) {
            employeeprojectId = new LongFilter();
        }
        return employeeprojectId;
    }

    public void setEmployeeprojectId(LongFilter employeeprojectId) {
        this.employeeprojectId = employeeprojectId;
    }

    public LongFilter getProjectroleId() {
        return projectroleId;
    }

    public LongFilter projectroleId() {
        if (projectroleId == null) {
            projectroleId = new LongFilter();
        }
        return projectroleId;
    }

    public void setProjectroleId(LongFilter projectroleId) {
        this.projectroleId = projectroleId;
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
        final EmployeeProjectRolesCriteria that = (EmployeeProjectRolesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeprojectId, that.employeeprojectId) &&
            Objects.equals(projectroleId, that.projectroleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, createdat, updatedat, employeeprojectId, projectroleId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRolesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeprojectId != null ? "employeeprojectId=" + employeeprojectId + ", " : "") +
            (projectroleId != null ? "projectroleId=" + projectroleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
