package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PcRatingsTrainings.
 */
@Entity
@Table(name = "pc_ratings_trainings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRatingsTrainings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 65535)
    @Column(name = "strength", length = 65535)
    private String strength;

    @Size(max = 65535)
    @Column(name = "development_area", length = 65535)
    private String developmentArea;

    @Size(max = 65535)
    @Column(name = "career_ambition", length = 65535)
    private String careerAmbition;

    @Size(max = 65535)
    @Column(name = "short_course", length = 65535)
    private String shortCourse;

    @Size(max = 65535)
    @Column(name = "technical_training", length = 65535)
    private String technicalTraining;

    @Size(max = 65535)
    @Column(name = "soft_skills_training", length = 65535)
    private String softSkillsTraining;

    @Column(name = "critical_position")
    private Boolean criticalPosition;

    @Column(name = "high_potential")
    private Boolean highPotential;

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
    private Employees successorFor;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PcRatingsTrainings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrength() {
        return this.strength;
    }

    public PcRatingsTrainings strength(String strength) {
        this.setStrength(strength);
        return this;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDevelopmentArea() {
        return this.developmentArea;
    }

    public PcRatingsTrainings developmentArea(String developmentArea) {
        this.setDevelopmentArea(developmentArea);
        return this;
    }

    public void setDevelopmentArea(String developmentArea) {
        this.developmentArea = developmentArea;
    }

    public String getCareerAmbition() {
        return this.careerAmbition;
    }

    public PcRatingsTrainings careerAmbition(String careerAmbition) {
        this.setCareerAmbition(careerAmbition);
        return this;
    }

    public void setCareerAmbition(String careerAmbition) {
        this.careerAmbition = careerAmbition;
    }

    public String getShortCourse() {
        return this.shortCourse;
    }

    public PcRatingsTrainings shortCourse(String shortCourse) {
        this.setShortCourse(shortCourse);
        return this;
    }

    public void setShortCourse(String shortCourse) {
        this.shortCourse = shortCourse;
    }

    public String getTechnicalTraining() {
        return this.technicalTraining;
    }

    public PcRatingsTrainings technicalTraining(String technicalTraining) {
        this.setTechnicalTraining(technicalTraining);
        return this;
    }

    public void setTechnicalTraining(String technicalTraining) {
        this.technicalTraining = technicalTraining;
    }

    public String getSoftSkillsTraining() {
        return this.softSkillsTraining;
    }

    public PcRatingsTrainings softSkillsTraining(String softSkillsTraining) {
        this.setSoftSkillsTraining(softSkillsTraining);
        return this;
    }

    public void setSoftSkillsTraining(String softSkillsTraining) {
        this.softSkillsTraining = softSkillsTraining;
    }

    public Boolean getCriticalPosition() {
        return this.criticalPosition;
    }

    public PcRatingsTrainings criticalPosition(Boolean criticalPosition) {
        this.setCriticalPosition(criticalPosition);
        return this;
    }

    public void setCriticalPosition(Boolean criticalPosition) {
        this.criticalPosition = criticalPosition;
    }

    public Boolean getHighPotential() {
        return this.highPotential;
    }

    public PcRatingsTrainings highPotential(Boolean highPotential) {
        this.setHighPotential(highPotential);
        return this;
    }

    public void setHighPotential(Boolean highPotential) {
        this.highPotential = highPotential;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PcRatingsTrainings createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public PcRatingsTrainings updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public PcRatingsTrainings deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PcRatingsTrainings version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Employees getSuccessorFor() {
        return this.successorFor;
    }

    public void setSuccessorFor(Employees employees) {
        this.successorFor = employees;
    }

    public PcRatingsTrainings successorFor(Employees employees) {
        this.setSuccessorFor(employees);
        return this;
    }

    public PcRatings getRating() {
        return this.rating;
    }

    public void setRating(PcRatings pcRatings) {
        this.rating = pcRatings;
    }

    public PcRatingsTrainings rating(PcRatings pcRatings) {
        this.setRating(pcRatings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PcRatingsTrainings)) {
            return false;
        }
        return id != null && id.equals(((PcRatingsTrainings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRatingsTrainings{" +
            "id=" + getId() +
            ", strength='" + getStrength() + "'" +
            ", developmentArea='" + getDevelopmentArea() + "'" +
            ", careerAmbition='" + getCareerAmbition() + "'" +
            ", shortCourse='" + getShortCourse() + "'" +
            ", technicalTraining='" + getTechnicalTraining() + "'" +
            ", softSkillsTraining='" + getSoftSkillsTraining() + "'" +
            ", criticalPosition='" + getCriticalPosition() + "'" +
            ", highPotential='" + getHighPotential() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
