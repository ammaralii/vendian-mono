package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Divisions.
 */
@Entity
@Table(name = "divisions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Divisions implements Serializable {

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

    @OneToMany(mappedBy = "division")
    @JsonIgnoreProperties(value = { "division", "employeejobinfoDepartments", "employeesDepartments" }, allowSetters = true)
    private Set<Departments> departmentsDivisions = new HashSet<>();

    @OneToMany(mappedBy = "division")
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
    private Set<EmployeeJobInfo> employeejobinfoDivisions = new HashSet<>();

    @OneToMany(mappedBy = "division")
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
    private Set<Employees> employeesDivisions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Divisions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Divisions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Divisions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Divisions updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<Departments> getDepartmentsDivisions() {
        return this.departmentsDivisions;
    }

    public void setDepartmentsDivisions(Set<Departments> departments) {
        if (this.departmentsDivisions != null) {
            this.departmentsDivisions.forEach(i -> i.setDivision(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setDivision(this));
        }
        this.departmentsDivisions = departments;
    }

    public Divisions departmentsDivisions(Set<Departments> departments) {
        this.setDepartmentsDivisions(departments);
        return this;
    }

    public Divisions addDepartmentsDivision(Departments departments) {
        this.departmentsDivisions.add(departments);
        departments.setDivision(this);
        return this;
    }

    public Divisions removeDepartmentsDivision(Departments departments) {
        this.departmentsDivisions.remove(departments);
        departments.setDivision(null);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoDivisions() {
        return this.employeejobinfoDivisions;
    }

    public void setEmployeejobinfoDivisions(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoDivisions != null) {
            this.employeejobinfoDivisions.forEach(i -> i.setDivision(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setDivision(this));
        }
        this.employeejobinfoDivisions = employeeJobInfos;
    }

    public Divisions employeejobinfoDivisions(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoDivisions(employeeJobInfos);
        return this;
    }

    public Divisions addEmployeejobinfoDivision(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDivisions.add(employeeJobInfo);
        employeeJobInfo.setDivision(this);
        return this;
    }

    public Divisions removeEmployeejobinfoDivision(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDivisions.remove(employeeJobInfo);
        employeeJobInfo.setDivision(null);
        return this;
    }

    public Set<Employees> getEmployeesDivisions() {
        return this.employeesDivisions;
    }

    public void setEmployeesDivisions(Set<Employees> employees) {
        if (this.employeesDivisions != null) {
            this.employeesDivisions.forEach(i -> i.setDivision(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setDivision(this));
        }
        this.employeesDivisions = employees;
    }

    public Divisions employeesDivisions(Set<Employees> employees) {
        this.setEmployeesDivisions(employees);
        return this;
    }

    public Divisions addEmployeesDivision(Employees employees) {
        this.employeesDivisions.add(employees);
        employees.setDivision(this);
        return this;
    }

    public Divisions removeEmployeesDivision(Employees employees) {
        this.employeesDivisions.remove(employees);
        employees.setDivision(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Divisions)) {
            return false;
        }
        return id != null && id.equals(((Divisions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Divisions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
