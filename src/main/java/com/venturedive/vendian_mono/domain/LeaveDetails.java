package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveDetails.
 */
@Entity
@Table(name = "leave_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @NotNull
    @Column(name = "used", precision = 21, scale = 2, nullable = false)
    private BigDecimal used;

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
    @JsonIgnoreProperties(value = { "user", "leavedetailsLeaves" }, allowSetters = true)
    private Leaves leave;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leavedetailsLeavetypes",
            "leavetypeapprovalrulesLeavetypes",
            "leavetypeconfigurationsLeavetypes",
            "leavetypeescalationrulesLeavetypes",
            "leavetyperestrictionsLeavetypes",
        },
        allowSetters = true
    )
    private LeaveTypes leaveType;

    @OneToMany(mappedBy = "leaveDetail")
    @JsonIgnoreProperties(value = { "leaveDetail", "adjustedBy" }, allowSetters = true)
    private Set<LeaveDetailAdjustmentLogs> leavedetailadjustmentlogsLeavedetails = new HashSet<>();

    @OneToMany(mappedBy = "leaveDetail")
    @JsonIgnoreProperties(
        value = {
            "leaveDetail", "leaveStatus", "parentLeaveRequest", "leaverequestapproversLeaverequests", "leaverequestsParentleaverequests",
        },
        allowSetters = true
    )
    private Set<LeaveRequests> leaverequestsLeavedetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public LeaveDetails total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUsed() {
        return this.used;
    }

    public LeaveDetails used(BigDecimal used) {
        this.setUsed(used);
        return this;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveDetails effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveDetails createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveDetails updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveDetails endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveDetails version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Leaves getLeave() {
        return this.leave;
    }

    public void setLeave(Leaves leaves) {
        this.leave = leaves;
    }

    public LeaveDetails leave(Leaves leaves) {
        this.setLeave(leaves);
        return this;
    }

    public LeaveTypes getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(LeaveTypes leaveTypes) {
        this.leaveType = leaveTypes;
    }

    public LeaveDetails leaveType(LeaveTypes leaveTypes) {
        this.setLeaveType(leaveTypes);
        return this;
    }

    public Set<LeaveDetailAdjustmentLogs> getLeavedetailadjustmentlogsLeavedetails() {
        return this.leavedetailadjustmentlogsLeavedetails;
    }

    public void setLeavedetailadjustmentlogsLeavedetails(Set<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogs) {
        if (this.leavedetailadjustmentlogsLeavedetails != null) {
            this.leavedetailadjustmentlogsLeavedetails.forEach(i -> i.setLeaveDetail(null));
        }
        if (leaveDetailAdjustmentLogs != null) {
            leaveDetailAdjustmentLogs.forEach(i -> i.setLeaveDetail(this));
        }
        this.leavedetailadjustmentlogsLeavedetails = leaveDetailAdjustmentLogs;
    }

    public LeaveDetails leavedetailadjustmentlogsLeavedetails(Set<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogs) {
        this.setLeavedetailadjustmentlogsLeavedetails(leaveDetailAdjustmentLogs);
        return this;
    }

    public LeaveDetails addLeavedetailadjustmentlogsLeavedetail(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        this.leavedetailadjustmentlogsLeavedetails.add(leaveDetailAdjustmentLogs);
        leaveDetailAdjustmentLogs.setLeaveDetail(this);
        return this;
    }

    public LeaveDetails removeLeavedetailadjustmentlogsLeavedetail(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        this.leavedetailadjustmentlogsLeavedetails.remove(leaveDetailAdjustmentLogs);
        leaveDetailAdjustmentLogs.setLeaveDetail(null);
        return this;
    }

    public Set<LeaveRequests> getLeaverequestsLeavedetails() {
        return this.leaverequestsLeavedetails;
    }

    public void setLeaverequestsLeavedetails(Set<LeaveRequests> leaveRequests) {
        if (this.leaverequestsLeavedetails != null) {
            this.leaverequestsLeavedetails.forEach(i -> i.setLeaveDetail(null));
        }
        if (leaveRequests != null) {
            leaveRequests.forEach(i -> i.setLeaveDetail(this));
        }
        this.leaverequestsLeavedetails = leaveRequests;
    }

    public LeaveDetails leaverequestsLeavedetails(Set<LeaveRequests> leaveRequests) {
        this.setLeaverequestsLeavedetails(leaveRequests);
        return this;
    }

    public LeaveDetails addLeaverequestsLeavedetail(LeaveRequests leaveRequests) {
        this.leaverequestsLeavedetails.add(leaveRequests);
        leaveRequests.setLeaveDetail(this);
        return this;
    }

    public LeaveDetails removeLeaverequestsLeavedetail(LeaveRequests leaveRequests) {
        this.leaverequestsLeavedetails.remove(leaveRequests);
        leaveRequests.setLeaveDetail(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveDetails)) {
            return false;
        }
        return id != null && id.equals(((LeaveDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveDetails{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", used=" + getUsed() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
