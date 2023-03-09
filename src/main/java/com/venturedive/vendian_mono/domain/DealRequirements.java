package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealRequirements.
 */
@Entity
@Table(name = "deal_requirements")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealRequirements implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "dealreqidentifier", length = 255, nullable = false)
    private String dealreqidentifier;

    @NotNull
    @Size(max = 255)
    @Column(name = "competencyname", length = 255, nullable = false)
    private String competencyname;

    @Column(name = "skills")
    private String skills;

    @NotNull
    @Column(name = "resourcerequired", nullable = false)
    private Double resourcerequired;

    @Size(max = 255)
    @Column(name = "minexperiencelevel", length = 255)
    private String minexperiencelevel;

    @Size(max = 255)
    @Column(name = "maxexperiencelevel", length = 255)
    private String maxexperiencelevel;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dealrequirementsDeals" }, allowSetters = true)
    private Deals deal;

    @OneToMany(mappedBy = "dealrequirement")
    @JsonIgnoreProperties(
        value = { "dealrequirement", "resource", "resourcestatus", "systemstatus", "dealresourceeventlogsMatchingresources" },
        allowSetters = true
    )
    private Set<DealRequirementMatchingResources> dealrequirementmatchingresourcesDealrequirements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealRequirements id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealreqidentifier() {
        return this.dealreqidentifier;
    }

    public DealRequirements dealreqidentifier(String dealreqidentifier) {
        this.setDealreqidentifier(dealreqidentifier);
        return this;
    }

    public void setDealreqidentifier(String dealreqidentifier) {
        this.dealreqidentifier = dealreqidentifier;
    }

    public String getCompetencyname() {
        return this.competencyname;
    }

    public DealRequirements competencyname(String competencyname) {
        this.setCompetencyname(competencyname);
        return this;
    }

    public void setCompetencyname(String competencyname) {
        this.competencyname = competencyname;
    }

    public String getSkills() {
        return this.skills;
    }

    public DealRequirements skills(String skills) {
        this.setSkills(skills);
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Double getResourcerequired() {
        return this.resourcerequired;
    }

    public DealRequirements resourcerequired(Double resourcerequired) {
        this.setResourcerequired(resourcerequired);
        return this;
    }

    public void setResourcerequired(Double resourcerequired) {
        this.resourcerequired = resourcerequired;
    }

    public String getMinexperiencelevel() {
        return this.minexperiencelevel;
    }

    public DealRequirements minexperiencelevel(String minexperiencelevel) {
        this.setMinexperiencelevel(minexperiencelevel);
        return this;
    }

    public void setMinexperiencelevel(String minexperiencelevel) {
        this.minexperiencelevel = minexperiencelevel;
    }

    public String getMaxexperiencelevel() {
        return this.maxexperiencelevel;
    }

    public DealRequirements maxexperiencelevel(String maxexperiencelevel) {
        this.setMaxexperiencelevel(maxexperiencelevel);
        return this;
    }

    public void setMaxexperiencelevel(String maxexperiencelevel) {
        this.maxexperiencelevel = maxexperiencelevel;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealRequirements createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DealRequirements updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public DealRequirements deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Deals getDeal() {
        return this.deal;
    }

    public void setDeal(Deals deals) {
        this.deal = deals;
    }

    public DealRequirements deal(Deals deals) {
        this.setDeal(deals);
        return this;
    }

    public Set<DealRequirementMatchingResources> getDealrequirementmatchingresourcesDealrequirements() {
        return this.dealrequirementmatchingresourcesDealrequirements;
    }

    public void setDealrequirementmatchingresourcesDealrequirements(
        Set<DealRequirementMatchingResources> dealRequirementMatchingResources
    ) {
        if (this.dealrequirementmatchingresourcesDealrequirements != null) {
            this.dealrequirementmatchingresourcesDealrequirements.forEach(i -> i.setDealrequirement(null));
        }
        if (dealRequirementMatchingResources != null) {
            dealRequirementMatchingResources.forEach(i -> i.setDealrequirement(this));
        }
        this.dealrequirementmatchingresourcesDealrequirements = dealRequirementMatchingResources;
    }

    public DealRequirements dealrequirementmatchingresourcesDealrequirements(
        Set<DealRequirementMatchingResources> dealRequirementMatchingResources
    ) {
        this.setDealrequirementmatchingresourcesDealrequirements(dealRequirementMatchingResources);
        return this;
    }

    public DealRequirements addDealrequirementmatchingresourcesDealrequirement(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesDealrequirements.add(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setDealrequirement(this);
        return this;
    }

    public DealRequirements removeDealrequirementmatchingresourcesDealrequirement(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesDealrequirements.remove(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setDealrequirement(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealRequirements)) {
            return false;
        }
        return id != null && id.equals(((DealRequirements) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealRequirements{" +
            "id=" + getId() +
            ", dealreqidentifier='" + getDealreqidentifier() + "'" +
            ", competencyname='" + getCompetencyname() + "'" +
            ", skills='" + getSkills() + "'" +
            ", resourcerequired=" + getResourcerequired() +
            ", minexperiencelevel='" + getMinexperiencelevel() + "'" +
            ", maxexperiencelevel='" + getMaxexperiencelevel() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
