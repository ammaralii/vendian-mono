package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveStatuses.
 */
@Entity
@Table(name = "leave_statuses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveStatuses implements Serializable {

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
    @Size(max = 20)
    @Column(name = "leave_group", length = 20, nullable = false)
    private String leaveGroup;

    @Size(max = 20)
    @Column(name = "system_key", length = 20)
    private String systemKey;

    @NotNull
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

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

    @OneToMany(mappedBy = "approverStatus")
    @JsonIgnoreProperties(value = { "approverStatus", "currentLeaveRequestStatus", "nextLeaveRequestStatus" }, allowSetters = true)
    private Set<LeaveRequestApproverFlows> leaverequestapproverflowsApproverstatuses = new HashSet<>();

    @OneToMany(mappedBy = "currentLeaveRequestStatus")
    @JsonIgnoreProperties(value = { "approverStatus", "currentLeaveRequestStatus", "nextLeaveRequestStatus" }, allowSetters = true)
    private Set<LeaveRequestApproverFlows> leaverequestapproverflowsCurrentleaverequeststatuses = new HashSet<>();

    @OneToMany(mappedBy = "nextLeaveRequestStatus")
    @JsonIgnoreProperties(value = { "approverStatus", "currentLeaveRequestStatus", "nextLeaveRequestStatus" }, allowSetters = true)
    private Set<LeaveRequestApproverFlows> leaverequestapproverflowsNextleaverequeststatuses = new HashSet<>();

    @OneToMany(mappedBy = "status")
    @JsonIgnoreProperties(value = { "leaveRequest", "user", "status" }, allowSetters = true)
    private Set<LeaveRequestApprovers> leaverequestapproversStatuses = new HashSet<>();

    @OneToMany(mappedBy = "leaveStatus")
    @JsonIgnoreProperties(
        value = {
            "leaveDetail", "leaveStatus", "parentLeaveRequest", "leaverequestapproversLeaverequests", "leaverequestsParentleaverequests",
        },
        allowSetters = true
    )
    private Set<LeaveRequests> leaverequestsLeavestatuses = new HashSet<>();

    @OneToMany(mappedBy = "currentStatus")
    @JsonIgnoreProperties(value = { "currentStatus", "nextStatus", "skipToStatus" }, allowSetters = true)
    private Set<LeaveStatusWorkFlows> leavestatusworkflowsCurrentstatuses = new HashSet<>();

    @OneToMany(mappedBy = "nextStatus")
    @JsonIgnoreProperties(value = { "currentStatus", "nextStatus", "skipToStatus" }, allowSetters = true)
    private Set<LeaveStatusWorkFlows> leavestatusworkflowsNextstatuses = new HashSet<>();

    @OneToMany(mappedBy = "skipToStatus")
    @JsonIgnoreProperties(value = { "currentStatus", "nextStatus", "skipToStatus" }, allowSetters = true)
    private Set<LeaveStatusWorkFlows> leavestatusworkflowsSkiptostatuses = new HashSet<>();

    @OneToMany(mappedBy = "leaveRequestStatus")
    @JsonIgnoreProperties(value = { "leaveEscalationCriteria", "leaveRequestStatus", "leaveType" }, allowSetters = true)
    private Set<LeaveTypeEscalationRules> leavetypeescalationrulesLeaverequeststatuses = new HashSet<>();

    @OneToMany(mappedBy = "approverStatus")
    @JsonIgnoreProperties(value = { "attribute", "approverStatus", "leaveescalationcriteria" }, allowSetters = true)
    private Set<UserAttributeEscalationRules> userattributeescalationrulesApproverstatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveStatuses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LeaveStatuses name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaveGroup() {
        return this.leaveGroup;
    }

    public LeaveStatuses leaveGroup(String leaveGroup) {
        this.setLeaveGroup(leaveGroup);
        return this;
    }

    public void setLeaveGroup(String leaveGroup) {
        this.leaveGroup = leaveGroup;
    }

    public String getSystemKey() {
        return this.systemKey;
    }

    public LeaveStatuses systemKey(String systemKey) {
        this.setSystemKey(systemKey);
        return this;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public LeaveStatuses isDefault(Boolean isDefault) {
        this.setIsDefault(isDefault);
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveStatuses effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveStatuses createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveStatuses updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveStatuses endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveStatuses version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<LeaveRequestApproverFlows> getLeaverequestapproverflowsApproverstatuses() {
        return this.leaverequestapproverflowsApproverstatuses;
    }

    public void setLeaverequestapproverflowsApproverstatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        if (this.leaverequestapproverflowsApproverstatuses != null) {
            this.leaverequestapproverflowsApproverstatuses.forEach(i -> i.setApproverStatus(null));
        }
        if (leaveRequestApproverFlows != null) {
            leaveRequestApproverFlows.forEach(i -> i.setApproverStatus(this));
        }
        this.leaverequestapproverflowsApproverstatuses = leaveRequestApproverFlows;
    }

    public LeaveStatuses leaverequestapproverflowsApproverstatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        this.setLeaverequestapproverflowsApproverstatuses(leaveRequestApproverFlows);
        return this;
    }

    public LeaveStatuses addLeaverequestapproverflowsApproverstatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsApproverstatuses.add(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setApproverStatus(this);
        return this;
    }

    public LeaveStatuses removeLeaverequestapproverflowsApproverstatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsApproverstatuses.remove(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setApproverStatus(null);
        return this;
    }

    public Set<LeaveRequestApproverFlows> getLeaverequestapproverflowsCurrentleaverequeststatuses() {
        return this.leaverequestapproverflowsCurrentleaverequeststatuses;
    }

    public void setLeaverequestapproverflowsCurrentleaverequeststatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        if (this.leaverequestapproverflowsCurrentleaverequeststatuses != null) {
            this.leaverequestapproverflowsCurrentleaverequeststatuses.forEach(i -> i.setCurrentLeaveRequestStatus(null));
        }
        if (leaveRequestApproverFlows != null) {
            leaveRequestApproverFlows.forEach(i -> i.setCurrentLeaveRequestStatus(this));
        }
        this.leaverequestapproverflowsCurrentleaverequeststatuses = leaveRequestApproverFlows;
    }

    public LeaveStatuses leaverequestapproverflowsCurrentleaverequeststatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        this.setLeaverequestapproverflowsCurrentleaverequeststatuses(leaveRequestApproverFlows);
        return this;
    }

    public LeaveStatuses addLeaverequestapproverflowsCurrentleaverequeststatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsCurrentleaverequeststatuses.add(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setCurrentLeaveRequestStatus(this);
        return this;
    }

    public LeaveStatuses removeLeaverequestapproverflowsCurrentleaverequeststatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsCurrentleaverequeststatuses.remove(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setCurrentLeaveRequestStatus(null);
        return this;
    }

    public Set<LeaveRequestApproverFlows> getLeaverequestapproverflowsNextleaverequeststatuses() {
        return this.leaverequestapproverflowsNextleaverequeststatuses;
    }

    public void setLeaverequestapproverflowsNextleaverequeststatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        if (this.leaverequestapproverflowsNextleaverequeststatuses != null) {
            this.leaverequestapproverflowsNextleaverequeststatuses.forEach(i -> i.setNextLeaveRequestStatus(null));
        }
        if (leaveRequestApproverFlows != null) {
            leaveRequestApproverFlows.forEach(i -> i.setNextLeaveRequestStatus(this));
        }
        this.leaverequestapproverflowsNextleaverequeststatuses = leaveRequestApproverFlows;
    }

    public LeaveStatuses leaverequestapproverflowsNextleaverequeststatuses(Set<LeaveRequestApproverFlows> leaveRequestApproverFlows) {
        this.setLeaverequestapproverflowsNextleaverequeststatuses(leaveRequestApproverFlows);
        return this;
    }

    public LeaveStatuses addLeaverequestapproverflowsNextleaverequeststatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsNextleaverequeststatuses.add(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setNextLeaveRequestStatus(this);
        return this;
    }

    public LeaveStatuses removeLeaverequestapproverflowsNextleaverequeststatus(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        this.leaverequestapproverflowsNextleaverequeststatuses.remove(leaveRequestApproverFlows);
        leaveRequestApproverFlows.setNextLeaveRequestStatus(null);
        return this;
    }

    public Set<LeaveRequestApprovers> getLeaverequestapproversStatuses() {
        return this.leaverequestapproversStatuses;
    }

    public void setLeaverequestapproversStatuses(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        if (this.leaverequestapproversStatuses != null) {
            this.leaverequestapproversStatuses.forEach(i -> i.setStatus(null));
        }
        if (leaveRequestApprovers != null) {
            leaveRequestApprovers.forEach(i -> i.setStatus(this));
        }
        this.leaverequestapproversStatuses = leaveRequestApprovers;
    }

    public LeaveStatuses leaverequestapproversStatuses(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        this.setLeaverequestapproversStatuses(leaveRequestApprovers);
        return this;
    }

    public LeaveStatuses addLeaverequestapproversStatus(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversStatuses.add(leaveRequestApprovers);
        leaveRequestApprovers.setStatus(this);
        return this;
    }

    public LeaveStatuses removeLeaverequestapproversStatus(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversStatuses.remove(leaveRequestApprovers);
        leaveRequestApprovers.setStatus(null);
        return this;
    }

    public Set<LeaveRequests> getLeaverequestsLeavestatuses() {
        return this.leaverequestsLeavestatuses;
    }

    public void setLeaverequestsLeavestatuses(Set<LeaveRequests> leaveRequests) {
        if (this.leaverequestsLeavestatuses != null) {
            this.leaverequestsLeavestatuses.forEach(i -> i.setLeaveStatus(null));
        }
        if (leaveRequests != null) {
            leaveRequests.forEach(i -> i.setLeaveStatus(this));
        }
        this.leaverequestsLeavestatuses = leaveRequests;
    }

    public LeaveStatuses leaverequestsLeavestatuses(Set<LeaveRequests> leaveRequests) {
        this.setLeaverequestsLeavestatuses(leaveRequests);
        return this;
    }

    public LeaveStatuses addLeaverequestsLeavestatus(LeaveRequests leaveRequests) {
        this.leaverequestsLeavestatuses.add(leaveRequests);
        leaveRequests.setLeaveStatus(this);
        return this;
    }

    public LeaveStatuses removeLeaverequestsLeavestatus(LeaveRequests leaveRequests) {
        this.leaverequestsLeavestatuses.remove(leaveRequests);
        leaveRequests.setLeaveStatus(null);
        return this;
    }

    public Set<LeaveStatusWorkFlows> getLeavestatusworkflowsCurrentstatuses() {
        return this.leavestatusworkflowsCurrentstatuses;
    }

    public void setLeavestatusworkflowsCurrentstatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        if (this.leavestatusworkflowsCurrentstatuses != null) {
            this.leavestatusworkflowsCurrentstatuses.forEach(i -> i.setCurrentStatus(null));
        }
        if (leaveStatusWorkFlows != null) {
            leaveStatusWorkFlows.forEach(i -> i.setCurrentStatus(this));
        }
        this.leavestatusworkflowsCurrentstatuses = leaveStatusWorkFlows;
    }

    public LeaveStatuses leavestatusworkflowsCurrentstatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        this.setLeavestatusworkflowsCurrentstatuses(leaveStatusWorkFlows);
        return this;
    }

    public LeaveStatuses addLeavestatusworkflowsCurrentstatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsCurrentstatuses.add(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setCurrentStatus(this);
        return this;
    }

    public LeaveStatuses removeLeavestatusworkflowsCurrentstatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsCurrentstatuses.remove(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setCurrentStatus(null);
        return this;
    }

    public Set<LeaveStatusWorkFlows> getLeavestatusworkflowsNextstatuses() {
        return this.leavestatusworkflowsNextstatuses;
    }

    public void setLeavestatusworkflowsNextstatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        if (this.leavestatusworkflowsNextstatuses != null) {
            this.leavestatusworkflowsNextstatuses.forEach(i -> i.setNextStatus(null));
        }
        if (leaveStatusWorkFlows != null) {
            leaveStatusWorkFlows.forEach(i -> i.setNextStatus(this));
        }
        this.leavestatusworkflowsNextstatuses = leaveStatusWorkFlows;
    }

    public LeaveStatuses leavestatusworkflowsNextstatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        this.setLeavestatusworkflowsNextstatuses(leaveStatusWorkFlows);
        return this;
    }

    public LeaveStatuses addLeavestatusworkflowsNextstatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsNextstatuses.add(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setNextStatus(this);
        return this;
    }

    public LeaveStatuses removeLeavestatusworkflowsNextstatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsNextstatuses.remove(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setNextStatus(null);
        return this;
    }

    public Set<LeaveStatusWorkFlows> getLeavestatusworkflowsSkiptostatuses() {
        return this.leavestatusworkflowsSkiptostatuses;
    }

    public void setLeavestatusworkflowsSkiptostatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        if (this.leavestatusworkflowsSkiptostatuses != null) {
            this.leavestatusworkflowsSkiptostatuses.forEach(i -> i.setSkipToStatus(null));
        }
        if (leaveStatusWorkFlows != null) {
            leaveStatusWorkFlows.forEach(i -> i.setSkipToStatus(this));
        }
        this.leavestatusworkflowsSkiptostatuses = leaveStatusWorkFlows;
    }

    public LeaveStatuses leavestatusworkflowsSkiptostatuses(Set<LeaveStatusWorkFlows> leaveStatusWorkFlows) {
        this.setLeavestatusworkflowsSkiptostatuses(leaveStatusWorkFlows);
        return this;
    }

    public LeaveStatuses addLeavestatusworkflowsSkiptostatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsSkiptostatuses.add(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setSkipToStatus(this);
        return this;
    }

    public LeaveStatuses removeLeavestatusworkflowsSkiptostatus(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        this.leavestatusworkflowsSkiptostatuses.remove(leaveStatusWorkFlows);
        leaveStatusWorkFlows.setSkipToStatus(null);
        return this;
    }

    public Set<LeaveTypeEscalationRules> getLeavetypeescalationrulesLeaverequeststatuses() {
        return this.leavetypeescalationrulesLeaverequeststatuses;
    }

    public void setLeavetypeescalationrulesLeaverequeststatuses(Set<LeaveTypeEscalationRules> leaveTypeEscalationRules) {
        if (this.leavetypeescalationrulesLeaverequeststatuses != null) {
            this.leavetypeescalationrulesLeaverequeststatuses.forEach(i -> i.setLeaveRequestStatus(null));
        }
        if (leaveTypeEscalationRules != null) {
            leaveTypeEscalationRules.forEach(i -> i.setLeaveRequestStatus(this));
        }
        this.leavetypeescalationrulesLeaverequeststatuses = leaveTypeEscalationRules;
    }

    public LeaveStatuses leavetypeescalationrulesLeaverequeststatuses(Set<LeaveTypeEscalationRules> leaveTypeEscalationRules) {
        this.setLeavetypeescalationrulesLeaverequeststatuses(leaveTypeEscalationRules);
        return this;
    }

    public LeaveStatuses addLeavetypeescalationrulesLeaverequeststatus(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        this.leavetypeescalationrulesLeaverequeststatuses.add(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveRequestStatus(this);
        return this;
    }

    public LeaveStatuses removeLeavetypeescalationrulesLeaverequeststatus(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        this.leavetypeescalationrulesLeaverequeststatuses.remove(leaveTypeEscalationRules);
        leaveTypeEscalationRules.setLeaveRequestStatus(null);
        return this;
    }

    public Set<UserAttributeEscalationRules> getUserattributeescalationrulesApproverstatuses() {
        return this.userattributeescalationrulesApproverstatuses;
    }

    public void setUserattributeescalationrulesApproverstatuses(Set<UserAttributeEscalationRules> userAttributeEscalationRules) {
        if (this.userattributeescalationrulesApproverstatuses != null) {
            this.userattributeescalationrulesApproverstatuses.forEach(i -> i.setApproverStatus(null));
        }
        if (userAttributeEscalationRules != null) {
            userAttributeEscalationRules.forEach(i -> i.setApproverStatus(this));
        }
        this.userattributeescalationrulesApproverstatuses = userAttributeEscalationRules;
    }

    public LeaveStatuses userattributeescalationrulesApproverstatuses(Set<UserAttributeEscalationRules> userAttributeEscalationRules) {
        this.setUserattributeescalationrulesApproverstatuses(userAttributeEscalationRules);
        return this;
    }

    public LeaveStatuses addUserattributeescalationrulesApproverstatus(UserAttributeEscalationRules userAttributeEscalationRules) {
        this.userattributeescalationrulesApproverstatuses.add(userAttributeEscalationRules);
        userAttributeEscalationRules.setApproverStatus(this);
        return this;
    }

    public LeaveStatuses removeUserattributeescalationrulesApproverstatus(UserAttributeEscalationRules userAttributeEscalationRules) {
        this.userattributeescalationrulesApproverstatuses.remove(userAttributeEscalationRules);
        userAttributeEscalationRules.setApproverStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveStatuses)) {
            return false;
        }
        return id != null && id.equals(((LeaveStatuses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveStatuses{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", leaveGroup='" + getLeaveGroup() + "'" +
            ", systemKey='" + getSystemKey() + "'" +
            ", isDefault='" + getIsDefault() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
