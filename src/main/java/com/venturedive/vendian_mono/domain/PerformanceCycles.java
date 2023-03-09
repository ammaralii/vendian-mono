package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PerformanceCycles.
 */
@Entity
@Table(name = "performance_cycles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceCycles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month")
    private Boolean month;

    @Column(name = "year")
    private Boolean year;

    @Column(name = "totalresources")
    private Boolean totalresources;

    @Column(name = "pmreviewed")
    private Boolean pmreviewed;

    @Column(name = "archreviewed")
    private Boolean archreviewed;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "projectcount")
    private Boolean projectcount;

    @Column(name = "criteria")
    private Integer criteria;

    @Column(name = "notificationsent")
    private Boolean notificationsent;

    @Column(name = "duedate")
    private Instant duedate;

    @Column(name = "initiationdate")
    private Instant initiationdate;

    @ManyToMany
    @JoinTable(
        name = "rel_performance_cycles__projectcycle",
        joinColumns = @JoinColumn(name = "performance_cycles_id"),
        inverseJoinColumns = @JoinColumn(name = "projectcycle_id")
    )
    @JsonIgnoreProperties(
        value = {
            "project",
            "allowedafterduedateby",
            "architect",
            "projectmanager",
            "ratings",
            "employeeprojectratingsProjectcycles",
            "performancecycles",
        },
        allowSetters = true
    )
    private Set<ProjectCycles> projectcycles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_performance_cycles__employeerating",
        joinColumns = @JoinColumn(name = "performance_cycles_id"),
        inverseJoinColumns = @JoinColumn(name = "employeerating_id")
    )
    @JsonIgnoreProperties(value = { "rater", "ratingattributesRatings", "performancecycles", "projectcycles" }, allowSetters = true)
    private Set<Ratings> employeeratings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceCycles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMonth() {
        return this.month;
    }

    public PerformanceCycles month(Boolean month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(Boolean month) {
        this.month = month;
    }

    public Boolean getYear() {
        return this.year;
    }

    public PerformanceCycles year(Boolean year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Boolean year) {
        this.year = year;
    }

    public Boolean getTotalresources() {
        return this.totalresources;
    }

    public PerformanceCycles totalresources(Boolean totalresources) {
        this.setTotalresources(totalresources);
        return this;
    }

    public void setTotalresources(Boolean totalresources) {
        this.totalresources = totalresources;
    }

    public Boolean getPmreviewed() {
        return this.pmreviewed;
    }

    public PerformanceCycles pmreviewed(Boolean pmreviewed) {
        this.setPmreviewed(pmreviewed);
        return this;
    }

    public void setPmreviewed(Boolean pmreviewed) {
        this.pmreviewed = pmreviewed;
    }

    public Boolean getArchreviewed() {
        return this.archreviewed;
    }

    public PerformanceCycles archreviewed(Boolean archreviewed) {
        this.setArchreviewed(archreviewed);
        return this;
    }

    public void setArchreviewed(Boolean archreviewed) {
        this.archreviewed = archreviewed;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public PerformanceCycles startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public PerformanceCycles enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public PerformanceCycles isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public PerformanceCycles createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public PerformanceCycles updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Boolean getProjectcount() {
        return this.projectcount;
    }

    public PerformanceCycles projectcount(Boolean projectcount) {
        this.setProjectcount(projectcount);
        return this;
    }

    public void setProjectcount(Boolean projectcount) {
        this.projectcount = projectcount;
    }

    public Integer getCriteria() {
        return this.criteria;
    }

    public PerformanceCycles criteria(Integer criteria) {
        this.setCriteria(criteria);
        return this;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }

    public Boolean getNotificationsent() {
        return this.notificationsent;
    }

    public PerformanceCycles notificationsent(Boolean notificationsent) {
        this.setNotificationsent(notificationsent);
        return this;
    }

    public void setNotificationsent(Boolean notificationsent) {
        this.notificationsent = notificationsent;
    }

    public Instant getDuedate() {
        return this.duedate;
    }

    public PerformanceCycles duedate(Instant duedate) {
        this.setDuedate(duedate);
        return this;
    }

    public void setDuedate(Instant duedate) {
        this.duedate = duedate;
    }

    public Instant getInitiationdate() {
        return this.initiationdate;
    }

    public PerformanceCycles initiationdate(Instant initiationdate) {
        this.setInitiationdate(initiationdate);
        return this;
    }

    public void setInitiationdate(Instant initiationdate) {
        this.initiationdate = initiationdate;
    }

    public Set<ProjectCycles> getProjectcycles() {
        return this.projectcycles;
    }

    public void setProjectcycles(Set<ProjectCycles> projectCycles) {
        this.projectcycles = projectCycles;
    }

    public PerformanceCycles projectcycles(Set<ProjectCycles> projectCycles) {
        this.setProjectcycles(projectCycles);
        return this;
    }

    public PerformanceCycles addProjectcycle(ProjectCycles projectCycles) {
        this.projectcycles.add(projectCycles);
        projectCycles.getPerformancecycles().add(this);
        return this;
    }

    public PerformanceCycles removeProjectcycle(ProjectCycles projectCycles) {
        this.projectcycles.remove(projectCycles);
        projectCycles.getPerformancecycles().remove(this);
        return this;
    }

    public Set<Ratings> getEmployeeratings() {
        return this.employeeratings;
    }

    public void setEmployeeratings(Set<Ratings> ratings) {
        this.employeeratings = ratings;
    }

    public PerformanceCycles employeeratings(Set<Ratings> ratings) {
        this.setEmployeeratings(ratings);
        return this;
    }

    public PerformanceCycles addEmployeerating(Ratings ratings) {
        this.employeeratings.add(ratings);
        ratings.getPerformancecycles().add(this);
        return this;
    }

    public PerformanceCycles removeEmployeerating(Ratings ratings) {
        this.employeeratings.remove(ratings);
        ratings.getPerformancecycles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceCycles)) {
            return false;
        }
        return id != null && id.equals(((PerformanceCycles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceCycles{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", totalresources='" + getTotalresources() + "'" +
            ", pmreviewed='" + getPmreviewed() + "'" +
            ", archreviewed='" + getArchreviewed() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", projectcount='" + getProjectcount() + "'" +
            ", criteria=" + getCriteria() +
            ", notificationsent='" + getNotificationsent() + "'" +
            ", duedate='" + getDuedate() + "'" +
            ", initiationdate='" + getInitiationdate() + "'" +
            "}";
    }
}
