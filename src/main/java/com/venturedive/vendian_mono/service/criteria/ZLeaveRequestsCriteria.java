package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ZLeaveRequests} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ZLeaveRequestsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /z-leave-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZLeaveRequestsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter action;

    private StringFilter userid;

    private StringFilter managerid;

    private StringFilter requestparams;

    private StringFilter responseparams;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private Boolean distinct;

    public ZLeaveRequestsCriteria() {}

    public ZLeaveRequestsCriteria(ZLeaveRequestsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.userid = other.userid == null ? null : other.userid.copy();
        this.managerid = other.managerid == null ? null : other.managerid.copy();
        this.requestparams = other.requestparams == null ? null : other.requestparams.copy();
        this.responseparams = other.responseparams == null ? null : other.responseparams.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ZLeaveRequestsCriteria copy() {
        return new ZLeaveRequestsCriteria(this);
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

    public StringFilter getAction() {
        return action;
    }

    public StringFilter action() {
        if (action == null) {
            action = new StringFilter();
        }
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
    }

    public StringFilter getUserid() {
        return userid;
    }

    public StringFilter userid() {
        if (userid == null) {
            userid = new StringFilter();
        }
        return userid;
    }

    public void setUserid(StringFilter userid) {
        this.userid = userid;
    }

    public StringFilter getManagerid() {
        return managerid;
    }

    public StringFilter managerid() {
        if (managerid == null) {
            managerid = new StringFilter();
        }
        return managerid;
    }

    public void setManagerid(StringFilter managerid) {
        this.managerid = managerid;
    }

    public StringFilter getRequestparams() {
        return requestparams;
    }

    public StringFilter requestparams() {
        if (requestparams == null) {
            requestparams = new StringFilter();
        }
        return requestparams;
    }

    public void setRequestparams(StringFilter requestparams) {
        this.requestparams = requestparams;
    }

    public StringFilter getResponseparams() {
        return responseparams;
    }

    public StringFilter responseparams() {
        if (responseparams == null) {
            responseparams = new StringFilter();
        }
        return responseparams;
    }

    public void setResponseparams(StringFilter responseparams) {
        this.responseparams = responseparams;
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
        final ZLeaveRequestsCriteria that = (ZLeaveRequestsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(action, that.action) &&
            Objects.equals(userid, that.userid) &&
            Objects.equals(managerid, that.managerid) &&
            Objects.equals(requestparams, that.requestparams) &&
            Objects.equals(responseparams, that.responseparams) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, userid, managerid, requestparams, responseparams, createdat, updatedat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZLeaveRequestsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (userid != null ? "userid=" + userid + ", " : "") +
            (managerid != null ? "managerid=" + managerid + ", " : "") +
            (requestparams != null ? "requestparams=" + requestparams + ", " : "") +
            (responseparams != null ? "responseparams=" + responseparams + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
