package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GoalGroups.
 */
@Entity
@Table(name = "goal_groups")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoalGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 65535)
    @Column(name = "description", length = 65535)
    private String description;

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

    @OneToMany(mappedBy = "goalGroup")
    @JsonIgnoreProperties(value = { "goalGroup", "goal" }, allowSetters = true)
    private Set<GoalGroupMappings> goalgroupmappingsGoalgroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GoalGroups id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public GoalGroups title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public GoalGroups description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public GoalGroups createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public GoalGroups updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public GoalGroups deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public GoalGroups version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<GoalGroupMappings> getGoalgroupmappingsGoalgroups() {
        return this.goalgroupmappingsGoalgroups;
    }

    public void setGoalgroupmappingsGoalgroups(Set<GoalGroupMappings> goalGroupMappings) {
        if (this.goalgroupmappingsGoalgroups != null) {
            this.goalgroupmappingsGoalgroups.forEach(i -> i.setGoalGroup(null));
        }
        if (goalGroupMappings != null) {
            goalGroupMappings.forEach(i -> i.setGoalGroup(this));
        }
        this.goalgroupmappingsGoalgroups = goalGroupMappings;
    }

    public GoalGroups goalgroupmappingsGoalgroups(Set<GoalGroupMappings> goalGroupMappings) {
        this.setGoalgroupmappingsGoalgroups(goalGroupMappings);
        return this;
    }

    public GoalGroups addGoalgroupmappingsGoalgroup(GoalGroupMappings goalGroupMappings) {
        this.goalgroupmappingsGoalgroups.add(goalGroupMappings);
        goalGroupMappings.setGoalGroup(this);
        return this;
    }

    public GoalGroups removeGoalgroupmappingsGoalgroup(GoalGroupMappings goalGroupMappings) {
        this.goalgroupmappingsGoalgroups.remove(goalGroupMappings);
        goalGroupMappings.setGoalGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoalGroups)) {
            return false;
        }
        return id != null && id.equals(((GoalGroups) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoalGroups{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
