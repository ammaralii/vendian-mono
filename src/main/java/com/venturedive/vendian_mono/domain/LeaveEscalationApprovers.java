package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveEscalationApprovers.
 */
@Entity
@Table(name = "leave_escalation_approvers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveEscalationApprovers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 17)
    @Column(name = "source", length = 17, nullable = false)
    private String source;

    @Column(name = "min_approvals")
    private Integer minApprovals;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

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
            "leaveescalationapproversLeaveescalationcriteria",
            "leavetypeescalationrulesLeaveescalationcriteria",
            "userattributeescalationrulesLeaveescalationcriteria",
        },
        allowSetters = true
    )
    private LeaveEscalationCriterias leaveEscalationCriteria;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveEscalationApprovers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public LeaveEscalationApprovers source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getMinApprovals() {
        return this.minApprovals;
    }

    public LeaveEscalationApprovers minApprovals(Integer minApprovals) {
        this.setMinApprovals(minApprovals);
        return this;
    }

    public void setMinApprovals(Integer minApprovals) {
        this.minApprovals = minApprovals;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LeaveEscalationApprovers priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveEscalationApprovers effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveEscalationApprovers createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveEscalationApprovers updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveEscalationApprovers endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveEscalationApprovers version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveEscalationCriterias getLeaveEscalationCriteria() {
        return this.leaveEscalationCriteria;
    }

    public void setLeaveEscalationCriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.leaveEscalationCriteria = leaveEscalationCriterias;
    }

    public LeaveEscalationApprovers leaveEscalationCriteria(LeaveEscalationCriterias leaveEscalationCriterias) {
        this.setLeaveEscalationCriteria(leaveEscalationCriterias);
        return this;
    }

    public Attributes getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attributes attributes) {
        this.attribute = attributes;
    }

    public LeaveEscalationApprovers attribute(Attributes attributes) {
        this.setAttribute(attributes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveEscalationApprovers)) {
            return false;
        }
        return id != null && id.equals(((LeaveEscalationApprovers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveEscalationApprovers{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", minApprovals=" + getMinApprovals() +
            ", priority=" + getPriority() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
