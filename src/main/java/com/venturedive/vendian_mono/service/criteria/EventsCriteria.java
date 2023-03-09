package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Events} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EventsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private LongFilter zemployeeprojectsEventId;

    private Boolean distinct;

    public EventsCriteria() {}

    public EventsCriteria(EventsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.zemployeeprojectsEventId = other.zemployeeprojectsEventId == null ? null : other.zemployeeprojectsEventId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventsCriteria copy() {
        return new EventsCriteria(this);
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

    public LongFilter getZemployeeprojectsEventId() {
        return zemployeeprojectsEventId;
    }

    public LongFilter zemployeeprojectsEventId() {
        if (zemployeeprojectsEventId == null) {
            zemployeeprojectsEventId = new LongFilter();
        }
        return zemployeeprojectsEventId;
    }

    public void setZemployeeprojectsEventId(LongFilter zemployeeprojectsEventId) {
        this.zemployeeprojectsEventId = zemployeeprojectsEventId;
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
        final EventsCriteria that = (EventsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(zemployeeprojectsEventId, that.zemployeeprojectsEventId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, zemployeeprojectsEventId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (zemployeeprojectsEventId != null ? "zemployeeprojectsEventId=" + zemployeeprojectsEventId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
