package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PerformanceCycleEmployeeRating.
 */
@Entity
@Table(name = "performance_cycle_employee_rating")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceCycleEmployeeRating implements Serializable {

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
        value = { "performancecycleemployeeratingPerformancecycles", "userratingsPerformancecycles" },
        allowSetters = true
    )
    private HrPerformanceCycles performancecycle;

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
    private Employees manager;

    @OneToMany(mappedBy = "pcemployeerating")
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
    private Set<PcRatings> pcratingsPcemployeeratings = new HashSet<>();

    @OneToMany(mappedBy = "pcEmployeeRating")
    @JsonIgnoreProperties(
        value = { "designationAfterPromotion", "designationAfterRedesignation", "rater", "pcEmployeeRating" },
        allowSetters = true
    )
    private Set<UserRatingsRemarks> userratingsremarksPcemployeeratings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceCycleEmployeeRating id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRating() {
        return this.rating;
    }

    public PerformanceCycleEmployeeRating rating(byte[] rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(byte[] rating) {
        this.rating = rating;
    }

    public String getRatingContentType() {
        return this.ratingContentType;
    }

    public PerformanceCycleEmployeeRating ratingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
        return this;
    }

    public void setRatingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
    }

    public byte[] getComment() {
        return this.comment;
    }

    public PerformanceCycleEmployeeRating comment(byte[] comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(byte[] comment) {
        this.comment = comment;
    }

    public String getCommentContentType() {
        return this.commentContentType;
    }

    public PerformanceCycleEmployeeRating commentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
        return this;
    }

    public void setCommentContentType(String commentContentType) {
        this.commentContentType = commentContentType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PerformanceCycleEmployeeRating createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PerformanceCycleEmployeeRating updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PerformanceCycleEmployeeRating deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PerformanceCycleEmployeeRating version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public HrPerformanceCycles getPerformancecycle() {
        return this.performancecycle;
    }

    public void setPerformancecycle(HrPerformanceCycles hrPerformanceCycles) {
        this.performancecycle = hrPerformanceCycles;
    }

    public PerformanceCycleEmployeeRating performancecycle(HrPerformanceCycles hrPerformanceCycles) {
        this.setPerformancecycle(hrPerformanceCycles);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public PerformanceCycleEmployeeRating employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Employees getManager() {
        return this.manager;
    }

    public void setManager(Employees employees) {
        this.manager = employees;
    }

    public PerformanceCycleEmployeeRating manager(Employees employees) {
        this.setManager(employees);
        return this;
    }

    public Set<PcRatings> getPcratingsPcemployeeratings() {
        return this.pcratingsPcemployeeratings;
    }

    public void setPcratingsPcemployeeratings(Set<PcRatings> pcRatings) {
        if (this.pcratingsPcemployeeratings != null) {
            this.pcratingsPcemployeeratings.forEach(i -> i.setPcemployeerating(null));
        }
        if (pcRatings != null) {
            pcRatings.forEach(i -> i.setPcemployeerating(this));
        }
        this.pcratingsPcemployeeratings = pcRatings;
    }

    public PerformanceCycleEmployeeRating pcratingsPcemployeeratings(Set<PcRatings> pcRatings) {
        this.setPcratingsPcemployeeratings(pcRatings);
        return this;
    }

    public PerformanceCycleEmployeeRating addPcratingsPcemployeerating(PcRatings pcRatings) {
        this.pcratingsPcemployeeratings.add(pcRatings);
        pcRatings.setPcemployeerating(this);
        return this;
    }

    public PerformanceCycleEmployeeRating removePcratingsPcemployeerating(PcRatings pcRatings) {
        this.pcratingsPcemployeeratings.remove(pcRatings);
        pcRatings.setPcemployeerating(null);
        return this;
    }

    public Set<UserRatingsRemarks> getUserratingsremarksPcemployeeratings() {
        return this.userratingsremarksPcemployeeratings;
    }

    public void setUserratingsremarksPcemployeeratings(Set<UserRatingsRemarks> userRatingsRemarks) {
        if (this.userratingsremarksPcemployeeratings != null) {
            this.userratingsremarksPcemployeeratings.forEach(i -> i.setPcEmployeeRating(null));
        }
        if (userRatingsRemarks != null) {
            userRatingsRemarks.forEach(i -> i.setPcEmployeeRating(this));
        }
        this.userratingsremarksPcemployeeratings = userRatingsRemarks;
    }

    public PerformanceCycleEmployeeRating userratingsremarksPcemployeeratings(Set<UserRatingsRemarks> userRatingsRemarks) {
        this.setUserratingsremarksPcemployeeratings(userRatingsRemarks);
        return this;
    }

    public PerformanceCycleEmployeeRating addUserratingsremarksPcemployeerating(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksPcemployeeratings.add(userRatingsRemarks);
        userRatingsRemarks.setPcEmployeeRating(this);
        return this;
    }

    public PerformanceCycleEmployeeRating removeUserratingsremarksPcemployeerating(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksPcemployeeratings.remove(userRatingsRemarks);
        userRatingsRemarks.setPcEmployeeRating(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceCycleEmployeeRating)) {
            return false;
        }
        return id != null && id.equals(((PerformanceCycleEmployeeRating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceCycleEmployeeRating{" +
            "id=" + getId() +
            ", rating='" + getRating() + "'" +
            ", ratingContentType='" + getRatingContentType() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentContentType='" + getCommentContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
