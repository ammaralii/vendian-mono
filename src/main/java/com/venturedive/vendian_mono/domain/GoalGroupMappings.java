package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GoalGroupMappings.
 */
@Entity
@Table(name = "goal_group_mappings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoalGroupMappings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "weightage", nullable = false)
    private Integer weightage;

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
    @JsonIgnoreProperties(value = { "goalgroupmappingsGoalgroups" }, allowSetters = true)
    private GoalGroups goalGroup;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "goalgroupmappingsGoals", "usergoalsReferencegoals" }, allowSetters = true)
    private Goals goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GoalGroupMappings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeightage() {
        return this.weightage;
    }

    public GoalGroupMappings weightage(Integer weightage) {
        this.setWeightage(weightage);
        return this;
    }

    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public GoalGroupMappings createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public GoalGroupMappings updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public GoalGroupMappings deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public GoalGroupMappings version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public GoalGroups getGoalGroup() {
        return this.goalGroup;
    }

    public void setGoalGroup(GoalGroups goalGroups) {
        this.goalGroup = goalGroups;
    }

    public GoalGroupMappings goalGroup(GoalGroups goalGroups) {
        this.setGoalGroup(goalGroups);
        return this;
    }

    public Goals getGoal() {
        return this.goal;
    }

    public void setGoal(Goals goals) {
        this.goal = goals;
    }

    public GoalGroupMappings goal(Goals goals) {
        this.setGoal(goals);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoalGroupMappings)) {
            return false;
        }
        return id != null && id.equals(((GoalGroupMappings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalGroupMappings{" +
            "id=" + getId() +
            ", weightage=" + getWeightage() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
