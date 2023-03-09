package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RatingAttributes.
 */
@Entity
@Table(name = "rating_attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "ra_rating")
    private byte[] raRating;

    @Column(name = "ra_rating_content_type")
    private String raRatingContentType;

    @Lob
    @Column(name = "comment")
    private byte[] comment;

    @Column(name = "comment_content_type")
    private String commentContentType;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rater", "ratingattributesRatings", "performancecycles", "projectcycles" }, allowSetters = true)
    private Ratings rating;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "raterattribute", "attributesFor", "attributesBy", "ratingattributesRaterattributemappings" },
        allowSetters = true
    )
    private RaterAttributeMappings raterattributemapping;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RatingAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRaRating() {
        return this.raRating;
    }

    public RatingAttributes raRating(byte[] raRating) {
        this.setRaRating(raRating);
        return this;
    }

    public void setRaRating(byte[] raRating) {
        this.raRating = raRating;
    }

    public String getRaRatingContentType() {
        return this.raRatingContentType;
    }

    public RatingAttributes raRatingContentType(String raRatingContentType) {
        this.raRatingContentType = raRatingContentType;
        return this;
    }

    public void setRaRatingContentType(String raRatingContentType) {
        this.raRatingContentType = raRatingContentType;
    }

    public byte[] getComment() {
        return this.comment;
    }

    public RatingAttributes comment(byte[] comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public String getCommentContentType() {
        return this.commentContentType;
    }

    public RatingAttributes commentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
        return this;
    }

    public void setCommentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public RatingAttributes createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public RatingAttributes updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Ratings getRating() {
        return this.rating;
    }

    public void setRating(Ratings ratings) {
        this.rating = ratings;
    }

    public RatingAttributes rating(Ratings ratings) {
        this.setRating(ratings);
        return this;
    }

    public RaterAttributeMappings getRaterattributemapping() {
        return this.raterattributemapping;
    }

    public void setRaterattributemapping(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemapping = raterAttributeMappings;
    }

    public RatingAttributes raterattributemapping(RaterAttributeMappings raterAttributeMappings) {
        this.setRaterattributemapping(raterAttributeMappings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingAttributes)) {
            return false;
        }
        return id != null && id.equals(((RatingAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingAttributes{" +
            "id=" + getId() +
            ", raRating='" + getRaRating() + "'" +
            ", raRatingContentType='" + getRaRatingContentType() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentContentType='" + getCommentContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
