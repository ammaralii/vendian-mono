package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Approvers} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ApproversResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approvers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproversCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userId;

    private StringFilter reference;

    private StringFilter as;

    private StringFilter comment;

    private StringFilter status;

    private InstantFilter stausDate;

    private IntegerFilter priority;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter approverGroupId;

    private Boolean distinct;

    public ApproversCriteria() {}

    public ApproversCriteria(ApproversCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.as = other.as == null ? null : other.as.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.stausDate = other.stausDate == null ? null : other.stausDate.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.approverGroupId = other.approverGroupId == null ? null : other.approverGroupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApproversCriteria copy() {
        return new ApproversCriteria(this);
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

    public StringFilter getUserId() {
        return userId;
    }

    public StringFilter userId() {
        if (userId == null) {
            userId = new StringFilter();
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getReference() {
        return reference;
    }

    public StringFilter reference() {
        if (reference == null) {
            reference = new StringFilter();
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getAs() {
        return as;
    }

    public StringFilter as() {
        if (as == null) {
            as = new StringFilter();
        }
        return as;
    }

    public void setAs(StringFilter as) {
        this.as = as;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getStausDate() {
        return stausDate;
    }

    public InstantFilter stausDate() {
        if (stausDate == null) {
            stausDate = new InstantFilter();
        }
        return stausDate;
    }

    public void setStausDate(InstantFilter stausDate) {
        this.stausDate = stausDate;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public IntegerFilter priority() {
        if (priority == null) {
            priority = new IntegerFilter();
        }
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            updatedAt = new InstantFilter();
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public InstantFilter deletedAt() {
        if (deletedAt == null) {
            deletedAt = new InstantFilter();
        }
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public IntegerFilter version() {
        if (version == null) {
            version = new IntegerFilter();
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public LongFilter getApproverGroupId() {
        return approverGroupId;
    }

    public LongFilter approverGroupId() {
        if (approverGroupId == null) {
            approverGroupId = new LongFilter();
        }
        return approverGroupId;
    }

    public void setApproverGroupId(LongFilter approverGroupId) {
        this.approverGroupId = approverGroupId;
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
        final ApproversCriteria that = (ApproversCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(as, that.as) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(status, that.status) &&
            Objects.equals(stausDate, that.stausDate) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(approverGroupId, that.approverGroupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userId,
            reference,
            as,
            comment,
            status,
            stausDate,
            priority,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            approverGroupId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproversCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (reference != null ? "reference=" + reference + ", " : "") +
            (as != null ? "as=" + as + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (stausDate != null ? "stausDate=" + stausDate + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (approverGroupId != null ? "approverGroupId=" + approverGroupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
