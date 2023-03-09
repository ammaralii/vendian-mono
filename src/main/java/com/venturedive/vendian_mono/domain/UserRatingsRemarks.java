package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserRatingsRemarks.
 */
@Entity
@Table(name = "user_ratings_remarks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserRatingsRemarks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_promotion")
    private Boolean isPromotion;

    @Size(max = 65535)
    @Column(name = "strength", length = 65535)
    private String strength;

    @Size(max = 65535)
    @Column(name = "area_of_improvement", length = 65535)
    private String areaOfImprovement;

    @Size(max = 65535)
    @Column(name = "promotion_justification", length = 65535)
    private String promotionJustification;

    @Size(max = 255)
    @Column(name = "new_grade", length = 255)
    private String newGrade;

    @Column(name = "is_redesignation")
    private Boolean isRedesignation;

    @Column(name = "recommended_salary")
    private Integer recommendedSalary;

    @Size(max = 9)
    @Column(name = "status", length = 9)
    private String status;

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

    @Size(max = 65535)
    @Column(name = "other_comments", length = 65535)
    private String otherComments;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "designationjobdescriptionsDesignations",
            "employeejobinfoDesignations",
            "employeesDesignations",
            "userratingsremarksDesignationafterpromotions",
            "userratingsremarksDesignationafterredesignations",
        },
        allowSetters = true
    )
    private Designations designationAfterPromotion;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "designationjobdescriptionsDesignations",
            "employeejobinfoDesignations",
            "employeesDesignations",
            "userratingsremarksDesignationafterpromotions",
            "userratingsremarksDesignationafterredesignations",
        },
        allowSetters = true
    )
    private Designations designationAfterRedesignation;

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
    private Employees rater;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "performancecycle", "employee", "manager", "pcratingsPcemployeeratings", "userratingsremarksPcemployeeratings" },
        allowSetters = true
    )
    private PerformanceCycleEmployeeRating pcEmployeeRating;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserRatingsRemarks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPromotion() {
        return this.isPromotion;
    }

    public UserRatingsRemarks isPromotion(Boolean isPromotion) {
        this.setIsPromotion(isPromotion);
        return this;
    }

    public void setIsPromotion(Boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getStrength() {
        return this.strength;
    }

    public UserRatingsRemarks strength(String strength) {
        this.setStrength(strength);
        return this;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getAreaOfImprovement() {
        return this.areaOfImprovement;
    }

    public UserRatingsRemarks areaOfImprovement(String areaOfImprovement) {
        this.setAreaOfImprovement(areaOfImprovement);
        return this;
    }

    public void setAreaOfImprovement(String areaOfImprovement) {
        this.areaOfImprovement = areaOfImprovement;
    }

    public String getPromotionJustification() {
        return this.promotionJustification;
    }

    public UserRatingsRemarks promotionJustification(String promotionJustification) {
        this.setPromotionJustification(promotionJustification);
        return this;
    }

    public void setPromotionJustification(String promotionJustification) {
        this.promotionJustification = promotionJustification;
    }

    public String getNewGrade() {
        return this.newGrade;
    }

    public UserRatingsRemarks newGrade(String newGrade) {
        this.setNewGrade(newGrade);
        return this;
    }

    public void setNewGrade(String newGrade) {
        this.newGrade = newGrade;
    }

    public Boolean getIsRedesignation() {
        return this.isRedesignation;
    }

    public UserRatingsRemarks isRedesignation(Boolean isRedesignation) {
        this.setIsRedesignation(isRedesignation);
        return this;
    }

    public void setIsRedesignation(Boolean isRedesignation) {
        this.isRedesignation = isRedesignation;
    }

    public Integer getRecommendedSalary() {
        return this.recommendedSalary;
    }

    public UserRatingsRemarks recommendedSalary(Integer recommendedSalary) {
        this.setRecommendedSalary(recommendedSalary);
        return this;
    }

    public void setRecommendedSalary(Integer recommendedSalary) {
        this.recommendedSalary = recommendedSalary;
    }

    public String getStatus() {
        return this.status;
    }

    public UserRatingsRemarks status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserRatingsRemarks createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UserRatingsRemarks updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public UserRatingsRemarks deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public UserRatingsRemarks version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOtherComments() {
        return this.otherComments;
    }

    public UserRatingsRemarks otherComments(String otherComments) {
        this.setOtherComments(otherComments);
        return this;
    }

    public void setOtherComments(String otherComments) {
        this.otherComments = otherComments;
    }

    public Designations getDesignationAfterPromotion() {
        return this.designationAfterPromotion;
    }

    public void setDesignationAfterPromotion(Designations designations) {
        this.designationAfterPromotion = designations;
    }

    public UserRatingsRemarks designationAfterPromotion(Designations designations) {
        this.setDesignationAfterPromotion(designations);
        return this;
    }

    public Designations getDesignationAfterRedesignation() {
        return this.designationAfterRedesignation;
    }

    public void setDesignationAfterRedesignation(Designations designations) {
        this.designationAfterRedesignation = designations;
    }

    public UserRatingsRemarks designationAfterRedesignation(Designations designations) {
        this.setDesignationAfterRedesignation(designations);
        return this;
    }

    public Employees getRater() {
        return this.rater;
    }

    public void setRater(Employees employees) {
        this.rater = employees;
    }

    public UserRatingsRemarks rater(Employees employees) {
        this.setRater(employees);
        return this;
    }

    public PerformanceCycleEmployeeRating getPcEmployeeRating() {
        return this.pcEmployeeRating;
    }

    public void setPcEmployeeRating(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.pcEmployeeRating = performanceCycleEmployeeRating;
    }

    public UserRatingsRemarks pcEmployeeRating(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.setPcEmployeeRating(performanceCycleEmployeeRating);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRatingsRemarks)) {
            return false;
        }
        return id != null && id.equals(((UserRatingsRemarks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRatingsRemarks{" +
            "id=" + getId() +
            ", isPromotion='" + getIsPromotion() + "'" +
            ", strength='" + getStrength() + "'" +
            ", areaOfImprovement='" + getAreaOfImprovement() + "'" +
            ", promotionJustification='" + getPromotionJustification() + "'" +
            ", newGrade='" + getNewGrade() + "'" +
            ", isRedesignation='" + getIsRedesignation() + "'" +
            ", recommendedSalary=" + getRecommendedSalary() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            ", otherComments='" + getOtherComments() + "'" +
            "}";
    }
}
