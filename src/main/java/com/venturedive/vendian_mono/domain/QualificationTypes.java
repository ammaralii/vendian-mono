package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A QualificationTypes.
 */
@Entity
@Table(name = "qualification_types")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QualificationTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "qualificationtype")
    @JsonIgnoreProperties(value = { "qualificationtype", "employee" }, allowSetters = true)
    private Set<EmployeeEducation> employeeeducationQualificationtypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QualificationTypes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public QualificationTypes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public QualificationTypes createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public QualificationTypes updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeEducation> getEmployeeeducationQualificationtypes() {
        return this.employeeeducationQualificationtypes;
    }

    public void setEmployeeeducationQualificationtypes(Set<EmployeeEducation> employeeEducations) {
        if (this.employeeeducationQualificationtypes != null) {
            this.employeeeducationQualificationtypes.forEach(i -> i.setQualificationtype(null));
        }
        if (employeeEducations != null) {
            employeeEducations.forEach(i -> i.setQualificationtype(this));
        }
        this.employeeeducationQualificationtypes = employeeEducations;
    }

    public QualificationTypes employeeeducationQualificationtypes(Set<EmployeeEducation> employeeEducations) {
        this.setEmployeeeducationQualificationtypes(employeeEducations);
        return this;
    }

    public QualificationTypes addEmployeeeducationQualificationtype(EmployeeEducation employeeEducation) {
        this.employeeeducationQualificationtypes.add(employeeEducation);
        employeeEducation.setQualificationtype(this);
        return this;
    }

    public QualificationTypes removeEmployeeeducationQualificationtype(EmployeeEducation employeeEducation) {
        this.employeeeducationQualificationtypes.remove(employeeEducation);
        employeeEducation.setQualificationtype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QualificationTypes)) {
            return false;
        }
        return id != null && id.equals(((QualificationTypes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QualificationTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
