package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealResourceStatus.
 */
@Entity
@Table(name = "deal_resource_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "displayname", length = 255, nullable = false)
    private String displayname;

    @NotNull
    @Size(max = 255)
    @Column(name = "jhi_group", length = 255, nullable = false)
    private String group;

    @NotNull
    @Size(max = 255)
    @Column(name = "system_key", length = 255, nullable = false, unique = true)
    private String systemKey;

    @Column(name = "effectivedate")
    private Instant effectivedate;

    @Column(name = "enddate")
    private Instant enddate;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "resourcestatus")
    @JsonIgnoreProperties(
        value = { "dealrequirement", "resource", "resourcestatus", "systemstatus", "dealresourceeventlogsMatchingresources" },
        allowSetters = true
    )
    private Set<DealRequirementMatchingResources> dealrequirementmatchingresourcesResourcestatuses = new HashSet<>();

    @OneToMany(mappedBy = "systemstatus")
    @JsonIgnoreProperties(
        value = { "dealrequirement", "resource", "resourcestatus", "systemstatus", "dealresourceeventlogsMatchingresources" },
        allowSetters = true
    )
    private Set<DealRequirementMatchingResources> dealrequirementmatchingresourcesSystemstatuses = new HashSet<>();

    @OneToMany(mappedBy = "resourcestatus")
    @JsonIgnoreProperties(value = { "matchingresource", "resourcestatus", "systemstatus" }, allowSetters = true)
    private Set<DealResourceEventLogs> dealresourceeventlogsResourcestatuses = new HashSet<>();

    @OneToMany(mappedBy = "systemstatus")
    @JsonIgnoreProperties(value = { "matchingresource", "resourcestatus", "systemstatus" }, allowSetters = true)
    private Set<DealResourceEventLogs> dealresourceeventlogsSystemstatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealResourceStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public DealResourceStatus displayname(String displayname) {
        this.setDisplayname(displayname);
        return this;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getGroup() {
        return this.group;
    }

    public DealResourceStatus group(String group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSystemKey() {
        return this.systemKey;
    }

    public DealResourceStatus systemKey(String systemKey) {
        this.setSystemKey(systemKey);
        return this;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public Instant getEffectivedate() {
        return this.effectivedate;
    }

    public DealResourceStatus effectivedate(Instant effectivedate) {
        this.setEffectivedate(effectivedate);
        return this;
    }

    public void setEffectivedate(Instant effectivedate) {
        this.effectivedate = effectivedate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public DealResourceStatus enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealResourceStatus createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DealResourceStatus updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<DealRequirementMatchingResources> getDealrequirementmatchingresourcesResourcestatuses() {
        return this.dealrequirementmatchingresourcesResourcestatuses;
    }

    public void setDealrequirementmatchingresourcesResourcestatuses(
        Set<DealRequirementMatchingResources> dealRequirementMatchingResources
    ) {
        if (this.dealrequirementmatchingresourcesResourcestatuses != null) {
            this.dealrequirementmatchingresourcesResourcestatuses.forEach(i -> i.setResourcestatus(null));
        }
        if (dealRequirementMatchingResources != null) {
            dealRequirementMatchingResources.forEach(i -> i.setResourcestatus(this));
        }
        this.dealrequirementmatchingresourcesResourcestatuses = dealRequirementMatchingResources;
    }

    public DealResourceStatus dealrequirementmatchingresourcesResourcestatuses(
        Set<DealRequirementMatchingResources> dealRequirementMatchingResources
    ) {
        this.setDealrequirementmatchingresourcesResourcestatuses(dealRequirementMatchingResources);
        return this;
    }

    public DealResourceStatus addDealrequirementmatchingresourcesResourcestatus(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesResourcestatuses.add(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setResourcestatus(this);
        return this;
    }

    public DealResourceStatus removeDealrequirementmatchingresourcesResourcestatus(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesResourcestatuses.remove(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setResourcestatus(null);
        return this;
    }

    public Set<DealRequirementMatchingResources> getDealrequirementmatchingresourcesSystemstatuses() {
        return this.dealrequirementmatchingresourcesSystemstatuses;
    }

    public void setDealrequirementmatchingresourcesSystemstatuses(Set<DealRequirementMatchingResources> dealRequirementMatchingResources) {
        if (this.dealrequirementmatchingresourcesSystemstatuses != null) {
            this.dealrequirementmatchingresourcesSystemstatuses.forEach(i -> i.setSystemstatus(null));
        }
        if (dealRequirementMatchingResources != null) {
            dealRequirementMatchingResources.forEach(i -> i.setSystemstatus(this));
        }
        this.dealrequirementmatchingresourcesSystemstatuses = dealRequirementMatchingResources;
    }

    public DealResourceStatus dealrequirementmatchingresourcesSystemstatuses(
        Set<DealRequirementMatchingResources> dealRequirementMatchingResources
    ) {
        this.setDealrequirementmatchingresourcesSystemstatuses(dealRequirementMatchingResources);
        return this;
    }

    public DealResourceStatus addDealrequirementmatchingresourcesSystemstatus(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesSystemstatuses.add(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setSystemstatus(this);
        return this;
    }

    public DealResourceStatus removeDealrequirementmatchingresourcesSystemstatus(
        DealRequirementMatchingResources dealRequirementMatchingResources
    ) {
        this.dealrequirementmatchingresourcesSystemstatuses.remove(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setSystemstatus(null);
        return this;
    }

    public Set<DealResourceEventLogs> getDealresourceeventlogsResourcestatuses() {
        return this.dealresourceeventlogsResourcestatuses;
    }

    public void setDealresourceeventlogsResourcestatuses(Set<DealResourceEventLogs> dealResourceEventLogs) {
        if (this.dealresourceeventlogsResourcestatuses != null) {
            this.dealresourceeventlogsResourcestatuses.forEach(i -> i.setResourcestatus(null));
        }
        if (dealResourceEventLogs != null) {
            dealResourceEventLogs.forEach(i -> i.setResourcestatus(this));
        }
        this.dealresourceeventlogsResourcestatuses = dealResourceEventLogs;
    }

    public DealResourceStatus dealresourceeventlogsResourcestatuses(Set<DealResourceEventLogs> dealResourceEventLogs) {
        this.setDealresourceeventlogsResourcestatuses(dealResourceEventLogs);
        return this;
    }

    public DealResourceStatus addDealresourceeventlogsResourcestatus(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsResourcestatuses.add(dealResourceEventLogs);
        dealResourceEventLogs.setResourcestatus(this);
        return this;
    }

    public DealResourceStatus removeDealresourceeventlogsResourcestatus(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsResourcestatuses.remove(dealResourceEventLogs);
        dealResourceEventLogs.setResourcestatus(null);
        return this;
    }

    public Set<DealResourceEventLogs> getDealresourceeventlogsSystemstatuses() {
        return this.dealresourceeventlogsSystemstatuses;
    }

    public void setDealresourceeventlogsSystemstatuses(Set<DealResourceEventLogs> dealResourceEventLogs) {
        if (this.dealresourceeventlogsSystemstatuses != null) {
            this.dealresourceeventlogsSystemstatuses.forEach(i -> i.setSystemstatus(null));
        }
        if (dealResourceEventLogs != null) {
            dealResourceEventLogs.forEach(i -> i.setSystemstatus(this));
        }
        this.dealresourceeventlogsSystemstatuses = dealResourceEventLogs;
    }

    public DealResourceStatus dealresourceeventlogsSystemstatuses(Set<DealResourceEventLogs> dealResourceEventLogs) {
        this.setDealresourceeventlogsSystemstatuses(dealResourceEventLogs);
        return this;
    }

    public DealResourceStatus addDealresourceeventlogsSystemstatus(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsSystemstatuses.add(dealResourceEventLogs);
        dealResourceEventLogs.setSystemstatus(this);
        return this;
    }

    public DealResourceStatus removeDealresourceeventlogsSystemstatus(DealResourceEventLogs dealResourceEventLogs) {
        this.dealresourceeventlogsSystemstatuses.remove(dealResourceEventLogs);
        dealResourceEventLogs.setSystemstatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealResourceStatus)) {
            return false;
        }
        return id != null && id.equals(((DealResourceStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourceStatus{" +
            "id=" + getId() +
            ", displayname='" + getDisplayname() + "'" +
            ", group='" + getGroup() + "'" +
            ", systemKey='" + getSystemKey() + "'" +
            ", effectivedate='" + getEffectivedate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
