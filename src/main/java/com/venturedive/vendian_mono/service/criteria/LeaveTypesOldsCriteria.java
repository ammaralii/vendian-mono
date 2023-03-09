package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveTypesOlds} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveTypesOldsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-types-olds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypesOldsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isactive;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter leaverequestsoldsLeavetypeId;

    private Boolean distinct;

    public LeaveTypesOldsCriteria() {}

    public LeaveTypesOldsCriteria(LeaveTypesOldsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.leaverequestsoldsLeavetypeId = other.leaverequestsoldsLeavetypeId == null ? null : other.leaverequestsoldsLeavetypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveTypesOldsCriteria copy() {
        return new LeaveTypesOldsCriteria(this);
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

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public LongFilter getLeaverequestsoldsLeavetypeId() {
        return leaverequestsoldsLeavetypeId;
    }

    public LongFilter leaverequestsoldsLeavetypeId() {
        if (leaverequestsoldsLeavetypeId == null) {
            leaverequestsoldsLeavetypeId = new LongFilter();
        }
        return leaverequestsoldsLeavetypeId;
    }

    public void setLeaverequestsoldsLeavetypeId(LongFilter leaverequestsoldsLeavetypeId) {
        this.leaverequestsoldsLeavetypeId = leaverequestsoldsLeavetypeId;
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
        final LeaveTypesOldsCriteria that = (LeaveTypesOldsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(leaverequestsoldsLeavetypeId, that.leaverequestsoldsLeavetypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isactive, createdat, updatedat, leaverequestsoldsLeavetypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypesOldsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (leaverequestsoldsLeavetypeId != null ? "leaverequestsoldsLeavetypeId=" + leaverequestsoldsLeavetypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
