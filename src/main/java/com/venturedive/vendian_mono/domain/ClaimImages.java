package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimImages.
 */
@Entity
@Table(name = "claim_images")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "images", length = 255)
    private String images;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimImages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImages() {
        return this.images;
    }

    public ClaimImages images(String images) {
        this.setImages(images);
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimImages createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimImages updatedat(Instant updatedat) {
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

    public ClaimImages claimrequest(ClaimRequests claimRequests) {
        this.setClaimrequest(claimRequests);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimImages)) {
            return false;
        }
        return id != null && id.equals(((ClaimImages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimImages{" +
            "id=" + getId() +
            ", images='" + getImages() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
