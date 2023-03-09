package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealRequirementMatchingResources.
 */
@Entity
@Table(name = "deal_requirement_matching_resources")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealRequirementMatchingResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "deal", "dealrequirementmatchingresourcesDealrequirements" }, allowSetters = true)
    private DealRequirements dealrequirement;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "dealrequirementmatchingresourcesResources", "dealresourceskillsResources" },
        allowSetters = true
    )
    private DealResources resource;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "dealrequirementmatchingresourcesResourcestatuses",
            "dealrequirementmatchingresourcesSystemstatuses",
            "dealresourceeventlogsResourcestatuses",
            "dealresourceeventlogsSystemstatuses",
        },
        allowSetters = true
    )
    private DealResourceStatus resourcestatus;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "dealrequirementmatchingresourcesResourcestatuses",
            "dealrequirementmatchingresourcesSystemstatuses",
            "dealresourceeventlogsResourcestatuses",
            "dealresourceeventlogsSystemstatuses",
        },
        allowSetters = true
    )
    private DealResourceStatus systemstatus;

    @OneToMany(mappedBy = "matchingresource")
    @JsonIgnoreProperties(value = { "matchingresource", "resourcestatus", "systemstatus" }, allowSetters = true)
    private Set<DealResourceEventLogs> dealresourceeventlogsMatchingresources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealRequirementMatchingResources id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return this.comments;
    }

    public DealRequirementMatchingResources comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealRequirementMatchingResources createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DealRequirementMatchingResources updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public DealRequirementMatchingResources deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public DealRequirements getDealrequirement() {
        return this.dealrequirement;
    }

    public void setDealrequirement(DealRequirements dealRequirements) {
        this.dealrequirement = dealRequirements;
    }

    public DealRequirementMatchingResources dealrequirement(DealRequirements dealRequirements) {
        this.setDealrequirement(dealRequirements);
        return this;
    }

    public DealResources getResource() {
        return this.resource;
    }

    public void setResource(DealResources dealResources) {
        this.resource = dealResources;
    }

    public DealRequirementMatchingResources resource(DealResources dealResources) {
        this.setResource(dealResources);
        return this;
    }

    public DealResourceStatus getResourcestatus() {
        return this.resourcestatus;
    }

    public void setResourcestatus(DealResourceStatus dealResourceStatus) {
        this.resourcestatus = dealResourceStatus;
    }

    public DealRequirementMatchingResources resourcestatus(DealResourceStatus dealResourceStatus) {
        this.setResourcestatus(dealResourceStatus);
        return this;
    }

    public DealResourceStatus getSystemstatus() {
        return this.systemstatus;
    }

    public void setSystemstatus(DealResourceStatus dealResourceStatus) {
        this.systemstatus = dealResourceStatus;
    }

    public DealRequirementMatchingResources systemstatus(DealResourceStatus dealResourceStatus) {
        this.setSystemstatus(dealResourceStatus);
        return this;
    }

    public Set<DealResourceEventLogs> getDealresourceeventlogsMatchingresources() {
        return this.dealresourceeventlogsMatchingresources;
    }

    public void setDealresourceeventlogsMatchingresources(Set<DealResourceEventLogs> dealResourceEventLogs) {
        if (this.dealresourceeventlogsMatchingresources != null) {
            this.dealresourceeventlogsMatchingresources.forEach(i -> i.setMatchingresource(null));
        }
        if (dealResourceEventLogs != null) {
            dealResourceEventLogs.forEach(i -> i.setMatchingresource(this));
        }
        this.dealresourceeventlogsMatchingresources = dealResourceEventLogs;
    }

    public DealRequirementMatchingResources dealresourceeventlogsMatchingresources(Set<DealResourceEventLogs> dealResourceEventLogs) {
        this.setDealresourceeventlogsMatchingresources(dealResourceEventLogs);
        return this;
    }

    public DealRequirementMatchingResources addDealresourceeventlogsMatchingresource(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsMatchingresources.add(dealResourceEventLogs);
        dealResourceEventLogs.setMatchingresource(this);
        return this;
    }

    public DealRequirementMatchingResources removeDealresourceeventlogsMatchingresource(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsMatchingresources.remove(dealResourceEventLogs);
        dealResourceEventLogs.setMatchingresource(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealRequirementMatchingResources)) {
            return false;
        }
        return id != null && id.equals(((DealRequirementMatchingResources) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealRequirementMatchingResources{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
