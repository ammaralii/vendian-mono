package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveApprovers.
 */
@Entity
@Table(name = "leave_approvers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApprovers implements Serializable {

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
            "leaveapproversLeaveapprovalcriteria",
            "leavetypeapprovalrulesLeaveapprovalcriteria",
            "userattributeapprovalrulesLeaveapprovalcriteria",
            "userrelationapprovalrulesLeaveapprovalcriteria",
        },
        allowSetters = true
    )
    private LeaveApprovalCriterias leaveApprovalCriteria;

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

    public LeaveApprovers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public LeaveApprovers source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getMinApprovals() {
        return this.minApprovals;
    }

    public LeaveApprovers minApprovals(Integer minApprovals) {
        this.setMinApprovals(minApprovals);
        return this;
    }

    public void setMinApprovals(Integer minApprovals) {
        this.minApprovals = minApprovals;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LeaveApprovers priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveApprovers effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveApprovers createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveApprovers updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveApprovers endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveApprovers version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveApprovalCriterias getLeaveApprovalCriteria() {
        return this.leaveApprovalCriteria;
    }

    public void setLeaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.leaveApprovalCriteria = leaveApprovalCriterias;
    }

    public LeaveApprovers leaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return this;
    }

    public Attributes getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attributes attributes) {
        this.attribute = attributes;
    }

    public LeaveApprovers attribute(Attributes attributes) {
        this.setAttribute(attributes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApprovers)) {
            return false;
        }
        return id != null && id.equals(((LeaveApprovers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApprovers{" +
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
