package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ProjectCycles20190826} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ProjectCycles20190826Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-cycles-20190826-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCycles20190826Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isactive;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter performancecycleid;

    private IntegerFilter projectid;

    private IntegerFilter allowedafterduedateby;

    private InstantFilter allowedafterduedateat;

    private InstantFilter duedate;

    private Boolean distinct;

    public ProjectCycles20190826Criteria() {}

    public ProjectCycles20190826Criteria(ProjectCycles20190826Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.performancecycleid = other.performancecycleid == null ? null : other.performancecycleid.copy();
        this.projectid = other.projectid == null ? null : other.projectid.copy();
        this.allowedafterduedateby = other.allowedafterduedateby == null ? null : other.allowedafterduedateby.copy();
        this.allowedafterduedateat = other.allowedafterduedateat == null ? null : other.allowedafterduedateat.copy();
        this.duedate = other.duedate == null ? null : other.duedate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectCycles20190826Criteria copy() {
        return new ProjectCycles20190826Criteria(this);
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

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public IntegerFilter getPerformancecycleid() {
        return performancecycleid;
    }

    public IntegerFilter performancecycleid() {
        if (performancecycleid == null) {
            performancecycleid = new IntegerFilter();
        }
        return performancecycleid;
    }

    public void setPerformancecycleid(IntegerFilter performancecycleid) {
        this.performancecycleid = performancecycleid;
    }

    public IntegerFilter getProjectid() {
        return projectid;
    }

    public IntegerFilter projectid() {
        if (projectid == null) {
            projectid = new IntegerFilter();
        }
        return projectid;
    }

    public void setProjectid(IntegerFilter projectid) {
        this.projectid = projectid;
    }

    public IntegerFilter getAllowedafterduedateby() {
        return allowedafterduedateby;
    }

    public IntegerFilter allowedafterduedateby() {
        if (allowedafterduedateby == null) {
            allowedafterduedateby = new IntegerFilter();
        }
        return allowedafterduedateby;
    }

    public void setAllowedafterduedateby(IntegerFilter allowedafterduedateby) {
        this.allowedafterduedateby = allowedafterduedateby;
    }

    public InstantFilter getAllowedafterduedateat() {
        return allowedafterduedateat;
    }

    public InstantFilter allowedafterduedateat() {
        if (allowedafterduedateat == null) {
            allowedafterduedateat = new InstantFilter();
        }
        return allowedafterduedateat;
    }

    public void setAllowedafterduedateat(InstantFilter allowedafterduedateat) {
        this.allowedafterduedateat = allowedafterduedateat;
    }

    public InstantFilter getDuedate() {
        return duedate;
    }

    public InstantFilter duedate() {
        if (duedate == null) {
            duedate = new InstantFilter();
        }
        return duedate;
    }

    public void setDuedate(InstantFilter duedate) {
        this.duedate = duedate;
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
        final ProjectCycles20190826Criteria that = (ProjectCycles20190826Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(performancecycleid, that.performancecycleid) &&
            Objects.equals(projectid, that.projectid) &&
            Objects.equals(allowedafterduedateby, that.allowedafterduedateby) &&
            Objects.equals(allowedafterduedateat, that.allowedafterduedateat) &&
            Objects.equals(duedate, that.duedate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isactive,
            createdat,
            updatedat,
            performancecycleid,
            projectid,
            allowedafterduedateby,
            allowedafterduedateat,
            duedate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCycles20190826Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (performancecycleid != null ? "performancecycleid=" + performancecycleid + ", " : "") +
            (projectid != null ? "projectid=" + projectid + ", " : "") +
            (allowedafterduedateby != null ? "allowedafterduedateby=" + allowedafterduedateby + ", " : "") +
            (allowedafterduedateat != null ? "allowedafterduedateat=" + allowedafterduedateat + ", " : "") +
            (duedate != null ? "duedate=" + duedate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
