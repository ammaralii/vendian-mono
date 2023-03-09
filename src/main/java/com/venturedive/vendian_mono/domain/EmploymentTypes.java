package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmploymentTypes.
 */
@Entity
@Table(name = "employment_types")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmploymentTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "employmenttype")
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
    private Set<EmployeeJobInfo> employeejobinfoEmploymenttypes = new HashSet<>();

    @OneToMany(mappedBy = "employmenttype")
    @JsonIgnoreProperties(
        value = {
            "location",
            "role",
            "manager",
            "leave",
            "department",
            "businessunit",
            "division",
            "competency",
            "employmentstatus",
            "employmenttype",
            "designation",
            "claimrequestviewsEmployees",
            "claimrequestsEmployees",
            "dealresourcesEmployees",
            "employeeaddressesEmployees",
            "employeebirthdaysEmployees",
            "employeecertificatesEmployees",
            "employeecommentsCommenters",
            "employeecommentsEmployees",
            "employeecompensationEmployees",
            "employeecontactsEmployees",
            "employeedetailsEmployees",
            "employeedocumentsEmployees",
            "employeeeducationEmployees",
            "employeeemergencycontactsEmployees",
            "employeefamilyinfoEmployees",
            "employeejobinfoEmployees",
            "employeejobinfoReviewmanagers",
            "employeejobinfoManagers",
            "employeeprofilehistorylogsEmployees",
            "employeeprojectratingsProjectarchitects",
            "employeeprojectratingsProjectmanagers",
            "employeeprojectratingsEmployees",
            "employeeprojectsEmployees",
            "employeeprojectsAssignors",
            "employeeskillsEmployees",
            "employeetalentsEmployees",
            "employeeworksEmployees",
            "employeesManagers",
            "employmenthistoryEmployees",
            "feedbackquestionsEmployees",
            "feedbackrequestsEmployees",
            "feedbackrequestsLinemanagers",
            "feedbackrespondentsEmployees",
            "interviewsEmployees",
            "leavedetailadjustmentlogsAdjustedbies",
            "leaverequestapproversUsers",
            "leaverequestsoldsManagers",
            "leaverequestsoldsEmployees",
            "leavesUsers",
            "notificationsentemaillogsRecipients",
            "pcratingsEmployees",
            "pcratingstrainingsSuccessorfors",
            "performancecycleemployeeratingEmployees",
            "performancecycleemployeeratingManagers",
            "projectcyclesAllowedafterduedatebies",
            "projectcyclesArchitects",
            "projectcyclesProjectmanagers",
            "projectleadershipEmployees",
            "projectsProjectmanagers",
            "ratingsRaters",
            "userattributesUsers",
            "usergoalsUsers",
            "userratingsUsers",
            "userratingsReviewmanagers",
            "userratingsremarksRaters",
            "userrelationsUsers",
            "userrelationsRelatedusers",
            "worklogsEmployees",
            "zemployeeprojectsEmployees",
            "zemployeeprojectsAssignors",
            "zemployeeprojectsProjectmanagers",
        },
        allowSetters = true
    )
    private Set<Employees> employeesEmploymenttypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmploymentTypes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EmploymentTypes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmploymentTypes createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmploymentTypes updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoEmploymenttypes() {
        return this.employeejobinfoEmploymenttypes;
    }

    public void setEmployeejobinfoEmploymenttypes(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoEmploymenttypes != null) {
            this.employeejobinfoEmploymenttypes.forEach(i -> i.setEmploymenttype(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setEmploymenttype(this));
        }
        this.employeejobinfoEmploymenttypes = employeeJobInfos;
    }

    public EmploymentTypes employeejobinfoEmploymenttypes(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoEmploymenttypes(employeeJobInfos);
        return this;
    }

    public EmploymentTypes addEmployeejobinfoEmploymenttype(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoEmploymenttypes.add(employeeJobInfo);
        employeeJobInfo.setEmploymenttype(this);
        return this;
    }

    public EmploymentTypes removeEmployeejobinfoEmploymenttype(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoEmploymenttypes.remove(employeeJobInfo);
        employeeJobInfo.setEmploymenttype(null);
        return this;
    }

    public Set<Employees> getEmployeesEmploymenttypes() {
        return this.employeesEmploymenttypes;
    }

    public void setEmployeesEmploymenttypes(Set<Employees> employees) {
        if (this.employeesEmploymenttypes != null) {
            this.employeesEmploymenttypes.forEach(i -> i.setEmploymenttype(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setEmploymenttype(this));
        }
        this.employeesEmploymenttypes = employees;
    }

    public EmploymentTypes employeesEmploymenttypes(Set<Employees> employees) {
        this.setEmployeesEmploymenttypes(employees);
        return this;
    }

    public EmploymentTypes addEmployeesEmploymenttype(Employees employees) {
        this.employeesEmploymenttypes.add(employees);
        employees.setEmploymenttype(this);
        return this;
    }

    public EmploymentTypes removeEmployeesEmploymenttype(Employees employees) {
        this.employeesEmploymenttypes.remove(employees);
        employees.setEmploymenttype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmploymentTypes)) {
            return false;
        }
        return id != null && id.equals(((EmploymentTypes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmploymentTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
