package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ApproverGroups.
 */
@Entity
@Table(name = "approver_groups")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApproverGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reference_id")
    private Integer referenceId;

    @NotNull
    @Size(max = 255)
    @Column(name = "reference_type", length = 255, nullable = false)
    private String referenceType;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @OneToMany(mappedBy = "approverGroup")
    @JsonIgnoreProperties(value = { "approverGroup" }, allowSetters = true)
    private Set<Approvers> approversApprovergroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApproverGroups id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReferenceId() {
        return this.referenceId;
    }

    public ApproverGroups referenceId(Integer referenceId) {
        this.setReferenceId(referenceId);
        return this;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return this.referenceType;
    }

    public ApproverGroups referenceType(String referenceType) {
        this.setReferenceType(referenceType);
        return this;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ApproverGroups createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public ApproverGroups updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public ApproverGroups deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public ApproverGroups version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<Approvers> getApproversApprovergroups() {
        return this.approversApprovergroups;
    }

    public void setApproversApprovergroups(Set<Approvers> approvers) {
        if (this.approversApprovergroups != null) {
            this.approversApprovergroups.forEach(i -> i.setApproverGroup(null));
        }
        if (approvers != null) {
            approvers.forEach(i -> i.setApproverGroup(this));
        }
        this.approversApprovergroups = approvers;
    }

    public ApproverGroups approversApprovergroups(Set<Approvers> approvers) {
        this.setApproversApprovergroups(approvers);
        return this;
    }

    public ApproverGroups addApproversApprovergroup(Approvers approvers) {
        this.approversApprovergroups.add(approvers);
        approvers.setApproverGroup(this);
        return this;
    }

    public ApproverGroups removeApproversApprovergroup(Approvers approvers) {
        this.approversApprovergroups.remove(approvers);
        approvers.setApproverGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApproverGroups)) {
            return false;
        }
        return id != null && id.equals(((ApproverGroups) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApproverGroups{" +
            "id=" + getId() +
            ", referenceId=" + getReferenceId() +
            ", referenceType='" + getReferenceType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
