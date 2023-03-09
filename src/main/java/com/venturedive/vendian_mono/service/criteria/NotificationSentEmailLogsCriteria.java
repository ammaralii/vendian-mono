package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.NotificationSentEmailLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.NotificationSentEmailLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notification-sent-email-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationSentEmailLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter notificationTemplateId;

    private LongFilter recipientId;

    private Boolean distinct;

    public NotificationSentEmailLogsCriteria() {}

    public NotificationSentEmailLogsCriteria(NotificationSentEmailLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.notificationTemplateId = other.notificationTemplateId == null ? null : other.notificationTemplateId.copy();
        this.recipientId = other.recipientId == null ? null : other.recipientId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationSentEmailLogsCriteria copy() {
        return new NotificationSentEmailLogsCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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

    public LongFilter getNotificationTemplateId() {
        return notificationTemplateId;
    }

    public LongFilter notificationTemplateId() {
        if (notificationTemplateId == null) {
            notificationTemplateId = new LongFilter();
        }
        return notificationTemplateId;
    }

    public void setNotificationTemplateId(LongFilter notificationTemplateId) {
        this.notificationTemplateId = notificationTemplateId;
    }

    public LongFilter getRecipientId() {
        return recipientId;
    }

    public LongFilter recipientId() {
        if (recipientId == null) {
            recipientId = new LongFilter();
        }
        return recipientId;
    }

    public void setRecipientId(LongFilter recipientId) {
        this.recipientId = recipientId;
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
        final NotificationSentEmailLogsCriteria that = (NotificationSentEmailLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(notificationTemplateId, that.notificationTemplateId) &&
            Objects.equals(recipientId, that.recipientId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, createdAt, updatedAt, deletedAt, version, notificationTemplateId, recipientId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationSentEmailLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (notificationTemplateId != null ? "notificationTemplateId=" + notificationTemplateId + ", " : "") +
            (recipientId != null ? "recipientId=" + recipientId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
