package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserGoalRaterAttributes.
 */
@Entity
@Table(name = "user_goal_rater_attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserGoalRaterAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "ugra_rating")
    private byte[] ugraRating;

    @Column(name = "ugra_rating_content_type")
    private String ugraRatingContentType;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "referenceGoal", "usergoalraterattributesUsergoals" }, allowSetters = true)
    private UserGoals usergoal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserGoalRaterAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getUgraRating() {
        return this.ugraRating;
    }

    public UserGoalRaterAttributes ugraRating(byte[] ugraRating) {
        this.setUgraRating(ugraRating);
        return this;
    }

    public void setUgraRating(byte[] ugraRating) {
        this.ugraRating = ugraRating;
    }

    public String getUgraRatingContentType() {
        return this.ugraRatingContentType;
    }

    public UserGoalRaterAttributes ugraRatingContentType(String ugraRatingContentType) {
        this.ugraRatingContentType = ugraRatingContentType;
        return this;
    }

    public void setUgraRatingContentType(String ugraRatingContentType) {
        this.ugraRatingContentType = ugraRatingContentType;
    }

    public byte[] getComment() {
        return this.comment;
    }

    public UserGoalRaterAttributes comment(byte[] comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public String getCommentContentType() {
        return this.commentContentType;
    }

    public UserGoalRaterAttributes commentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
        return this;
    }

    public void setCommentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserGoalRaterAttributes createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UserGoalRaterAttributes updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public UserGoalRaterAttributes deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public UserGoalRaterAttributes version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PcRatings getRating() {
        return this.rating;
    }

    public void setRating(PcRatings pcRatings) {
        this.rating = pcRatings;
    }

    public UserGoalRaterAttributes rating(PcRatings pcRatings) {
        this.setRating(pcRatings);
        return this;
    }

    public UserGoals getUsergoal() {
        return this.usergoal;
    }

    public void setUsergoal(UserGoals userGoals) {
        this.usergoal = userGoals;
    }

    public UserGoalRaterAttributes usergoal(UserGoals userGoals) {
        this.setUsergoal(userGoals);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGoalRaterAttributes)) {
            return false;
        }
        return id != null && id.equals(((UserGoalRaterAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGoalRaterAttributes{" +
            "id=" + getId() +
            ", ugraRating='" + getUgraRating() + "'" +
            ", ugraRatingContentType='" + getUgraRatingContentType() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentContentType='" + getCommentContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
