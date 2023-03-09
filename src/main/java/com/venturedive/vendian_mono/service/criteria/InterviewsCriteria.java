package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Interviews} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.InterviewsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterviewsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter result;

    private StringFilter clientcomments;

    private StringFilter lmcomments;

    private InstantFilter scheduledat;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter employeeId;

    private LongFilter projectId;

    private LongFilter trackId;

    private LongFilter questionsInterviewId;

    private Boolean distinct;

    public InterviewsCriteria() {}

    public InterviewsCriteria(InterviewsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.clientcomments = other.clientcomments == null ? null : other.clientcomments.copy();
        this.lmcomments = other.lmcomments == null ? null : other.lmcomments.copy();
        this.scheduledat = other.scheduledat == null ? null : other.scheduledat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.trackId = other.trackId == null ? null : other.trackId.copy();
        this.questionsInterviewId = other.questionsInterviewId == null ? null : other.questionsInterviewId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterviewsCriteria copy() {
        return new InterviewsCriteria(this);
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

    public StringFilter getResult() {
        return result;
    }

    public StringFilter result() {
        if (result == null) {
            result = new StringFilter();
        }
        return result;
    }

    public void setResult(StringFilter result) {
        this.result = result;
    }

    public StringFilter getClientcomments() {
        return clientcomments;
    }

    public StringFilter clientcomments() {
        if (clientcomments == null) {
            clientcomments = new StringFilter();
        }
        return clientcomments;
    }

    public void setClientcomments(StringFilter clientcomments) {
        this.clientcomments = clientcomments;
    }

    public StringFilter getLmcomments() {
        return lmcomments;
    }

    public StringFilter lmcomments() {
        if (lmcomments == null) {
            lmcomments = new StringFilter();
        }
        return lmcomments;
    }

    public void setLmcomments(StringFilter lmcomments) {
        this.lmcomments = lmcomments;
    }

    public InstantFilter getScheduledat() {
        return scheduledat;
    }

    public InstantFilter scheduledat() {
        if (scheduledat == null) {
            scheduledat = new InstantFilter();
        }
        return scheduledat;
    }

    public void setScheduledat(InstantFilter scheduledat) {
        this.scheduledat = scheduledat;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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

    public LongFilter getQuestionsInterviewId() {
        return questionsInterviewId;
    }

    public LongFilter questionsInterviewId() {
        if (questionsInterviewId == null) {
            questionsInterviewId = new LongFilter();
        }
        return questionsInterviewId;
    }

    public void setQuestionsInterviewId(LongFilter questionsInterviewId) {
        this.questionsInterviewId = questionsInterviewId;
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
        final InterviewsCriteria that = (InterviewsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(result, that.result) &&
            Objects.equals(clientcomments, that.clientcomments) &&
            Objects.equals(lmcomments, that.lmcomments) &&
            Objects.equals(scheduledat, that.scheduledat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(trackId, that.trackId) &&
            Objects.equals(questionsInterviewId, that.questionsInterviewId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            result,
            clientcomments,
            lmcomments,
            scheduledat,
            createdat,
            updatedat,
            deletedat,
            employeeId,
            projectId,
            trackId,
            questionsInterviewId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (result != null ? "result=" + result + ", " : "") +
            (clientcomments != null ? "clientcomments=" + clientcomments + ", " : "") +
            (lmcomments != null ? "lmcomments=" + lmcomments + ", " : "") +
            (scheduledat != null ? "scheduledat=" + scheduledat + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (trackId != null ? "trackId=" + trackId + ", " : "") +
            (questionsInterviewId != null ? "questionsInterviewId=" + questionsInterviewId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
