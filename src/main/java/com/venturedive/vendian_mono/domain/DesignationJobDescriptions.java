package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DesignationJobDescriptions.
 */
@Entity
@Table(name = "designation_job_descriptions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesignationJobDescriptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "designationjobdescriptionsDocuments", "employeecommentsDocuments", "employeedocumentsDocuments" },
        allowSetters = true
    )
    private Documents document;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "designationjobdescriptionsDesignations",
            "employeejobinfoDesignations",
            "employeesDesignations",
            "userratingsremarksDesignationafterpromotions",
            "userratingsremarksDesignationafterredesignations",
        },
        allowSetters = true
    )
    private Designations designation;

    @OneToMany(mappedBy = "jobdescription")
    @JsonIgnoreProperties(
        value = {
            "employee",
            "designation",
            "reviewmanager",
            "manager",
            "department",
            "employmenttype",
            "jobdescription",
            "division",
            "businessunit",
            "competency",
        },
        allowSetters = true
    )
    private Set<EmployeeJobInfo> employeejobinfoJobdescriptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DesignationJobDescriptions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DesignationJobDescriptions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DesignationJobDescriptions updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Documents getDocument() {
        return this.document;
    }

    public void setDocument(Documents documents) {
        this.document = documents;
    }

    public DesignationJobDescriptions document(Documents documents) {
        this.setDocument(documents);
        return this;
    }

    public Designations getDesignation() {
        return this.designation;
    }

    public void setDesignation(Designations designations) {
        this.designation = designations;
    }

    public DesignationJobDescriptions designation(Designations designations) {
        this.setDesignation(designations);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoJobdescriptions() {
        return this.employeejobinfoJobdescriptions;
    }

    public void setEmployeejobinfoJobdescriptions(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoJobdescriptions != null) {
            this.employeejobinfoJobdescriptions.forEach(i -> i.setJobdescription(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setJobdescription(this));
        }
        this.employeejobinfoJobdescriptions = employeeJobInfos;
    }

    public DesignationJobDescriptions employeejobinfoJobdescriptions(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoJobdescriptions(employeeJobInfos);
        return this;
    }

    public DesignationJobDescriptions addEmployeejobinfoJobdescription(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoJobdescriptions.add(employeeJobInfo);
        employeeJobInfo.setJobdescription(this);
        return this;
    }

    public DesignationJobDescriptions removeEmployeejobinfoJobdescription(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoJobdescriptions.remove(employeeJobInfo);
        employeeJobInfo.setJobdescription(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DesignationJobDescriptions)) {
            return false;
        }
        return id != null && id.equals(((DesignationJobDescriptions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesignationJobDescriptions{" +
            "id=" + getId() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
