package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeProjectRatings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeProjectRatingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-project-ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRatingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter projectarchitectId;

    private LongFilter projectmanagerId;

    private LongFilter employeeId;

    private LongFilter projectcycleId;

    private Boolean distinct;

    public EmployeeProjectRatingsCriteria() {}

    public EmployeeProjectRatingsCriteria(EmployeeProjectRatingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.projectarchitectId = other.projectarchitectId == null ? null : other.projectarchitectId.copy();
        this.projectmanagerId = other.projectmanagerId == null ? null : other.projectmanagerId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.projectcycleId = other.projectcycleId == null ? null : other.projectcycleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeProjectRatingsCriteria copy() {
        return new EmployeeProjectRatingsCriteria(this);
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

    public LongFilter getProjectarchitectId() {
        return projectarchitectId;
    }

    public LongFilter projectarchitectId() {
        if (projectarchitectId == null) {
            projectarchitectId = new LongFilter();
        }
        return projectarchitectId;
    }

    public void setProjectarchitectId(LongFilter projectarchitectId) {
        this.projectarchitectId = projectarchitectId;
    }

    public LongFilter getProjectmanagerId() {
        return projectmanagerId;
    }

    public LongFilter projectmanagerId() {
        if (projectmanagerId == null) {
            projectmanagerId = new LongFilter();
        }
        return projectmanagerId;
    }

    public void setProjectmanagerId(LongFilter projectmanagerId) {
        this.projectmanagerId = projectmanagerId;
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

    public LongFilter getProjectcycleId() {
        return projectcycleId;
    }

    public LongFilter projectcycleId() {
        if (projectcycleId == null) {
            projectcycleId = new LongFilter();
        }
        return projectcycleId;
    }

    public void setProjectcycleId(LongFilter projectcycleId) {
        this.projectcycleId = projectcycleId;
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
        final EmployeeProjectRatingsCriteria that = (EmployeeProjectRatingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(projectarchitectId, that.projectarchitectId) &&
            Objects.equals(projectmanagerId, that.projectmanagerId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(projectcycleId, that.projectcycleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, projectarchitectId, projectmanagerId, employeeId, projectcycleId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRatingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (projectarchitectId != null ? "projectarchitectId=" + projectarchitectId + ", " : "") +
            (projectmanagerId != null ? "projectmanagerId=" + projectmanagerId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (projectcycleId != null ? "projectcycleId=" + projectcycleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
