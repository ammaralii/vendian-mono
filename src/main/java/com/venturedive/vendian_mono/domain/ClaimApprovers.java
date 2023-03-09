package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimApprovers.
 */
@Entity
@Table(name = "claim_approvers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimApprovers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "referenceid")
    private Integer referenceid;

    @Size(max = 255)
    @Column(name = "designation", length = 255)
    private String designation;

    @Column(name = "approveorder")
    private Integer approveorder;

    @Size(max = 255)
    @Column(name = "reference", length = 255)
    private String reference;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Size(max = 255)
    @Column(name = "approvedby", length = 255)
    private String approvedby;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "claimapproversStatuses" }, allowSetters = true)
    private ClaimStatus status;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "claimapproversClaimrequests", "claimdetailsClaimrequests", "claimimagesClaimrequests" },
        allowSetters = true
    )
    private ClaimRequests claimrequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimApprovers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReferenceid() {
        return this.referenceid;
    }

    public ClaimApprovers referenceid(Integer referenceid) {
        this.setReferenceid(referenceid);
        return this;
    }

    public void setReferenceid(Integer referenceid) {
        this.referenceid = referenceid;
    }

    public String getDesignation() {
        return this.designation;
    }

    public ClaimApprovers designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getApproveorder() {
        return this.approveorder;
    }

    public ClaimApprovers approveorder(Integer approveorder) {
        this.setApproveorder(approveorder);
        return this;
    }

    public void setApproveorder(Integer approveorder) {
        this.approveorder = approveorder;
    }

    public String getReference() {
        return this.reference;
    }

    public ClaimApprovers reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getComments() {
        return this.comments;
    }

    public ClaimApprovers comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprovedby() {
        return this.approvedby;
    }

    public ClaimApprovers approvedby(String approvedby) {
        this.setApprovedby(approvedby);
        return this;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimApprovers createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimApprovers updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public ClaimStatus getStatus() {
        return this.status;
    }

    public void setStatus(ClaimStatus claimStatus) {
        this.status = claimStatus;
    }

    public ClaimApprovers status(ClaimStatus claimStatus) {
        this.setStatus(claimStatus);
        return this;
    }

    public ClaimRequests getClaimrequest() {
        return this.claimrequest;
    }

    public void setClaimrequest(ClaimRequests claimRequests) {
        this.claimrequest = claimRequests;
    }

    public ClaimApprovers claimrequest(ClaimRequests claimRequests) {
        this.setClaimrequest(claimRequests);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimApprovers)) {
            return false;
        }
        return id != null && id.equals(((ClaimApprovers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimApprovers{" +
            "id=" + getId() +
            ", referenceid=" + getReferenceid() +
            ", designation='" + getDesignation() + "'" +
            ", approveorder=" + getApproveorder() +
            ", reference='" + getReference() + "'" +
            ", comments='" + getComments() + "'" +
            ", approvedby='" + getApprovedby() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
