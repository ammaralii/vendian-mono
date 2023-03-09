package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RatingOptions.
 */
@Entity
@Table(name = "rating_options")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 65535)
    @Column(name = "description", length = 65535)
    private String description;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private Float from;

    @Column(name = "jhi_to")
    private Float to;

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

    @OneToMany(mappedBy = "ratingOption")
    @JsonIgnoreProperties(value = { "ratingAttributeMapping", "ratingOption", "rating" }, allowSetters = true)
    private Set<PcRaterAttributes> pcraterattributesRatingoptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RatingOptions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RatingOptions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public RatingOptions description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getFrom() {
        return this.from;
    }

    public RatingOptions from(Float from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(Float from) {
        this.from = from;
    }

    public Float getTo() {
        return this.to;
    }

    public RatingOptions to(Float to) {
        this.setTo(to);
        return this;
    }

    public void setTo(Float to) {
        this.to = to;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public RatingOptions effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public RatingOptions createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public RatingOptions updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public RatingOptions endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public RatingOptions version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<PcRaterAttributes> getPcraterattributesRatingoptions() {
        return this.pcraterattributesRatingoptions;
    }

    public void setPcraterattributesRatingoptions(Set<PcRaterAttributes> pcRaterAttributes) {
        if (this.pcraterattributesRatingoptions != null) {
            this.pcraterattributesRatingoptions.forEach(i -> i.setRatingOption(null));
        }
        if (pcRaterAttributes != null) {
            pcRaterAttributes.forEach(i -> i.setRatingOption(this));
        }
        this.pcraterattributesRatingoptions = pcRaterAttributes;
    }

    public RatingOptions pcraterattributesRatingoptions(Set<PcRaterAttributes> pcRaterAttributes) {
        this.setPcraterattributesRatingoptions(pcRaterAttributes);
        return this;
    }

    public RatingOptions addPcraterattributesRatingoption(PcRaterAttributes pcRaterAttributes) {
        this.pcraterattributesRatingoptions.add(pcRaterAttributes);
        pcRaterAttributes.setRatingOption(this);
        return this;
    }

    public RatingOptions removePcraterattributesRatingoption(PcRaterAttributes pcRaterAttributes) {
        this.pcraterattributesRatingoptions.remove(pcRaterAttributes);
        pcRaterAttributes.setRatingOption(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingOptions)) {
            return false;
        }
        return id != null && id.equals(((RatingOptions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingOptions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", from=" + getFrom() +
            ", to=" + getTo() +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
