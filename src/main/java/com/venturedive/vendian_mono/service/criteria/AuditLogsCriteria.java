package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.AuditLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.AuditLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /audit-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter event;

    private InstantFilter eventTime;

    private StringFilter description;

    private StringFilter oldChange;

    private StringFilter newChange;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private IntegerFilter version;

    private Boolean distinct;

    public AuditLogsCriteria() {}

    public AuditLogsCriteria(AuditLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.event = other.event == null ? null : other.event.copy();
        this.eventTime = other.eventTime == null ? null : other.eventTime.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.oldChange = other.oldChange == null ? null : other.oldChange.copy();
        this.newChange = other.newChange == null ? null : other.newChange.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AuditLogsCriteria copy() {
        return new AuditLogsCriteria(this);
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

    public StringFilter getEvent() {
        return event;
    }

    public StringFilter event() {
        if (event == null) {
            event = new StringFilter();
        }
        return event;
    }

    public void setEvent(StringFilter event) {
        this.event = event;
    }

    public InstantFilter getEventTime() {
        return eventTime;
    }

    public InstantFilter eventTime() {
        if (eventTime == null) {
            eventTime = new InstantFilter();
        }
        return eventTime;
    }

    public void setEventTime(InstantFilter eventTime) {
        this.eventTime = eventTime;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getOldChange() {
        return oldChange;
    }

    public StringFilter oldChange() {
        if (oldChange == null) {
            oldChange = new StringFilter();
        }
        return oldChange;
    }

    public void setOldChange(StringFilter oldChange) {
        this.oldChange = oldChange;
    }

    public StringFilter getNewChange() {
        return newChange;
    }

    public StringFilter newChange() {
        if (newChange == null) {
            newChange = new StringFilter();
        }
        return newChange;
    }

    public void setNewChange(StringFilter newChange) {
        this.newChange = newChange;
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
        final AuditLogsCriteria that = (AuditLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(event, that.event) &&
            Objects.equals(eventTime, that.eventTime) &&
            Objects.equals(description, that.description) &&
            Objects.equals(oldChange, that.oldChange) &&
            Objects.equals(newChange, that.newChange) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, eventTime, description, oldChange, newChange, createdAt, updatedAt, version, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (event != null ? "event=" + event + ", " : "") +
            (eventTime != null ? "eventTime=" + eventTime + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (oldChange != null ? "oldChange=" + oldChange + ", " : "") +
            (newChange != null ? "newChange=" + newChange + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
