package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Skills.
 */
@Entity
@Table(name = "skills")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Skills implements Serializable {

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

    @OneToMany(mappedBy = "skill")
    @JsonIgnoreProperties(value = { "resource", "skill" }, allowSetters = true)
    private Set<DealResourceSkills> dealresourceskillsSkills = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @JsonIgnoreProperties(value = { "employee", "skill" }, allowSetters = true)
    private Set<EmployeeSkills> employeeskillsSkills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Skills id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Skills name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Skills createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Skills updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<DealResourceSkills> getDealresourceskillsSkills() {
        return this.dealresourceskillsSkills;
    }

    public void setDealresourceskillsSkills(Set<DealResourceSkills> dealResourceSkills) {
        if (this.dealresourceskillsSkills != null) {
            this.dealresourceskillsSkills.forEach(i -> i.setSkill(null));
        }
        if (dealResourceSkills != null) {
            dealResourceSkills.forEach(i -> i.setSkill(this));
        }
        this.dealresourceskillsSkills = dealResourceSkills;
    }

    public Skills dealresourceskillsSkills(Set<DealResourceSkills> dealResourceSkills) {
        this.setDealresourceskillsSkills(dealResourceSkills);
        return this;
    }

    public Skills addDealresourceskillsSkill(DealResourceSkills dealResourceSkills) {
        this.dealresourceskillsSkills.add(dealResourceSkills);
        dealResourceSkills.setSkill(this);
        return this;
    }

    public Skills removeDealresourceskillsSkill(DealResourceSkills dealResourceSkills) {
        this.dealresourceskillsSkills.remove(dealResourceSkills);
        dealResourceSkills.setSkill(null);
        return this;
    }

    public Set<EmployeeSkills> getEmployeeskillsSkills() {
        return this.employeeskillsSkills;
    }

    public void setEmployeeskillsSkills(Set<EmployeeSkills> employeeSkills) {
        if (this.employeeskillsSkills != null) {
            this.employeeskillsSkills.forEach(i -> i.setSkill(null));
        }
        if (employeeSkills != null) {
            employeeSkills.forEach(i -> i.setSkill(this));
        }
        this.employeeskillsSkills = employeeSkills;
    }

    public Skills employeeskillsSkills(Set<EmployeeSkills> employeeSkills) {
        this.setEmployeeskillsSkills(employeeSkills);
        return this;
    }

    public Skills addEmployeeskillsSkill(EmployeeSkills employeeSkills) {
        this.employeeskillsSkills.add(employeeSkills);
        employeeSkills.setSkill(this);
        return this;
    }

    public Skills removeEmployeeskillsSkill(EmployeeSkills employeeSkills) {
        this.employeeskillsSkills.remove(employeeSkills);
        employeeSkills.setSkill(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skills)) {
            return false;
        }
        return id != null && id.equals(((Skills) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skills{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
