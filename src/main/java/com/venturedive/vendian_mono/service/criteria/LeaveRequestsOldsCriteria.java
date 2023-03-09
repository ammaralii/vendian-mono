package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveRequestsOlds} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveRequestsOldsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-requests-olds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestsOldsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private StringFilter requesternote;

    private StringFilter managernote;

    private StringFilter currentstatus;

    private BooleanFilter leavescanceled;

    private InstantFilter requestdate;

    private StringFilter linkstring;

    private BooleanFilter linkused;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private BooleanFilter ishalfday;

    private InstantFilter actiondate;

    private StringFilter lmstatus;

    private StringFilter pmstatus;

    private BooleanFilter isbench;

    private BooleanFilter isescalated;

    private BooleanFilter iscommented;

    private BooleanFilter isreminded;

    private BooleanFilter isnotified;

    private BooleanFilter isremindedhr;

    private BooleanFilter isdm;

    private LongFilter leavetypeId;

    private LongFilter managerId;

    private LongFilter employeeId;

    private Boolean distinct;

    public LeaveRequestsOldsCriteria() {}

    public LeaveRequestsOldsCriteria(LeaveRequestsOldsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.requesternote = other.requesternote == null ? null : other.requesternote.copy();
        this.managernote = other.managernote == null ? null : other.managernote.copy();
        this.currentstatus = other.currentstatus == null ? null : other.currentstatus.copy();
        this.leavescanceled = other.leavescanceled == null ? null : other.leavescanceled.copy();
        this.requestdate = other.requestdate == null ? null : other.requestdate.copy();
        this.linkstring = other.linkstring == null ? null : other.linkstring.copy();
        this.linkused = other.linkused == null ? null : other.linkused.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.ishalfday = other.ishalfday == null ? null : other.ishalfday.copy();
        this.actiondate = other.actiondate == null ? null : other.actiondate.copy();
        this.lmstatus = other.lmstatus == null ? null : other.lmstatus.copy();
        this.pmstatus = other.pmstatus == null ? null : other.pmstatus.copy();
        this.isbench = other.isbench == null ? null : other.isbench.copy();
        this.isescalated = other.isescalated == null ? null : other.isescalated.copy();
        this.iscommented = other.iscommented == null ? null : other.iscommented.copy();
        this.isreminded = other.isreminded == null ? null : other.isreminded.copy();
        this.isnotified = other.isnotified == null ? null : other.isnotified.copy();
        this.isremindedhr = other.isremindedhr == null ? null : other.isremindedhr.copy();
        this.isdm = other.isdm == null ? null : other.isdm.copy();
        this.leavetypeId = other.leavetypeId == null ? null : other.leavetypeId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveRequestsOldsCriteria copy() {
        return new LeaveRequestsOldsCriteria(this);
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

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
    }

    public StringFilter getRequesternote() {
        return requesternote;
    }

    public StringFilter requesternote() {
        if (requesternote == null) {
            requesternote = new StringFilter();
        }
        return requesternote;
    }

    public void setRequesternote(StringFilter requesternote) {
        this.requesternote = requesternote;
    }

    public StringFilter getManagernote() {
        return managernote;
    }

    public StringFilter managernote() {
        if (managernote == null) {
            managernote = new StringFilter();
        }
        return managernote;
    }

    public void setManagernote(StringFilter managernote) {
        this.managernote = managernote;
    }

    public StringFilter getCurrentstatus() {
        return currentstatus;
    }

    public StringFilter currentstatus() {
        if (currentstatus == null) {
            currentstatus = new StringFilter();
        }
        return currentstatus;
    }

    public void setCurrentstatus(StringFilter currentstatus) {
        this.currentstatus = currentstatus;
    }

    public BooleanFilter getLeavescanceled() {
        return leavescanceled;
    }

    public BooleanFilter leavescanceled() {
        if (leavescanceled == null) {
            leavescanceled = new BooleanFilter();
        }
        return leavescanceled;
    }

    public void setLeavescanceled(BooleanFilter leavescanceled) {
        this.leavescanceled = leavescanceled;
    }

    public InstantFilter getRequestdate() {
        return requestdate;
    }

    public InstantFilter requestdate() {
        if (requestdate == null) {
            requestdate = new InstantFilter();
        }
        return requestdate;
    }

    public void setRequestdate(InstantFilter requestdate) {
        this.requestdate = requestdate;
    }

    public StringFilter getLinkstring() {
        return linkstring;
    }

    public StringFilter linkstring() {
        if (linkstring == null) {
            linkstring = new StringFilter();
        }
        return linkstring;
    }

    public void setLinkstring(StringFilter linkstring) {
        this.linkstring = linkstring;
    }

    public BooleanFilter getLinkused() {
        return linkused;
    }

    public BooleanFilter linkused() {
        if (linkused == null) {
            linkused = new BooleanFilter();
        }
        return linkused;
    }

    public void setLinkused(BooleanFilter linkused) {
        this.linkused = linkused;
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

    public BooleanFilter getIshalfday() {
        return ishalfday;
    }

    public BooleanFilter ishalfday() {
        if (ishalfday == null) {
            ishalfday = new BooleanFilter();
        }
        return ishalfday;
    }

    public void setIshalfday(BooleanFilter ishalfday) {
        this.ishalfday = ishalfday;
    }

    public InstantFilter getActiondate() {
        return actiondate;
    }

    public InstantFilter actiondate() {
        if (actiondate == null) {
            actiondate = new InstantFilter();
        }
        return actiondate;
    }

    public void setActiondate(InstantFilter actiondate) {
        this.actiondate = actiondate;
    }

    public StringFilter getLmstatus() {
        return lmstatus;
    }

    public StringFilter lmstatus() {
        if (lmstatus == null) {
            lmstatus = new StringFilter();
        }
        return lmstatus;
    }

    public void setLmstatus(StringFilter lmstatus) {
        this.lmstatus = lmstatus;
    }

    public StringFilter getPmstatus() {
        return pmstatus;
    }

    public StringFilter pmstatus() {
        if (pmstatus == null) {
            pmstatus = new StringFilter();
        }
        return pmstatus;
    }

    public void setPmstatus(StringFilter pmstatus) {
        this.pmstatus = pmstatus;
    }

    public BooleanFilter getIsbench() {
        return isbench;
    }

    public BooleanFilter isbench() {
        if (isbench == null) {
            isbench = new BooleanFilter();
        }
        return isbench;
    }

    public void setIsbench(BooleanFilter isbench) {
        this.isbench = isbench;
    }

    public BooleanFilter getIsescalated() {
        return isescalated;
    }

    public BooleanFilter isescalated() {
        if (isescalated == null) {
            isescalated = new BooleanFilter();
        }
        return isescalated;
    }

    public void setIsescalated(BooleanFilter isescalated) {
        this.isescalated = isescalated;
    }

    public BooleanFilter getIscommented() {
        return iscommented;
    }

    public BooleanFilter iscommented() {
        if (iscommented == null) {
            iscommented = new BooleanFilter();
        }
        return iscommented;
    }

    public void setIscommented(BooleanFilter iscommented) {
        this.iscommented = iscommented;
    }

    public BooleanFilter getIsreminded() {
        return isreminded;
    }

    public BooleanFilter isreminded() {
        if (isreminded == null) {
            isreminded = new BooleanFilter();
        }
        return isreminded;
    }

    public void setIsreminded(BooleanFilter isreminded) {
        this.isreminded = isreminded;
    }

    public BooleanFilter getIsnotified() {
        return isnotified;
    }

    public BooleanFilter isnotified() {
        if (isnotified == null) {
            isnotified = new BooleanFilter();
        }
        return isnotified;
    }

    public void setIsnotified(BooleanFilter isnotified) {
        this.isnotified = isnotified;
    }

    public BooleanFilter getIsremindedhr() {
        return isremindedhr;
    }

    public BooleanFilter isremindedhr() {
        if (isremindedhr == null) {
            isremindedhr = new BooleanFilter();
        }
        return isremindedhr;
    }

    public void setIsremindedhr(BooleanFilter isremindedhr) {
        this.isremindedhr = isremindedhr;
    }

    public BooleanFilter getIsdm() {
        return isdm;
    }

    public BooleanFilter isdm() {
        if (isdm == null) {
            isdm = new BooleanFilter();
        }
        return isdm;
    }

    public void setIsdm(BooleanFilter isdm) {
        this.isdm = isdm;
    }

    public LongFilter getLeavetypeId() {
        return leavetypeId;
    }

    public LongFilter leavetypeId() {
        if (leavetypeId == null) {
            leavetypeId = new LongFilter();
        }
        return leavetypeId;
    }

    public void setLeavetypeId(LongFilter leavetypeId) {
        this.leavetypeId = leavetypeId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
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
        final LeaveRequestsOldsCriteria that = (LeaveRequestsOldsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(requesternote, that.requesternote) &&
            Objects.equals(managernote, that.managernote) &&
            Objects.equals(currentstatus, that.currentstatus) &&
            Objects.equals(leavescanceled, that.leavescanceled) &&
            Objects.equals(requestdate, that.requestdate) &&
            Objects.equals(linkstring, that.linkstring) &&
            Objects.equals(linkused, that.linkused) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(ishalfday, that.ishalfday) &&
            Objects.equals(actiondate, that.actiondate) &&
            Objects.equals(lmstatus, that.lmstatus) &&
            Objects.equals(pmstatus, that.pmstatus) &&
            Objects.equals(isbench, that.isbench) &&
            Objects.equals(isescalated, that.isescalated) &&
            Objects.equals(iscommented, that.iscommented) &&
            Objects.equals(isreminded, that.isreminded) &&
            Objects.equals(isnotified, that.isnotified) &&
            Objects.equals(isremindedhr, that.isremindedhr) &&
            Objects.equals(isdm, that.isdm) &&
            Objects.equals(leavetypeId, that.leavetypeId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            startdate,
            enddate,
            requesternote,
            managernote,
            currentstatus,
            leavescanceled,
            requestdate,
            linkstring,
            linkused,
            createdat,
            updatedat,
            ishalfday,
            actiondate,
            lmstatus,
            pmstatus,
            isbench,
            isescalated,
            iscommented,
            isreminded,
            isnotified,
            isremindedhr,
            isdm,
            leavetypeId,
            managerId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestsOldsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (requesternote != null ? "requesternote=" + requesternote + ", " : "") +
            (managernote != null ? "managernote=" + managernote + ", " : "") +
            (currentstatus != null ? "currentstatus=" + currentstatus + ", " : "") +
            (leavescanceled != null ? "leavescanceled=" + leavescanceled + ", " : "") +
            (requestdate != null ? "requestdate=" + requestdate + ", " : "") +
            (linkstring != null ? "linkstring=" + linkstring + ", " : "") +
            (linkused != null ? "linkused=" + linkused + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (ishalfday != null ? "ishalfday=" + ishalfday + ", " : "") +
            (actiondate != null ? "actiondate=" + actiondate + ", " : "") +
            (lmstatus != null ? "lmstatus=" + lmstatus + ", " : "") +
            (pmstatus != null ? "pmstatus=" + pmstatus + ", " : "") +
            (isbench != null ? "isbench=" + isbench + ", " : "") +
            (isescalated != null ? "isescalated=" + isescalated + ", " : "") +
            (iscommented != null ? "iscommented=" + iscommented + ", " : "") +
            (isreminded != null ? "isreminded=" + isreminded + ", " : "") +
            (isnotified != null ? "isnotified=" + isnotified + ", " : "") +
            (isremindedhr != null ? "isremindedhr=" + isremindedhr + ", " : "") +
            (isdm != null ? "isdm=" + isdm + ", " : "") +
            (leavetypeId != null ? "leavetypeId=" + leavetypeId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
