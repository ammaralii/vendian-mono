package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.NotificationTemplates} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.NotificationTemplatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notification-templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationTemplatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter type;

    private StringFilter subject;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter notificationEventId;

    private LongFilter notificationsentemaillogsNotificationtemplateId;

    private Boolean distinct;

    public NotificationTemplatesCriteria() {}

    public NotificationTemplatesCriteria(NotificationTemplatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.notificationEventId = other.notificationEventId == null ? null : other.notificationEventId.copy();
        this.notificationsentemaillogsNotificationtemplateId =
            other.notificationsentemaillogsNotificationtemplateId == null
                ? null
                : other.notificationsentemaillogsNotificationtemplateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationTemplatesCriteria copy() {
        return new NotificationTemplatesCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public StringFilter getSubject() {
        return subject;
    }

    public StringFilter subject() {
        if (subject == null) {
            subject = new StringFilter();
        }
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public InstantFilter getEffDate() {
        return effDate;
    }

    public InstantFilter effDate() {
        if (effDate == null) {
            effDate = new InstantFilter();
        }
        return effDate;
    }

    public void setEffDate(InstantFilter effDate) {
        this.effDate = effDate;
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

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
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

    public LongFilter getNotificationEventId() {
        return notificationEventId;
    }

    public LongFilter notificationEventId() {
        if (notificationEventId == null) {
            notificationEventId = new LongFilter();
        }
        return notificationEventId;
    }

    public void setNotificationEventId(LongFilter notificationEventId) {
        this.notificationEventId = notificationEventId;
    }

    public LongFilter getNotificationsentemaillogsNotificationtemplateId() {
        return notificationsentemaillogsNotificationtemplateId;
    }

    public LongFilter notificationsentemaillogsNotificationtemplateId() {
        if (notificationsentemaillogsNotificationtemplateId == null) {
            notificationsentemaillogsNotificationtemplateId = new LongFilter();
        }
        return notificationsentemaillogsNotificationtemplateId;
    }

    public void setNotificationsentemaillogsNotificationtemplateId(LongFilter notificationsentemaillogsNotificationtemplateId) {
        this.notificationsentemaillogsNotificationtemplateId = notificationsentemaillogsNotificationtemplateId;
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
        final NotificationTemplatesCriteria that = (NotificationTemplatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(notificationEventId, that.notificationEventId) &&
            Objects.equals(notificationsentemaillogsNotificationtemplateId, that.notificationsentemaillogsNotificationtemplateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            type,
            subject,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            notificationEventId,
            notificationsentemaillogsNotificationtemplateId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (subject != null ? "subject=" + subject + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (notificationEventId != null ? "notificationEventId=" + notificationEventId + ", " : "") +
            (notificationsentemaillogsNotificationtemplateId != null ? "notificationsentemaillogsNotificationtemplateId=" + notificationsentemaillogsNotificationtemplateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
