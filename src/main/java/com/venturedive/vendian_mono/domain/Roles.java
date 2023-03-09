package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Roles.
 */
@Entity
@Table(name = "roles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "role", length = 255)
    private String role;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = { "role" }, allowSetters = true)
    private Set<EmployeeRoles> employeerolesRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
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
    private Set<Employees> employeesRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = { "role", "permission" }, allowSetters = true)
    private Set<RolePermissions> rolepermissionsRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Roles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public Roles role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Roles createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Roles updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeRoles> getEmployeerolesRoles() {
        return this.employeerolesRoles;
    }

    public void setEmployeerolesRoles(Set<EmployeeRoles> employeeRoles) {
        if (this.employeerolesRoles != null) {
            this.employeerolesRoles.forEach(i -> i.setRole(null));
        }
        if (employeeRoles != null) {
            employeeRoles.forEach(i -> i.setRole(this));
        }
        this.employeerolesRoles = employeeRoles;
    }

    public Roles employeerolesRoles(Set<EmployeeRoles> employeeRoles) {
        this.setEmployeerolesRoles(employeeRoles);
        return this;
    }

    public Roles addEmployeerolesRole(EmployeeRoles employeeRoles) {
        this.employeerolesRoles.add(employeeRoles);
        employeeRoles.setRole(this);
        return this;
    }

    public Roles removeEmployeerolesRole(EmployeeRoles employeeRoles) {
        this.employeerolesRoles.remove(employeeRoles);
        employeeRoles.setRole(null);
        return this;
    }

    public Set<Employees> getEmployeesRoles() {
        return this.employeesRoles;
    }

    public void setEmployeesRoles(Set<Employees> employees) {
        if (this.employeesRoles != null) {
            this.employeesRoles.forEach(i -> i.setRole(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setRole(this));
        }
        this.employeesRoles = employees;
    }

    public Roles employeesRoles(Set<Employees> employees) {
        this.setEmployeesRoles(employees);
        return this;
    }

    public Roles addEmployeesRole(Employees employees) {
        this.employeesRoles.add(employees);
        employees.setRole(this);
        return this;
    }

    public Roles removeEmployeesRole(Employees employees) {
        this.employeesRoles.remove(employees);
        employees.setRole(null);
        return this;
    }

    public Set<RolePermissions> getRolepermissionsRoles() {
        return this.rolepermissionsRoles;
    }

    public void setRolepermissionsRoles(Set<RolePermissions> rolePermissions) {
        if (this.rolepermissionsRoles != null) {
            this.rolepermissionsRoles.forEach(i -> i.setRole(null));
        }
        if (rolePermissions != null) {
            rolePermissions.forEach(i -> i.setRole(this));
        }
        this.rolepermissionsRoles = rolePermissions;
    }

    public Roles rolepermissionsRoles(Set<RolePermissions> rolePermissions) {
        this.setRolepermissionsRoles(rolePermissions);
        return this;
    }

    public Roles addRolepermissionsRole(RolePermissions rolePermissions) {
        this.rolepermissionsRoles.add(rolePermissions);
        rolePermissions.setRole(this);
        return this;
    }

    public Roles removeRolepermissionsRole(RolePermissions rolePermissions) {
        this.rolepermissionsRoles.remove(rolePermissions);
        rolePermissions.setRole(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Roles)) {
            return false;
        }
        return id != null && id.equals(((Roles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
