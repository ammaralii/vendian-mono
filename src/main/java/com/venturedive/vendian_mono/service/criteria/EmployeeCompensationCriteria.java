package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeCompensation} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeCompensationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-compensations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCompensationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private StringFilter type;

    private InstantFilter commitmentuntil;

    private StringFilter comments;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter reasoncomments;

    private LongFilter employeeId;

    private LongFilter reasonId;

    private LongFilter compensationbenefitsEmployeecompensationId;

    private Boolean distinct;

    public EmployeeCompensationCriteria() {}

    public EmployeeCompensationCriteria(EmployeeCompensationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.commitmentuntil = other.commitmentuntil == null ? null : other.commitmentuntil.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.reasoncomments = other.reasoncomments == null ? null : other.reasoncomments.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.reasonId = other.reasonId == null ? null : other.reasonId.copy();
        this.compensationbenefitsEmployeecompensationId =
            other.compensationbenefitsEmployeecompensationId == null ? null : other.compensationbenefitsEmployeecompensationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCompensationCriteria copy() {
        return new EmployeeCompensationCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public InstantFilter getCommitmentuntil() {
        return commitmentuntil;
    }

    public InstantFilter commitmentuntil() {
        if (commitmentuntil == null) {
            commitmentuntil = new InstantFilter();
        }
        return commitmentuntil;
    }

    public void setCommitmentuntil(InstantFilter commitmentuntil) {
        this.commitmentuntil = commitmentuntil;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
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

    public StringFilter getReasoncomments() {
        return reasoncomments;
    }

    public StringFilter reasoncomments() {
        if (reasoncomments == null) {
            reasoncomments = new StringFilter();
        }
        return reasoncomments;
    }

    public void setReasoncomments(StringFilter reasoncomments) {
        this.reasoncomments = reasoncomments;
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

    public LongFilter getReasonId() {
        return reasonId;
    }

    public LongFilter reasonId() {
        if (reasonId == null) {
            reasonId = new LongFilter();
        }
        return reasonId;
    }

    public void setReasonId(LongFilter reasonId) {
        this.reasonId = reasonId;
    }

    public LongFilter getCompensationbenefitsEmployeecompensationId() {
        return compensationbenefitsEmployeecompensationId;
    }

    public LongFilter compensationbenefitsEmployeecompensationId() {
        if (compensationbenefitsEmployeecompensationId == null) {
            compensationbenefitsEmployeecompensationId = new LongFilter();
        }
        return compensationbenefitsEmployeecompensationId;
    }

    public void setCompensationbenefitsEmployeecompensationId(LongFilter compensationbenefitsEmployeecompensationId) {
        this.compensationbenefitsEmployeecompensationId = compensationbenefitsEmployeecompensationId;
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
        final EmployeeCompensationCriteria that = (EmployeeCompensationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(type, that.type) &&
            Objects.equals(commitmentuntil, that.commitmentuntil) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(reasoncomments, that.reasoncomments) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(reasonId, that.reasonId) &&
            Objects.equals(compensationbenefitsEmployeecompensationId, that.compensationbenefitsEmployeecompensationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            type,
            commitmentuntil,
            comments,
            createdat,
            updatedat,
            reasoncomments,
            employeeId,
            reasonId,
            compensationbenefitsEmployeecompensationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCompensationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (commitmentuntil != null ? "commitmentuntil=" + commitmentuntil + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (reasoncomments != null ? "reasoncomments=" + reasoncomments + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (reasonId != null ? "reasonId=" + reasonId + ", " : "") +
            (compensationbenefitsEmployeecompensationId != null ? "compensationbenefitsEmployeecompensationId=" + compensationbenefitsEmployeecompensationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
