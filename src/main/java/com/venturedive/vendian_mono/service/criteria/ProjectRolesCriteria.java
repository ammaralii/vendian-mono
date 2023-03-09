package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ProjectRoles} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ProjectRolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectRolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter role;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private BooleanFilter isleadership;

    private LongFilter employeeprojectrolesProjectroleId;

    private LongFilter projectleadershipProjectroleId;

    private Boolean distinct;

    public ProjectRolesCriteria() {}

    public ProjectRolesCriteria(ProjectRolesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.isleadership = other.isleadership == null ? null : other.isleadership.copy();
        this.employeeprojectrolesProjectroleId =
            other.employeeprojectrolesProjectroleId == null ? null : other.employeeprojectrolesProjectroleId.copy();
        this.projectleadershipProjectroleId =
            other.projectleadershipProjectroleId == null ? null : other.projectleadershipProjectroleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectRolesCriteria copy() {
        return new ProjectRolesCriteria(this);
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

    public StringFilter getRole() {
        return role;
    }

    public StringFilter role() {
        if (role == null) {
            role = new StringFilter();
        }
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
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

    public BooleanFilter getIsleadership() {
        return isleadership;
    }

    public BooleanFilter isleadership() {
        if (isleadership == null) {
            isleadership = new BooleanFilter();
        }
        return isleadership;
    }

    public void setIsleadership(BooleanFilter isleadership) {
        this.isleadership = isleadership;
    }

    public LongFilter getEmployeeprojectrolesProjectroleId() {
        return employeeprojectrolesProjectroleId;
    }

    public LongFilter employeeprojectrolesProjectroleId() {
        if (employeeprojectrolesProjectroleId == null) {
            employeeprojectrolesProjectroleId = new LongFilter();
        }
        return employeeprojectrolesProjectroleId;
    }

    public void setEmployeeprojectrolesProjectroleId(LongFilter employeeprojectrolesProjectroleId) {
        this.employeeprojectrolesProjectroleId = employeeprojectrolesProjectroleId;
    }

    public LongFilter getProjectleadershipProjectroleId() {
        return projectleadershipProjectroleId;
    }

    public LongFilter projectleadershipProjectroleId() {
        if (projectleadershipProjectroleId == null) {
            projectleadershipProjectroleId = new LongFilter();
        }
        return projectleadershipProjectroleId;
    }

    public void setProjectleadershipProjectroleId(LongFilter projectleadershipProjectroleId) {
        this.projectleadershipProjectroleId = projectleadershipProjectroleId;
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
        final ProjectRolesCriteria that = (ProjectRolesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(role, that.role) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(isleadership, that.isleadership) &&
            Objects.equals(employeeprojectrolesProjectroleId, that.employeeprojectrolesProjectroleId) &&
            Objects.equals(projectleadershipProjectroleId, that.projectleadershipProjectroleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            role,
            createdat,
            updatedat,
            isleadership,
            employeeprojectrolesProjectroleId,
            projectleadershipProjectroleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectRolesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (isleadership != null ? "isleadership=" + isleadership + ", " : "") +
            (employeeprojectrolesProjectroleId != null ? "employeeprojectrolesProjectroleId=" + employeeprojectrolesProjectroleId + ", " : "") +
            (projectleadershipProjectroleId != null ? "projectleadershipProjectroleId=" + projectleadershipProjectroleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
