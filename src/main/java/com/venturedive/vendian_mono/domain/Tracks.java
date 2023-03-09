package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tracks.
 */
@Entity
@Table(name = "tracks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tracks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeejobinfoCompetencies", "employeesCompetencies", "tracksCompetencies" }, allowSetters = true)
    private Competencies competency;

    @OneToMany(mappedBy = "track")
    @JsonIgnoreProperties(value = { "employee", "project", "track", "questionsInterviews" }, allowSetters = true)
    private Set<Interviews> interviewsTracks = new HashSet<>();

    @OneToMany(mappedBy = "track")
    @JsonIgnoreProperties(value = { "interview", "project", "track" }, allowSetters = true)
    private Set<Questions> questionsTracks = new HashSet<>();

    @OneToMany(mappedBy = "track")
    @JsonIgnoreProperties(value = { "project", "track" }, allowSetters = true)
    private Set<QuestionsFrequencyPerClientTrack> questionsfrequencyperclienttrackTracks = new HashSet<>();

    @OneToMany(mappedBy = "track")
    @JsonIgnoreProperties(value = { "track" }, allowSetters = true)
    private Set<QuestionsFrequencyPerTrack> questionsfrequencypertrackTracks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tracks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Tracks name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Tracks description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Tracks createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Tracks updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Tracks deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Competencies getCompetency() {
        return this.competency;
    }

    public void setCompetency(Competencies competencies) {
        this.competency = competencies;
    }

    public Tracks competency(Competencies competencies) {
        this.setCompetency(competencies);
        return this;
    }

    public Set<Interviews> getInterviewsTracks() {
        return this.interviewsTracks;
    }

    public void setInterviewsTracks(Set<Interviews> interviews) {
        if (this.interviewsTracks != null) {
            this.interviewsTracks.forEach(i -> i.setTrack(null));
        }
        if (interviews != null) {
            interviews.forEach(i -> i.setTrack(this));
        }
        this.interviewsTracks = interviews;
    }

    public Tracks interviewsTracks(Set<Interviews> interviews) {
        this.setInterviewsTracks(interviews);
        return this;
    }

    public Tracks addInterviewsTrack(Interviews interviews) {
        this.interviewsTracks.add(interviews);
        interviews.setTrack(this);
        return this;
    }

    public Tracks removeInterviewsTrack(Interviews interviews) {
        this.interviewsTracks.remove(interviews);
        interviews.setTrack(null);
        return this;
    }

    public Set<Questions> getQuestionsTracks() {
        return this.questionsTracks;
    }

    public void setQuestionsTracks(Set<Questions> questions) {
        if (this.questionsTracks != null) {
            this.questionsTracks.forEach(i -> i.setTrack(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setTrack(this));
        }
        this.questionsTracks = questions;
    }

    public Tracks questionsTracks(Set<Questions> questions) {
        this.setQuestionsTracks(questions);
        return this;
    }

    public Tracks addQuestionsTrack(Questions questions) {
        this.questionsTracks.add(questions);
        questions.setTrack(this);
        return this;
    }

    public Tracks removeQuestionsTrack(Questions questions) {
        this.questionsTracks.remove(questions);
        questions.setTrack(null);
        return this;
    }

    public Set<QuestionsFrequencyPerClientTrack> getQuestionsfrequencyperclienttrackTracks() {
        return this.questionsfrequencyperclienttrackTracks;
    }

    public void setQuestionsfrequencyperclienttrackTracks(Set<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTracks) {
        if (this.questionsfrequencyperclienttrackTracks != null) {
            this.questionsfrequencyperclienttrackTracks.forEach(i -> i.setTrack(null));
        }
        if (questionsFrequencyPerClientTracks != null) {
            questionsFrequencyPerClientTracks.forEach(i -> i.setTrack(this));
        }
        this.questionsfrequencyperclienttrackTracks = questionsFrequencyPerClientTracks;
    }

    public Tracks questionsfrequencyperclienttrackTracks(Set<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTracks) {
        this.setQuestionsfrequencyperclienttrackTracks(questionsFrequencyPerClientTracks);
        return this;
    }

    public Tracks addQuestionsfrequencyperclienttrackTrack(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        this.questionsfrequencyperclienttrackTracks.add(questionsFrequencyPerClientTrack);
        questionsFrequencyPerClientTrack.setTrack(this);
        return this;
    }

    public Tracks removeQuestionsfrequencyperclienttrackTrack(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        this.questionsfrequencyperclienttrackTracks.remove(questionsFrequencyPerClientTrack);
        questionsFrequencyPerClientTrack.setTrack(null);
        return this;
    }

    public Set<QuestionsFrequencyPerTrack> getQuestionsfrequencypertrackTracks() {
        return this.questionsfrequencypertrackTracks;
    }

    public void setQuestionsfrequencypertrackTracks(Set<QuestionsFrequencyPerTrack> questionsFrequencyPerTracks) {
        if (this.questionsfrequencypertrackTracks != null) {
            this.questionsfrequencypertrackTracks.forEach(i -> i.setTrack(null));
        }
        if (questionsFrequencyPerTracks != null) {
            questionsFrequencyPerTracks.forEach(i -> i.setTrack(this));
        }
        this.questionsfrequencypertrackTracks = questionsFrequencyPerTracks;
    }

    public Tracks questionsfrequencypertrackTracks(Set<QuestionsFrequencyPerTrack> questionsFrequencyPerTracks) {
        this.setQuestionsfrequencypertrackTracks(questionsFrequencyPerTracks);
        return this;
    }

    public Tracks addQuestionsfrequencypertrackTrack(QuestionsFrequencyPerTrack questionsFrequencyPerTrack) {
        this.questionsfrequencypertrackTracks.add(questionsFrequencyPerTrack);
        questionsFrequencyPerTrack.setTrack(this);
        return this;
    }

    public Tracks removeQuestionsfrequencypertrackTrack(QuestionsFrequencyPerTrack questionsFrequencyPerTrack) {
        this.questionsfrequencypertrackTracks.remove(questionsFrequencyPerTrack);
        questionsFrequencyPerTrack.setTrack(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tracks)) {
            return false;
        }
        return id != null && id.equals(((Tracks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tracks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
