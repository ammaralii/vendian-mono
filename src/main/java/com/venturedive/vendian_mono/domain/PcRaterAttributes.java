package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PcRaterAttributes.
 */
@Entity
@Table(name = "pc_rater_attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRaterAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "pc_rating")
    private byte[] pcRating;

    @Column(name = "pc_rating_content_type")
    private String pcRatingContentType;

    @Lob
    @Column(name = "comment")
    private byte[] comment;

    @Column(name = "comment_content_type")
    private String commentContentType;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "attribute", "ratingAttribute", "pcraterattributesRatingattributemappings" }, allowSetters = true)
    private RatingAttributeMappings ratingAttributeMapping;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pcraterattributesRatingoptions" }, allowSetters = true)
    private RatingOptions ratingOption;

    @ManyToOne(optional = false)
    @NotNull
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
    private PcRatings rating;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PcRaterAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPcRating() {
        return this.pcRating;
    }

    public PcRaterAttributes pcRating(byte[] pcRating) {
        this.setPcRating(pcRating);
        return this;
    }

    public void setPcRating(byte[] pcRating) {
        this.pcRating = pcRating;
    }

    public String getPcRatingContentType() {
        return this.pcRatingContentType;
    }

    public PcRaterAttributes pcRatingContentType(String pcRatingContentType) {
        this.pcRatingContentType = pcRatingContentType;
        return this;
    }

    public void setPcRatingContentType(String pcRatingContentType) {
        this.pcRatingContentType = pcRatingContentType;
    }

    public byte[] getComment() {
        return this.comment;
    }

    public PcRaterAttributes comment(byte[] comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public String getCommentContentType() {
        return this.commentContentType;
    }

    public PcRaterAttributes commentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
        return this;
    }

    public void setCommentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PcRaterAttributes createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PcRaterAttributes updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PcRaterAttributes deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PcRaterAttributes version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public RatingAttributeMappings getRatingAttributeMapping() {
        return this.ratingAttributeMapping;
    }

    public void setRatingAttributeMapping(RatingAttributeMappings ratingAttributeMappings) {
        this.ratingAttributeMapping = ratingAttributeMappings;
    }

    public PcRaterAttributes ratingAttributeMapping(RatingAttributeMappings ratingAttributeMappings) {
        this.setRatingAttributeMapping(ratingAttributeMappings);
        return this;
    }

    public RatingOptions getRatingOption() {
        return this.ratingOption;
    }

    public void setRatingOption(RatingOptions ratingOptions) {
        this.ratingOption = ratingOptions;
    }

    public PcRaterAttributes ratingOption(RatingOptions ratingOptions) {
        this.setRatingOption(ratingOptions);
        return this;
    }

    public PcRatings getRating() {
        return this.rating;
    }

    public void setRating(PcRatings pcRatings) {
        this.rating = pcRatings;
    }

    public PcRaterAttributes rating(PcRatings pcRatings) {
        this.setRating(pcRatings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PcRaterAttributes)) {
            return false;
        }
        return id != null && id.equals(((PcRaterAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRaterAttributes{" +
            "id=" + getId() +
            ", pcRating='" + getPcRating() + "'" +
            ", pcRatingContentType='" + getPcRatingContentType() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentContentType='" + getCommentContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
