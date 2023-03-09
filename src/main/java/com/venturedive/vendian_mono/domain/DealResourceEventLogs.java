package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealResourceEventLogs.
 */
@Entity
@Table(name = "deal_resource_event_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourceEventLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "comments", length = 500, nullable = false)
    private String comments;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "dealrequirement", "resource", "resourcestatus", "systemstatus", "dealresourceeventlogsMatchingresources" },
        allowSetters = true
    )
    private DealRequirementMatchingResources matchingresource;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealResourceEventLogs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return this.comments;
    }

    public DealResourceEventLogs comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealResourceEventLogs createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public DealRequirementMatchingResources getMatchingresource() {
        return this.matchingresource;
    }

    public void setMatchingresource(DealRequirementMatchingResources dealRequirementMatchingResources) {
        this.matchingresource = dealRequirementMatchingResources;
    }

    public DealResourceEventLogs matchingresource(DealRequirementMatchingResources dealRequirementMatchingResources) {
        this.setMatchingresource(dealRequirementMatchingResources);
        return this;
    }

    public DealResourceStatus getResourcestatus() {
        return this.resourcestatus;
    }

    public void setResourcestatus(DealResourceStatus dealResourceStatus) {
        this.resourcestatus = dealResourceStatus;
    }

    public DealResourceEventLogs resourcestatus(DealResourceStatus dealResourceStatus) {
        this.setResourcestatus(dealResourceStatus);
        return this;
    }

    public DealResourceStatus getSystemstatus() {
        return this.systemstatus;
    }

    public void setSystemstatus(DealResourceStatus dealResourceStatus) {
        this.systemstatus = dealResourceStatus;
    }

    public DealResourceEventLogs systemstatus(DealResourceStatus dealResourceStatus) {
        this.setSystemstatus(dealResourceStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealResourceEventLogs)) {
            return false;
        }
        return id != null && id.equals(((DealResourceEventLogs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourceEventLogs{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
