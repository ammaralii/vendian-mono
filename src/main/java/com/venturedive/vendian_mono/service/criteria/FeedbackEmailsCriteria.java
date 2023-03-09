package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.FeedbackEmails} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.FeedbackEmailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feedback-emails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackEmailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter to;

    private StringFilter body;

    private IntegerFilter status;

    private InstantFilter sentat;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter respondentId;

    private Boolean distinct;

    public FeedbackEmailsCriteria() {}

    public FeedbackEmailsCriteria(FeedbackEmailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.body = other.body == null ? null : other.body.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.sentat = other.sentat == null ? null : other.sentat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.respondentId = other.respondentId == null ? null : other.respondentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FeedbackEmailsCriteria copy() {
        return new FeedbackEmailsCriteria(this);
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

    public StringFilter getTo() {
        return to;
    }

    public StringFilter to() {
        if (to == null) {
            to = new StringFilter();
        }
        return to;
    }

    public void setTo(StringFilter to) {
        this.to = to;
    }

    public StringFilter getBody() {
        return body;
    }

    public StringFilter body() {
        if (body == null) {
            body = new StringFilter();
        }
        return body;
    }

    public void setBody(StringFilter body) {
        this.body = body;
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

    public InstantFilter getSentat() {
        return sentat;
    }

    public InstantFilter sentat() {
        if (sentat == null) {
            sentat = new InstantFilter();
        }
        return sentat;
    }

    public void setSentat(InstantFilter sentat) {
        this.sentat = sentat;
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

    public LongFilter getRespondentId() {
        return respondentId;
    }

    public LongFilter respondentId() {
        if (respondentId == null) {
            respondentId = new LongFilter();
        }
        return respondentId;
    }

    public void setRespondentId(LongFilter respondentId) {
        this.respondentId = respondentId;
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
        final FeedbackEmailsCriteria that = (FeedbackEmailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(to, that.to) &&
            Objects.equals(body, that.body) &&
            Objects.equals(status, that.status) &&
            Objects.equals(sentat, that.sentat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(respondentId, that.respondentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, to, body, status, sentat, createdat, updatedat, respondentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackEmailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (to != null ? "to=" + to + ", " : "") +
            (body != null ? "body=" + body + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (sentat != null ? "sentat=" + sentat + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (respondentId != null ? "respondentId=" + respondentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
