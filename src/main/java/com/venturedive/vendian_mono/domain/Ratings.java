package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ratings.
 */
@Entity
@Table(name = "ratings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rateeid")
    private Integer rateeid;

    @Size(max = 8)
    @Column(name = "rateetype", length = 8)
    private String rateetype;

    @Column(name = "duedate")
    private Instant duedate;

    @Lob
    @Column(name = "freeze")
    private byte[] freeze;

    @Column(name = "freeze_content_type")
    private String freezeContentType;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "location",
            "role",
            "manager",
            "leave",
            "department",
            "businessunit",
            "division",
            "competency",
            "employmentstatus",
            "employmenttype",
            "designation",
            "claimrequestviewsEmployees",
            "claimrequestsEmployees",
            "dealresourcesEmployees",
            "employeeaddressesEmployees",
            "employeebirthdaysEmployees",
            "employeecertificatesEmployees",
            "employeecommentsCommenters",
            "employeecommentsEmployees",
            "employeecompensationEmployees",
            "employeecontactsEmployees",
            "employeedetailsEmployees",
            "employeedocumentsEmployees",
            "employeeeducationEmployees",
            "employeeemergencycontactsEmployees",
            "employeefamilyinfoEmployees",
            "employeejobinfoEmployees",
            "employeejobinfoReviewmanagers",
            "employeejobinfoManagers",
            "employeeprofilehistorylogsEmployees",
            "employeeprojectratingsProjectarchitects",
            "employeeprojectratingsProjectmanagers",
            "employeeprojectratingsEmployees",
            "employeeprojectsEmployees",
            "employeeprojectsAssignors",
            "employeeskillsEmployees",
            "employeetalentsEmployees",
            "employeeworksEmployees",
            "employeesManagers",
            "employmenthistoryEmployees",
            "feedbackquestionsEmployees",
            "feedbackrequestsEmployees",
            "feedbackrequestsLinemanagers",
            "feedbackrespondentsEmployees",
            "interviewsEmployees",
            "leavedetailadjustmentlogsAdjustedbies",
            "leaverequestapproversUsers",
            "leaverequestsoldsManagers",
            "leaverequestsoldsEmployees",
            "leavesUsers",
            "notificationsentemaillogsRecipients",
            "pcratingsEmployees",
            "pcratingstrainingsSuccessorfors",
            "performancecycleemployeeratingEmployees",
            "performancecycleemployeeratingManagers",
            "projectcyclesAllowedafterduedatebies",
            "projectcyclesArchitects",
            "projectcyclesProjectmanagers",
            "projectleadershipEmployees",
            "projectsProjectmanagers",
            "ratingsRaters",
            "userattributesUsers",
            "usergoalsUsers",
            "userratingsUsers",
            "userratingsReviewmanagers",
            "userratingsremarksRaters",
            "userrelationsUsers",
            "userrelationsRelatedusers",
            "worklogsEmployees",
            "zemployeeprojectsEmployees",
            "zemployeeprojectsAssignors",
            "zemployeeprojectsProjectmanagers",
        },
        allowSetters = true
    )
    private Employees rater;

    @OneToMany(mappedBy = "rating")
    @JsonIgnoreProperties(value = { "rating", "raterattributemapping" }, allowSetters = true)
    private Set<RatingAttributes> ratingattributesRatings = new HashSet<>();

    @ManyToMany(mappedBy = "employeeratings")
    @JsonIgnoreProperties(value = { "projectcycles", "employeeratings" }, allowSetters = true)
    private Set<PerformanceCycles> performancecycles = new HashSet<>();

    @ManyToMany(mappedBy = "ratings")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ratings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRateeid() {
        return this.rateeid;
    }

    public Ratings rateeid(Integer rateeid) {
        this.setRateeid(rateeid);
        return this;
    }

    public void setRateeid(Integer rateeid) {
        this.rateeid = rateeid;
    }

    public String getRateetype() {
        return this.rateetype;
    }

    public Ratings rateetype(String rateetype) {
        this.setRateetype(rateetype);
        return this;
    }

    public void setRateetype(String rateetype) {
        this.rateetype = rateetype;
    }

    public Instant getDuedate() {
        return this.duedate;
    }

    public Ratings duedate(Instant duedate) {
        this.setDuedate(duedate);
        return this;
    }

    public void setDuedate(Instant duedate) {
        this.duedate = duedate;
    }

    public byte[] getFreeze() {
        return this.freeze;
    }

    public Ratings freeze(byte[] freeze) {
        this.setFreeze(freeze);
        return this;
    }

    public void setFreeze(byte[] freeze) {
        this.freeze = freeze;
    }

    public String getFreezeContentType() {
        return this.freezeContentType;
    }

    public Ratings freezeContentType(String freezeContentType) {
        this.freezeContentType = freezeContentType;
        return this;
    }

    public void setFreezeContentType(String freezeContentType) {
        this.freezeContentType = freezeContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Ratings createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Ratings updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Ratings deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Employees getRater() {
        return this.rater;
    }

    public void setRater(Employees employees) {
        this.rater = employees;
    }

    public Ratings rater(Employees employees) {
        this.setRater(employees);
        return this;
    }

    public Set<RatingAttributes> getRatingattributesRatings() {
        return this.ratingattributesRatings;
    }

    public void setRatingattributesRatings(Set<RatingAttributes> ratingAttributes) {
        if (this.ratingattributesRatings != null) {
            this.ratingattributesRatings.forEach(i -> i.setRating(null));
        }
        if (ratingAttributes != null) {
            ratingAttributes.forEach(i -> i.setRating(this));
        }
        this.ratingattributesRatings = ratingAttributes;
    }

    public Ratings ratingattributesRatings(Set<RatingAttributes> ratingAttributes) {
        this.setRatingattributesRatings(ratingAttributes);
        return this;
    }

    public Ratings addRatingattributesRating(RatingAttributes ratingAttributes) {
        this.ratingattributesRatings.add(ratingAttributes);
        ratingAttributes.setRating(this);
        return this;
    }

    public Ratings removeRatingattributesRating(RatingAttributes ratingAttributes) {
        this.ratingattributesRatings.remove(ratingAttributes);
        ratingAttributes.setRating(null);
        return this;
    }

    public Set<PerformanceCycles> getPerformancecycles() {
        return this.performancecycles;
    }

    public void setPerformancecycles(Set<PerformanceCycles> performanceCycles) {
        if (this.performancecycles != null) {
            this.performancecycles.forEach(i -> i.removeEmployeerating(this));
        }
        if (performanceCycles != null) {
            performanceCycles.forEach(i -> i.addEmployeerating(this));
        }
        this.performancecycles = performanceCycles;
    }

    public Ratings performancecycles(Set<PerformanceCycles> performanceCycles) {
        this.setPerformancecycles(performanceCycles);
        return this;
    }

    public Ratings addPerformancecycle(PerformanceCycles performanceCycles) {
        this.performancecycles.add(performanceCycles);
        performanceCycles.getEmployeeratings().add(this);
        return this;
    }

    public Ratings removePerformancecycle(PerformanceCycles performanceCycles) {
        this.performancecycles.remove(performanceCycles);
        performanceCycles.getEmployeeratings().remove(this);
        return this;
    }

    public Set<ProjectCycles> getProjectcycles() {
        return this.projectcycles;
    }

    public void setProjectcycles(Set<ProjectCycles> projectCycles) {
        if (this.projectcycles != null) {
            this.projectcycles.forEach(i -> i.removeRating(this));
        }
        if (projectCycles != null) {
            projectCycles.forEach(i -> i.addRating(this));
        }
        this.projectcycles = projectCycles;
    }

    public Ratings projectcycles(Set<ProjectCycles> projectCycles) {
        this.setProjectcycles(projectCycles);
        return this;
    }

    public Ratings addProjectcycle(ProjectCycles projectCycles) {
        this.projectcycles.add(projectCycles);
        projectCycles.getRatings().add(this);
        return this;
    }

    public Ratings removeProjectcycle(ProjectCycles projectCycles) {
        this.projectcycles.remove(projectCycles);
        projectCycles.getRatings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ratings)) {
            return false;
        }
        return id != null && id.equals(((Ratings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ratings{" +
            "id=" + getId() +
            ", rateeid=" + getRateeid() +
            ", rateetype='" + getRateetype() + "'" +
            ", duedate='" + getDuedate() + "'" +
            ", freeze='" + getFreeze() + "'" +
            ", freezeContentType='" + getFreezeContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
