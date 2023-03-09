package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProjectRoles.
 */
@Entity
@Table(name = "project_roles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectRoles implements Serializable {

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

    @Column(name = "isleadership")
    private Boolean isleadership;

    @OneToMany(mappedBy = "projectrole")
    @JsonIgnoreProperties(value = { "employeeproject", "projectrole" }, allowSetters = true)
    private Set<EmployeeProjectRoles> employeeprojectrolesProjectroles = new HashSet<>();

    @OneToMany(mappedBy = "projectrole")
    @JsonIgnoreProperties(value = { "projectrole", "project", "employee" }, allowSetters = true)
    private Set<ProjectLeadership> projectleadershipProjectroles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectRoles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public ProjectRoles role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ProjectRoles createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ProjectRoles updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Boolean getIsleadership() {
        return this.isleadership;
    }

    public ProjectRoles isleadership(Boolean isleadership) {
        this.setIsleadership(isleadership);
        return this;
    }

    public void setIsleadership(Boolean isleadership) {
        this.isleadership = isleadership;
    }

    public Set<EmployeeProjectRoles> getEmployeeprojectrolesProjectroles() {
        return this.employeeprojectrolesProjectroles;
    }

    public void setEmployeeprojectrolesProjectroles(Set<EmployeeProjectRoles> employeeProjectRoles) {
        if (this.employeeprojectrolesProjectroles != null) {
            this.employeeprojectrolesProjectroles.forEach(i -> i.setProjectrole(null));
        }
        if (employeeProjectRoles != null) {
            employeeProjectRoles.forEach(i -> i.setProjectrole(this));
        }
        this.employeeprojectrolesProjectroles = employeeProjectRoles;
    }

    public ProjectRoles employeeprojectrolesProjectroles(Set<EmployeeProjectRoles> employeeProjectRoles) {
        this.setEmployeeprojectrolesProjectroles(employeeProjectRoles);
        return this;
    }

    public ProjectRoles addEmployeeprojectrolesProjectrole(EmployeeProjectRoles employeeProjectRoles) {
        this.employeeprojectrolesProjectroles.add(employeeProjectRoles);
        employeeProjectRoles.setProjectrole(this);
        return this;
    }

    public ProjectRoles removeEmployeeprojectrolesProjectrole(EmployeeProjectRoles employeeProjectRoles) {
        this.employeeprojectrolesProjectroles.remove(employeeProjectRoles);
        employeeProjectRoles.setProjectrole(null);
        return this;
    }

    public Set<ProjectLeadership> getProjectleadershipProjectroles() {
        return this.projectleadershipProjectroles;
    }

    public void setProjectleadershipProjectroles(Set<ProjectLeadership> projectLeaderships) {
        if (this.projectleadershipProjectroles != null) {
            this.projectleadershipProjectroles.forEach(i -> i.setProjectrole(null));
        }
        if (projectLeaderships != null) {
            projectLeaderships.forEach(i -> i.setProjectrole(this));
        }
        this.projectleadershipProjectroles = projectLeaderships;
    }

    public ProjectRoles projectleadershipProjectroles(Set<ProjectLeadership> projectLeaderships) {
        this.setProjectleadershipProjectroles(projectLeaderships);
        return this;
    }

    public ProjectRoles addProjectleadershipProjectrole(ProjectLeadership projectLeadership) {
        this.projectleadershipProjectroles.add(projectLeadership);
        projectLeadership.setProjectrole(this);
        return this;
    }

    public ProjectRoles removeProjectleadershipProjectrole(ProjectLeadership projectLeadership) {
        this.projectleadershipProjectroles.remove(projectLeadership);
        projectLeadership.setProjectrole(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectRoles)) {
            return false;
        }
        return id != null && id.equals(((ProjectRoles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectRoles{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", isleadership='" + getIsleadership() + "'" +
            "}";
    }
}
