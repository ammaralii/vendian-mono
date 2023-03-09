package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.CompensationBenefits} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.CompensationBenefitsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compensation-benefits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompensationBenefitsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter amount;

    private BooleanFilter isdeleted;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter benefitId;

    private LongFilter employeecompensationId;

    private Boolean distinct;

    public CompensationBenefitsCriteria() {}

    public CompensationBenefitsCriteria(CompensationBenefitsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.isdeleted = other.isdeleted == null ? null : other.isdeleted.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.benefitId = other.benefitId == null ? null : other.benefitId.copy();
        this.employeecompensationId = other.employeecompensationId == null ? null : other.employeecompensationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompensationBenefitsCriteria copy() {
        return new CompensationBenefitsCriteria(this);
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

    public FloatFilter getAmount() {
        return amount;
    }

    public FloatFilter amount() {
        if (amount == null) {
            amount = new FloatFilter();
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public BooleanFilter getIsdeleted() {
        return isdeleted;
    }

    public BooleanFilter isdeleted() {
        if (isdeleted == null) {
            isdeleted = new BooleanFilter();
        }
        return isdeleted;
    }

    public void setIsdeleted(BooleanFilter isdeleted) {
        this.isdeleted = isdeleted;
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

    public LongFilter getBenefitId() {
        return benefitId;
    }

    public LongFilter benefitId() {
        if (benefitId == null) {
            benefitId = new LongFilter();
        }
        return benefitId;
    }

    public void setBenefitId(LongFilter benefitId) {
        this.benefitId = benefitId;
    }

    public LongFilter getEmployeecompensationId() {
        return employeecompensationId;
    }

    public LongFilter employeecompensationId() {
        if (employeecompensationId == null) {
            employeecompensationId = new LongFilter();
        }
        return employeecompensationId;
    }

    public void setEmployeecompensationId(LongFilter employeecompensationId) {
        this.employeecompensationId = employeecompensationId;
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
        final CompensationBenefitsCriteria that = (CompensationBenefitsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isdeleted, that.isdeleted) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(benefitId, that.benefitId) &&
            Objects.equals(employeecompensationId, that.employeecompensationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, isdeleted, createdat, updatedat, benefitId, employeecompensationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompensationBenefitsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (isdeleted != null ? "isdeleted=" + isdeleted + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (benefitId != null ? "benefitId=" + benefitId + ", " : "") +
            (employeecompensationId != null ? "employeecompensationId=" + employeecompensationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
