package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeProjectRatings20190826Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-project-ratings-20190826-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRatings20190826Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter projectarchitectid;

    private IntegerFilter projectmanagerid;

    private IntegerFilter employeeid;

    private IntegerFilter projectcycleid;

    private Boolean distinct;

    public EmployeeProjectRatings20190826Criteria() {}

    public EmployeeProjectRatings20190826Criteria(EmployeeProjectRatings20190826Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.projectarchitectid = other.projectarchitectid == null ? null : other.projectarchitectid.copy();
        this.projectmanagerid = other.projectmanagerid == null ? null : other.projectmanagerid.copy();
        this.employeeid = other.employeeid == null ? null : other.employeeid.copy();
        this.projectcycleid = other.projectcycleid == null ? null : other.projectcycleid.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeProjectRatings20190826Criteria copy() {
        return new EmployeeProjectRatings20190826Criteria(this);
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

    public IntegerFilter getProjectarchitectid() {
        return projectarchitectid;
    }

    public IntegerFilter projectarchitectid() {
        if (projectarchitectid == null) {
            projectarchitectid = new IntegerFilter();
        }
        return projectarchitectid;
    }

    public void setProjectarchitectid(IntegerFilter projectarchitectid) {
        this.projectarchitectid = projectarchitectid;
    }

    public IntegerFilter getProjectmanagerid() {
        return projectmanagerid;
    }

    public IntegerFilter projectmanagerid() {
        if (projectmanagerid == null) {
            projectmanagerid = new IntegerFilter();
        }
        return projectmanagerid;
    }

    public void setProjectmanagerid(IntegerFilter projectmanagerid) {
        this.projectmanagerid = projectmanagerid;
    }

    public IntegerFilter getEmployeeid() {
        return employeeid;
    }

    public IntegerFilter employeeid() {
        if (employeeid == null) {
            employeeid = new IntegerFilter();
        }
        return employeeid;
    }

    public void setEmployeeid(IntegerFilter employeeid) {
        this.employeeid = employeeid;
    }

    public IntegerFilter getProjectcycleid() {
        return projectcycleid;
    }

    public IntegerFilter projectcycleid() {
        if (projectcycleid == null) {
            projectcycleid = new IntegerFilter();
        }
        return projectcycleid;
    }

    public void setProjectcycleid(IntegerFilter projectcycleid) {
        this.projectcycleid = projectcycleid;
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
        final EmployeeProjectRatings20190826Criteria that = (EmployeeProjectRatings20190826Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(projectarchitectid, that.projectarchitectid) &&
            Objects.equals(projectmanagerid, that.projectmanagerid) &&
            Objects.equals(employeeid, that.employeeid) &&
            Objects.equals(projectcycleid, that.projectcycleid) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, projectarchitectid, projectmanagerid, employeeid, projectcycleid, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRatings20190826Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (projectarchitectid != null ? "projectarchitectid=" + projectarchitectid + ", " : "") +
            (projectmanagerid != null ? "projectmanagerid=" + projectmanagerid + ", " : "") +
            (employeeid != null ? "employeeid=" + employeeid + ", " : "") +
            (projectcycleid != null ? "projectcycleid=" + projectcycleid + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
