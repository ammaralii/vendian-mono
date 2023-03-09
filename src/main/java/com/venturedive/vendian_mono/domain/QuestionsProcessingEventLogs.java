package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A QuestionsProcessingEventLogs.
 */
@Entity
@Table(name = "questions_processing_event_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsProcessingEventLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 65535)
    @Column(name = "processed_on", length = 65535, nullable = false)
    private String processedOn;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuestionsProcessingEventLogs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessedOn() {
        return this.processedOn;
    }

    public QuestionsProcessingEventLogs processedOn(String processedOn) {
        this.setProcessedOn(processedOn);
        return this;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public QuestionsProcessingEventLogs createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public QuestionsProcessingEventLogs updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsProcessingEventLogs)) {
            return false;
        }
        return id != null && id.equals(((QuestionsProcessingEventLogs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsProcessingEventLogs{" +
            "id=" + getId() +
            ", processedOn='" + getProcessedOn() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
