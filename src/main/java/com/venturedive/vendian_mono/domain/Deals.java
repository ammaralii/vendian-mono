package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Deals.
 */
@Entity
@Table(name = "deals")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "dealnumber", length = 255, nullable = false)
    private String dealnumber;

    @NotNull
    @Size(max = 255)
    @Column(name = "dealname", length = 255, nullable = false)
    private String dealname;

    @Size(max = 255)
    @Column(name = "businessunit", length = 255)
    private String businessunit;

    @NotNull
    @Size(max = 255)
    @Column(name = "clientname", length = 255, nullable = false)
    private String clientname;

    @Size(max = 255)
    @Column(name = "dealowner", length = 255)
    private String dealowner;

    @Size(max = 255)
    @Column(name = "proposaltype", length = 255)
    private String proposaltype;

    @Column(name = "projectid")
    private Integer projectid;

    @NotNull
    @Column(name = "expectedstartdate", nullable = false)
    private Instant expectedstartdate;

    @Size(max = 255)
    @Column(name = "stage", length = 255)
    private String stage;

    @NotNull
    @Column(name = "probability", nullable = false)
    private Double probability;

    @NotNull
    @Column(name = "projectduration", nullable = false)
    private Double projectduration;

    @NotNull
    @Size(max = 9)
    @Column(name = "type", length = 9, nullable = false)
    private String type;

    @NotNull
    @Size(max = 8)
    @Column(name = "status", length = 8, nullable = false)
    private String status;

    @Column(name = "closedat")
    private Instant closedat;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @Column(name = "resourcingenteredinvendians")
    private Boolean resourcingenteredinvendians;

    @OneToMany(mappedBy = "deal")
    @JsonIgnoreProperties(value = { "deal", "dealrequirementmatchingresourcesDealrequirements" }, allowSetters = true)
    private Set<DealRequirements> dealrequirementsDeals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deals id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealnumber() {
        return this.dealnumber;
    }

    public Deals dealnumber(String dealnumber) {
        this.setDealnumber(dealnumber);
        return this;
    }

    public void setDealnumber(String dealnumber) {
        this.dealnumber = dealnumber;
    }

    public String getDealname() {
        return this.dealname;
    }

    public Deals dealname(String dealname) {
        this.setDealname(dealname);
        return this;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getBusinessunit() {
        return this.businessunit;
    }

    public Deals businessunit(String businessunit) {
        this.setBusinessunit(businessunit);
        return this;
    }

    public void setBusinessunit(String businessunit) {
        this.businessunit = businessunit;
    }

    public String getClientname() {
        return this.clientname;
    }

    public Deals clientname(String clientname) {
        this.setClientname(clientname);
        return this;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getDealowner() {
        return this.dealowner;
    }

    public Deals dealowner(String dealowner) {
        this.setDealowner(dealowner);
        return this;
    }

    public void setDealowner(String dealowner) {
        this.dealowner = dealowner;
    }

    public String getProposaltype() {
        return this.proposaltype;
    }

    public Deals proposaltype(String proposaltype) {
        this.setProposaltype(proposaltype);
        return this;
    }

    public void setProposaltype(String proposaltype) {
        this.proposaltype = proposaltype;
    }

    public Integer getProjectid() {
        return this.projectid;
    }

    public Deals projectid(Integer projectid) {
        this.setProjectid(projectid);
        return this;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Instant getExpectedstartdate() {
        return this.expectedstartdate;
    }

    public Deals expectedstartdate(Instant expectedstartdate) {
        this.setExpectedstartdate(expectedstartdate);
        return this;
    }

    public void setExpectedstartdate(Instant expectedstartdate) {
        this.expectedstartdate = expectedstartdate;
    }

    public String getStage() {
        return this.stage;
    }

    public Deals stage(String stage) {
        this.setStage(stage);
        return this;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Double getProbability() {
        return this.probability;
    }

    public Deals probability(Double probability) {
        this.setProbability(probability);
        return this;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getProjectduration() {
        return this.projectduration;
    }

    public Deals projectduration(Double projectduration) {
        this.setProjectduration(projectduration);
        return this;
    }

    public void setProjectduration(Double projectduration) {
        this.projectduration = projectduration;
    }

    public String getType() {
        return this.type;
    }

    public Deals type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public Deals status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getClosedat() {
        return this.closedat;
    }

    public Deals closedat(Instant closedat) {
        this.setClosedat(closedat);
        return this;
    }

    public void setClosedat(Instant closedat) {
        this.closedat = closedat;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Deals createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Deals updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Deals deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Boolean getResourcingenteredinvendians() {
        return this.resourcingenteredinvendians;
    }

    public Deals resourcingenteredinvendians(Boolean resourcingenteredinvendians) {
        this.setResourcingenteredinvendians(resourcingenteredinvendians);
        return this;
    }

    public void setResourcingenteredinvendians(Boolean resourcingenteredinvendians) {
        this.resourcingenteredinvendians = resourcingenteredinvendians;
    }

    public Set<DealRequirements> getDealrequirementsDeals() {
        return this.dealrequirementsDeals;
    }

    public void setDealrequirementsDeals(Set<DealRequirements> dealRequirements) {
        if (this.dealrequirementsDeals != null) {
            this.dealrequirementsDeals.forEach(i -> i.setDeal(null));
        }
        if (dealRequirements != null) {
            dealRequirements.forEach(i -> i.setDeal(this));
        }
        this.dealrequirementsDeals = dealRequirements;
    }

    public Deals dealrequirementsDeals(Set<DealRequirements> dealRequirements) {
        this.setDealrequirementsDeals(dealRequirements);
        return this;
    }

    public Deals addDealrequirementsDeal(DealRequirements dealRequirements) {
        this.dealrequirementsDeals.add(dealRequirements);
        dealRequirements.setDeal(this);
        return this;
    }

    public Deals removeDealrequirementsDeal(DealRequirements dealRequirements) {
        this.dealrequirementsDeals.remove(dealRequirements);
        dealRequirements.setDeal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deals)) {
            return false;
        }
        return id != null && id.equals(((Deals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deals{" +
            "id=" + getId() +
            ", dealnumber='" + getDealnumber() + "'" +
            ", dealname='" + getDealname() + "'" +
            ", businessunit='" + getBusinessunit() + "'" +
            ", clientname='" + getClientname() + "'" +
            ", dealowner='" + getDealowner() + "'" +
            ", proposaltype='" + getProposaltype() + "'" +
            ", projectid=" + getProjectid() +
            ", expectedstartdate='" + getExpectedstartdate() + "'" +
            ", stage='" + getStage() + "'" +
            ", probability=" + getProbability() +
            ", projectduration=" + getProjectduration() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", closedat='" + getClosedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            ", resourcingenteredinvendians='" + getResourcingenteredinvendians() + "'" +
            "}";
    }
}
