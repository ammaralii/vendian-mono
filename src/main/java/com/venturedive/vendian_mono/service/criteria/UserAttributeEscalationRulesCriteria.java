package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.UserAttributeEscalationRules} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.UserAttributeEscalationRulesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-attribute-escalation-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAttributeEscalationRulesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter leaveEscalationCriteriaId;

    private IntegerFilter noOfDays;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter attributeId;

    private LongFilter approverStatusId;

    private LongFilter leaveescalationcriteriaId;

    private Boolean distinct;

    public UserAttributeEscalationRulesCriteria() {}

    public UserAttributeEscalationRulesCriteria(UserAttributeEscalationRulesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leaveEscalationCriteriaId = other.leaveEscalationCriteriaId == null ? null : other.leaveEscalationCriteriaId.copy();
        this.noOfDays = other.noOfDays == null ? null : other.noOfDays.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.attributeId = other.attributeId == null ? null : other.attributeId.copy();
        this.approverStatusId = other.approverStatusId == null ? null : other.approverStatusId.copy();
        this.leaveescalationcriteriaId = other.leaveescalationcriteriaId == null ? null : other.leaveescalationcriteriaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserAttributeEscalationRulesCriteria copy() {
        return new UserAttributeEscalationRulesCriteria(this);
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

    public IntegerFilter getLeaveEscalationCriteriaId() {
        return leaveEscalationCriteriaId;
    }

    public IntegerFilter leaveEscalationCriteriaId() {
        if (leaveEscalationCriteriaId == null) {
            leaveEscalationCriteriaId = new IntegerFilter();
        }
        return leaveEscalationCriteriaId;
    }

    public void setLeaveEscalationCriteriaId(IntegerFilter leaveEscalationCriteriaId) {
        this.leaveEscalationCriteriaId = leaveEscalationCriteriaId;
    }

    public IntegerFilter getNoOfDays() {
        return noOfDays;
    }

    public IntegerFilter noOfDays() {
        if (noOfDays == null) {
            noOfDays = new IntegerFilter();
        }
        return noOfDays;
    }

    public void setNoOfDays(IntegerFilter noOfDays) {
        this.noOfDays = noOfDays;
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

    public LongFilter getAttributeId() {
        return attributeId;
    }

    public LongFilter attributeId() {
        if (attributeId == null) {
            attributeId = new LongFilter();
        }
        return attributeId;
    }

    public void setAttributeId(LongFilter attributeId) {
        this.attributeId = attributeId;
    }

    public LongFilter getApproverStatusId() {
        return approverStatusId;
    }

    public LongFilter approverStatusId() {
        if (approverStatusId == null) {
            approverStatusId = new LongFilter();
        }
        return approverStatusId;
    }

    public void setApproverStatusId(LongFilter approverStatusId) {
        this.approverStatusId = approverStatusId;
    }

    public LongFilter getLeaveescalationcriteriaId() {
        return leaveescalationcriteriaId;
    }

    public LongFilter leaveescalationcriteriaId() {
        if (leaveescalationcriteriaId == null) {
            leaveescalationcriteriaId = new LongFilter();
        }
        return leaveescalationcriteriaId;
    }

    public void setLeaveescalationcriteriaId(LongFilter leaveescalationcriteriaId) {
        this.leaveescalationcriteriaId = leaveescalationcriteriaId;
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
        final UserAttributeEscalationRulesCriteria that = (UserAttributeEscalationRulesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leaveEscalationCriteriaId, that.leaveEscalationCriteriaId) &&
            Objects.equals(noOfDays, that.noOfDays) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(attributeId, that.attributeId) &&
            Objects.equals(approverStatusId, that.approverStatusId) &&
            Objects.equals(leaveescalationcriteriaId, that.leaveescalationcriteriaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            leaveEscalationCriteriaId,
            noOfDays,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            attributeId,
            approverStatusId,
            leaveescalationcriteriaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAttributeEscalationRulesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leaveEscalationCriteriaId != null ? "leaveEscalationCriteriaId=" + leaveEscalationCriteriaId + ", " : "") +
            (noOfDays != null ? "noOfDays=" + noOfDays + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (attributeId != null ? "attributeId=" + attributeId + ", " : "") +
            (approverStatusId != null ? "approverStatusId=" + approverStatusId + ", " : "") +
            (leaveescalationcriteriaId != null ? "leaveescalationcriteriaId=" + leaveescalationcriteriaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
