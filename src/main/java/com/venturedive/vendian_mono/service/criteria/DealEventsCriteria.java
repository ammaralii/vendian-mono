package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealEvents} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealEventsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealEventsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eventtype;

    private InstantFilter createdat;

    private Boolean distinct;

    public DealEventsCriteria() {}

    public DealEventsCriteria(DealEventsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eventtype = other.eventtype == null ? null : other.eventtype.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealEventsCriteria copy() {
        return new DealEventsCriteria(this);
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

    public StringFilter getEventtype() {
        return eventtype;
    }

    public StringFilter eventtype() {
        if (eventtype == null) {
            eventtype = new StringFilter();
        }
        return eventtype;
    }

    public void setEventtype(StringFilter eventtype) {
        this.eventtype = eventtype;
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
        final DealEventsCriteria that = (DealEventsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eventtype, that.eventtype) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventtype, createdat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealEventsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eventtype != null ? "eventtype=" + eventtype + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
