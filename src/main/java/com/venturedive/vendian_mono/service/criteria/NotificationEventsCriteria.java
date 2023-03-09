package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.NotificationEvents} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.NotificationEventsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notification-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationEventsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter notificationmergefieldsNotificationeventId;

    private LongFilter notificationtemplatesNotificationeventId;

    private Boolean distinct;

    public NotificationEventsCriteria() {}

    public NotificationEventsCriteria(NotificationEventsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.notificationmergefieldsNotificationeventId =
            other.notificationmergefieldsNotificationeventId == null ? null : other.notificationmergefieldsNotificationeventId.copy();
        this.notificationtemplatesNotificationeventId =
            other.notificationtemplatesNotificationeventId == null ? null : other.notificationtemplatesNotificationeventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationEventsCriteria copy() {
        return new NotificationEventsCriteria(this);
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

    public LongFilter getNotificationmergefieldsNotificationeventId() {
        return notificationmergefieldsNotificationeventId;
    }

    public LongFilter notificationmergefieldsNotificationeventId() {
        if (notificationmergefieldsNotificationeventId == null) {
            notificationmergefieldsNotificationeventId = new LongFilter();
        }
        return notificationmergefieldsNotificationeventId;
    }

    public void setNotificationmergefieldsNotificationeventId(LongFilter notificationmergefieldsNotificationeventId) {
        this.notificationmergefieldsNotificationeventId = notificationmergefieldsNotificationeventId;
    }

    public LongFilter getNotificationtemplatesNotificationeventId() {
        return notificationtemplatesNotificationeventId;
    }

    public LongFilter notificationtemplatesNotificationeventId() {
        if (notificationtemplatesNotificationeventId == null) {
            notificationtemplatesNotificationeventId = new LongFilter();
        }
        return notificationtemplatesNotificationeventId;
    }

    public void setNotificationtemplatesNotificationeventId(LongFilter notificationtemplatesNotificationeventId) {
        this.notificationtemplatesNotificationeventId = notificationtemplatesNotificationeventId;
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
        final NotificationEventsCriteria that = (NotificationEventsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(notificationmergefieldsNotificationeventId, that.notificationmergefieldsNotificationeventId) &&
            Objects.equals(notificationtemplatesNotificationeventId, that.notificationtemplatesNotificationeventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            notificationmergefieldsNotificationeventId,
            notificationtemplatesNotificationeventId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationEventsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (notificationmergefieldsNotificationeventId != null ? "notificationmergefieldsNotificationeventId=" + notificationmergefieldsNotificationeventId + ", " : "") +
            (notificationtemplatesNotificationeventId != null ? "notificationtemplatesNotificationeventId=" + notificationtemplatesNotificationeventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
