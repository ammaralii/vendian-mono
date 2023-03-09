package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Goals.
 */
@Entity
@Table(name = "goals")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Goals implements Serializable {

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

    @Size(max = 65535)
    @Column(name = "measurement", length = 65535)
    private String measurement;

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

    @OneToMany(mappedBy = "goal")
    @JsonIgnoreProperties(value = { "goalGroup", "goal" }, allowSetters = true)
    private Set<GoalGroupMappings> goalgroupmappingsGoals = new HashSet<>();

    @OneToMany(mappedBy = "referenceGoal")
    @JsonIgnoreProperties(value = { "user", "referenceGoal", "usergoalraterattributesUsergoals" }, allowSetters = true)
    private Set<UserGoals> usergoalsReferencegoals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Goals id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Goals title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Goals description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeasurement() {
        return this.measurement;
    }

    public Goals measurement(String measurement) {
        this.setMeasurement(measurement);
        return this;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Goals createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Goals updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Goals deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Goals version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<GoalGroupMappings> getGoalgroupmappingsGoals() {
        return this.goalgroupmappingsGoals;
    }

    public void setGoalgroupmappingsGoals(Set<GoalGroupMappings> goalGroupMappings) {
        if (this.goalgroupmappingsGoals != null) {
            this.goalgroupmappingsGoals.forEach(i -> i.setGoal(null));
        }
        if (goalGroupMappings != null) {
            goalGroupMappings.forEach(i -> i.setGoal(this));
        }
        this.goalgroupmappingsGoals = goalGroupMappings;
    }

    public Goals goalgroupmappingsGoals(Set<GoalGroupMappings> goalGroupMappings) {
        this.setGoalgroupmappingsGoals(goalGroupMappings);
        return this;
    }

    public Goals addGoalgroupmappingsGoal(GoalGroupMappings goalGroupMappings) {
        this.goalgroupmappingsGoals.add(goalGroupMappings);
        goalGroupMappings.setGoal(this);
        return this;
    }

    public Goals removeGoalgroupmappingsGoal(GoalGroupMappings goalGroupMappings) {
        this.goalgroupmappingsGoals.remove(goalGroupMappings);
        goalGroupMappings.setGoal(null);
        return this;
    }

    public Set<UserGoals> getUsergoalsReferencegoals() {
        return this.usergoalsReferencegoals;
    }

    public void setUsergoalsReferencegoals(Set<UserGoals> userGoals) {
        if (this.usergoalsReferencegoals != null) {
            this.usergoalsReferencegoals.forEach(i -> i.setReferenceGoal(null));
        }
        if (userGoals != null) {
            userGoals.forEach(i -> i.setReferenceGoal(this));
        }
        this.usergoalsReferencegoals = userGoals;
    }

    public Goals usergoalsReferencegoals(Set<UserGoals> userGoals) {
        this.setUsergoalsReferencegoals(userGoals);
        return this;
    }

    public Goals addUsergoalsReferencegoal(UserGoals userGoals) {
        this.usergoalsReferencegoals.add(userGoals);
        userGoals.setReferenceGoal(this);
        return this;
    }

    public Goals removeUsergoalsReferencegoal(UserGoals userGoals) {
        this.usergoalsReferencegoals.remove(userGoals);
        userGoals.setReferenceGoal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goals)) {
            return false;
        }
        return id != null && id.equals(((Goals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Goals{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", measurement='" + getMeasurement() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
