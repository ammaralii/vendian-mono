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
 * A PcRatings.
 */
@Entity
@Table(name = "pc_ratings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRatings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "rating")
    private byte[] rating;

    @Column(name = "rating_content_type")
    private String ratingContentType;

    @Lob
    @Column(name = "comment")
    private byte[] comment;

    @Column(name = "comment_content_type")
    private String commentContentType;

    @Column(name = "staus_date")
    private Instant stausDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "freeze")
    private Boolean freeze;

    @Column(name = "include_in_final_rating")
    private Boolean includeInFinalRating;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "pcratingsStatuses" }, allowSetters = true)
    private PcStatuses status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "performancecycle", "employee", "manager", "pcratingsPcemployeeratings", "userratingsremarksPcemployeeratings" },
        allowSetters = true
    )
    private PerformanceCycleEmployeeRating pcemployeerating;

    @ManyToOne(optional = false)
    @NotNull
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
    private Employees employee;

    @OneToMany(mappedBy = "rating")
    @JsonIgnoreProperties(value = { "ratingAttributeMapping", "ratingOption", "rating" }, allowSetters = true)
    private Set<PcRaterAttributes> pcraterattributesRatings = new HashSet<>();

    @OneToMany(mappedBy = "rating")
    @JsonIgnoreProperties(value = { "successorFor", "rating" }, allowSetters = true)
    private Set<PcRatingsTrainings> pcratingstrainingsRatings = new HashSet<>();

    @OneToMany(mappedBy = "rating")
    @JsonIgnoreProperties(value = { "rating", "usergoal" }, allowSetters = true)
    private Set<UserGoalRaterAttributes> usergoalraterattributesRatings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PcRatings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRating() {
        return this.rating;
    }

    public PcRatings rating(byte[] rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(byte[] rating) {
        this.rating = rating;
    }

    public String getRatingContentType() {
        return this.ratingContentType;
    }

    public PcRatings ratingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
        return this;
    }

    public void setRatingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
    }

    public byte[] getComment() {
        return this.comment;
    }

    public PcRatings comment(byte[] comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public String getCommentContentType() {
        return this.commentContentType;
    }

    public PcRatings commentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
        return this;
    }

    public void setCommentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
    }

    public Instant getStausDate() {
        return this.stausDate;
    }

    public PcRatings stausDate(Instant stausDate) {
        this.setStausDate(stausDate);
        return this;
    }

    public void setStausDate(Instant stausDate) {
        this.stausDate = stausDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public PcRatings dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getFreeze() {
        return this.freeze;
    }

    public PcRatings freeze(Boolean freeze) {
        this.setFreeze(freeze);
        return this;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    public Boolean getIncludeInFinalRating() {
        return this.includeInFinalRating;
    }

    public PcRatings includeInFinalRating(Boolean includeInFinalRating) {
        this.setIncludeInFinalRating(includeInFinalRating);
        return this;
    }

    public void setIncludeInFinalRating(Boolean includeInFinalRating) {
        this.includeInFinalRating = includeInFinalRating;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PcRatings createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PcRatings updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PcRatings deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PcRatings version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PcStatuses getStatus() {
        return this.status;
    }

    public void setStatus(PcStatuses pcStatuses) {
        this.status = pcStatuses;
    }

    public PcRatings status(PcStatuses pcStatuses) {
        this.setStatus(pcStatuses);
        return this;
    }

    public PerformanceCycleEmployeeRating getPcemployeerating() {
        return this.pcemployeerating;
    }

    public void setPcemployeerating(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.pcemployeerating = performanceCycleEmployeeRating;
    }

    public PcRatings pcemployeerating(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.setPcemployeerating(performanceCycleEmployeeRating);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public PcRatings employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Set<PcRaterAttributes> getPcraterattributesRatings() {
        return this.pcraterattributesRatings;
    }

    public void setPcraterattributesRatings(Set<PcRaterAttributes> pcRaterAttributes) {
        if (this.pcraterattributesRatings != null) {
            this.pcraterattributesRatings.forEach(i -> i.setRating(null));
        }
        if (pcRaterAttributes != null) {
            pcRaterAttributes.forEach(i -> i.setRating(this));
        }
        this.pcraterattributesRatings = pcRaterAttributes;
    }

    public PcRatings pcraterattributesRatings(Set<PcRaterAttributes> pcRaterAttributes) {
        this.setPcraterattributesRatings(pcRaterAttributes);
        return this;
    }

    public PcRatings addPcraterattributesRating(PcRaterAttributes pcRaterAttributes) {
        this.pcraterattributesRatings.add(pcRaterAttributes);
        pcRaterAttributes.setRating(this);
        return this;
    }

    public PcRatings removePcraterattributesRating(PcRaterAttributes pcRaterAttributes) {
        this.pcraterattributesRatings.remove(pcRaterAttributes);
        pcRaterAttributes.setRating(null);
        return this;
    }

    public Set<PcRatingsTrainings> getPcratingstrainingsRatings() {
        return this.pcratingstrainingsRatings;
    }

    public void setPcratingstrainingsRatings(Set<PcRatingsTrainings> pcRatingsTrainings) {
        if (this.pcratingstrainingsRatings != null) {
            this.pcratingstrainingsRatings.forEach(i -> i.setRating(null));
        }
        if (pcRatingsTrainings != null) {
            pcRatingsTrainings.forEach(i -> i.setRating(this));
        }
        this.pcratingstrainingsRatings = pcRatingsTrainings;
    }

    public PcRatings pcratingstrainingsRatings(Set<PcRatingsTrainings> pcRatingsTrainings) {
        this.setPcratingstrainingsRatings(pcRatingsTrainings);
        return this;
    }

    public PcRatings addPcratingstrainingsRating(PcRatingsTrainings pcRatingsTrainings) {
        this.pcratingstrainingsRatings.add(pcRatingsTrainings);
        pcRatingsTrainings.setRating(this);
        return this;
    }

    public PcRatings removePcratingstrainingsRating(PcRatingsTrainings pcRatingsTrainings) {
        this.pcratingstrainingsRatings.remove(pcRatingsTrainings);
        pcRatingsTrainings.setRating(null);
        return this;
    }

    public Set<UserGoalRaterAttributes> getUsergoalraterattributesRatings() {
        return this.usergoalraterattributesRatings;
    }

    public void setUsergoalraterattributesRatings(Set<UserGoalRaterAttributes> userGoalRaterAttributes) {
        if (this.usergoalraterattributesRatings != null) {
            this.usergoalraterattributesRatings.forEach(i -> i.setRating(null));
        }
        if (userGoalRaterAttributes != null) {
            userGoalRaterAttributes.forEach(i -> i.setRating(this));
        }
        this.usergoalraterattributesRatings = userGoalRaterAttributes;
    }

    public PcRatings usergoalraterattributesRatings(Set<UserGoalRaterAttributes> userGoalRaterAttributes) {
        this.setUsergoalraterattributesRatings(userGoalRaterAttributes);
        return this;
    }

    public PcRatings addUsergoalraterattributesRating(UserGoalRaterAttributes userGoalRaterAttributes) {
        this.usergoalraterattributesRatings.add(userGoalRaterAttributes);
        userGoalRaterAttributes.setRating(this);
        return this;
    }

    public PcRatings removeUsergoalraterattributesRating(UserGoalRaterAttributes userGoalRaterAttributes) {
        this.usergoalraterattributesRatings.remove(userGoalRaterAttributes);
        userGoalRaterAttributes.setRating(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PcRatings)) {
            return false;
        }
        return id != null && id.equals(((PcRatings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRatings{" +
            "id=" + getId() +
            ", rating='" + getRating() + "'" +
            ", ratingContentType='" + getRatingContentType() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentContentType='" + getCommentContentType() + "'" +
            ", stausDate='" + getStausDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", freeze='" + getFreeze() + "'" +
            ", includeInFinalRating='" + getIncludeInFinalRating() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
