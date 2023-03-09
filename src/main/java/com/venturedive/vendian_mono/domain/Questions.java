package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Questions.
 */
@Entity
@Table(name = "questions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 65535)
    @Column(name = "text", length = 65535)
    private String text;

    @Size(max = 65535)
    @Column(name = "answer", length = 65535)
    private String answer;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @Size(max = 65535)
    @Column(name = "cleaneduptext", length = 65535)
    private String cleaneduptext;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employee", "project", "track", "questionsInterviews" }, allowSetters = true)
    private Interviews interview;

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

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "competency",
            "interviewsTracks",
            "questionsTracks",
            "questionsfrequencyperclienttrackTracks",
            "questionsfrequencypertrackTracks",
        },
        allowSetters = true
    )
    private Tracks track;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Questions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public Questions text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return this.answer;
    }

    public Questions answer(String answer) {
        this.setAnswer(answer);
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Questions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Questions updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Questions deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public String getCleaneduptext() {
        return this.cleaneduptext;
    }

    public Questions cleaneduptext(String cleaneduptext) {
        this.setCleaneduptext(cleaneduptext);
        return this;
    }

    public void setCleaneduptext(String cleaneduptext) {
        this.cleaneduptext = cleaneduptext;
    }

    public Interviews getInterview() {
        return this.interview;
    }

    public void setInterview(Interviews interviews) {
        this.interview = interviews;
    }

    public Questions interview(Interviews interviews) {
        this.setInterview(interviews);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public Questions project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public Tracks getTrack() {
        return this.track;
    }

    public void setTrack(Tracks tracks) {
        this.track = tracks;
    }

    public Questions track(Tracks tracks) {
        this.setTrack(tracks);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questions)) {
            return false;
        }
        return id != null && id.equals(((Questions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", cleaneduptext='" + getCleaneduptext() + "'" +
            "}";
    }
}
