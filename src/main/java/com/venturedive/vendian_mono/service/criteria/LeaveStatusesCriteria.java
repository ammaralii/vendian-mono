package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.LeaveStatuses} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.LeaveStatusesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveStatusesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter leaveGroup;

    private StringFilter systemKey;

    private BooleanFilter isDefault;

    private InstantFilter effDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter endDate;

    private IntegerFilter version;

    private LongFilter leaverequestapproverflowsApproverstatusId;

    private LongFilter leaverequestapproverflowsCurrentleaverequeststatusId;

    private LongFilter leaverequestapproverflowsNextleaverequeststatusId;

    private LongFilter leaverequestapproversStatusId;

    private LongFilter leaverequestsLeavestatusId;

    private LongFilter leavestatusworkflowsCurrentstatusId;

    private LongFilter leavestatusworkflowsNextstatusId;

    private LongFilter leavestatusworkflowsSkiptostatusId;

    private LongFilter leavetypeescalationrulesLeaverequeststatusId;

    private LongFilter userattributeescalationrulesApproverstatusId;

    private Boolean distinct;

    public LeaveStatusesCriteria() {}

    public LeaveStatusesCriteria(LeaveStatusesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.leaveGroup = other.leaveGroup == null ? null : other.leaveGroup.copy();
        this.systemKey = other.systemKey == null ? null : other.systemKey.copy();
        this.isDefault = other.isDefault == null ? null : other.isDefault.copy();
        this.effDate = other.effDate == null ? null : other.effDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.leaverequestapproverflowsApproverstatusId =
            other.leaverequestapproverflowsApproverstatusId == null ? null : other.leaverequestapproverflowsApproverstatusId.copy();
        this.leaverequestapproverflowsCurrentleaverequeststatusId =
            other.leaverequestapproverflowsCurrentleaverequeststatusId == null
                ? null
                : other.leaverequestapproverflowsCurrentleaverequeststatusId.copy();
        this.leaverequestapproverflowsNextleaverequeststatusId =
            other.leaverequestapproverflowsNextleaverequeststatusId == null
                ? null
                : other.leaverequestapproverflowsNextleaverequeststatusId.copy();
        this.leaverequestapproversStatusId =
            other.leaverequestapproversStatusId == null ? null : other.leaverequestapproversStatusId.copy();
        this.leaverequestsLeavestatusId = other.leaverequestsLeavestatusId == null ? null : other.leaverequestsLeavestatusId.copy();
        this.leavestatusworkflowsCurrentstatusId =
            other.leavestatusworkflowsCurrentstatusId == null ? null : other.leavestatusworkflowsCurrentstatusId.copy();
        this.leavestatusworkflowsNextstatusId =
            other.leavestatusworkflowsNextstatusId == null ? null : other.leavestatusworkflowsNextstatusId.copy();
        this.leavestatusworkflowsSkiptostatusId =
            other.leavestatusworkflowsSkiptostatusId == null ? null : other.leavestatusworkflowsSkiptostatusId.copy();
        this.leavetypeescalationrulesLeaverequeststatusId =
            other.leavetypeescalationrulesLeaverequeststatusId == null ? null : other.leavetypeescalationrulesLeaverequeststatusId.copy();
        this.userattributeescalationrulesApproverstatusId =
            other.userattributeescalationrulesApproverstatusId == null ? null : other.userattributeescalationrulesApproverstatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveStatusesCriteria copy() {
        return new LeaveStatusesCriteria(this);
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

    public StringFilter getLeaveGroup() {
        return leaveGroup;
    }

    public StringFilter leaveGroup() {
        if (leaveGroup == null) {
            leaveGroup = new StringFilter();
        }
        return leaveGroup;
    }

    public void setLeaveGroup(StringFilter leaveGroup) {
        this.leaveGroup = leaveGroup;
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

    public BooleanFilter getIsDefault() {
        return isDefault;
    }

    public BooleanFilter isDefault() {
        if (isDefault == null) {
            isDefault = new BooleanFilter();
        }
        return isDefault;
    }

    public void setIsDefault(BooleanFilter isDefault) {
        this.isDefault = isDefault;
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

    public LongFilter getLeaverequestapproverflowsApproverstatusId() {
        return leaverequestapproverflowsApproverstatusId;
    }

    public LongFilter leaverequestapproverflowsApproverstatusId() {
        if (leaverequestapproverflowsApproverstatusId == null) {
            leaverequestapproverflowsApproverstatusId = new LongFilter();
        }
        return leaverequestapproverflowsApproverstatusId;
    }

    public void setLeaverequestapproverflowsApproverstatusId(LongFilter leaverequestapproverflowsApproverstatusId) {
        this.leaverequestapproverflowsApproverstatusId = leaverequestapproverflowsApproverstatusId;
    }

    public LongFilter getLeaverequestapproverflowsCurrentleaverequeststatusId() {
        return leaverequestapproverflowsCurrentleaverequeststatusId;
    }

    public LongFilter leaverequestapproverflowsCurrentleaverequeststatusId() {
        if (leaverequestapproverflowsCurrentleaverequeststatusId == null) {
            leaverequestapproverflowsCurrentleaverequeststatusId = new LongFilter();
        }
        return leaverequestapproverflowsCurrentleaverequeststatusId;
    }

    public void setLeaverequestapproverflowsCurrentleaverequeststatusId(LongFilter leaverequestapproverflowsCurrentleaverequeststatusId) {
        this.leaverequestapproverflowsCurrentleaverequeststatusId = leaverequestapproverflowsCurrentleaverequeststatusId;
    }

    public LongFilter getLeaverequestapproverflowsNextleaverequeststatusId() {
        return leaverequestapproverflowsNextleaverequeststatusId;
    }

    public LongFilter leaverequestapproverflowsNextleaverequeststatusId() {
        if (leaverequestapproverflowsNextleaverequeststatusId == null) {
            leaverequestapproverflowsNextleaverequeststatusId = new LongFilter();
        }
        return leaverequestapproverflowsNextleaverequeststatusId;
    }

    public void setLeaverequestapproverflowsNextleaverequeststatusId(LongFilter leaverequestapproverflowsNextleaverequeststatusId) {
        this.leaverequestapproverflowsNextleaverequeststatusId = leaverequestapproverflowsNextleaverequeststatusId;
    }

    public LongFilter getLeaverequestapproversStatusId() {
        return leaverequestapproversStatusId;
    }

    public LongFilter leaverequestapproversStatusId() {
        if (leaverequestapproversStatusId == null) {
            leaverequestapproversStatusId = new LongFilter();
        }
        return leaverequestapproversStatusId;
    }

    public void setLeaverequestapproversStatusId(LongFilter leaverequestapproversStatusId) {
        this.leaverequestapproversStatusId = leaverequestapproversStatusId;
    }

    public LongFilter getLeaverequestsLeavestatusId() {
        return leaverequestsLeavestatusId;
    }

    public LongFilter leaverequestsLeavestatusId() {
        if (leaverequestsLeavestatusId == null) {
            leaverequestsLeavestatusId = new LongFilter();
        }
        return leaverequestsLeavestatusId;
    }

    public void setLeaverequestsLeavestatusId(LongFilter leaverequestsLeavestatusId) {
        this.leaverequestsLeavestatusId = leaverequestsLeavestatusId;
    }

    public LongFilter getLeavestatusworkflowsCurrentstatusId() {
        return leavestatusworkflowsCurrentstatusId;
    }

    public LongFilter leavestatusworkflowsCurrentstatusId() {
        if (leavestatusworkflowsCurrentstatusId == null) {
            leavestatusworkflowsCurrentstatusId = new LongFilter();
        }
        return leavestatusworkflowsCurrentstatusId;
    }

    public void setLeavestatusworkflowsCurrentstatusId(LongFilter leavestatusworkflowsCurrentstatusId) {
        this.leavestatusworkflowsCurrentstatusId = leavestatusworkflowsCurrentstatusId;
    }

    public LongFilter getLeavestatusworkflowsNextstatusId() {
        return leavestatusworkflowsNextstatusId;
    }

    public LongFilter leavestatusworkflowsNextstatusId() {
        if (leavestatusworkflowsNextstatusId == null) {
            leavestatusworkflowsNextstatusId = new LongFilter();
        }
        return leavestatusworkflowsNextstatusId;
    }

    public void setLeavestatusworkflowsNextstatusId(LongFilter leavestatusworkflowsNextstatusId) {
        this.leavestatusworkflowsNextstatusId = leavestatusworkflowsNextstatusId;
    }

    public LongFilter getLeavestatusworkflowsSkiptostatusId() {
        return leavestatusworkflowsSkiptostatusId;
    }

    public LongFilter leavestatusworkflowsSkiptostatusId() {
        if (leavestatusworkflowsSkiptostatusId == null) {
            leavestatusworkflowsSkiptostatusId = new LongFilter();
        }
        return leavestatusworkflowsSkiptostatusId;
    }

    public void setLeavestatusworkflowsSkiptostatusId(LongFilter leavestatusworkflowsSkiptostatusId) {
        this.leavestatusworkflowsSkiptostatusId = leavestatusworkflowsSkiptostatusId;
    }

    public LongFilter getLeavetypeescalationrulesLeaverequeststatusId() {
        return leavetypeescalationrulesLeaverequeststatusId;
    }

    public LongFilter leavetypeescalationrulesLeaverequeststatusId() {
        if (leavetypeescalationrulesLeaverequeststatusId == null) {
            leavetypeescalationrulesLeaverequeststatusId = new LongFilter();
        }
        return leavetypeescalationrulesLeaverequeststatusId;
    }

    public void setLeavetypeescalationrulesLeaverequeststatusId(LongFilter leavetypeescalationrulesLeaverequeststatusId) {
        this.leavetypeescalationrulesLeaverequeststatusId = leavetypeescalationrulesLeaverequeststatusId;
    }

    public LongFilter getUserattributeescalationrulesApproverstatusId() {
        return userattributeescalationrulesApproverstatusId;
    }

    public LongFilter userattributeescalationrulesApproverstatusId() {
        if (userattributeescalationrulesApproverstatusId == null) {
            userattributeescalationrulesApproverstatusId = new LongFilter();
        }
        return userattributeescalationrulesApproverstatusId;
    }

    public void setUserattributeescalationrulesApproverstatusId(LongFilter userattributeescalationrulesApproverstatusId) {
        this.userattributeescalationrulesApproverstatusId = userattributeescalationrulesApproverstatusId;
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
        final LeaveStatusesCriteria that = (LeaveStatusesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(leaveGroup, that.leaveGroup) &&
            Objects.equals(systemKey, that.systemKey) &&
            Objects.equals(isDefault, that.isDefault) &&
            Objects.equals(effDate, that.effDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(version, that.version) &&
            Objects.equals(leaverequestapproverflowsApproverstatusId, that.leaverequestapproverflowsApproverstatusId) &&
            Objects.equals(
                leaverequestapproverflowsCurrentleaverequeststatusId,
                that.leaverequestapproverflowsCurrentleaverequeststatusId
            ) &&
            Objects.equals(leaverequestapproverflowsNextleaverequeststatusId, that.leaverequestapproverflowsNextleaverequeststatusId) &&
            Objects.equals(leaverequestapproversStatusId, that.leaverequestapproversStatusId) &&
            Objects.equals(leaverequestsLeavestatusId, that.leaverequestsLeavestatusId) &&
            Objects.equals(leavestatusworkflowsCurrentstatusId, that.leavestatusworkflowsCurrentstatusId) &&
            Objects.equals(leavestatusworkflowsNextstatusId, that.leavestatusworkflowsNextstatusId) &&
            Objects.equals(leavestatusworkflowsSkiptostatusId, that.leavestatusworkflowsSkiptostatusId) &&
            Objects.equals(leavetypeescalationrulesLeaverequeststatusId, that.leavetypeescalationrulesLeaverequeststatusId) &&
            Objects.equals(userattributeescalationrulesApproverstatusId, that.userattributeescalationrulesApproverstatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            leaveGroup,
            systemKey,
            isDefault,
            effDate,
            createdAt,
            updatedAt,
            endDate,
            version,
            leaverequestapproverflowsApproverstatusId,
            leaverequestapproverflowsCurrentleaverequeststatusId,
            leaverequestapproverflowsNextleaverequeststatusId,
            leaverequestapproversStatusId,
            leaverequestsLeavestatusId,
            leavestatusworkflowsCurrentstatusId,
            leavestatusworkflowsNextstatusId,
            leavestatusworkflowsSkiptostatusId,
            leavetypeescalationrulesLeaverequeststatusId,
            userattributeescalationrulesApproverstatusId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveStatusesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (leaveGroup != null ? "leaveGroup=" + leaveGroup + ", " : "") +
            (systemKey != null ? "systemKey=" + systemKey + ", " : "") +
            (isDefault != null ? "isDefault=" + isDefault + ", " : "") +
            (effDate != null ? "effDate=" + effDate + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (leaverequestapproverflowsApproverstatusId != null ? "leaverequestapproverflowsApproverstatusId=" + leaverequestapproverflowsApproverstatusId + ", " : "") +
            (leaverequestapproverflowsCurrentleaverequeststatusId != null ? "leaverequestapproverflowsCurrentleaverequeststatusId=" + leaverequestapproverflowsCurrentleaverequeststatusId + ", " : "") +
            (leaverequestapproverflowsNextleaverequeststatusId != null ? "leaverequestapproverflowsNextleaverequeststatusId=" + leaverequestapproverflowsNextleaverequeststatusId + ", " : "") +
            (leaverequestapproversStatusId != null ? "leaverequestapproversStatusId=" + leaverequestapproversStatusId + ", " : "") +
            (leaverequestsLeavestatusId != null ? "leaverequestsLeavestatusId=" + leaverequestsLeavestatusId + ", " : "") +
            (leavestatusworkflowsCurrentstatusId != null ? "leavestatusworkflowsCurrentstatusId=" + leavestatusworkflowsCurrentstatusId + ", " : "") +
            (leavestatusworkflowsNextstatusId != null ? "leavestatusworkflowsNextstatusId=" + leavestatusworkflowsNextstatusId + ", " : "") +
            (leavestatusworkflowsSkiptostatusId != null ? "leavestatusworkflowsSkiptostatusId=" + leavestatusworkflowsSkiptostatusId + ", " : "") +
            (leavetypeescalationrulesLeaverequeststatusId != null ? "leavetypeescalationrulesLeaverequeststatusId=" + leavetypeescalationrulesLeaverequeststatusId + ", " : "") +
            (userattributeescalationrulesApproverstatusId != null ? "userattributeescalationrulesApproverstatusId=" + userattributeescalationrulesApproverstatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
