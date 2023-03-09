package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealResourceStatus} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealResourceStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-resource-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourceStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter displayname;

    private StringFilter group;

    private StringFilter systemKey;

    private InstantFilter effectivedate;

    private InstantFilter enddate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter dealrequirementmatchingresourcesResourcestatusId;

    private LongFilter dealrequirementmatchingresourcesSystemstatusId;

    private LongFilter dealresourceeventlogsResourcestatusId;

    private LongFilter dealresourceeventlogsSystemstatusId;

    private Boolean distinct;

    public DealResourceStatusCriteria() {}

    public DealResourceStatusCriteria(DealResourceStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.displayname = other.displayname == null ? null : other.displayname.copy();
        this.group = other.group == null ? null : other.group.copy();
        this.systemKey = other.systemKey == null ? null : other.systemKey.copy();
        this.effectivedate = other.effectivedate == null ? null : other.effectivedate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.dealrequirementmatchingresourcesResourcestatusId =
            other.dealrequirementmatchingresourcesResourcestatusId == null
                ? null
                : other.dealrequirementmatchingresourcesResourcestatusId.copy();
        this.dealrequirementmatchingresourcesSystemstatusId =
            other.dealrequirementmatchingresourcesSystemstatusId == null
                ? null
                : other.dealrequirementmatchingresourcesSystemstatusId.copy();
        this.dealresourceeventlogsResourcestatusId =
            other.dealresourceeventlogsResourcestatusId == null ? null : other.dealresourceeventlogsResourcestatusId.copy();
        this.dealresourceeventlogsSystemstatusId =
            other.dealresourceeventlogsSystemstatusId == null ? null : other.dealresourceeventlogsSystemstatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealResourceStatusCriteria copy() {
        return new DealResourceStatusCriteria(this);
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

    public StringFilter getDisplayname() {
        return displayname;
    }

    public StringFilter displayname() {
        if (displayname == null) {
            displayname = new StringFilter();
        }
        return displayname;
    }

    public void setDisplayname(StringFilter displayname) {
        this.displayname = displayname;
    }

    public StringFilter getGroup() {
        return group;
    }

    public StringFilter group() {
        if (group == null) {
            group = new StringFilter();
        }
        return group;
    }

    public void setGroup(StringFilter group) {
        this.group = group;
    }

    public StringFilter getSystemKey() {
        return systemKey;
    }

    public StringFilter systemKey() {
        if (systemKey == null) {
            systemKey = new StringFilter();
        }
        return systemKey;
    }

    public void setSystemKey(StringFilter systemKey) {
        this.systemKey = systemKey;
    }

    public InstantFilter getEffectivedate() {
        return effectivedate;
    }

    public InstantFilter effectivedate() {
        if (effectivedate == null) {
            effectivedate = new InstantFilter();
        }
        return effectivedate;
    }

    public void setEffectivedate(InstantFilter effectivedate) {
        this.effectivedate = effectivedate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
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

    public LongFilter getDealrequirementmatchingresourcesResourcestatusId() {
        return dealrequirementmatchingresourcesResourcestatusId;
    }

    public LongFilter dealrequirementmatchingresourcesResourcestatusId() {
        if (dealrequirementmatchingresourcesResourcestatusId == null) {
            dealrequirementmatchingresourcesResourcestatusId = new LongFilter();
        }
        return dealrequirementmatchingresourcesResourcestatusId;
    }

    public void setDealrequirementmatchingresourcesResourcestatusId(LongFilter dealrequirementmatchingresourcesResourcestatusId) {
        this.dealrequirementmatchingresourcesResourcestatusId = dealrequirementmatchingresourcesResourcestatusId;
    }

    public LongFilter getDealrequirementmatchingresourcesSystemstatusId() {
        return dealrequirementmatchingresourcesSystemstatusId;
    }

    public LongFilter dealrequirementmatchingresourcesSystemstatusId() {
        if (dealrequirementmatchingresourcesSystemstatusId == null) {
            dealrequirementmatchingresourcesSystemstatusId = new LongFilter();
        }
        return dealrequirementmatchingresourcesSystemstatusId;
    }

    public void setDealrequirementmatchingresourcesSystemstatusId(LongFilter dealrequirementmatchingresourcesSystemstatusId) {
        this.dealrequirementmatchingresourcesSystemstatusId = dealrequirementmatchingresourcesSystemstatusId;
    }

    public LongFilter getDealresourceeventlogsResourcestatusId() {
        return dealresourceeventlogsResourcestatusId;
    }

    public LongFilter dealresourceeventlogsResourcestatusId() {
        if (dealresourceeventlogsResourcestatusId == null) {
            dealresourceeventlogsResourcestatusId = new LongFilter();
        }
        return dealresourceeventlogsResourcestatusId;
    }

    public void setDealresourceeventlogsResourcestatusId(LongFilter dealresourceeventlogsResourcestatusId) {
        this.dealresourceeventlogsResourcestatusId = dealresourceeventlogsResourcestatusId;
    }

    public LongFilter getDealresourceeventlogsSystemstatusId() {
        return dealresourceeventlogsSystemstatusId;
    }

    public LongFilter dealresourceeventlogsSystemstatusId() {
        if (dealresourceeventlogsSystemstatusId == null) {
            dealresourceeventlogsSystemstatusId = new LongFilter();
        }
        return dealresourceeventlogsSystemstatusId;
    }

    public void setDealresourceeventlogsSystemstatusId(LongFilter dealresourceeventlogsSystemstatusId) {
        this.dealresourceeventlogsSystemstatusId = dealresourceeventlogsSystemstatusId;
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
        final DealResourceStatusCriteria that = (DealResourceStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(displayname, that.displayname) &&
            Objects.equals(group, that.group) &&
            Objects.equals(systemKey, that.systemKey) &&
            Objects.equals(effectivedate, that.effectivedate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(dealrequirementmatchingresourcesResourcestatusId, that.dealrequirementmatchingresourcesResourcestatusId) &&
            Objects.equals(dealrequirementmatchingresourcesSystemstatusId, that.dealrequirementmatchingresourcesSystemstatusId) &&
            Objects.equals(dealresourceeventlogsResourcestatusId, that.dealresourceeventlogsResourcestatusId) &&
            Objects.equals(dealresourceeventlogsSystemstatusId, that.dealresourceeventlogsSystemstatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            displayname,
            group,
            systemKey,
            effectivedate,
            enddate,
            createdat,
            updatedat,
            dealrequirementmatchingresourcesResourcestatusId,
            dealrequirementmatchingresourcesSystemstatusId,
            dealresourceeventlogsResourcestatusId,
            dealresourceeventlogsSystemstatusId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourceStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (displayname != null ? "displayname=" + displayname + ", " : "") +
            (group != null ? "group=" + group + ", " : "") +
            (systemKey != null ? "systemKey=" + systemKey + ", " : "") +
            (effectivedate != null ? "effectivedate=" + effectivedate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (dealrequirementmatchingresourcesResourcestatusId != null ? "dealrequirementmatchingresourcesResourcestatusId=" + dealrequirementmatchingresourcesResourcestatusId + ", " : "") +
            (dealrequirementmatchingresourcesSystemstatusId != null ? "dealrequirementmatchingresourcesSystemstatusId=" + dealrequirementmatchingresourcesSystemstatusId + ", " : "") +
            (dealresourceeventlogsResourcestatusId != null ? "dealresourceeventlogsResourcestatusId=" + dealresourceeventlogsResourcestatusId + ", " : "") +
            (dealresourceeventlogsSystemstatusId != null ? "dealresourceeventlogsSystemstatusId=" + dealresourceeventlogsSystemstatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
