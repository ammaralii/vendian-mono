package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PcStatuses.
 */
@Entity
@Table(name = "pc_statuses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcStatuses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "jhi_group", length = 100, nullable = false)
    private String group;

    @Size(max = 100)
    @Column(name = "system_key", length = 100)
    private String systemKey;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @OneToMany(mappedBy = "status")
    @JsonIgnoreProperties(
        value = {
            "status",
            "pcemployeerating",
            "employee",
            "pcraterattributesRatings",
            "pcratingstrainingsRatings",
            "usergoalraterattributesRatings",
        },
        allowSetters = true
    )
    private Set<PcRatings> pcratingsStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PcStatuses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PcStatuses name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return this.group;
    }

    public PcStatuses group(String group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSystemKey() {
        return this.systemKey;
    }

    public PcStatuses systemKey(String systemKey) {
        this.setSystemKey(systemKey);
        return this;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PcStatuses createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PcStatuses updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PcStatuses deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PcStatuses version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<PcRatings> getPcratingsStatuses() {
        return this.pcratingsStatuses;
    }

    public void setPcratingsStatuses(Set<PcRatings> pcRatings) {
        if (this.pcratingsStatuses != null) {
            this.pcratingsStatuses.forEach(i -> i.setStatus(null));
        }
        if (pcRatings != null) {
            pcRatings.forEach(i -> i.setStatus(this));
        }
        this.pcratingsStatuses = pcRatings;
    }

    public PcStatuses pcratingsStatuses(Set<PcRatings> pcRatings) {
        this.setPcratingsStatuses(pcRatings);
        return this;
    }

    public PcStatuses addPcratingsStatus(PcRatings pcRatings) {
        this.pcratingsStatuses.add(pcRatings);
        pcRatings.setStatus(this);
        return this;
    }

    public PcStatuses removePcratingsStatus(PcRatings pcRatings) {
        this.pcratingsStatuses.remove(pcRatings);
        pcRatings.setStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PcStatuses)) {
            return false;
        }
        return id != null && id.equals(((PcStatuses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcStatuses{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", group='" + getGroup() + "'" +
            ", systemKey='" + getSystemKey() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
