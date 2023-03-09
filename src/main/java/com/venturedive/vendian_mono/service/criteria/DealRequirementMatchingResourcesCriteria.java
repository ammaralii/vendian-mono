package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealRequirementMatchingResources} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealRequirementMatchingResourcesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-requirement-matching-resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealRequirementMatchingResourcesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comments;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter dealrequirementId;

    private LongFilter resourceId;

    private LongFilter resourcestatusId;

    private LongFilter systemstatusId;

    private LongFilter dealresourceeventlogsMatchingresourceId;

    private Boolean distinct;

    public DealRequirementMatchingResourcesCriteria() {}

    public DealRequirementMatchingResourcesCriteria(DealRequirementMatchingResourcesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.dealrequirementId = other.dealrequirementId == null ? null : other.dealrequirementId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.resourcestatusId = other.resourcestatusId == null ? null : other.resourcestatusId.copy();
        this.systemstatusId = other.systemstatusId == null ? null : other.systemstatusId.copy();
        this.dealresourceeventlogsMatchingresourceId =
            other.dealresourceeventlogsMatchingresourceId == null ? null : other.dealresourceeventlogsMatchingresourceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealRequirementMatchingResourcesCriteria copy() {
        return new DealRequirementMatchingResourcesCriteria(this);
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

    public LongFilter getDealrequirementId() {
        return dealrequirementId;
    }

    public LongFilter dealrequirementId() {
        if (dealrequirementId == null) {
            dealrequirementId = new LongFilter();
        }
        return dealrequirementId;
    }

    public void setDealrequirementId(LongFilter dealrequirementId) {
        this.dealrequirementId = dealrequirementId;
    }

    public LongFilter getResourceId() {
        return resourceId;
    }

    public LongFilter resourceId() {
        if (resourceId == null) {
            resourceId = new LongFilter();
        }
        return resourceId;
    }

    public void setResourceId(LongFilter resourceId) {
        this.resourceId = resourceId;
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

    public LongFilter getDealresourceeventlogsMatchingresourceId() {
        return dealresourceeventlogsMatchingresourceId;
    }

    public LongFilter dealresourceeventlogsMatchingresourceId() {
        if (dealresourceeventlogsMatchingresourceId == null) {
            dealresourceeventlogsMatchingresourceId = new LongFilter();
        }
        return dealresourceeventlogsMatchingresourceId;
    }

    public void setDealresourceeventlogsMatchingresourceId(LongFilter dealresourceeventlogsMatchingresourceId) {
        this.dealresourceeventlogsMatchingresourceId = dealresourceeventlogsMatchingresourceId;
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
        final DealRequirementMatchingResourcesCriteria that = (DealRequirementMatchingResourcesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(dealrequirementId, that.dealrequirementId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(resourcestatusId, that.resourcestatusId) &&
            Objects.equals(systemstatusId, that.systemstatusId) &&
            Objects.equals(dealresourceeventlogsMatchingresourceId, that.dealresourceeventlogsMatchingresourceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            comments,
            createdat,
            updatedat,
            deletedat,
            dealrequirementId,
            resourceId,
            resourcestatusId,
            systemstatusId,
            dealresourceeventlogsMatchingresourceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealRequirementMatchingResourcesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (dealrequirementId != null ? "dealrequirementId=" + dealrequirementId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            (resourcestatusId != null ? "resourcestatusId=" + resourcestatusId + ", " : "") +
            (systemstatusId != null ? "systemstatusId=" + systemstatusId + ", " : "") +
            (dealresourceeventlogsMatchingresourceId != null ? "dealresourceeventlogsMatchingresourceId=" + dealresourceeventlogsMatchingresourceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
