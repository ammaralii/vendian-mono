package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Roles} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter role;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeerolesRoleId;

    private LongFilter employeesRoleId;

    private LongFilter rolepermissionsRoleId;

    private Boolean distinct;

    public RolesCriteria() {}

    public RolesCriteria(RolesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeerolesRoleId = other.employeerolesRoleId == null ? null : other.employeerolesRoleId.copy();
        this.employeesRoleId = other.employeesRoleId == null ? null : other.employeesRoleId.copy();
        this.rolepermissionsRoleId = other.rolepermissionsRoleId == null ? null : other.rolepermissionsRoleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RolesCriteria copy() {
        return new RolesCriteria(this);
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

    public LongFilter getEmployeerolesRoleId() {
        return employeerolesRoleId;
    }

    public LongFilter employeerolesRoleId() {
        if (employeerolesRoleId == null) {
            employeerolesRoleId = new LongFilter();
        }
        return employeerolesRoleId;
    }

    public void setEmployeerolesRoleId(LongFilter employeerolesRoleId) {
        this.employeerolesRoleId = employeerolesRoleId;
    }

    public LongFilter getEmployeesRoleId() {
        return employeesRoleId;
    }

    public LongFilter employeesRoleId() {
        if (employeesRoleId == null) {
            employeesRoleId = new LongFilter();
        }
        return employeesRoleId;
    }

    public void setEmployeesRoleId(LongFilter employeesRoleId) {
        this.employeesRoleId = employeesRoleId;
    }

    public LongFilter getRolepermissionsRoleId() {
        return rolepermissionsRoleId;
    }

    public LongFilter rolepermissionsRoleId() {
        if (rolepermissionsRoleId == null) {
            rolepermissionsRoleId = new LongFilter();
        }
        return rolepermissionsRoleId;
    }

    public void setRolepermissionsRoleId(LongFilter rolepermissionsRoleId) {
        this.rolepermissionsRoleId = rolepermissionsRoleId;
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
        final RolesCriteria that = (RolesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(role, that.role) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeerolesRoleId, that.employeerolesRoleId) &&
            Objects.equals(employeesRoleId, that.employeesRoleId) &&
            Objects.equals(rolepermissionsRoleId, that.rolepermissionsRoleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, createdat, updatedat, employeerolesRoleId, employeesRoleId, rolepermissionsRoleId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeerolesRoleId != null ? "employeerolesRoleId=" + employeerolesRoleId + ", " : "") +
            (employeesRoleId != null ? "employeesRoleId=" + employeesRoleId + ", " : "") +
            (rolepermissionsRoleId != null ? "rolepermissionsRoleId=" + rolepermissionsRoleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
