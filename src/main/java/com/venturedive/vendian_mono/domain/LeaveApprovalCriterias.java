package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveApprovalCriterias.
 */
@Entity
@Table(name = "leave_approval_criterias")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApprovalCriterias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

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

    @OneToMany(mappedBy = "leaveApprovalCriteria")
    @JsonIgnoreProperties(value = { "leaveApprovalCriteria", "attribute" }, allowSetters = true)
    private Set<LeaveApprovers> leaveapproversLeaveapprovalcriteria = new HashSet<>();

    @OneToMany(mappedBy = "leaveApprovalCriteria")
    @JsonIgnoreProperties(value = { "leaveApprovalCriteria", "leaveType" }, allowSetters = true)
    private Set<LeaveTypeApprovalRules> leavetypeapprovalrulesLeaveapprovalcriteria = new HashSet<>();

    @OneToMany(mappedBy = "leaveApprovalCriteria")
    @JsonIgnoreProperties(value = { "attribute", "leaveApprovalCriteria" }, allowSetters = true)
    private Set<UserAttributeApprovalRules> userattributeapprovalrulesLeaveapprovalcriteria = new HashSet<>();

    @OneToMany(mappedBy = "leaveApprovalCriteria")
    @JsonIgnoreProperties(value = { "attribute", "leaveApprovalCriteria" }, allowSetters = true)
    private Set<UserRelationApprovalRules> userrelationapprovalrulesLeaveapprovalcriteria = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveApprovalCriterias id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LeaveApprovalCriterias name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LeaveApprovalCriterias priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveApprovalCriterias effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveApprovalCriterias createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveApprovalCriterias updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveApprovalCriterias endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveApprovalCriterias version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<LeaveApprovers> getLeaveapproversLeaveapprovalcriteria() {
        return this.leaveapproversLeaveapprovalcriteria;
    }

    public void setLeaveapproversLeaveapprovalcriteria(Set<LeaveApprovers> leaveApprovers) {
        if (this.leaveapproversLeaveapprovalcriteria != null) {
            this.leaveapproversLeaveapprovalcriteria.forEach(i -> i.setLeaveApprovalCriteria(null));
        }
        if (leaveApprovers != null) {
            leaveApprovers.forEach(i -> i.setLeaveApprovalCriteria(this));
        }
        this.leaveapproversLeaveapprovalcriteria = leaveApprovers;
    }

    public LeaveApprovalCriterias leaveapproversLeaveapprovalcriteria(Set<LeaveApprovers> leaveApprovers) {
        this.setLeaveapproversLeaveapprovalcriteria(leaveApprovers);
        return this;
    }

    public LeaveApprovalCriterias addLeaveapproversLeaveapprovalcriteria(LeaveApprovers leaveApprovers) {
        this.leaveapproversLeaveapprovalcriteria.add(leaveApprovers);
        leaveApprovers.setLeaveApprovalCriteria(this);
        return this;
    }

    public LeaveApprovalCriterias removeLeaveapproversLeaveapprovalcriteria(LeaveApprovers leaveApprovers) {
        this.leaveapproversLeaveapprovalcriteria.remove(leaveApprovers);
        leaveApprovers.setLeaveApprovalCriteria(null);
        return this;
    }

    public Set<LeaveTypeApprovalRules> getLeavetypeapprovalrulesLeaveapprovalcriteria() {
        return this.leavetypeapprovalrulesLeaveapprovalcriteria;
    }

    public void setLeavetypeapprovalrulesLeaveapprovalcriteria(Set<LeaveTypeApprovalRules> leaveTypeApprovalRules) {
        if (this.leavetypeapprovalrulesLeaveapprovalcriteria != null) {
            this.leavetypeapprovalrulesLeaveapprovalcriteria.forEach(i -> i.setLeaveApprovalCriteria(null));
        }
        if (leaveTypeApprovalRules != null) {
            leaveTypeApprovalRules.forEach(i -> i.setLeaveApprovalCriteria(this));
        }
        this.leavetypeapprovalrulesLeaveapprovalcriteria = leaveTypeApprovalRules;
    }

    public LeaveApprovalCriterias leavetypeapprovalrulesLeaveapprovalcriteria(Set<LeaveTypeApprovalRules> leaveTypeApprovalRules) {
        this.setLeavetypeapprovalrulesLeaveapprovalcriteria(leaveTypeApprovalRules);
        return this;
    }

    public LeaveApprovalCriterias addLeavetypeapprovalrulesLeaveapprovalcriteria(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        this.leavetypeapprovalrulesLeaveapprovalcriteria.add(leaveTypeApprovalRules);
        leaveTypeApprovalRules.setLeaveApprovalCriteria(this);
        return this;
    }

    public LeaveApprovalCriterias removeLeavetypeapprovalrulesLeaveapprovalcriteria(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        this.leavetypeapprovalrulesLeaveapprovalcriteria.remove(leaveTypeApprovalRules);
        leaveTypeApprovalRules.setLeaveApprovalCriteria(null);
        return this;
    }

    public Set<UserAttributeApprovalRules> getUserattributeapprovalrulesLeaveapprovalcriteria() {
        return this.userattributeapprovalrulesLeaveapprovalcriteria;
    }

    public void setUserattributeapprovalrulesLeaveapprovalcriteria(Set<UserAttributeApprovalRules> userAttributeApprovalRules) {
        if (this.userattributeapprovalrulesLeaveapprovalcriteria != null) {
            this.userattributeapprovalrulesLeaveapprovalcriteria.forEach(i -> i.setLeaveApprovalCriteria(null));
        }
        if (userAttributeApprovalRules != null) {
            userAttributeApprovalRules.forEach(i -> i.setLeaveApprovalCriteria(this));
        }
        this.userattributeapprovalrulesLeaveapprovalcriteria = userAttributeApprovalRules;
    }

    public LeaveApprovalCriterias userattributeapprovalrulesLeaveapprovalcriteria(
        Set<UserAttributeApprovalRules> userAttributeApprovalRules
    ) {
        this.setUserattributeapprovalrulesLeaveapprovalcriteria(userAttributeApprovalRules);
        return this;
    }

    public LeaveApprovalCriterias addUserattributeapprovalrulesLeaveapprovalcriteria(
        UserAttributeApprovalRules userAttributeApprovalRules
    ) {
        this.userattributeapprovalrulesLeaveapprovalcriteria.add(userAttributeApprovalRules);
        userAttributeApprovalRules.setLeaveApprovalCriteria(this);
        return this;
    }

    public LeaveApprovalCriterias removeUserattributeapprovalrulesLeaveapprovalcriteria(
        UserAttributeApprovalRules userAttributeApprovalRules
    ) {
        this.userattributeapprovalrulesLeaveapprovalcriteria.remove(userAttributeApprovalRules);
        userAttributeApprovalRules.setLeaveApprovalCriteria(null);
        return this;
    }

    public Set<UserRelationApprovalRules> getUserrelationapprovalrulesLeaveapprovalcriteria() {
        return this.userrelationapprovalrulesLeaveapprovalcriteria;
    }

    public void setUserrelationapprovalrulesLeaveapprovalcriteria(Set<UserRelationApprovalRules> userRelationApprovalRules) {
        if (this.userrelationapprovalrulesLeaveapprovalcriteria != null) {
            this.userrelationapprovalrulesLeaveapprovalcriteria.forEach(i -> i.setLeaveApprovalCriteria(null));
        }
        if (userRelationApprovalRules != null) {
            userRelationApprovalRules.forEach(i -> i.setLeaveApprovalCriteria(this));
        }
        this.userrelationapprovalrulesLeaveapprovalcriteria = userRelationApprovalRules;
    }

    public LeaveApprovalCriterias userrelationapprovalrulesLeaveapprovalcriteria(Set<UserRelationApprovalRules> userRelationApprovalRules) {
        this.setUserrelationapprovalrulesLeaveapprovalcriteria(userRelationApprovalRules);
        return this;
    }

    public LeaveApprovalCriterias addUserrelationapprovalrulesLeaveapprovalcriteria(UserRelationApprovalRules userRelationApprovalRules) {
        this.userrelationapprovalrulesLeaveapprovalcriteria.add(userRelationApprovalRules);
        userRelationApprovalRules.setLeaveApprovalCriteria(this);
        return this;
    }

    public LeaveApprovalCriterias removeUserrelationapprovalrulesLeaveapprovalcriteria(
        UserRelationApprovalRules userRelationApprovalRules
    ) {
        this.userrelationapprovalrulesLeaveapprovalcriteria.remove(userRelationApprovalRules);
        userRelationApprovalRules.setLeaveApprovalCriteria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApprovalCriterias)) {
            return false;
        }
        return id != null && id.equals(((LeaveApprovalCriterias) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApprovalCriterias{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priority=" + getPriority() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
