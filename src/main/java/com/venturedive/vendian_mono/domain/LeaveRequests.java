package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveRequests.
 */
@Entity
@Table(name = "leave_requests")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "request_start_date", nullable = false)
    private LocalDate requestStartDate;

    @NotNull
    @Column(name = "request_end_date", nullable = false)
    private LocalDate requestEndDate;

    @Column(name = "is_half_day")
    private Boolean isHalfDay;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @NotNull
    @Size(max = 65535)
    @Column(name = "comments", length = 65535, nullable = false)
    private String comments;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "leave", "leaveType", "leavedetailadjustmentlogsLeavedetails", "leaverequestsLeavedetails" },
        allowSetters = true
    )
    private LeaveDetails leaveDetail;

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
    private LeaveStatuses leaveStatus;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leaveDetail", "leaveStatus", "parentLeaveRequest", "leaverequestapproversLeaverequests", "leaverequestsParentleaverequests",
        },
        allowSetters = true
    )
    private LeaveRequests parentLeaveRequest;

    @OneToMany(mappedBy = "leaveRequest")
    @JsonIgnoreProperties(value = { "leaveRequest", "user", "status" }, allowSetters = true)
    private Set<LeaveRequestApprovers> leaverequestapproversLeaverequests = new HashSet<>();

    @OneToMany(mappedBy = "parentLeaveRequest")
    @JsonIgnoreProperties(
        value = {
            "leaveDetail", "leaveStatus", "parentLeaveRequest", "leaverequestapproversLeaverequests", "leaverequestsParentleaverequests",
        },
        allowSetters = true
    )
    private Set<LeaveRequests> leaverequestsParentleaverequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveRequests id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveRequests createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getRequestStartDate() {
        return this.requestStartDate;
    }

    public LeaveRequests requestStartDate(LocalDate requestStartDate) {
        this.setRequestStartDate(requestStartDate);
        return this;
    }

    public void setRequestStartDate(LocalDate requestStartDate) {
        this.requestStartDate = requestStartDate;
    }

    public LocalDate getRequestEndDate() {
        return this.requestEndDate;
    }

    public LeaveRequests requestEndDate(LocalDate requestEndDate) {
        this.setRequestEndDate(requestEndDate);
        return this;
    }

    public void setRequestEndDate(LocalDate requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public Boolean getIsHalfDay() {
        return this.isHalfDay;
    }

    public LeaveRequests isHalfDay(Boolean isHalfDay) {
        this.setIsHalfDay(isHalfDay);
        return this;
    }

    public void setIsHalfDay(Boolean isHalfDay) {
        this.isHalfDay = isHalfDay;
    }

    public Instant getStatusDate() {
        return this.statusDate;
    }

    public LeaveRequests statusDate(Instant statusDate) {
        this.setStatusDate(statusDate);
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getComments() {
        return this.comments;
    }

    public LeaveRequests comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveRequests updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public LeaveRequests deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveRequests version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveDetails getLeaveDetail() {
        return this.leaveDetail;
    }

    public void setLeaveDetail(LeaveDetails leaveDetails) {
        this.leaveDetail = leaveDetails;
    }

    public LeaveRequests leaveDetail(LeaveDetails leaveDetails) {
        this.setLeaveDetail(leaveDetails);
        return this;
    }

    public LeaveStatuses getLeaveStatus() {
        return this.leaveStatus;
    }

    public void setLeaveStatus(LeaveStatuses leaveStatuses) {
        this.leaveStatus = leaveStatuses;
    }

    public LeaveRequests leaveStatus(LeaveStatuses leaveStatuses) {
        this.setLeaveStatus(leaveStatuses);
        return this;
    }

    public LeaveRequests getParentLeaveRequest() {
        return this.parentLeaveRequest;
    }

    public void setParentLeaveRequest(LeaveRequests leaveRequests) {
        this.parentLeaveRequest = leaveRequests;
    }

    public LeaveRequests parentLeaveRequest(LeaveRequests leaveRequests) {
        this.setParentLeaveRequest(leaveRequests);
        return this;
    }

    public Set<LeaveRequestApprovers> getLeaverequestapproversLeaverequests() {
        return this.leaverequestapproversLeaverequests;
    }

    public void setLeaverequestapproversLeaverequests(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        if (this.leaverequestapproversLeaverequests != null) {
            this.leaverequestapproversLeaverequests.forEach(i -> i.setLeaveRequest(null));
        }
        if (leaveRequestApprovers != null) {
            leaveRequestApprovers.forEach(i -> i.setLeaveRequest(this));
        }
        this.leaverequestapproversLeaverequests = leaveRequestApprovers;
    }

    public LeaveRequests leaverequestapproversLeaverequests(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        this.setLeaverequestapproversLeaverequests(leaveRequestApprovers);
        return this;
    }

    public LeaveRequests addLeaverequestapproversLeaverequest(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversLeaverequests.add(leaveRequestApprovers);
        leaveRequestApprovers.setLeaveRequest(this);
        return this;
    }

    public LeaveRequests removeLeaverequestapproversLeaverequest(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversLeaverequests.remove(leaveRequestApprovers);
        leaveRequestApprovers.setLeaveRequest(null);
        return this;
    }

    public Set<LeaveRequests> getLeaverequestsParentleaverequests() {
        return this.leaverequestsParentleaverequests;
    }

    public void setLeaverequestsParentleaverequests(Set<LeaveRequests> leaveRequests) {
        if (this.leaverequestsParentleaverequests != null) {
            this.leaverequestsParentleaverequests.forEach(i -> i.setParentLeaveRequest(null));
        }
        if (leaveRequests != null) {
            leaveRequests.forEach(i -> i.setParentLeaveRequest(this));
        }
        this.leaverequestsParentleaverequests = leaveRequests;
    }

    public LeaveRequests leaverequestsParentleaverequests(Set<LeaveRequests> leaveRequests) {
        this.setLeaverequestsParentleaverequests(leaveRequests);
        return this;
    }

    public LeaveRequests addLeaverequestsParentleaverequest(LeaveRequests leaveRequests) {
        this.leaverequestsParentleaverequests.add(leaveRequests);
        leaveRequests.setParentLeaveRequest(this);
        return this;
    }

    public LeaveRequests removeLeaverequestsParentleaverequest(LeaveRequests leaveRequests) {
        this.leaverequestsParentleaverequests.remove(leaveRequests);
        leaveRequests.setParentLeaveRequest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveRequests)) {
            return false;
        }
        return id != null && id.equals(((LeaveRequests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequests{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", requestStartDate='" + getRequestStartDate() + "'" +
            ", requestEndDate='" + getRequestEndDate() + "'" +
            ", isHalfDay='" + getIsHalfDay() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
