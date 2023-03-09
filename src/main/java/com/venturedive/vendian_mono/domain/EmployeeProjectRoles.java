package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeProjectRoles.
 */
@Entity
@Table(name = "employee_project_roles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "project", "assignor", "employeeprojectrolesEmployeeprojects", "zemployeeprojectsEmployeeprojects" },
        allowSetters = true
    )
    private EmployeeProjects employeeproject;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeeprojectrolesProjectroles", "projectleadershipProjectroles" }, allowSetters = true)
    private ProjectRoles projectrole;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeProjectRoles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public EmployeeProjectRoles status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeProjectRoles createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeProjectRoles updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public EmployeeProjects getEmployeeproject() {
        return this.employeeproject;
    }

    public void setEmployeeproject(EmployeeProjects employeeProjects) {
        this.employeeproject = employeeProjects;
    }

    public EmployeeProjectRoles employeeproject(EmployeeProjects employeeProjects) {
        this.setEmployeeproject(employeeProjects);
        return this;
    }

    public ProjectRoles getProjectrole() {
        return this.projectrole;
    }

    public void setProjectrole(ProjectRoles projectRoles) {
        this.projectrole = projectRoles;
    }

    public EmployeeProjectRoles projectrole(ProjectRoles projectRoles) {
        this.setProjectrole(projectRoles);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProjectRoles)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProjectRoles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRoles{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
