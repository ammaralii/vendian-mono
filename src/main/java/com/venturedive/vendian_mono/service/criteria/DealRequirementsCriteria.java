package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealRequirements} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealRequirementsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-requirements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealRequirementsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dealreqidentifier;

    private StringFilter competencyname;

    private StringFilter skills;

    private DoubleFilter resourcerequired;

    private StringFilter minexperiencelevel;

    private StringFilter maxexperiencelevel;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter dealId;

    private LongFilter dealrequirementmatchingresourcesDealrequirementId;

    private Boolean distinct;

    public DealRequirementsCriteria() {}

    public DealRequirementsCriteria(DealRequirementsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dealreqidentifier = other.dealreqidentifier == null ? null : other.dealreqidentifier.copy();
        this.competencyname = other.competencyname == null ? null : other.competencyname.copy();
        this.skills = other.skills == null ? null : other.skills.copy();
        this.resourcerequired = other.resourcerequired == null ? null : other.resourcerequired.copy();
        this.minexperiencelevel = other.minexperiencelevel == null ? null : other.minexperiencelevel.copy();
        this.maxexperiencelevel = other.maxexperiencelevel == null ? null : other.maxexperiencelevel.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.dealId = other.dealId == null ? null : other.dealId.copy();
        this.dealrequirementmatchingresourcesDealrequirementId =
            other.dealrequirementmatchingresourcesDealrequirementId == null
                ? null
                : other.dealrequirementmatchingresourcesDealrequirementId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealRequirementsCriteria copy() {
        return new DealRequirementsCriteria(this);
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

    public StringFilter getDealreqidentifier() {
        return dealreqidentifier;
    }

    public StringFilter dealreqidentifier() {
        if (dealreqidentifier == null) {
            dealreqidentifier = new StringFilter();
        }
        return dealreqidentifier;
    }

    public void setDealreqidentifier(StringFilter dealreqidentifier) {
        this.dealreqidentifier = dealreqidentifier;
    }

    public StringFilter getCompetencyname() {
        return competencyname;
    }

    public StringFilter competencyname() {
        if (competencyname == null) {
            competencyname = new StringFilter();
        }
        return competencyname;
    }

    public void setCompetencyname(StringFilter competencyname) {
        this.competencyname = competencyname;
    }

    public StringFilter getSkills() {
        return skills;
    }

    public StringFilter skills() {
        if (skills == null) {
            skills = new StringFilter();
        }
        return skills;
    }

    public void setSkills(StringFilter skills) {
        this.skills = skills;
    }

    public DoubleFilter getResourcerequired() {
        return resourcerequired;
    }

    public DoubleFilter resourcerequired() {
        if (resourcerequired == null) {
            resourcerequired = new DoubleFilter();
        }
        return resourcerequired;
    }

    public void setResourcerequired(DoubleFilter resourcerequired) {
        this.resourcerequired = resourcerequired;
    }

    public StringFilter getMinexperiencelevel() {
        return minexperiencelevel;
    }

    public StringFilter minexperiencelevel() {
        if (minexperiencelevel == null) {
            minexperiencelevel = new StringFilter();
        }
        return minexperiencelevel;
    }

    public void setMinexperiencelevel(StringFilter minexperiencelevel) {
        this.minexperiencelevel = minexperiencelevel;
    }

    public StringFilter getMaxexperiencelevel() {
        return maxexperiencelevel;
    }

    public StringFilter maxexperiencelevel() {
        if (maxexperiencelevel == null) {
            maxexperiencelevel = new StringFilter();
        }
        return maxexperiencelevel;
    }

    public void setMaxexperiencelevel(StringFilter maxexperiencelevel) {
        this.maxexperiencelevel = maxexperiencelevel;
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

    public LongFilter getDealId() {
        return dealId;
    }

    public LongFilter dealId() {
        if (dealId == null) {
            dealId = new LongFilter();
        }
        return dealId;
    }

    public void setDealId(LongFilter dealId) {
        this.dealId = dealId;
    }

    public LongFilter getDealrequirementmatchingresourcesDealrequirementId() {
        return dealrequirementmatchingresourcesDealrequirementId;
    }

    public LongFilter dealrequirementmatchingresourcesDealrequirementId() {
        if (dealrequirementmatchingresourcesDealrequirementId == null) {
            dealrequirementmatchingresourcesDealrequirementId = new LongFilter();
        }
        return dealrequirementmatchingresourcesDealrequirementId;
    }

    public void setDealrequirementmatchingresourcesDealrequirementId(LongFilter dealrequirementmatchingresourcesDealrequirementId) {
        this.dealrequirementmatchingresourcesDealrequirementId = dealrequirementmatchingresourcesDealrequirementId;
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
        final DealRequirementsCriteria that = (DealRequirementsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dealreqidentifier, that.dealreqidentifier) &&
            Objects.equals(competencyname, that.competencyname) &&
            Objects.equals(skills, that.skills) &&
            Objects.equals(resourcerequired, that.resourcerequired) &&
            Objects.equals(minexperiencelevel, that.minexperiencelevel) &&
            Objects.equals(maxexperiencelevel, that.maxexperiencelevel) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(dealId, that.dealId) &&
            Objects.equals(dealrequirementmatchingresourcesDealrequirementId, that.dealrequirementmatchingresourcesDealrequirementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dealreqidentifier,
            competencyname,
            skills,
            resourcerequired,
            minexperiencelevel,
            maxexperiencelevel,
            createdat,
            updatedat,
            deletedat,
            dealId,
            dealrequirementmatchingresourcesDealrequirementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealRequirementsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dealreqidentifier != null ? "dealreqidentifier=" + dealreqidentifier + ", " : "") +
            (competencyname != null ? "competencyname=" + competencyname + ", " : "") +
            (skills != null ? "skills=" + skills + ", " : "") +
            (resourcerequired != null ? "resourcerequired=" + resourcerequired + ", " : "") +
            (minexperiencelevel != null ? "minexperiencelevel=" + minexperiencelevel + ", " : "") +
            (maxexperiencelevel != null ? "maxexperiencelevel=" + maxexperiencelevel + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (dealId != null ? "dealId=" + dealId + ", " : "") +
            (dealrequirementmatchingresourcesDealrequirementId != null ? "dealrequirementmatchingresourcesDealrequirementId=" + dealrequirementmatchingresourcesDealrequirementId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
