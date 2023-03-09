package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealResourceEventLogs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealResourceEventLogsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-resource-event-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourceEventLogsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comments;

    private InstantFilter createdat;

    private LongFilter matchingresourceId;

    private LongFilter resourcestatusId;

    private LongFilter systemstatusId;

    private Boolean distinct;

    public DealResourceEventLogsCriteria() {}

    public DealResourceEventLogsCriteria(DealResourceEventLogsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.matchingresourceId = other.matchingresourceId == null ? null : other.matchingresourceId.copy();
        this.resourcestatusId = other.resourcestatusId == null ? null : other.resourcestatusId.copy();
        this.systemstatusId = other.systemstatusId == null ? null : other.systemstatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealResourceEventLogsCriteria copy() {
        return new DealResourceEventLogsCriteria(this);
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

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
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

    public LongFilter getMatchingresourceId() {
        return matchingresourceId;
    }

    public LongFilter matchingresourceId() {
        if (matchingresourceId == null) {
            matchingresourceId = new LongFilter();
        }
        return matchingresourceId;
    }

    public void setMatchingresourceId(LongFilter matchingresourceId) {
        this.matchingresourceId = matchingresourceId;
    }

    public LongFilter getResourcestatusId() {
        return resourcestatusId;
    }

    public LongFilter resourcestatusId() {
        if (resourcestatusId == null) {
            resourcestatusId = new LongFilter();
        }
        return resourcestatusId;
    }

    public void setResourcestatusId(LongFilter resourcestatusId) {
        this.resourcestatusId = resourcestatusId;
    }

    public LongFilter getSystemstatusId() {
        return systemstatusId;
    }

    public LongFilter systemstatusId() {
        if (systemstatusId == null) {
            systemstatusId = new LongFilter();
        }
        return systemstatusId;
    }

    public void setSystemstatusId(LongFilter systemstatusId) {
        this.systemstatusId = systemstatusId;
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
        final DealResourceEventLogsCriteria that = (DealResourceEventLogsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(matchingresourceId, that.matchingresourceId) &&
            Objects.equals(resourcestatusId, that.resourcestatusId) &&
            Objects.equals(systemstatusId, that.systemstatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comments, createdat, matchingresourceId, resourcestatusId, systemstatusId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourceEventLogsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (matchingresourceId != null ? "matchingresourceId=" + matchingresourceId + ", " : "") +
            (resourcestatusId != null ? "resourcestatusId=" + resourcestatusId + ", " : "") +
            (systemstatusId != null ? "systemstatusId=" + systemstatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
