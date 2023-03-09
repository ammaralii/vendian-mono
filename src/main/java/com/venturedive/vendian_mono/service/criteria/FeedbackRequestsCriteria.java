package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.FeedbackRequests} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.FeedbackRequestsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feedback-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackRequestsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter status;

    private BooleanFilter isreportavailable;

    private StringFilter reportpath;

    private InstantFilter approvedat;

    private InstantFilter expiredat;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private LongFilter linemanagerId;

    private LongFilter feedbackrespondentsRequestId;

    private Boolean distinct;

    public FeedbackRequestsCriteria() {}

    public FeedbackRequestsCriteria(FeedbackRequestsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.isreportavailable = other.isreportavailable == null ? null : other.isreportavailable.copy();
        this.reportpath = other.reportpath == null ? null : other.reportpath.copy();
        this.approvedat = other.approvedat == null ? null : other.approvedat.copy();
        this.expiredat = other.expiredat == null ? null : other.expiredat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.linemanagerId = other.linemanagerId == null ? null : other.linemanagerId.copy();
        this.feedbackrespondentsRequestId = other.feedbackrespondentsRequestId == null ? null : other.feedbackrespondentsRequestId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FeedbackRequestsCriteria copy() {
        return new FeedbackRequestsCriteria(this);
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

    public IntegerFilter getStatus() {
        return status;
    }

    public IntegerFilter status() {
        if (status == null) {
            status = new IntegerFilter();
        }
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public BooleanFilter getIsreportavailable() {
        return isreportavailable;
    }

    public BooleanFilter isreportavailable() {
        if (isreportavailable == null) {
            isreportavailable = new BooleanFilter();
        }
        return isreportavailable;
    }

    public void setIsreportavailable(BooleanFilter isreportavailable) {
        this.isreportavailable = isreportavailable;
    }

    public StringFilter getReportpath() {
        return reportpath;
    }

    public StringFilter reportpath() {
        if (reportpath == null) {
            reportpath = new StringFilter();
        }
        return reportpath;
    }

    public void setReportpath(StringFilter reportpath) {
        this.reportpath = reportpath;
    }

    public InstantFilter getApprovedat() {
        return approvedat;
    }

    public InstantFilter approvedat() {
        if (approvedat == null) {
            approvedat = new InstantFilter();
        }
        return approvedat;
    }

    public void setApprovedat(InstantFilter approvedat) {
        this.approvedat = approvedat;
    }

    public InstantFilter getExpiredat() {
        return expiredat;
    }

    public InstantFilter expiredat() {
        if (expiredat == null) {
            expiredat = new InstantFilter();
        }
        return expiredat;
    }

    public void setExpiredat(InstantFilter expiredat) {
        this.expiredat = expiredat;
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

    public LongFilter getLinemanagerId() {
        return linemanagerId;
    }

    public LongFilter linemanagerId() {
        if (linemanagerId == null) {
            linemanagerId = new LongFilter();
        }
        return linemanagerId;
    }

    public void setLinemanagerId(LongFilter linemanagerId) {
        this.linemanagerId = linemanagerId;
    }

    public LongFilter getFeedbackrespondentsRequestId() {
        return feedbackrespondentsRequestId;
    }

    public LongFilter feedbackrespondentsRequestId() {
        if (feedbackrespondentsRequestId == null) {
            feedbackrespondentsRequestId = new LongFilter();
        }
        return feedbackrespondentsRequestId;
    }

    public void setFeedbackrespondentsRequestId(LongFilter feedbackrespondentsRequestId) {
        this.feedbackrespondentsRequestId = feedbackrespondentsRequestId;
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
        final FeedbackRequestsCriteria that = (FeedbackRequestsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(isreportavailable, that.isreportavailable) &&
            Objects.equals(reportpath, that.reportpath) &&
            Objects.equals(approvedat, that.approvedat) &&
            Objects.equals(expiredat, that.expiredat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(linemanagerId, that.linemanagerId) &&
            Objects.equals(feedbackrespondentsRequestId, that.feedbackrespondentsRequestId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            status,
            isreportavailable,
            reportpath,
            approvedat,
            expiredat,
            createdat,
            updatedat,
            employeeId,
            linemanagerId,
            feedbackrespondentsRequestId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackRequestsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (isreportavailable != null ? "isreportavailable=" + isreportavailable + ", " : "") +
            (reportpath != null ? "reportpath=" + reportpath + ", " : "") +
            (approvedat != null ? "approvedat=" + approvedat + ", " : "") +
            (expiredat != null ? "expiredat=" + expiredat + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (linemanagerId != null ? "linemanagerId=" + linemanagerId + ", " : "") +
            (feedbackrespondentsRequestId != null ? "feedbackrespondentsRequestId=" + feedbackrespondentsRequestId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
