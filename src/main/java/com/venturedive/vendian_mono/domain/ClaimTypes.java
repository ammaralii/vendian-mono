package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimTypes.
 */
@Entity
@Table(name = "claim_types")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "claimtype", length = 255)
    private String claimtype;

    @Column(name = "daterangerequired")
    private Boolean daterangerequired;

    @Column(name = "descriptionrequired")
    private Boolean descriptionrequired;

    @Column(name = "parentid")
    private Integer parentid;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "claimtype")
    @JsonIgnoreProperties(value = { "claimrequest", "claimtype" }, allowSetters = true)
    private Set<ClaimDetails> claimdetailsClaimtypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimTypes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimtype() {
        return this.claimtype;
    }

    public ClaimTypes claimtype(String claimtype) {
        this.setClaimtype(claimtype);
        return this;
    }

    public void setClaimtype(String claimtype) {
        this.claimtype = claimtype;
    }

    public Boolean getDaterangerequired() {
        return this.daterangerequired;
    }

    public ClaimTypes daterangerequired(Boolean daterangerequired) {
        this.setDaterangerequired(daterangerequired);
        return this;
    }

    public void setDaterangerequired(Boolean daterangerequired) {
        this.daterangerequired = daterangerequired;
    }

    public Boolean getDescriptionrequired() {
        return this.descriptionrequired;
    }

    public ClaimTypes descriptionrequired(Boolean descriptionrequired) {
        this.setDescriptionrequired(descriptionrequired);
        return this;
    }

    public void setDescriptionrequired(Boolean descriptionrequired) {
        this.descriptionrequired = descriptionrequired;
    }

    public Integer getParentid() {
        return this.parentid;
    }

    public ClaimTypes parentid(Integer parentid) {
        this.setParentid(parentid);
        return this;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimTypes createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimTypes updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<ClaimDetails> getClaimdetailsClaimtypes() {
        return this.claimdetailsClaimtypes;
    }

    public void setClaimdetailsClaimtypes(Set<ClaimDetails> claimDetails) {
        if (this.claimdetailsClaimtypes != null) {
            this.claimdetailsClaimtypes.forEach(i -> i.setClaimtype(null));
        }
        if (claimDetails != null) {
            claimDetails.forEach(i -> i.setClaimtype(this));
        }
        this.claimdetailsClaimtypes = claimDetails;
    }

    public ClaimTypes claimdetailsClaimtypes(Set<ClaimDetails> claimDetails) {
        this.setClaimdetailsClaimtypes(claimDetails);
        return this;
    }

    public ClaimTypes addClaimdetailsClaimtype(ClaimDetails claimDetails) {
        this.claimdetailsClaimtypes.add(claimDetails);
        claimDetails.setClaimtype(this);
        return this;
    }

    public ClaimTypes removeClaimdetailsClaimtype(ClaimDetails claimDetails) {
        this.claimdetailsClaimtypes.remove(claimDetails);
        claimDetails.setClaimtype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimTypes)) {
            return false;
        }
        return id != null && id.equals(((ClaimTypes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimTypes{" +
            "id=" + getId() +
            ", claimtype='" + getClaimtype() + "'" +
            ", daterangerequired='" + getDaterangerequired() + "'" +
            ", descriptionrequired='" + getDescriptionrequired() + "'" +
            ", parentid=" + getParentid() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
