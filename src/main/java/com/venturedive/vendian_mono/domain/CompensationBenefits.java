package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CompensationBenefits.
 */
@Entity
@Table(name = "compensation_benefits")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompensationBenefits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "isdeleted")
    private Boolean isdeleted;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "compensationbenefitsBenefits" }, allowSetters = true)
    private Benefits benefit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employee", "reason", "compensationbenefitsEmployeecompensations" }, allowSetters = true)
    private EmployeeCompensation employeecompensation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompensationBenefits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return this.amount;
    }

    public CompensationBenefits amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public CompensationBenefits isdeleted(Boolean isdeleted) {
        this.setIsdeleted(isdeleted);
        return this;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public CompensationBenefits createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public CompensationBenefits updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Benefits getBenefit() {
        return this.benefit;
    }

    public void setBenefit(Benefits benefits) {
        this.benefit = benefits;
    }

    public CompensationBenefits benefit(Benefits benefits) {
        this.setBenefit(benefits);
        return this;
    }

    public EmployeeCompensation getEmployeecompensation() {
        return this.employeecompensation;
    }

    public void setEmployeecompensation(EmployeeCompensation employeeCompensation) {
        this.employeecompensation = employeeCompensation;
    }

    public CompensationBenefits employeecompensation(EmployeeCompensation employeeCompensation) {
        this.setEmployeecompensation(employeeCompensation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompensationBenefits)) {
            return false;
        }
        return id != null && id.equals(((CompensationBenefits) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompensationBenefits{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", isdeleted='" + getIsdeleted() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
