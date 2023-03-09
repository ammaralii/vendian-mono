package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeavesOlds} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeavesOldsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leaves-olds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavesOldsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter annualtotal;

    private BigDecimalFilter annualused;

    private BigDecimalFilter annualadjustments;

    private BigDecimalFilter casualtotal;

    private BigDecimalFilter casualused;

    private BigDecimalFilter sicktotal;

    private BigDecimalFilter sickused;

    private BigDecimalFilter annualtotalnextyear;

    private BigDecimalFilter annualusednextyear;

    private BigDecimalFilter casualtotalnextyear;

    private BigDecimalFilter casualusednextyear;

    private BigDecimalFilter sicktotalnextyear;

    private BigDecimalFilter sickusednextyear;

    private BigDecimalFilter carryforward;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeesLeaveId;

    private Boolean distinct;

    public LeavesOldsCriteria() {}

    public LeavesOldsCriteria(LeavesOldsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.annualtotal = other.annualtotal == null ? null : other.annualtotal.copy();
        this.annualused = other.annualused == null ? null : other.annualused.copy();
        this.annualadjustments = other.annualadjustments == null ? null : other.annualadjustments.copy();
        this.casualtotal = other.casualtotal == null ? null : other.casualtotal.copy();
        this.casualused = other.casualused == null ? null : other.casualused.copy();
        this.sicktotal = other.sicktotal == null ? null : other.sicktotal.copy();
        this.sickused = other.sickused == null ? null : other.sickused.copy();
        this.annualtotalnextyear = other.annualtotalnextyear == null ? null : other.annualtotalnextyear.copy();
        this.annualusednextyear = other.annualusednextyear == null ? null : other.annualusednextyear.copy();
        this.casualtotalnextyear = other.casualtotalnextyear == null ? null : other.casualtotalnextyear.copy();
        this.casualusednextyear = other.casualusednextyear == null ? null : other.casualusednextyear.copy();
        this.sicktotalnextyear = other.sicktotalnextyear == null ? null : other.sicktotalnextyear.copy();
        this.sickusednextyear = other.sickusednextyear == null ? null : other.sickusednextyear.copy();
        this.carryforward = other.carryforward == null ? null : other.carryforward.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeesLeaveId = other.employeesLeaveId == null ? null : other.employeesLeaveId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeavesOldsCriteria copy() {
        return new LeavesOldsCriteria(this);
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

    public BigDecimalFilter getAnnualtotal() {
        return annualtotal;
    }

    public BigDecimalFilter annualtotal() {
        if (annualtotal == null) {
            annualtotal = new BigDecimalFilter();
        }
        return annualtotal;
    }

    public void setAnnualtotal(BigDecimalFilter annualtotal) {
        this.annualtotal = annualtotal;
    }

    public BigDecimalFilter getAnnualused() {
        return annualused;
    }

    public BigDecimalFilter annualused() {
        if (annualused == null) {
            annualused = new BigDecimalFilter();
        }
        return annualused;
    }

    public void setAnnualused(BigDecimalFilter annualused) {
        this.annualused = annualused;
    }

    public BigDecimalFilter getAnnualadjustments() {
        return annualadjustments;
    }

    public BigDecimalFilter annualadjustments() {
        if (annualadjustments == null) {
            annualadjustments = new BigDecimalFilter();
        }
        return annualadjustments;
    }

    public void setAnnualadjustments(BigDecimalFilter annualadjustments) {
        this.annualadjustments = annualadjustments;
    }

    public BigDecimalFilter getCasualtotal() {
        return casualtotal;
    }

    public BigDecimalFilter casualtotal() {
        if (casualtotal == null) {
            casualtotal = new BigDecimalFilter();
        }
        return casualtotal;
    }

    public void setCasualtotal(BigDecimalFilter casualtotal) {
        this.casualtotal = casualtotal;
    }

    public BigDecimalFilter getCasualused() {
        return casualused;
    }

    public BigDecimalFilter casualused() {
        if (casualused == null) {
            casualused = new BigDecimalFilter();
        }
        return casualused;
    }

    public void setCasualused(BigDecimalFilter casualused) {
        this.casualused = casualused;
    }

    public BigDecimalFilter getSicktotal() {
        return sicktotal;
    }

    public BigDecimalFilter sicktotal() {
        if (sicktotal == null) {
            sicktotal = new BigDecimalFilter();
        }
        return sicktotal;
    }

    public void setSicktotal(BigDecimalFilter sicktotal) {
        this.sicktotal = sicktotal;
    }

    public BigDecimalFilter getSickused() {
        return sickused;
    }

    public BigDecimalFilter sickused() {
        if (sickused == null) {
            sickused = new BigDecimalFilter();
        }
        return sickused;
    }

    public void setSickused(BigDecimalFilter sickused) {
        this.sickused = sickused;
    }

    public BigDecimalFilter getAnnualtotalnextyear() {
        return annualtotalnextyear;
    }

    public BigDecimalFilter annualtotalnextyear() {
        if (annualtotalnextyear == null) {
            annualtotalnextyear = new BigDecimalFilter();
        }
        return annualtotalnextyear;
    }

    public void setAnnualtotalnextyear(BigDecimalFilter annualtotalnextyear) {
        this.annualtotalnextyear = annualtotalnextyear;
    }

    public BigDecimalFilter getAnnualusednextyear() {
        return annualusednextyear;
    }

    public BigDecimalFilter annualusednextyear() {
        if (annualusednextyear == null) {
            annualusednextyear = new BigDecimalFilter();
        }
        return annualusednextyear;
    }

    public void setAnnualusednextyear(BigDecimalFilter annualusednextyear) {
        this.annualusednextyear = annualusednextyear;
    }

    public BigDecimalFilter getCasualtotalnextyear() {
        return casualtotalnextyear;
    }

    public BigDecimalFilter casualtotalnextyear() {
        if (casualtotalnextyear == null) {
            casualtotalnextyear = new BigDecimalFilter();
        }
        return casualtotalnextyear;
    }

    public void setCasualtotalnextyear(BigDecimalFilter casualtotalnextyear) {
        this.casualtotalnextyear = casualtotalnextyear;
    }

    public BigDecimalFilter getCasualusednextyear() {
        return casualusednextyear;
    }

    public BigDecimalFilter casualusednextyear() {
        if (casualusednextyear == null) {
            casualusednextyear = new BigDecimalFilter();
        }
        return casualusednextyear;
    }

    public void setCasualusednextyear(BigDecimalFilter casualusednextyear) {
        this.casualusednextyear = casualusednextyear;
    }

    public BigDecimalFilter getSicktotalnextyear() {
        return sicktotalnextyear;
    }

    public BigDecimalFilter sicktotalnextyear() {
        if (sicktotalnextyear == null) {
            sicktotalnextyear = new BigDecimalFilter();
        }
        return sicktotalnextyear;
    }

    public void setSicktotalnextyear(BigDecimalFilter sicktotalnextyear) {
        this.sicktotalnextyear = sicktotalnextyear;
    }

    public BigDecimalFilter getSickusednextyear() {
        return sickusednextyear;
    }

    public BigDecimalFilter sickusednextyear() {
        if (sickusednextyear == null) {
            sickusednextyear = new BigDecimalFilter();
        }
        return sickusednextyear;
    }

    public void setSickusednextyear(BigDecimalFilter sickusednextyear) {
        this.sickusednextyear = sickusednextyear;
    }

    public BigDecimalFilter getCarryforward() {
        return carryforward;
    }

    public BigDecimalFilter carryforward() {
        if (carryforward == null) {
            carryforward = new BigDecimalFilter();
        }
        return carryforward;
    }

    public void setCarryforward(BigDecimalFilter carryforward) {
        this.carryforward = carryforward;
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

    public LongFilter getEmployeesLeaveId() {
        return employeesLeaveId;
    }

    public LongFilter employeesLeaveId() {
        if (employeesLeaveId == null) {
            employeesLeaveId = new LongFilter();
        }
        return employeesLeaveId;
    }

    public void setEmployeesLeaveId(LongFilter employeesLeaveId) {
        this.employeesLeaveId = employeesLeaveId;
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
        final LeavesOldsCriteria that = (LeavesOldsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(annualtotal, that.annualtotal) &&
            Objects.equals(annualused, that.annualused) &&
            Objects.equals(annualadjustments, that.annualadjustments) &&
            Objects.equals(casualtotal, that.casualtotal) &&
            Objects.equals(casualused, that.casualused) &&
            Objects.equals(sicktotal, that.sicktotal) &&
            Objects.equals(sickused, that.sickused) &&
            Objects.equals(annualtotalnextyear, that.annualtotalnextyear) &&
            Objects.equals(annualusednextyear, that.annualusednextyear) &&
            Objects.equals(casualtotalnextyear, that.casualtotalnextyear) &&
            Objects.equals(casualusednextyear, that.casualusednextyear) &&
            Objects.equals(sicktotalnextyear, that.sicktotalnextyear) &&
            Objects.equals(sickusednextyear, that.sickusednextyear) &&
            Objects.equals(carryforward, that.carryforward) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeesLeaveId, that.employeesLeaveId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            annualtotal,
            annualused,
            annualadjustments,
            casualtotal,
            casualused,
            sicktotal,
            sickused,
            annualtotalnextyear,
            annualusednextyear,
            casualtotalnextyear,
            casualusednextyear,
            sicktotalnextyear,
            sickusednextyear,
            carryforward,
            createdat,
            updatedat,
            employeesLeaveId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavesOldsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (annualtotal != null ? "annualtotal=" + annualtotal + ", " : "") +
            (annualused != null ? "annualused=" + annualused + ", " : "") +
            (annualadjustments != null ? "annualadjustments=" + annualadjustments + ", " : "") +
            (casualtotal != null ? "casualtotal=" + casualtotal + ", " : "") +
            (casualused != null ? "casualused=" + casualused + ", " : "") +
            (sicktotal != null ? "sicktotal=" + sicktotal + ", " : "") +
            (sickused != null ? "sickused=" + sickused + ", " : "") +
            (annualtotalnextyear != null ? "annualtotalnextyear=" + annualtotalnextyear + ", " : "") +
            (annualusednextyear != null ? "annualusednextyear=" + annualusednextyear + ", " : "") +
            (casualtotalnextyear != null ? "casualtotalnextyear=" + casualtotalnextyear + ", " : "") +
            (casualusednextyear != null ? "casualusednextyear=" + casualusednextyear + ", " : "") +
            (sicktotalnextyear != null ? "sicktotalnextyear=" + sicktotalnextyear + ", " : "") +
            (sickusednextyear != null ? "sickusednextyear=" + sickusednextyear + ", " : "") +
            (carryforward != null ? "carryforward=" + carryforward + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeesLeaveId != null ? "employeesLeaveId=" + employeesLeaveId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
