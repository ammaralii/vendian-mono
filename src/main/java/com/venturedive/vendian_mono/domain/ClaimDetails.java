package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimDetails.
 */
@Entity
@Table(name = "claim_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "claimapproversClaimrequests", "claimdetailsClaimrequests", "claimimagesClaimrequests" },
        allowSetters = true
    )
    private ClaimRequests claimrequest;

    @ManyToOne
    @JsonIgnoreProperties(value = { "claimdetailsClaimtypes" }, allowSetters = true)
    private ClaimTypes claimtype;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public ClaimDetails amount(Integer amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public ClaimDetails startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public ClaimDetails enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return this.description;
    }

    public ClaimDetails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimDetails createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimDetails updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public ClaimRequests getClaimrequest() {
        return this.claimrequest;
    }

    public void setClaimrequest(ClaimRequests claimRequests) {
        this.claimrequest = claimRequests;
    }

    public ClaimDetails claimrequest(ClaimRequests claimRequests) {
        this.setClaimrequest(claimRequests);
        return this;
    }

    public ClaimTypes getClaimtype() {
        return this.claimtype;
    }

    public void setClaimtype(ClaimTypes claimTypes) {
        this.claimtype = claimTypes;
    }

    public ClaimDetails claimtype(ClaimTypes claimTypes) {
        this.setClaimtype(claimTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimDetails)) {
            return false;
        }
        return id != null && id.equals(((ClaimDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimDetails{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
