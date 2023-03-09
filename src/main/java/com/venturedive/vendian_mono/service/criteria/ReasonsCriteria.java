package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Reasons} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ReasonsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reasons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReasonsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isdefault;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeecompensationReasonId;

    private Boolean distinct;

    public ReasonsCriteria() {}

    public ReasonsCriteria(ReasonsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isdefault = other.isdefault == null ? null : other.isdefault.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeecompensationReasonId = other.employeecompensationReasonId == null ? null : other.employeecompensationReasonId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReasonsCriteria copy() {
        return new ReasonsCriteria(this);
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

    public BooleanFilter getIsdefault() {
        return isdefault;
    }

    public BooleanFilter isdefault() {
        if (isdefault == null) {
            isdefault = new BooleanFilter();
        }
        return isdefault;
    }

    public void setIsdefault(BooleanFilter isdefault) {
        this.isdefault = isdefault;
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

    public LongFilter getEmployeecompensationReasonId() {
        return employeecompensationReasonId;
    }

    public LongFilter employeecompensationReasonId() {
        if (employeecompensationReasonId == null) {
            employeecompensationReasonId = new LongFilter();
        }
        return employeecompensationReasonId;
    }

    public void setEmployeecompensationReasonId(LongFilter employeecompensationReasonId) {
        this.employeecompensationReasonId = employeecompensationReasonId;
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
        final ReasonsCriteria that = (ReasonsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isdefault, that.isdefault) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeecompensationReasonId, that.employeecompensationReasonId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isdefault, createdat, updatedat, employeecompensationReasonId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReasonsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (isdefault != null ? "isdefault=" + isdefault + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeecompensationReasonId != null ? "employeecompensationReasonId=" + employeecompensationReasonId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
