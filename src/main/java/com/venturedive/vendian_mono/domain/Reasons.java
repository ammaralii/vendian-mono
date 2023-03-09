package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Reasons.
 */
@Entity
@Table(name = "reasons")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reasons implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "isdefault")
    private Boolean isdefault;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "reason")
    @JsonIgnoreProperties(value = { "employee", "reason", "compensationbenefitsEmployeecompensations" }, allowSetters = true)
    private Set<EmployeeCompensation> employeecompensationReasons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reasons id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Reasons name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsdefault() {
        return this.isdefault;
    }

    public Reasons isdefault(Boolean isdefault) {
        this.setIsdefault(isdefault);
        return this;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Reasons createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Reasons updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeCompensation> getEmployeecompensationReasons() {
        return this.employeecompensationReasons;
    }

    public void setEmployeecompensationReasons(Set<EmployeeCompensation> employeeCompensations) {
        if (this.employeecompensationReasons != null) {
            this.employeecompensationReasons.forEach(i -> i.setReason(null));
        }
        if (employeeCompensations != null) {
            employeeCompensations.forEach(i -> i.setReason(this));
        }
        this.employeecompensationReasons = employeeCompensations;
    }

    public Reasons employeecompensationReasons(Set<EmployeeCompensation> employeeCompensations) {
        this.setEmployeecompensationReasons(employeeCompensations);
        return this;
    }

    public Reasons addEmployeecompensationReason(EmployeeCompensation employeeCompensation) {
        this.employeecompensationReasons.add(employeeCompensation);
        employeeCompensation.setReason(this);
        return this;
    }

    public Reasons removeEmployeecompensationReason(EmployeeCompensation employeeCompensation) {
        this.employeecompensationReasons.remove(employeeCompensation);
        employeeCompensation.setReason(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reasons)) {
            return false;
        }
        return id != null && id.equals(((Reasons) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reasons{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isdefault='" + getIsdefault() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
