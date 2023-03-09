package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveEscalationCriterias.
 */
@Entity
@Table(name = "leave_escalation_criterias")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveEscalationCriterias implements Serializable {

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
    @Column(name = "total", nullable = false)
    private Integer total;

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

    @OneToMany(mappedBy = "leaveEscalationCriteria")
    @JsonIgnoreProperties(value = { "leaveEscalationCriteria", "attribute" }, allowSetters = true)
    private Set<LeaveEscalationApprovers> leaveescalationapproversLeaveescalationcriteria = new HashSet<>();

    @OneToMany(mappedBy = "leaveEscalationCriteria")
    @JsonIgnoreProperties(value = { "leaveEscalationCriteria", "leaveRequestStatus", "leaveType" }, allowSetters = true)
    private Set<LeaveTypeEscalationRules> leavetypeescalationrulesLeaveescalationcriteria = new HashSet<>();

    @OneToMany(mappedBy = "leaveescalationcriteria")
    @JsonIgnoreProperties(value = { "attribute", "approverStatus", "leaveescalationcriteria" }, allowSetters = true)
    private Set<UserAttributeEscalationRules> userattributeescalationrulesLeaveescalationcriteria = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveEscalationCriterias id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LeaveEscalationCriterias name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public LeaveEscalationCriterias priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getTotal() {
        return this.total;
    }

    public LeaveEscalationCriterias total(Integer total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveEscalationCriterias effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveEscalationCriterias createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveEscalationCriterias updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveEscalationCriterias endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveEscalationCriterias version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<LeaveEscalationApprovers> getLeaveescalationapproversLeaveescalationcriteria() {
        return this.leaveescalationapproversLeaveescalationcriteria;
    }

    public void setLeaveescalationapproversLeaveescalationcriteria(Set<LeaveEscalationApprovers> leaveEscalationApprovers) {
        if (this.leaveescalationapproversLeaveescalationcriteria != null) {
            this.leaveescalationapproversLeaveescalationcriteria.forEach(i -> i.setLeaveEscalationCriteria(null));
        }
        if (leaveEscalationApprovers != null) {
            leaveEscalationApprovers.forEach(i -> i.setLeaveEscalationCriteria(this));
        }
        this.leaveescalationapproversLeaveescalationcriteria = leaveEscalationApprovers;
    }

    public LeaveEscalationCriterias leaveescalationapproversLeaveescalationcriteria(
        Set<LeaveEscalationApprovers> leaveEscalationApprovers
    ) {
        this.setLeaveescalationapproversLeaveescalationcriteria(leaveEscalationApprovers);
        return this;
    }

    public LeaveEscalationCriterias addLeaveescalationapproversLeaveescalationcriteria(LeaveEscalationApprovers leaveEscalationApprovers) {
        this.leaveescalationapproversLeaveescalationcriteria.add(leaveEscalationApprovers);
        leaveEscalationApprovers.setLeaveEscalationCriteria(this);
        return this;
    }

    public LeaveEscalationCriterias removeLeaveescalationapproversLeaveescalationcriteria(
        LeaveEscalationApprovers leaveEscalationApprovers
    ) {
        this.leaveescalationapproversLeaveescalationcriteria.remove(leaveEscalationApprovers);
        leaveEscalationApprovers.setLeaveEscalationCriteria(null);
        return this;
    }

    public Set<LeaveTypeEscalationRules> getLeavetypeescalationrulesLeaveescalationcriteria() {
        return this.leavetypeescalationrulesLeaveescalationcriteria;
    }

    public void setLeavetypeescalationrulesLeaveescalationcriteria(Set<LeaveTypeEscalationRules> leaveTypeEscalationRules) {
        if (this.leavetypeescalationrulesLeaveescalationcriteria != null) {
            this.leavetypeescalationrulesLeaveescalationcriteria.forEach(i -> i.setLeaveEscalationCriteria(null));
        }
        if (leaveTypeEscalationRules != null) {
            leaveTypeEscalationRules.forEach(i -> i.setLeaveEscalationCriteria(this));
        }
        this.leavetypeescalationrulesLeaveescalationcriteria = leaveTypeEscalationRules;
    }

    public LeaveEscalationCriterias leavetypeescalationrulesLeaveescalationcriteria(
        Set<LeaveTypeEscalationRules> leaveTypeEscalationRules
    ) {
        this.setLeavetypeescalationrulesLeaveescalationcriteria(leaveTypeEscalationRules);
        return this;
    }

    public LeaveEscalationCriterias addLeavetypeescalationrulesLeaveescalationcriteria(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        this.leavetypeescalationrulesLeaveescalationcriteria.add(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveEscalationCriteria(this);
        return this;
    }

    public LeaveEscalationCriterias removeLeavetypeescalationrulesLeaveescalationcriteria(
        LeaveTypeEscalationRules leaveTypeEscalationRules
    ) {
        this.leavetypeescalationrulesLeaveescalationcriteria.remove(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveEscalationCriteria(null);
        return this;
    }

    public Set<UserAttributeEscalationRules> getUserattributeescalationrulesLeaveescalationcriteria() {
        return this.userattributeescalationrulesLeaveescalationcriteria;
    }

    public void setUserattributeescalationrulesLeaveescalationcriteria(Set<UserAttributeEscalationRules> userAttributeEscalationRules) {
        if (this.userattributeescalationrulesLeaveescalationcriteria != null) {
            this.userattributeescalationrulesLeaveescalationcriteria.forEach(i -> i.setLeaveescalationcriteria(null));
        }
        if (userAttributeEscalationRules != null) {
            userAttributeEscalationRules.forEach(i -> i.setLeaveescalationcriteria(this));
        }
        this.userattributeescalationrulesLeaveescalationcriteria = userAttributeEscalationRules;
    }

    public LeaveEscalationCriterias userattributeescalationrulesLeaveescalationcriteria(
        Set<UserAttributeEscalationRules> userAttributeEscalationRules
    ) {
        this.setUserattributeescalationrulesLeaveescalationcriteria(userAttributeEscalationRules);
        return this;
    }

    public LeaveEscalationCriterias addUserattributeescalationrulesLeaveescalationcriteria(
        UserAttributeEscalationRules userAttributeEscalationRules
    ) {
        this.userattributeescalationrulesLeaveescalationcriteria.add(userAttributeEscalationRules);
        userAttributeEscalationRules.setLeaveescalationcriteria(this);
        return this;
    }

    public LeaveEscalationCriterias removeUserattributeescalationrulesLeaveescalationcriteria(
        UserAttributeEscalationRules userAttributeEscalationRules
    ) {
        this.userattributeescalationrulesLeaveescalationcriteria.remove(userAttributeEscalationRules);
        userAttributeEscalationRules.setLeaveescalationcriteria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveEscalationCriterias)) {
            return false;
        }
        return id != null && id.equals(((LeaveEscalationCriterias) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveEscalationCriterias{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priority=" + getPriority() +
            ", total=" + getTotal() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
