package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserRelationApprovalRules.
 */
@Entity
@Table(name = "user_relation_approval_rules")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserRelationApprovalRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
            "leaveapproversLeaveapprovalcriteria",
            "leavetypeapprovalrulesLeaveapprovalcriteria",
            "userattributeapprovalrulesLeaveapprovalcriteria",
            "userrelationapprovalrulesLeaveapprovalcriteria",
        },
        allowSetters = true
    )
    private LeaveApprovalCriterias leaveApprovalCriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserRelationApprovalRules id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public UserRelationApprovalRules effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserRelationApprovalRules createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UserRelationApprovalRules updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public UserRelationApprovalRules endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public UserRelationApprovalRules version(Integer version) {
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

    public UserRelationApprovalRules attribute(Attributes attributes) {
        this.setAttribute(attributes);
        return this;
    }

    public LeaveApprovalCriterias getLeaveApprovalCriteria() {
        return this.leaveApprovalCriteria;
    }

    public void setLeaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.leaveApprovalCriteria = leaveApprovalCriterias;
    }

    public UserRelationApprovalRules leaveApprovalCriteria(LeaveApprovalCriterias leaveApprovalCriterias) {
        this.setLeaveApprovalCriteria(leaveApprovalCriterias);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRelationApprovalRules)) {
            return false;
        }
        return id != null && id.equals(((UserRelationApprovalRules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRelationApprovalRules{" +
            "id=" + getId() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
