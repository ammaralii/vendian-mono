package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RatingCategories.
 */
@Entity
@Table(name = "rating_categories")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingCategories implements Serializable {

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
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = { "category", "ratingAttribute" }, allowSetters = true)
    private Set<PcRatingAttributesCategories> pcratingattributescategoriesCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RatingCategories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RatingCategories name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public RatingCategories effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public RatingCategories createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public RatingCategories updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public RatingCategories endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public RatingCategories version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<PcRatingAttributesCategories> getPcratingattributescategoriesCategories() {
        return this.pcratingattributescategoriesCategories;
    }

    public void setPcratingattributescategoriesCategories(Set<PcRatingAttributesCategories> pcRatingAttributesCategories) {
        if (this.pcratingattributescategoriesCategories != null) {
            this.pcratingattributescategoriesCategories.forEach(i -> i.setCategory(null));
        }
        if (pcRatingAttributesCategories != null) {
            pcRatingAttributesCategories.forEach(i -> i.setCategory(this));
        }
        this.pcratingattributescategoriesCategories = pcRatingAttributesCategories;
    }

    public RatingCategories pcratingattributescategoriesCategories(Set<PcRatingAttributesCategories> pcRatingAttributesCategories) {
        this.setPcratingattributescategoriesCategories(pcRatingAttributesCategories);
        return this;
    }

    public RatingCategories addPcratingattributescategoriesCategory(PcRatingAttributesCategories pcRatingAttributesCategories) {
        this.pcratingattributescategoriesCategories.add(pcRatingAttributesCategories);
        pcRatingAttributesCategories.setCategory(this);
        return this;
    }

    public RatingCategories removePcratingattributescategoriesCategory(PcRatingAttributesCategories pcRatingAttributesCategories) {
        this.pcratingattributescategoriesCategories.remove(pcRatingAttributesCategories);
        pcRatingAttributesCategories.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingCategories)) {
            return false;
        }
        return id != null && id.equals(((RatingCategories) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingCategories{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
