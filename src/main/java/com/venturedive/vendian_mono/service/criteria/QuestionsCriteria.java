package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Questions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.QuestionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private StringFilter answer;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private StringFilter cleaneduptext;

    private LongFilter interviewId;

    private LongFilter projectId;

    private LongFilter trackId;

    private Boolean distinct;

    public QuestionsCriteria() {}

    public QuestionsCriteria(QuestionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.cleaneduptext = other.cleaneduptext == null ? null : other.cleaneduptext.copy();
        this.interviewId = other.interviewId == null ? null : other.interviewId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.trackId = other.trackId == null ? null : other.trackId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuestionsCriteria copy() {
        return new QuestionsCriteria(this);
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

    public StringFilter getText() {
        return text;
    }

    public StringFilter text() {
        if (text == null) {
            text = new StringFilter();
        }
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public StringFilter answer() {
        if (answer == null) {
            answer = new StringFilter();
        }
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
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

    public InstantFilter getDeletedat() {
        return deletedat;
    }

    public InstantFilter deletedat() {
        if (deletedat == null) {
            deletedat = new InstantFilter();
        }
        return deletedat;
    }

    public void setDeletedat(InstantFilter deletedat) {
        this.deletedat = deletedat;
    }

    public StringFilter getCleaneduptext() {
        return cleaneduptext;
    }

    public StringFilter cleaneduptext() {
        if (cleaneduptext == null) {
            cleaneduptext = new StringFilter();
        }
        return cleaneduptext;
    }

    public void setCleaneduptext(StringFilter cleaneduptext) {
        this.cleaneduptext = cleaneduptext;
    }

    public LongFilter getInterviewId() {
        return interviewId;
    }

    public LongFilter interviewId() {
        if (interviewId == null) {
            interviewId = new LongFilter();
        }
        return interviewId;
    }

    public void setInterviewId(LongFilter interviewId) {
        this.interviewId = interviewId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
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
        final QuestionsCriteria that = (QuestionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(cleaneduptext, that.cleaneduptext) &&
            Objects.equals(interviewId, that.interviewId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(trackId, that.trackId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, answer, createdat, updatedat, deletedat, cleaneduptext, interviewId, projectId, trackId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (answer != null ? "answer=" + answer + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (cleaneduptext != null ? "cleaneduptext=" + cleaneduptext + ", " : "") +
            (interviewId != null ? "interviewId=" + interviewId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (trackId != null ? "trackId=" + trackId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
