package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ApproverFlows.
 */
@Entity
@Table(name = "approver_flows")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverFlows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "reference_type", length = 255, nullable = false)
    private String referenceType;

    @NotNull
    @Size(max = 9)
    @Column(name = "approver_status", length = 9, nullable = false)
    private String approverStatus;

    @NotNull
    @Size(max = 3)
    @Column(name = "approval", length = 3, nullable = false)
    private String approval;

    @NotNull
    @Size(max = 9)
    @Column(name = "current_status", length = 9, nullable = false)
    private String currentStatus;

    @NotNull
    @Size(max = 9)
    @Column(name = "next_status", length = 9, nullable = false)
    private String nextStatus;

    @NotNull
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApproverFlows id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceType() {
        return this.referenceType;
    }

    public ApproverFlows referenceType(String referenceType) {
        this.setReferenceType(referenceType);
        return this;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getApproverStatus() {
        return this.approverStatus;
    }

    public ApproverFlows approverStatus(String approverStatus) {
        this.setApproverStatus(approverStatus);
        return this;
    }

    public void setApproverStatus(String approverStatus) {
        this.approverStatus = approverStatus;
    }

    public String getApproval() {
        return this.approval;
    }

    public ApproverFlows approval(String approval) {
        this.setApproval(approval);
        return this;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public ApproverFlows currentStatus(String currentStatus) {
        this.setCurrentStatus(currentStatus);
        return this;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getNextStatus() {
        return this.nextStatus;
    }

    public ApproverFlows nextStatus(String nextStatus) {
        this.setNextStatus(nextStatus);
        return this;
    }

    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public ApproverFlows effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ApproverFlows createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public ApproverFlows updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public ApproverFlows version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApproverFlows)) {
            return false;
        }
        return id != null && id.equals(((ApproverFlows) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverFlows{" +
            "id=" + getId() +
            ", referenceType='" + getReferenceType() + "'" +
            ", approverStatus='" + getApproverStatus() + "'" +
            ", approval='" + getApproval() + "'" +
            ", currentStatus='" + getCurrentStatus() + "'" +
            ", nextStatus='" + getNextStatus() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
