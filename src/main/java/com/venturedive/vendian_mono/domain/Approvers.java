package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Approvers.
 */
@Entity
@Table(name = "approvers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Approvers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "user_id", length = 255)
    private String userId;

    @NotNull
    @Size(max = 17)
    @Column(name = "reference", length = 17, nullable = false)
    private String reference;

    @NotNull
    @Column(name = "jhi_as", nullable = false)
    private String as;

    @Size(max = 65535)
    @Column(name = "comment", length = 65535)
    private String comment;

    @NotNull
    @Size(max = 8)
    @Column(name = "status", length = 8, nullable = false)
    private String status;

    @NotNull
    @Column(name = "staus_date", nullable = false)
    private Instant stausDate;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "approversApprovergroups" }, allowSetters = true)
    private ApproverGroups approverGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Approvers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Approvers userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReference() {
        return this.reference;
    }

    public Approvers reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAs() {
        return this.as;
    }

    public Approvers as(String as) {
        this.setAs(as);
        return this;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getComment() {
        return this.comment;
    }

    public Approvers comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return this.status;
    }

    public Approvers status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStausDate() {
        return this.stausDate;
    }

    public Approvers stausDate(Instant stausDate) {
        this.setStausDate(stausDate);
        return this;
    }

    public void setStausDate(Instant stausDate) {
        this.stausDate = stausDate;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Approvers priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Approvers createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Approvers updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Approvers deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Approvers version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ApproverGroups getApproverGroup() {
        return this.approverGroup;
    }

    public void setApproverGroup(ApproverGroups approverGroups) {
        this.approverGroup = approverGroups;
    }

    public Approvers approverGroup(ApproverGroups approverGroups) {
        this.setApproverGroup(approverGroups);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Approvers)) {
            return false;
        }
        return id != null && id.equals(((Approvers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approvers{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", reference='" + getReference() + "'" +
            ", as='" + getAs() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", stausDate='" + getStausDate() + "'" +
            ", priority=" + getPriority() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
