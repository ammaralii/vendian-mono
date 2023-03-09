package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.QuestionsFrequencyPerTrackResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions-frequency-per-tracks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsFrequencyPerTrackCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter question;

    private IntegerFilter frequency;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter trackId;

    private Boolean distinct;

    public QuestionsFrequencyPerTrackCriteria() {}

    public QuestionsFrequencyPerTrackCriteria(QuestionsFrequencyPerTrackCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.question = other.question == null ? null : other.question.copy();
        this.frequency = other.frequency == null ? null : other.frequency.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.trackId = other.trackId == null ? null : other.trackId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuestionsFrequencyPerTrackCriteria copy() {
        return new QuestionsFrequencyPerTrackCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuestion() {
        return question;
    }

    public StringFilter question() {
        if (question == null) {
            question = new StringFilter();
        }
        return question;
    }

    public void setQuestion(StringFilter question) {
        this.question = question;
    }

    public IntegerFilter getFrequency() {
        return frequency;
    }

    public IntegerFilter frequency() {
        if (frequency == null) {
            frequency = new IntegerFilter();
        }
        return frequency;
    }

    public void setFrequency(IntegerFilter frequency) {
        this.frequency = frequency;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
    }

    public LongFilter getTrackId() {
        return trackId;
    }

    public LongFilter trackId() {
        if (trackId == null) {
            trackId = new LongFilter();
        }
        return trackId;
    }

    public void setTrackId(LongFilter trackId) {
        this.trackId = trackId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionsFrequencyPerTrackCriteria that = (QuestionsFrequencyPerTrackCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(question, that.question) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(trackId, that.trackId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, frequency, createdat, updatedat, trackId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsFrequencyPerTrackCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (question != null ? "question=" + question + ", " : "") +
            (frequency != null ? "frequency=" + frequency + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (trackId != null ? "trackId=" + trackId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
