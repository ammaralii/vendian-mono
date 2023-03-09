package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PcRatingAttributes.
 */
@Entity
@Table(name = "pc_rating_attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRatingAttributes implements Serializable {

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

    @Size(max = 255)
    @Column(name = "sub_category", length = 255)
    private String subCategory;

    @Size(max = 65535)
    @Column(name = "description", length = 65535)
    private String description;

    @OneToMany(mappedBy = "ratingAttribute")
    @JsonIgnoreProperties(value = { "category", "ratingAttribute" }, allowSetters = true)
    private Set<PcRatingAttributesCategories> pcratingattributescategoriesRatingattributes = new HashSet<>();

    @OneToMany(mappedBy = "ratingAttribute")
    @JsonIgnoreProperties(value = { "attribute", "ratingAttribute", "pcraterattributesRatingattributemappings" }, allowSetters = true)
    private Set<RatingAttributeMappings> ratingattributemappingsRatingattributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PcRatingAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PcRatingAttributes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public PcRatingAttributes effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PcRatingAttributes createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PcRatingAttributes updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public PcRatingAttributes endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PcRatingAttributes version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public PcRatingAttributes subCategory(String subCategory) {
        this.setSubCategory(subCategory);
        return this;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return this.description;
    }

    public PcRatingAttributes description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PcRatingAttributesCategories> getPcratingattributescategoriesRatingattributes() {
        return this.pcratingattributescategoriesRatingattributes;
    }

    public void setPcratingattributescategoriesRatingattributes(Set<PcRatingAttributesCategories> pcRatingAttributesCategories) {
        if (this.pcratingattributescategoriesRatingattributes != null) {
            this.pcratingattributescategoriesRatingattributes.forEach(i -> i.setRatingAttribute(null));
        }
        if (pcRatingAttributesCategories != null) {
            pcRatingAttributesCategories.forEach(i -> i.setRatingAttribute(this));
        }
        this.pcratingattributescategoriesRatingattributes = pcRatingAttributesCategories;
    }

    public PcRatingAttributes pcratingattributescategoriesRatingattributes(Set<PcRatingAttributesCategories> pcRatingAttributesCategories) {
        this.setPcratingattributescategoriesRatingattributes(pcRatingAttributesCategories);
        return this;
    }

    public PcRatingAttributes addPcratingattributescategoriesRatingattribute(PcRatingAttributesCategories pcRatingAttributesCategories) {
        this.pcratingattributescategoriesRatingattributes.add(pcRatingAttributesCategories);
        pcRatingAttributesCategories.setRatingAttribute(this);
        return this;
    }

    public PcRatingAttributes removePcratingattributescategoriesRatingattribute(PcRatingAttributesCategories pcRatingAttributesCategories) {
        this.pcratingattributescategoriesRatingattributes.remove(pcRatingAttributesCategories);
        pcRatingAttributesCategories.setRatingAttribute(null);
        return this;
    }

    public Set<RatingAttributeMappings> getRatingattributemappingsRatingattributes() {
        return this.ratingattributemappingsRatingattributes;
    }

    public void setRatingattributemappingsRatingattributes(Set<RatingAttributeMappings> ratingAttributeMappings) {
        if (this.ratingattributemappingsRatingattributes != null) {
            this.ratingattributemappingsRatingattributes.forEach(i -> i.setRatingAttribute(null));
        }
        if (ratingAttributeMappings != null) {
            ratingAttributeMappings.forEach(i -> i.setRatingAttribute(this));
        }
        this.ratingattributemappingsRatingattributes = ratingAttributeMappings;
    }

    public PcRatingAttributes ratingattributemappingsRatingattributes(Set<RatingAttributeMappings> ratingAttributeMappings) {
        this.setRatingattributemappingsRatingattributes(ratingAttributeMappings);
        return this;
    }

    public PcRatingAttributes addRatingattributemappingsRatingattribute(RatingAttributeMappings ratingAttributeMappings) {
        this.ratingattributemappingsRatingattributes.add(ratingAttributeMappings);
        ratingAttributeMappings.setRatingAttribute(this);
        return this;
    }

    public PcRatingAttributes removeRatingattributemappingsRatingattribute(RatingAttributeMappings ratingAttributeMappings) {
        this.ratingattributemappingsRatingattributes.remove(ratingAttributeMappings);
        ratingAttributeMappings.setRatingAttribute(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PcRatingAttributes)) {
            return false;
        }
        return id != null && id.equals(((PcRatingAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRatingAttributes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            ", subCategory='" + getSubCategory() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
