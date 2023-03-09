package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Tracks} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.TracksResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tracks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TracksCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter competencyId;

    private LongFilter interviewsTrackId;

    private LongFilter questionsTrackId;

    private LongFilter questionsfrequencyperclienttrackTrackId;

    private LongFilter questionsfrequencypertrackTrackId;

    private Boolean distinct;

    public TracksCriteria() {}

    public TracksCriteria(TracksCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.competencyId = other.competencyId == null ? null : other.competencyId.copy();
        this.interviewsTrackId = other.interviewsTrackId == null ? null : other.interviewsTrackId.copy();
        this.questionsTrackId = other.questionsTrackId == null ? null : other.questionsTrackId.copy();
        this.questionsfrequencyperclienttrackTrackId =
            other.questionsfrequencyperclienttrackTrackId == null ? null : other.questionsfrequencyperclienttrackTrackId.copy();
        this.questionsfrequencypertrackTrackId =
            other.questionsfrequencypertrackTrackId == null ? null : other.questionsfrequencypertrackTrackId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TracksCriteria copy() {
        return new TracksCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getCompetencyId() {
        return competencyId;
    }

    public LongFilter competencyId() {
        if (competencyId == null) {
            competencyId = new LongFilter();
        }
        return competencyId;
    }

    public void setCompetencyId(LongFilter competencyId) {
        this.competencyId = competencyId;
    }

    public LongFilter getInterviewsTrackId() {
        return interviewsTrackId;
    }

    public LongFilter interviewsTrackId() {
        if (interviewsTrackId == null) {
            interviewsTrackId = new LongFilter();
        }
        return interviewsTrackId;
    }

    public void setInterviewsTrackId(LongFilter interviewsTrackId) {
        this.interviewsTrackId = interviewsTrackId;
    }

    public LongFilter getQuestionsTrackId() {
        return questionsTrackId;
    }

    public LongFilter questionsTrackId() {
        if (questionsTrackId == null) {
            questionsTrackId = new LongFilter();
        }
        return questionsTrackId;
    }

    public void setQuestionsTrackId(LongFilter questionsTrackId) {
        this.questionsTrackId = questionsTrackId;
    }

    public LongFilter getQuestionsfrequencyperclienttrackTrackId() {
        return questionsfrequencyperclienttrackTrackId;
    }

    public LongFilter questionsfrequencyperclienttrackTrackId() {
        if (questionsfrequencyperclienttrackTrackId == null) {
            questionsfrequencyperclienttrackTrackId = new LongFilter();
        }
        return questionsfrequencyperclienttrackTrackId;
    }

    public void setQuestionsfrequencyperclienttrackTrackId(LongFilter questionsfrequencyperclienttrackTrackId) {
        this.questionsfrequencyperclienttrackTrackId = questionsfrequencyperclienttrackTrackId;
    }

    public LongFilter getQuestionsfrequencypertrackTrackId() {
        return questionsfrequencypertrackTrackId;
    }

    public LongFilter questionsfrequencypertrackTrackId() {
        if (questionsfrequencypertrackTrackId == null) {
            questionsfrequencypertrackTrackId = new LongFilter();
        }
        return questionsfrequencypertrackTrackId;
    }

    public void setQuestionsfrequencypertrackTrackId(LongFilter questionsfrequencypertrackTrackId) {
        this.questionsfrequencypertrackTrackId = questionsfrequencypertrackTrackId;
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
        final TracksCriteria that = (TracksCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(competencyId, that.competencyId) &&
            Objects.equals(interviewsTrackId, that.interviewsTrackId) &&
            Objects.equals(questionsTrackId, that.questionsTrackId) &&
            Objects.equals(questionsfrequencyperclienttrackTrackId, that.questionsfrequencyperclienttrackTrackId) &&
            Objects.equals(questionsfrequencypertrackTrackId, that.questionsfrequencypertrackTrackId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            createdat,
            updatedat,
            deletedat,
            competencyId,
            interviewsTrackId,
            questionsTrackId,
            questionsfrequencyperclienttrackTrackId,
            questionsfrequencypertrackTrackId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TracksCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (competencyId != null ? "competencyId=" + competencyId + ", " : "") +
            (interviewsTrackId != null ? "interviewsTrackId=" + interviewsTrackId + ", " : "") +
            (questionsTrackId != null ? "questionsTrackId=" + questionsTrackId + ", " : "") +
            (questionsfrequencyperclienttrackTrackId != null ? "questionsfrequencyperclienttrackTrackId=" + questionsfrequencyperclienttrackTrackId + ", " : "") +
            (questionsfrequencypertrackTrackId != null ? "questionsfrequencypertrackTrackId=" + questionsfrequencypertrackTrackId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
