package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypes.
 */
@Entity
@Table(name = "leave_types")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypes implements Serializable {

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
    @Size(max = 9)
    @Column(name = "category", length = 9, nullable = false)
    private String category;

    @NotNull
    @Size(max = 5)
    @Column(name = "cycle_type", length = 5, nullable = false)
    private String cycleType;

    @NotNull
    @Column(name = "cycle_count", nullable = false)
    private Integer cycleCount;

    @Column(name = "max_quota")
    private Integer maxQuota;

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

    @OneToMany(mappedBy = "leaveType")
    @JsonIgnoreProperties(
        value = { "leave", "leaveType", "leavedetailadjustmentlogsLeavedetails", "leaverequestsLeavedetails" },
        allowSetters = true
    )
    private Set<LeaveDetails> leavedetailsLeavetypes = new HashSet<>();

    @OneToMany(mappedBy = "leaveType")
    @JsonIgnoreProperties(value = { "leaveApprovalCriteria", "leaveType" }, allowSetters = true)
    private Set<LeaveTypeApprovalRules> leavetypeapprovalrulesLeavetypes = new HashSet<>();

    @OneToMany(mappedBy = "leaveType")
    @JsonIgnoreProperties(value = { "leaveType" }, allowSetters = true)
    private Set<LeaveTypeConfigurations> leavetypeconfigurationsLeavetypes = new HashSet<>();

    @OneToMany(mappedBy = "leaveType")
    @JsonIgnoreProperties(value = { "leaveEscalationCriteria", "leaveRequestStatus", "leaveType" }, allowSetters = true)
    private Set<LeaveTypeEscalationRules> leavetypeescalationrulesLeavetypes = new HashSet<>();

    @OneToMany(mappedBy = "leaveType")
    @JsonIgnoreProperties(value = { "leaveType" }, allowSetters = true)
    private Set<LeaveTypeRestrictions> leavetyperestrictionsLeavetypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveTypes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LeaveTypes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public LeaveTypes category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCycleType() {
        return this.cycleType;
    }

    public LeaveTypes cycleType(String cycleType) {
        this.setCycleType(cycleType);
        return this;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public Integer getCycleCount() {
        return this.cycleCount;
    }

    public LeaveTypes cycleCount(Integer cycleCount) {
        this.setCycleCount(cycleCount);
        return this;
    }

    public void setCycleCount(Integer cycleCount) {
        this.cycleCount = cycleCount;
    }

    public Integer getMaxQuota() {
        return this.maxQuota;
    }

    public LeaveTypes maxQuota(Integer maxQuota) {
        this.setMaxQuota(maxQuota);
        return this;
    }

    public void setMaxQuota(Integer maxQuota) {
        this.maxQuota = maxQuota;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveTypes effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveTypes createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveTypes updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveTypes endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveTypes version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<LeaveDetails> getLeavedetailsLeavetypes() {
        return this.leavedetailsLeavetypes;
    }

    public void setLeavedetailsLeavetypes(Set<LeaveDetails> leaveDetails) {
        if (this.leavedetailsLeavetypes != null) {
            this.leavedetailsLeavetypes.forEach(i -> i.setLeaveType(null));
        }
        if (leaveDetails != null) {
            leaveDetails.forEach(i -> i.setLeaveType(this));
        }
        this.leavedetailsLeavetypes = leaveDetails;
    }

    public LeaveTypes leavedetailsLeavetypes(Set<LeaveDetails> leaveDetails) {
        this.setLeavedetailsLeavetypes(leaveDetails);
        return this;
    }

    public LeaveTypes addLeavedetailsLeavetype(LeaveDetails leaveDetails) {
        this.leavedetailsLeavetypes.add(leaveDetails);
        leaveDetails.setLeaveType(this);
        return this;
    }

    public LeaveTypes removeLeavedetailsLeavetype(LeaveDetails leaveDetails) {
        this.leavedetailsLeavetypes.remove(leaveDetails);
        leaveDetails.setLeaveType(null);
        return this;
    }

    public Set<LeaveTypeApprovalRules> getLeavetypeapprovalrulesLeavetypes() {
        return this.leavetypeapprovalrulesLeavetypes;
    }

    public void setLeavetypeapprovalrulesLeavetypes(Set<LeaveTypeApprovalRules> leaveTypeApprovalRules) {
        if (this.leavetypeapprovalrulesLeavetypes != null) {
            this.leavetypeapprovalrulesLeavetypes.forEach(i -> i.setLeaveType(null));
        }
        if (leaveTypeApprovalRules != null) {
            leaveTypeApprovalRules.forEach(i -> i.setLeaveType(this));
        }
        this.leavetypeapprovalrulesLeavetypes = leaveTypeApprovalRules;
    }

    public LeaveTypes leavetypeapprovalrulesLeavetypes(Set<LeaveTypeApprovalRules> leaveTypeApprovalRules) {
        this.setLeavetypeapprovalrulesLeavetypes(leaveTypeApprovalRules);
        return this;
    }

    public LeaveTypes addLeavetypeapprovalrulesLeavetype(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        this.leavetypeapprovalrulesLeavetypes.add(leaveTypeApprovalRules);
        leaveTypeApprovalRules.setLeaveType(this);
        return this;
    }

    public LeaveTypes removeLeavetypeapprovalrulesLeavetype(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        this.leavetypeapprovalrulesLeavetypes.remove(leaveTypeApprovalRules);
        leaveTypeApprovalRules.setLeaveType(null);
        return this;
    }

    public Set<LeaveTypeConfigurations> getLeavetypeconfigurationsLeavetypes() {
        return this.leavetypeconfigurationsLeavetypes;
    }

    public void setLeavetypeconfigurationsLeavetypes(Set<LeaveTypeConfigurations> leaveTypeConfigurations) {
        if (this.leavetypeconfigurationsLeavetypes != null) {
            this.leavetypeconfigurationsLeavetypes.forEach(i -> i.setLeaveType(null));
        }
        if (leaveTypeConfigurations != null) {
            leaveTypeConfigurations.forEach(i -> i.setLeaveType(this));
        }
        this.leavetypeconfigurationsLeavetypes = leaveTypeConfigurations;
    }

    public LeaveTypes leavetypeconfigurationsLeavetypes(Set<LeaveTypeConfigurations> leaveTypeConfigurations) {
        this.setLeavetypeconfigurationsLeavetypes(leaveTypeConfigurations);
        return this;
    }

    public LeaveTypes addLeavetypeconfigurationsLeavetype(LeaveTypeConfigurations leaveTypeConfigurations) {
        this.leavetypeconfigurationsLeavetypes.add(leaveTypeConfigurations);
        leaveTypeConfigurations.setLeaveType(this);
        return this;
    }

    public LeaveTypes removeLeavetypeconfigurationsLeavetype(LeaveTypeConfigurations leaveTypeConfigurations) {
        this.leavetypeconfigurationsLeavetypes.remove(leaveTypeConfigurations);
        leaveTypeConfigurations.setLeaveType(null);
        return this;
    }

    public Set<LeaveTypeEscalationRules> getLeavetypeescalationrulesLeavetypes() {
        return this.leavetypeescalationrulesLeavetypes;
    }

    public void setLeavetypeescalationrulesLeavetypes(Set<LeaveTypeEscalationRules> leaveTypeEscalationRules) {
        if (this.leavetypeescalationrulesLeavetypes != null) {
            this.leavetypeescalationrulesLeavetypes.forEach(i -> i.setLeaveType(null));
        }
        if (leaveTypeEscalationRules != null) {
            leaveTypeEscalationRules.forEach(i -> i.setLeaveType(this));
        }
        this.leavetypeescalationrulesLeavetypes = leaveTypeEscalationRules;
    }

    public LeaveTypes leavetypeescalationrulesLeavetypes(Set<LeaveTypeEscalationRules> leaveTypeEscalationRules) {
        this.setLeavetypeescalationrulesLeavetypes(leaveTypeEscalationRules);
        return this;
    }

    public LeaveTypes addLeavetypeescalationrulesLeavetype(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        this.leavetypeescalationrulesLeavetypes.add(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveType(this);
        return this;
    }

    public LeaveTypes removeLeavetypeescalationrulesLeavetype(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        this.leavetypeescalationrulesLeavetypes.remove(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveType(null);
        return this;
    }

    public Set<LeaveTypeRestrictions> getLeavetyperestrictionsLeavetypes() {
        return this.leavetyperestrictionsLeavetypes;
    }

    public void setLeavetyperestrictionsLeavetypes(Set<LeaveTypeRestrictions> leaveTypeRestrictions) {
        if (this.leavetyperestrictionsLeavetypes != null) {
            this.leavetyperestrictionsLeavetypes.forEach(i -> i.setLeaveType(null));
        }
        if (leaveTypeRestrictions != null) {
            leaveTypeRestrictions.forEach(i -> i.setLeaveType(this));
        }
        this.leavetyperestrictionsLeavetypes = leaveTypeRestrictions;
    }

    public LeaveTypes leavetyperestrictionsLeavetypes(Set<LeaveTypeRestrictions> leaveTypeRestrictions) {
        this.setLeavetyperestrictionsLeavetypes(leaveTypeRestrictions);
        return this;
    }

    public LeaveTypes addLeavetyperestrictionsLeavetype(LeaveTypeRestrictions leaveTypeRestrictions) {
        this.leavetyperestrictionsLeavetypes.add(leaveTypeRestrictions);
        leaveTypeRestrictions.setLeaveType(this);
        return this;
    }

    public LeaveTypes removeLeavetyperestrictionsLeavetype(LeaveTypeRestrictions leaveTypeRestrictions) {
        this.leavetyperestrictionsLeavetypes.remove(leaveTypeRestrictions);
        leaveTypeRestrictions.setLeaveType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypes)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", cycleType='" + getCycleType() + "'" +
            ", cycleCount=" + getCycleCount() +
            ", maxQuota=" + getMaxQuota() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
