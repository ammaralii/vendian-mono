package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BusinessUnits.
 */
@Entity
@Table(name = "business_units")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "businessunit")
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
    private Set<EmployeeJobInfo> employeejobinfoBusinessunits = new HashSet<>();

    @OneToMany(mappedBy = "businessunit")
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
    private Set<Employees> employeesBusinessunits = new HashSet<>();

    @OneToMany(mappedBy = "businessunit")
    @JsonIgnoreProperties(
        value = {
            "projectmanager",
            "businessunit",
            "employeeprojectsProjects",
            "interviewsProjects",
            "projectcyclesProjects",
            "projectleadershipProjects",
            "questionsProjects",
            "questionsfrequencyperclienttrackProjects",
            "worklogdetailsProjects",
            "zemployeeprojectsProjects",
        },
        allowSetters = true
    )
    private Set<Projects> projectsBusinessunits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessUnits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BusinessUnits name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public BusinessUnits createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public BusinessUnits updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoBusinessunits() {
        return this.employeejobinfoBusinessunits;
    }

    public void setEmployeejobinfoBusinessunits(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoBusinessunits != null) {
            this.employeejobinfoBusinessunits.forEach(i -> i.setBusinessunit(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setBusinessunit(this));
        }
        this.employeejobinfoBusinessunits = employeeJobInfos;
    }

    public BusinessUnits employeejobinfoBusinessunits(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoBusinessunits(employeeJobInfos);
        return this;
    }

    public BusinessUnits addEmployeejobinfoBusinessunit(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoBusinessunits.add(employeeJobInfo);
        employeeJobInfo.setBusinessunit(this);
        return this;
    }

    public BusinessUnits removeEmployeejobinfoBusinessunit(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoBusinessunits.remove(employeeJobInfo);
        employeeJobInfo.setBusinessunit(null);
        return this;
    }

    public Set<Employees> getEmployeesBusinessunits() {
        return this.employeesBusinessunits;
    }

    public void setEmployeesBusinessunits(Set<Employees> employees) {
        if (this.employeesBusinessunits != null) {
            this.employeesBusinessunits.forEach(i -> i.setBusinessunit(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setBusinessunit(this));
        }
        this.employeesBusinessunits = employees;
    }

    public BusinessUnits employeesBusinessunits(Set<Employees> employees) {
        this.setEmployeesBusinessunits(employees);
        return this;
    }

    public BusinessUnits addEmployeesBusinessunit(Employees employees) {
        this.employeesBusinessunits.add(employees);
        employees.setBusinessunit(this);
        return this;
    }

    public BusinessUnits removeEmployeesBusinessunit(Employees employees) {
        this.employeesBusinessunits.remove(employees);
        employees.setBusinessunit(null);
        return this;
    }

    public Set<Projects> getProjectsBusinessunits() {
        return this.projectsBusinessunits;
    }

    public void setProjectsBusinessunits(Set<Projects> projects) {
        if (this.projectsBusinessunits != null) {
            this.projectsBusinessunits.forEach(i -> i.setBusinessunit(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setBusinessunit(this));
        }
        this.projectsBusinessunits = projects;
    }

    public BusinessUnits projectsBusinessunits(Set<Projects> projects) {
        this.setProjectsBusinessunits(projects);
        return this;
    }

    public BusinessUnits addProjectsBusinessunit(Projects projects) {
        this.projectsBusinessunits.add(projects);
        projects.setBusinessunit(this);
        return this;
    }

    public BusinessUnits removeProjectsBusinessunit(Projects projects) {
        this.projectsBusinessunits.remove(projects);
        projects.setBusinessunit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessUnits)) {
            return false;
        }
        return id != null && id.equals(((BusinessUnits) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnits{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
