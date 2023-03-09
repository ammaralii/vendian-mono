package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A QuestionsFrequencyPerClientTrack.
 */
@Entity
@Table(name = "questions_frequency_per_client_track")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsFrequencyPerClientTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 65535)
    @Column(name = "question", length = 65535, nullable = false)
    private String question;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

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

    public QuestionsFrequencyPerClientTrack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public QuestionsFrequencyPerClientTrack question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getFrequency() {
        return this.frequency;
    }

    public QuestionsFrequencyPerClientTrack frequency(Integer frequency) {
        this.setFrequency(frequency);
        return this;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public QuestionsFrequencyPerClientTrack createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public QuestionsFrequencyPerClientTrack updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public QuestionsFrequencyPerClientTrack project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public Tracks getTrack() {
        return this.track;
    }

    public void setTrack(Tracks tracks) {
        this.track = tracks;
    }

    public QuestionsFrequencyPerClientTrack track(Tracks tracks) {
        this.setTrack(tracks);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsFrequencyPerClientTrack)) {
            return false;
        }
        return id != null && id.equals(((QuestionsFrequencyPerClientTrack) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsFrequencyPerClientTrack{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", frequency=" + getFrequency() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
