package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Benefits.
 */
@Entity
@Table(name = "benefits")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Benefits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "isdeleted")
    private Boolean isdeleted;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "benefit")
    @JsonIgnoreProperties(value = { "benefit", "employeecompensation" }, allowSetters = true)
    private Set<CompensationBenefits> compensationbenefitsBenefits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Benefits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Benefits name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public Benefits isdeleted(Boolean isdeleted) {
        this.setIsdeleted(isdeleted);
        return this;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Benefits createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Benefits updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<CompensationBenefits> getCompensationbenefitsBenefits() {
        return this.compensationbenefitsBenefits;
    }

    public void setCompensationbenefitsBenefits(Set<CompensationBenefits> compensationBenefits) {
        if (this.compensationbenefitsBenefits != null) {
            this.compensationbenefitsBenefits.forEach(i -> i.setBenefit(null));
        }
        if (compensationBenefits != null) {
            compensationBenefits.forEach(i -> i.setBenefit(this));
        }
        this.compensationbenefitsBenefits = compensationBenefits;
    }

    public Benefits compensationbenefitsBenefits(Set<CompensationBenefits> compensationBenefits) {
        this.setCompensationbenefitsBenefits(compensationBenefits);
        return this;
    }

    public Benefits addCompensationbenefitsBenefit(CompensationBenefits compensationBenefits) {
        this.compensationbenefitsBenefits.add(compensationBenefits);
        compensationBenefits.setBenefit(this);
        return this;
    }

    public Benefits removeCompensationbenefitsBenefit(CompensationBenefits compensationBenefits) {
        this.compensationbenefitsBenefits.remove(compensationBenefits);
        compensationBenefits.setBenefit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Benefits)) {
            return false;
        }
        return id != null && id.equals(((Benefits) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Benefits{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isdeleted='" + getIsdeleted() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
