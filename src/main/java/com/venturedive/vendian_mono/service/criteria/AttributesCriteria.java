package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Attributes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.AttributesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttributesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private InstantFilter effDate;

    private LongFilter leaveapproversAttributeId;

    private LongFilter leaveescalationapproversAttributeId;

    private LongFilter raterattributemappingsAttributesforId;

    private LongFilter raterattributemappingsAttributesbyId;

    private LongFilter ratingattributemappingsAttributeId;

    private LongFilter userattributeapprovalrulesAttributeId;

    private LongFilter userattributeescalationrulesAttributeId;

    private LongFilter userattributesAttributeId;

    private LongFilter userrelationapprovalrulesAttributeId;

    private LongFilter userrelationsAttributeId;

    private Boolean distinct;

    public AttributesCriteria() {}

    public AttributesCriteria(AttributesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.leaveapproversAttributeId = other.leaveapproversAttributeId == null ? null : other.leaveapproversAttributeId.copy();
        this.leaveescalationapproversAttributeId =
            other.leaveescalationapproversAttributeId == null ? null : other.leaveescalationapproversAttributeId.copy();
        this.raterattributemappingsAttributesforId =
            other.raterattributemappingsAttributesforId == null ? null : other.raterattributemappingsAttributesforId.copy();
        this.raterattributemappingsAttributesbyId =
            other.raterattributemappingsAttributesbyId == null ? null : other.raterattributemappingsAttributesbyId.copy();
        this.ratingattributemappingsAttributeId =
            other.ratingattributemappingsAttributeId == null ? null : other.ratingattributemappingsAttributeId.copy();
        this.userattributeapprovalrulesAttributeId =
            other.userattributeapprovalrulesAttributeId == null ? null : other.userattributeapprovalrulesAttributeId.copy();
        this.userattributeescalationrulesAttributeId =
            other.userattributeescalationrulesAttributeId == null ? null : other.userattributeescalationrulesAttributeId.copy();
        this.userattributesAttributeId = other.userattributesAttributeId == null ? null : other.userattributesAttributeId.copy();
        this.userrelationapprovalrulesAttributeId =
            other.userrelationapprovalrulesAttributeId == null ? null : other.userrelationapprovalrulesAttributeId.copy();
        this.userrelationsAttributeId = other.userrelationsAttributeId == null ? null : other.userrelationsAttributeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttributesCriteria copy() {
        return new AttributesCriteria(this);
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

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            updatedAt = new InstantFilter();
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public IntegerFilter version() {
        if (version == null) {
            version = new IntegerFilter();
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public InstantFilter getEffDate() {
        return effDate;
    }

    public InstantFilter effDate() {
        if (effDate == null) {
            effDate = new InstantFilter();
        }
        return effDate;
    }

    public void setEffDate(InstantFilter effDate) {
        this.effDate = effDate;
    }

    public LongFilter getLeaveapproversAttributeId() {
        return leaveapproversAttributeId;
    }

    public LongFilter leaveapproversAttributeId() {
        if (leaveapproversAttributeId == null) {
            leaveapproversAttributeId = new LongFilter();
        }
        return leaveapproversAttributeId;
    }

    public void setLeaveapproversAttributeId(LongFilter leaveapproversAttributeId) {
        this.leaveapproversAttributeId = leaveapproversAttributeId;
    }

    public LongFilter getLeaveescalationapproversAttributeId() {
        return leaveescalationapproversAttributeId;
    }

    public LongFilter leaveescalationapproversAttributeId() {
        if (leaveescalationapproversAttributeId == null) {
            leaveescalationapproversAttributeId = new LongFilter();
        }
        return leaveescalationapproversAttributeId;
    }

    public void setLeaveescalationapproversAttributeId(LongFilter leaveescalationapproversAttributeId) {
        this.leaveescalationapproversAttributeId = leaveescalationapproversAttributeId;
    }

    public LongFilter getRaterattributemappingsAttributesforId() {
        return raterattributemappingsAttributesforId;
    }

    public LongFilter raterattributemappingsAttributesforId() {
        if (raterattributemappingsAttributesforId == null) {
            raterattributemappingsAttributesforId = new LongFilter();
        }
        return raterattributemappingsAttributesforId;
    }

    public void setRaterattributemappingsAttributesforId(LongFilter raterattributemappingsAttributesforId) {
        this.raterattributemappingsAttributesforId = raterattributemappingsAttributesforId;
    }

    public LongFilter getRaterattributemappingsAttributesbyId() {
        return raterattributemappingsAttributesbyId;
    }

    public LongFilter raterattributemappingsAttributesbyId() {
        if (raterattributemappingsAttributesbyId == null) {
            raterattributemappingsAttributesbyId = new LongFilter();
        }
        return raterattributemappingsAttributesbyId;
    }

    public void setRaterattributemappingsAttributesbyId(LongFilter raterattributemappingsAttributesbyId) {
        this.raterattributemappingsAttributesbyId = raterattributemappingsAttributesbyId;
    }

    public LongFilter getRatingattributemappingsAttributeId() {
        return ratingattributemappingsAttributeId;
    }

    public LongFilter ratingattributemappingsAttributeId() {
        if (ratingattributemappingsAttributeId == null) {
            ratingattributemappingsAttributeId = new LongFilter();
        }
        return ratingattributemappingsAttributeId;
    }

    public void setRatingattributemappingsAttributeId(LongFilter ratingattributemappingsAttributeId) {
        this.ratingattributemappingsAttributeId = ratingattributemappingsAttributeId;
    }

    public LongFilter getUserattributeapprovalrulesAttributeId() {
        return userattributeapprovalrulesAttributeId;
    }

    public LongFilter userattributeapprovalrulesAttributeId() {
        if (userattributeapprovalrulesAttributeId == null) {
            userattributeapprovalrulesAttributeId = new LongFilter();
        }
        return userattributeapprovalrulesAttributeId;
    }

    public void setUserattributeapprovalrulesAttributeId(LongFilter userattributeapprovalrulesAttributeId) {
        this.userattributeapprovalrulesAttributeId = userattributeapprovalrulesAttributeId;
    }

    public LongFilter getUserattributeescalationrulesAttributeId() {
        return userattributeescalationrulesAttributeId;
    }

    public LongFilter userattributeescalationrulesAttributeId() {
        if (userattributeescalationrulesAttributeId == null) {
            userattributeescalationrulesAttributeId = new LongFilter();
        }
        return userattributeescalationrulesAttributeId;
    }

    public void setUserattributeescalationrulesAttributeId(LongFilter userattributeescalationrulesAttributeId) {
        this.userattributeescalationrulesAttributeId = userattributeescalationrulesAttributeId;
    }

    public LongFilter getUserattributesAttributeId() {
        return userattributesAttributeId;
    }

    public LongFilter userattributesAttributeId() {
        if (userattributesAttributeId == null) {
            userattributesAttributeId = new LongFilter();
        }
        return userattributesAttributeId;
    }

    public void setUserattributesAttributeId(LongFilter userattributesAttributeId) {
        this.userattributesAttributeId = userattributesAttributeId;
    }

    public LongFilter getUserrelationapprovalrulesAttributeId() {
        return userrelationapprovalrulesAttributeId;
    }

    public LongFilter userrelationapprovalrulesAttributeId() {
        if (userrelationapprovalrulesAttributeId == null) {
            userrelationapprovalrulesAttributeId = new LongFilter();
        }
        return userrelationapprovalrulesAttributeId;
    }

    public void setUserrelationapprovalrulesAttributeId(LongFilter userrelationapprovalrulesAttributeId) {
        this.userrelationapprovalrulesAttributeId = userrelationapprovalrulesAttributeId;
    }

    public LongFilter getUserrelationsAttributeId() {
        return userrelationsAttributeId;
    }

    public LongFilter userrelationsAttributeId() {
        if (userrelationsAttributeId == null) {
            userrelationsAttributeId = new LongFilter();
        }
        return userrelationsAttributeId;
    }

    public void setUserrelationsAttributeId(LongFilter userrelationsAttributeId) {
        this.userrelationsAttributeId = userrelationsAttributeId;
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
        final AttributesCriteria that = (AttributesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(leaveapproversAttributeId, that.leaveapproversAttributeId) &&
            Objects.equals(leaveescalationapproversAttributeId, that.leaveescalationapproversAttributeId) &&
            Objects.equals(raterattributemappingsAttributesforId, that.raterattributemappingsAttributesforId) &&
            Objects.equals(raterattributemappingsAttributesbyId, that.raterattributemappingsAttributesbyId) &&
            Objects.equals(ratingattributemappingsAttributeId, that.ratingattributemappingsAttributeId) &&
            Objects.equals(userattributeapprovalrulesAttributeId, that.userattributeapprovalrulesAttributeId) &&
            Objects.equals(userattributeescalationrulesAttributeId, that.userattributeescalationrulesAttributeId) &&
            Objects.equals(userattributesAttributeId, that.userattributesAttributeId) &&
            Objects.equals(userrelationapprovalrulesAttributeId, that.userrelationapprovalrulesAttributeId) &&
            Objects.equals(userrelationsAttributeId, that.userrelationsAttributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            createdAt,
            updatedAt,
            endDate,
            version,
            effDate,
            leaveapproversAttributeId,
            leaveescalationapproversAttributeId,
            raterattributemappingsAttributesforId,
            raterattributemappingsAttributesbyId,
            ratingattributemappingsAttributeId,
            userattributeapprovalrulesAttributeId,
            userattributeescalationrulesAttributeId,
            userattributesAttributeId,
            userrelationapprovalrulesAttributeId,
            userrelationsAttributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (leaveapproversAttributeId != null ? "leaveapproversAttributeId=" + leaveapproversAttributeId + ", " : "") +
            (leaveescalationapproversAttributeId != null ? "leaveescalationapproversAttributeId=" + leaveescalationapproversAttributeId + ", " : "") +
            (raterattributemappingsAttributesforId != null ? "raterattributemappingsAttributesforId=" + raterattributemappingsAttributesforId + ", " : "") +
            (raterattributemappingsAttributesbyId != null ? "raterattributemappingsAttributesbyId=" + raterattributemappingsAttributesbyId + ", " : "") +
            (ratingattributemappingsAttributeId != null ? "ratingattributemappingsAttributeId=" + ratingattributemappingsAttributeId + ", " : "") +
            (userattributeapprovalrulesAttributeId != null ? "userattributeapprovalrulesAttributeId=" + userattributeapprovalrulesAttributeId + ", " : "") +
            (userattributeescalationrulesAttributeId != null ? "userattributeescalationrulesAttributeId=" + userattributeescalationrulesAttributeId + ", " : "") +
            (userattributesAttributeId != null ? "userattributesAttributeId=" + userattributesAttributeId + ", " : "") +
            (userrelationapprovalrulesAttributeId != null ? "userrelationapprovalrulesAttributeId=" + userrelationapprovalrulesAttributeId + ", " : "") +
            (userrelationsAttributeId != null ? "userrelationsAttributeId=" + userrelationsAttributeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
