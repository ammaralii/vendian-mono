package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserAttributeEscalationRules.
 */
@Entity
@Table(name = "user_attribute_escalation_rules")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAttributeEscalationRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "leave_escalation_criteria_id", nullable = false)
    private Integer leaveEscalationCriteriaId;

    @NotNull
    @Column(name = "no_of_days", nullable = false)
    private Integer noOfDays;

    @NotNull
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leaveapproversAttributes",
            "leaveescalationapproversAttributes",
            "raterattributemappingsAttributesfors",
            "raterattributemappingsAttributesbies",
            "ratingattributemappingsAttributes",
            "userattributeapprovalrulesAttributes",
            "userattributeescalationrulesAttributes",
            "userattributesAttributes",
            "userrelationapprovalrulesAttributes",
            "userrelationsAttributes",
        },
        allowSetters = true
    )
    private Attributes attribute;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leaverequestapproverflowsApproverstatuses",
            "leaverequestapproverflowsCurrentleaverequeststatuses",
            "leaverequestapproverflowsNextleaverequeststatuses",
            "leaverequestapproversStatuses",
            "leaverequestsLeavestatuses",
            "leavestatusworkflowsCurrentstatuses",
            "leavestatusworkflowsNextstatuses",
            "leavestatusworkflowsSkiptostatuses",
            "leavetypeescalationrulesLeaverequeststatuses",
            "userattributeescalationrulesApproverstatuses",
        },
        allowSetters = true
    )
    private LeaveStatuses approverStatus;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leaveescalationapproversLeaveescalationcriteria",
            "leavetypeescalationrulesLeaveescalationcriteria",
            "userattributeescalationrulesLeaveescalationcriteria",
        },
        allowSetters = true
    )
    private LeaveEscalationCriterias leaveescalationcriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAttributeEscalationRules id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLeaveEscalationCriteriaId() {
        return this.leaveEscalationCriteriaId;
    }

    public UserAttributeEscalationRules leaveEscalationCriteriaId(Integer leaveEscalationCriteriaId) {
        this.setLeaveEscalationCriteriaId(leaveEscalationCriteriaId);
        return this;
    }

    public void setLeaveEscalationCriteriaId(Integer leaveEscalationCriteriaId) {
        this.leaveEscalationCriteriaId = leaveEscalationCriteriaId;
    }

    public Integer getNoOfDays() {
        return this.noOfDays;
    }

    public UserAttributeEscalationRules noOfDays(Integer noOfDays) {
        this.setNoOfDays(noOfDays);
        return this;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public UserAttributeEscalationRules effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserAttributeEscalationRules createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UserAttributeEscalationRules updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public UserAttributeEscalationRules endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public UserAttributeEscalationRules version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Attributes getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attributes attributes) {
        this.attribute = attributes;
    }

    public UserAttributeEscalationRules attribute(Attributes attributes) {
        this.setAttribute(attributes);
        return this;
    }

    public LeaveStatuses getApproverStatus() {
        return this.approverStatus;
    }

    public void setApproverStatus(LeaveStatuses leaveStatuses) {
        this.approverStatus = leaveStatuses;
    }

    public UserAttributeEscalationRules approverStatus(LeaveStatuses leaveStatuses) {
        this.setApproverStatus(leaveStatuses);
        return this;
    }

    public LeaveEscalationCriterias getLeaveescalationcriteria() {
        return this.leaveescalationcriteria;
    }

    public void setLeaveescalationcriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.leaveescalationcriteria = leaveEscalationCriterias;
    }

    public UserAttributeEscalationRules leaveescalationcriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.setLeaveescalationcriteria(leaveEscalationCriterias);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAttributeEscalationRules)) {
            return false;
        }
        return id != null && id.equals(((UserAttributeEscalationRules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAttributeEscalationRules{" +
            "id=" + getId() +
            ", leaveEscalationCriteriaId=" + getLeaveEscalationCriteriaId() +
            ", noOfDays=" + getNoOfDays() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
