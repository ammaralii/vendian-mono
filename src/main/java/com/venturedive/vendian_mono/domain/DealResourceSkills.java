package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A DealResourceSkills.
 */
@Entity
@Table(name = "deal_resource_skills")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourceSkills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "dealrequirementmatchingresourcesResources", "dealresourceskillsResources" },
        allowSetters = true
    )
    private DealResources resource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dealresourceskillsSkills", "employeeskillsSkills" }, allowSetters = true)
    private Skills skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealResourceSkills id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealResourceSkills createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DealResourceSkills updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public DealResourceSkills deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public DealResources getResource() {
        return this.resource;
    }

    public void setResource(DealResources dealResources) {
        this.resource = dealResources;
    }

    public DealResourceSkills resource(DealResources dealResources) {
        this.setResource(dealResources);
        return this;
    }

    public Skills getSkill() {
        return this.skill;
    }

    public void setSkill(Skills skills) {
        this.skill = skills;
    }

    public DealResourceSkills skill(Skills skills) {
        this.setSkill(skills);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealResourceSkills)) {
            return false;
        }
        return id != null && id.equals(((DealResourceSkills) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourceSkills{" +
            "id=" + getId() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
