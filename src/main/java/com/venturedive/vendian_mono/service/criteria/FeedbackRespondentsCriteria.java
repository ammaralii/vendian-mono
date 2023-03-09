package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.FeedbackRespondents} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.FeedbackRespondentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feedback-respondents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackRespondentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter category;

    private BooleanFilter hasresponded;

    private InstantFilter respondedat;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private LongFilter requestId;

    private LongFilter feedbackemailsRespondentId;

    private LongFilter feedbackresponsesRespondentId;

    private Boolean distinct;

    public FeedbackRespondentsCriteria() {}

    public FeedbackRespondentsCriteria(FeedbackRespondentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.hasresponded = other.hasresponded == null ? null : other.hasresponded.copy();
        this.respondedat = other.respondedat == null ? null : other.respondedat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
        this.feedbackemailsRespondentId = other.feedbackemailsRespondentId == null ? null : other.feedbackemailsRespondentId.copy();
        this.feedbackresponsesRespondentId =
            other.feedbackresponsesRespondentId == null ? null : other.feedbackresponsesRespondentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FeedbackRespondentsCriteria copy() {
        return new FeedbackRespondentsCriteria(this);
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

    public IntegerFilter getCategory() {
        return category;
    }

    public IntegerFilter category() {
        if (category == null) {
            category = new IntegerFilter();
        }
        return category;
    }

    public void setCategory(IntegerFilter category) {
        this.category = category;
    }

    public BooleanFilter getHasresponded() {
        return hasresponded;
    }

    public BooleanFilter hasresponded() {
        if (hasresponded == null) {
            hasresponded = new BooleanFilter();
        }
        return hasresponded;
    }

    public void setHasresponded(BooleanFilter hasresponded) {
        this.hasresponded = hasresponded;
    }

    public InstantFilter getRespondedat() {
        return respondedat;
    }

    public InstantFilter respondedat() {
        if (respondedat == null) {
            respondedat = new InstantFilter();
        }
        return respondedat;
    }

    public void setRespondedat(InstantFilter respondedat) {
        this.respondedat = respondedat;
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

    public LongFilter getRequestId() {
        return requestId;
    }

    public LongFilter requestId() {
        if (requestId == null) {
            requestId = new LongFilter();
        }
        return requestId;
    }

    public void setRequestId(LongFilter requestId) {
        this.requestId = requestId;
    }

    public LongFilter getFeedbackemailsRespondentId() {
        return feedbackemailsRespondentId;
    }

    public LongFilter feedbackemailsRespondentId() {
        if (feedbackemailsRespondentId == null) {
            feedbackemailsRespondentId = new LongFilter();
        }
        return feedbackemailsRespondentId;
    }

    public void setFeedbackemailsRespondentId(LongFilter feedbackemailsRespondentId) {
        this.feedbackemailsRespondentId = feedbackemailsRespondentId;
    }

    public LongFilter getFeedbackresponsesRespondentId() {
        return feedbackresponsesRespondentId;
    }

    public LongFilter feedbackresponsesRespondentId() {
        if (feedbackresponsesRespondentId == null) {
            feedbackresponsesRespondentId = new LongFilter();
        }
        return feedbackresponsesRespondentId;
    }

    public void setFeedbackresponsesRespondentId(LongFilter feedbackresponsesRespondentId) {
        this.feedbackresponsesRespondentId = feedbackresponsesRespondentId;
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
        final FeedbackRespondentsCriteria that = (FeedbackRespondentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(hasresponded, that.hasresponded) &&
            Objects.equals(respondedat, that.respondedat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(requestId, that.requestId) &&
            Objects.equals(feedbackemailsRespondentId, that.feedbackemailsRespondentId) &&
            Objects.equals(feedbackresponsesRespondentId, that.feedbackresponsesRespondentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            category,
            hasresponded,
            respondedat,
            createdat,
            updatedat,
            employeeId,
            requestId,
            feedbackemailsRespondentId,
            feedbackresponsesRespondentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackRespondentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (hasresponded != null ? "hasresponded=" + hasresponded + ", " : "") +
            (respondedat != null ? "respondedat=" + respondedat + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (requestId != null ? "requestId=" + requestId + ", " : "") +
            (feedbackemailsRespondentId != null ? "feedbackemailsRespondentId=" + feedbackemailsRespondentId + ", " : "") +
            (feedbackresponsesRespondentId != null ? "feedbackresponsesRespondentId=" + feedbackresponsesRespondentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
