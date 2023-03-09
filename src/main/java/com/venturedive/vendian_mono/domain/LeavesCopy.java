package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeavesCopy.
 */
@Entity
@Table(name = "leaves_copy")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavesCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "annualtotal", precision = 21, scale = 2)
    private BigDecimal annualtotal;

    @Column(name = "annualused", precision = 21, scale = 2)
    private BigDecimal annualused;

    @Column(name = "annualadjustments", precision = 21, scale = 2)
    private BigDecimal annualadjustments;

    @Column(name = "casualtotal", precision = 21, scale = 2)
    private BigDecimal casualtotal;

    @Column(name = "casualused", precision = 21, scale = 2)
    private BigDecimal casualused;

    @Column(name = "sicktotal", precision = 21, scale = 2)
    private BigDecimal sicktotal;

    @Column(name = "sickused", precision = 21, scale = 2)
    private BigDecimal sickused;

    @Column(name = "annualtotalnextyear", precision = 21, scale = 2)
    private BigDecimal annualtotalnextyear;

    @Column(name = "annualusednextyear", precision = 21, scale = 2)
    private BigDecimal annualusednextyear;

    @Column(name = "casualtotalnextyear", precision = 21, scale = 2)
    private BigDecimal casualtotalnextyear;

    @Column(name = "casualusednextyear", precision = 21, scale = 2)
    private BigDecimal casualusednextyear;

    @Column(name = "sicktotalnextyear", precision = 21, scale = 2)
    private BigDecimal sicktotalnextyear;

    @Column(name = "sickusednextyear", precision = 21, scale = 2)
    private BigDecimal sickusednextyear;

    @Column(name = "carryforward", precision = 21, scale = 2)
    private BigDecimal carryforward;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeavesCopy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAnnualtotal() {
        return this.annualtotal;
    }

    public LeavesCopy annualtotal(BigDecimal annualtotal) {
        this.setAnnualtotal(annualtotal);
        return this;
    }

    public void setAnnualtotal(BigDecimal annualtotal) {
        this.annualtotal = annualtotal;
    }

    public BigDecimal getAnnualused() {
        return this.annualused;
    }

    public LeavesCopy annualused(BigDecimal annualused) {
        this.setAnnualused(annualused);
        return this;
    }

    public void setAnnualused(BigDecimal annualused) {
        this.annualused = annualused;
    }

    public BigDecimal getAnnualadjustments() {
        return this.annualadjustments;
    }

    public LeavesCopy annualadjustments(BigDecimal annualadjustments) {
        this.setAnnualadjustments(annualadjustments);
        return this;
    }

    public void setAnnualadjustments(BigDecimal annualadjustments) {
        this.annualadjustments = annualadjustments;
    }

    public BigDecimal getCasualtotal() {
        return this.casualtotal;
    }

    public LeavesCopy casualtotal(BigDecimal casualtotal) {
        this.setCasualtotal(casualtotal);
        return this;
    }

    public void setCasualtotal(BigDecimal casualtotal) {
        this.casualtotal = casualtotal;
    }

    public BigDecimal getCasualused() {
        return this.casualused;
    }

    public LeavesCopy casualused(BigDecimal casualused) {
        this.setCasualused(casualused);
        return this;
    }

    public void setCasualused(BigDecimal casualused) {
        this.casualused = casualused;
    }

    public BigDecimal getSicktotal() {
        return this.sicktotal;
    }

    public LeavesCopy sicktotal(BigDecimal sicktotal) {
        this.setSicktotal(sicktotal);
        return this;
    }

    public void setSicktotal(BigDecimal sicktotal) {
        this.sicktotal = sicktotal;
    }

    public BigDecimal getSickused() {
        return this.sickused;
    }

    public LeavesCopy sickused(BigDecimal sickused) {
        this.setSickused(sickused);
        return this;
    }

    public void setSickused(BigDecimal sickused) {
        this.sickused = sickused;
    }

    public BigDecimal getAnnualtotalnextyear() {
        return this.annualtotalnextyear;
    }

    public LeavesCopy annualtotalnextyear(BigDecimal annualtotalnextyear) {
        this.setAnnualtotalnextyear(annualtotalnextyear);
        return this;
    }

    public void setAnnualtotalnextyear(BigDecimal annualtotalnextyear) {
        this.annualtotalnextyear = annualtotalnextyear;
    }

    public BigDecimal getAnnualusednextyear() {
        return this.annualusednextyear;
    }

    public LeavesCopy annualusednextyear(BigDecimal annualusednextyear) {
        this.setAnnualusednextyear(annualusednextyear);
        return this;
    }

    public void setAnnualusednextyear(BigDecimal annualusednextyear) {
        this.annualusednextyear = annualusednextyear;
    }

    public BigDecimal getCasualtotalnextyear() {
        return this.casualtotalnextyear;
    }

    public LeavesCopy casualtotalnextyear(BigDecimal casualtotalnextyear) {
        this.setCasualtotalnextyear(casualtotalnextyear);
        return this;
    }

    public void setCasualtotalnextyear(BigDecimal casualtotalnextyear) {
        this.casualtotalnextyear = casualtotalnextyear;
    }

    public BigDecimal getCasualusednextyear() {
        return this.casualusednextyear;
    }

    public LeavesCopy casualusednextyear(BigDecimal casualusednextyear) {
        this.setCasualusednextyear(casualusednextyear);
        return this;
    }

    public void setCasualusednextyear(BigDecimal casualusednextyear) {
        this.casualusednextyear = casualusednextyear;
    }

    public BigDecimal getSicktotalnextyear() {
        return this.sicktotalnextyear;
    }

    public LeavesCopy sicktotalnextyear(BigDecimal sicktotalnextyear) {
        this.setSicktotalnextyear(sicktotalnextyear);
        return this;
    }

    public void setSicktotalnextyear(BigDecimal sicktotalnextyear) {
        this.sicktotalnextyear = sicktotalnextyear;
    }

    public BigDecimal getSickusednextyear() {
        return this.sickusednextyear;
    }

    public LeavesCopy sickusednextyear(BigDecimal sickusednextyear) {
        this.setSickusednextyear(sickusednextyear);
        return this;
    }

    public void setSickusednextyear(BigDecimal sickusednextyear) {
        this.sickusednextyear = sickusednextyear;
    }

    public BigDecimal getCarryforward() {
        return this.carryforward;
    }

    public LeavesCopy carryforward(BigDecimal carryforward) {
        this.setCarryforward(carryforward);
        return this;
    }

    public void setCarryforward(BigDecimal carryforward) {
        this.carryforward = carryforward;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public LeavesCopy createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public LeavesCopy updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeavesCopy)) {
            return false;
        }
        return id != null && id.equals(((LeavesCopy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavesCopy{" +
            "id=" + getId() +
            ", annualtotal=" + getAnnualtotal() +
            ", annualused=" + getAnnualused() +
            ", annualadjustments=" + getAnnualadjustments() +
            ", casualtotal=" + getCasualtotal() +
            ", casualused=" + getCasualused() +
            ", sicktotal=" + getSicktotal() +
            ", sickused=" + getSickused() +
            ", annualtotalnextyear=" + getAnnualtotalnextyear() +
            ", annualusednextyear=" + getAnnualusednextyear() +
            ", casualtotalnextyear=" + getCasualtotalnextyear() +
            ", casualusednextyear=" + getCasualusednextyear() +
            ", sicktotalnextyear=" + getSicktotalnextyear() +
            ", sickusednextyear=" + getSickusednextyear() +
            ", carryforward=" + getCarryforward() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
