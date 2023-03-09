package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Departments.
 */
@Entity
@Table(name = "departments")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Departments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "departmentsDivisions", "employeejobinfoDivisions", "employeesDivisions" }, allowSetters = true)
    private Divisions division;

    @OneToMany(mappedBy = "department")
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
    private Set<EmployeeJobInfo> employeejobinfoDepartments = new HashSet<>();

    @OneToMany(mappedBy = "department")
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
    private Set<Employees> employeesDepartments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Departments name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Departments createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Departments updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Divisions getDivision() {
        return this.division;
    }

    public void setDivision(Divisions divisions) {
        this.division = divisions;
    }

    public Departments division(Divisions divisions) {
        this.setDivision(divisions);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoDepartments() {
        return this.employeejobinfoDepartments;
    }

    public void setEmployeejobinfoDepartments(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoDepartments != null) {
            this.employeejobinfoDepartments.forEach(i -> i.setDepartment(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setDepartment(this));
        }
        this.employeejobinfoDepartments = employeeJobInfos;
    }

    public Departments employeejobinfoDepartments(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoDepartments(employeeJobInfos);
        return this;
    }

    public Departments addEmployeejobinfoDepartment(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDepartments.add(employeeJobInfo);
        employeeJobInfo.setDepartment(this);
        return this;
    }

    public Departments removeEmployeejobinfoDepartment(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDepartments.remove(employeeJobInfo);
        employeeJobInfo.setDepartment(null);
        return this;
    }

    public Set<Employees> getEmployeesDepartments() {
        return this.employeesDepartments;
    }

    public void setEmployeesDepartments(Set<Employees> employees) {
        if (this.employeesDepartments != null) {
            this.employeesDepartments.forEach(i -> i.setDepartment(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setDepartment(this));
        }
        this.employeesDepartments = employees;
    }

    public Departments employeesDepartments(Set<Employees> employees) {
        this.setEmployeesDepartments(employees);
        return this;
    }

    public Departments addEmployeesDepartment(Employees employees) {
        this.employeesDepartments.add(employees);
        employees.setDepartment(this);
        return this;
    }

    public Departments removeEmployeesDepartment(Employees employees) {
        this.employeesDepartments.remove(employees);
        employees.setDepartment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departments)) {
            return false;
        }
        return id != null && id.equals(((Departments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departments{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
