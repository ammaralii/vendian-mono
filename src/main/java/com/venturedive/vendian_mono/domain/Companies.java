package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Companies.
 */
@Entity
@Table(name = "companies")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "employee", "company" }, allowSetters = true)
    private Set<EmployeeWorks> employeeworksCompanies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Companies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Companies name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Companies createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Companies updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeWorks> getEmployeeworksCompanies() {
        return this.employeeworksCompanies;
    }

    public void setEmployeeworksCompanies(Set<EmployeeWorks> employeeWorks) {
        if (this.employeeworksCompanies != null) {
            this.employeeworksCompanies.forEach(i -> i.setCompany(null));
        }
        if (employeeWorks != null) {
            employeeWorks.forEach(i -> i.setCompany(this));
        }
        this.employeeworksCompanies = employeeWorks;
    }

    public Companies employeeworksCompanies(Set<EmployeeWorks> employeeWorks) {
        this.setEmployeeworksCompanies(employeeWorks);
        return this;
    }

    public Companies addEmployeeworksCompany(EmployeeWorks employeeWorks) {
        this.employeeworksCompanies.add(employeeWorks);
        employeeWorks.setCompany(this);
        return this;
    }

    public Companies removeEmployeeworksCompany(EmployeeWorks employeeWorks) {
        this.employeeworksCompanies.remove(employeeWorks);
        employeeWorks.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Companies)) {
            return false;
        }
        return id != null && id.equals(((Companies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Companies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
