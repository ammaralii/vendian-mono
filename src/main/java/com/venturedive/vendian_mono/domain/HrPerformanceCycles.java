package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A HrPerformanceCycles.
 */
@Entity
@Table(name = "hr_performance_cycles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HrPerformanceCycles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "freeze")
    private Boolean freeze;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    @OneToMany(mappedBy = "performancecycle")
    @JsonIgnoreProperties(
        value = { "performancecycle", "employee", "manager", "pcratingsPcemployeeratings", "userratingsremarksPcemployeeratings" },
        allowSetters = true
    )
    private Set<PerformanceCycleEmployeeRating> performancecycleemployeeratingPerformancecycles = new HashSet<>();

    @OneToMany(mappedBy = "performanceCycle")
    @JsonIgnoreProperties(value = { "user", "reviewManager", "performanceCycle" }, allowSetters = true)
    private Set<UserRatings> userratingsPerformancecycles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HrPerformanceCycles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public HrPerformanceCycles title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getFreeze() {
        return this.freeze;
    }

    public HrPerformanceCycles freeze(Boolean freeze) {
        this.setFreeze(freeze);
        return this;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public HrPerformanceCycles dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public HrPerformanceCycles startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public HrPerformanceCycles endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public HrPerformanceCycles createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public HrPerformanceCycles updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public HrPerformanceCycles deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public HrPerformanceCycles version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<PerformanceCycleEmployeeRating> getPerformancecycleemployeeratingPerformancecycles() {
        return this.performancecycleemployeeratingPerformancecycles;
    }

    public void setPerformancecycleemployeeratingPerformancecycles(Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings) {
        if (this.performancecycleemployeeratingPerformancecycles != null) {
            this.performancecycleemployeeratingPerformancecycles.forEach(i -> i.setPerformancecycle(null));
        }
        if (performanceCycleEmployeeRatings != null) {
            performanceCycleEmployeeRatings.forEach(i -> i.setPerformancecycle(this));
        }
        this.performancecycleemployeeratingPerformancecycles = performanceCycleEmployeeRatings;
    }

    public HrPerformanceCycles performancecycleemployeeratingPerformancecycles(
        Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings
    ) {
        this.setPerformancecycleemployeeratingPerformancecycles(performanceCycleEmployeeRatings);
        return this;
    }

    public HrPerformanceCycles addPerformancecycleemployeeratingPerformancecycle(
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating
    ) {
        this.performancecycleemployeeratingPerformancecycles.add(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setPerformancecycle(this);
        return this;
    }

    public HrPerformanceCycles removePerformancecycleemployeeratingPerformancecycle(
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating
    ) {
        this.performancecycleemployeeratingPerformancecycles.remove(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setPerformancecycle(null);
        return this;
    }

    public Set<UserRatings> getUserratingsPerformancecycles() {
        return this.userratingsPerformancecycles;
    }

    public void setUserratingsPerformancecycles(Set<UserRatings> userRatings) {
        if (this.userratingsPerformancecycles != null) {
            this.userratingsPerformancecycles.forEach(i -> i.setPerformanceCycle(null));
        }
        if (userRatings != null) {
            userRatings.forEach(i -> i.setPerformanceCycle(this));
        }
        this.userratingsPerformancecycles = userRatings;
    }

    public HrPerformanceCycles userratingsPerformancecycles(Set<UserRatings> userRatings) {
        this.setUserratingsPerformancecycles(userRatings);
        return this;
    }

    public HrPerformanceCycles addUserratingsPerformancecycle(UserRatings userRatings) {
        this.userratingsPerformancecycles.add(userRatings);
        userRatings.setPerformanceCycle(this);
        return this;
    }

    public HrPerformanceCycles removeUserratingsPerformancecycle(UserRatings userRatings) {
        this.userratingsPerformancecycles.remove(userRatings);
        userRatings.setPerformanceCycle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HrPerformanceCycles)) {
            return false;
        }
        return id != null && id.equals(((HrPerformanceCycles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HrPerformanceCycles{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", freeze='" + getFreeze() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
