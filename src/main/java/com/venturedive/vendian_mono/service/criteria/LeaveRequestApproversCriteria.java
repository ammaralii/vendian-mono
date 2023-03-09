package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveRequestApprovers} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveRequestApproversResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-request-approvers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestApproversCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reference;

    private StringFilter as;

    private StringFilter comments;

    private StringFilter approverGroup;

    private IntegerFilter priority;

    private InstantFilter statusDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter leaveRequestId;

    private LongFilter userId;

    private LongFilter statusId;

    private Boolean distinct;

    public LeaveRequestApproversCriteria() {}

    public LeaveRequestApproversCriteria(LeaveRequestApproversCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.as = other.as == null ? null : other.as.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.approverGroup = other.approverGroup == null ? null : other.approverGroup.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaveRequestId = other.leaveRequestId == null ? null : other.leaveRequestId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveRequestApproversCriteria copy() {
        return new LeaveRequestApproversCriteria(this);
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

    public StringFilter getApproverGroup() {
        return approverGroup;
    }

    public StringFilter approverGroup() {
        if (approverGroup == null) {
            approverGroup = new StringFilter();
        }
        return approverGroup;
    }

    public void setApproverGroup(StringFilter approverGroup) {
        this.approverGroup = approverGroup;
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

    public InstantFilter getStatusDate() {
        return statusDate;
    }

    public InstantFilter statusDate() {
        if (statusDate == null) {
            statusDate = new InstantFilter();
        }
        return statusDate;
    }

    public void setStatusDate(InstantFilter statusDate) {
        this.statusDate = statusDate;
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

    public LongFilter getLeaveRequestId() {
        return leaveRequestId;
    }

    public LongFilter leaveRequestId() {
        if (leaveRequestId == null) {
            leaveRequestId = new LongFilter();
        }
        return leaveRequestId;
    }

    public void setLeaveRequestId(LongFilter leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
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
        final LeaveRequestApproversCriteria that = (LeaveRequestApproversCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(as, that.as) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(approverGroup, that.approverGroup) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaveRequestId, that.leaveRequestId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            reference,
            as,
            comments,
            approverGroup,
            priority,
            statusDate,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            leaveRequestId,
            userId,
            statusId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestApproversCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (reference != null ? "reference=" + reference + ", " : "") +
            (as != null ? "as=" + as + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (approverGroup != null ? "approverGroup=" + approverGroup + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaveRequestId != null ? "leaveRequestId=" + leaveRequestId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
