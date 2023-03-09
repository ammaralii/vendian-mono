package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimStatus.
 */
@Entity
@Table(name = "claim_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "status")
    @JsonIgnoreProperties(value = { "status", "claimrequest" }, allowSetters = true)
    private Set<ClaimApprovers> claimapproversStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public ClaimStatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimStatus createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimStatus updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<ClaimApprovers> getClaimapproversStatuses() {
        return this.claimapproversStatuses;
    }

    public void setClaimapproversStatuses(Set<ClaimApprovers> claimApprovers) {
        if (this.claimapproversStatuses != null) {
            this.claimapproversStatuses.forEach(i -> i.setStatus(null));
        }
        if (claimApprovers != null) {
            claimApprovers.forEach(i -> i.setStatus(this));
        }
        this.claimapproversStatuses = claimApprovers;
    }

    public ClaimStatus claimapproversStatuses(Set<ClaimApprovers> claimApprovers) {
        this.setClaimapproversStatuses(claimApprovers);
        return this;
    }

    public ClaimStatus addClaimapproversStatus(ClaimApprovers claimApprovers) {
        this.claimapproversStatuses.add(claimApprovers);
        claimApprovers.setStatus(this);
        return this;
    }

    public ClaimStatus removeClaimapproversStatus(ClaimApprovers claimApprovers) {
        this.claimapproversStatuses.remove(claimApprovers);
        claimApprovers.setStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimStatus)) {
            return false;
        }
        return id != null && id.equals(((ClaimStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
