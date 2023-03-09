package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.QuestionsProcessingEventLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions-processing-event-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsProcessingEventLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter processedOn;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private Boolean distinct;

    public QuestionsProcessingEventLogsCriteria() {}

    public QuestionsProcessingEventLogsCriteria(QuestionsProcessingEventLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.processedOn = other.processedOn == null ? null : other.processedOn.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuestionsProcessingEventLogsCriteria copy() {
        return new QuestionsProcessingEventLogsCriteria(this);
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

    public StringFilter getProcessedOn() {
        return processedOn;
    }

    public StringFilter processedOn() {
        if (processedOn == null) {
            processedOn = new StringFilter();
        }
        return processedOn;
    }

    public void setProcessedOn(StringFilter processedOn) {
        this.processedOn = processedOn;
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
        final QuestionsProcessingEventLogsCriteria that = (QuestionsProcessingEventLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(processedOn, that.processedOn) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, processedOn, createdat, updatedat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsProcessingEventLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (processedOn != null ? "processedOn=" + processedOn + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
