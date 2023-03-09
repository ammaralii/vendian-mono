package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A WorkLogDetails.
 */
@Entity
@Table(name = "work_log_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkLogDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "percentage", length = 255)
    private String percentage;

    @Column(name = "hours")
    private Integer hours;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employee", "worklogdetailsWorklogs" }, allowSetters = true)
    private WorkLogs worklog;

    @ManyToOne
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
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkLogDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPercentage() {
        return this.percentage;
    }

    public WorkLogDetails percentage(String percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Integer getHours() {
        return this.hours;
    }

    public WorkLogDetails hours(Integer hours) {
        this.setHours(hours);
        return this;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public WorkLogDetails createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public WorkLogDetails updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public WorkLogs getWorklog() {
        return this.worklog;
    }

    public void setWorklog(WorkLogs workLogs) {
        this.worklog = workLogs;
    }

    public WorkLogDetails worklog(WorkLogs workLogs) {
        this.setWorklog(workLogs);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public WorkLogDetails project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkLogDetails)) {
            return false;
        }
        return id != null && id.equals(((WorkLogDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkLogDetails{" +
            "id=" + getId() +
            ", percentage='" + getPercentage() + "'" +
            ", hours=" + getHours() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
