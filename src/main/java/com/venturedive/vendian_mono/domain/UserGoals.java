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
 * A UserGoals.
 */
@Entity
@Table(name = "user_goals")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserGoals implements Serializable {

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

    @Column(name = "weightage")
    private Integer weightage;

    @Column(name = "progress")
    private Integer progress;

    @Size(max = 16)
    @Column(name = "status", length = 16)
    private String status;

    @Column(name = "due_date")
    private LocalDate dueDate;

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
    private Employees user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "goalgroupmappingsGoals", "usergoalsReferencegoals" }, allowSetters = true)
    private Goals referenceGoal;

    @OneToMany(mappedBy = "usergoal")
    @JsonIgnoreProperties(value = { "rating", "usergoal" }, allowSetters = true)
    private Set<UserGoalRaterAttributes> usergoalraterattributesUsergoals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserGoals id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public UserGoals title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public UserGoals description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeasurement() {
        return this.measurement;
    }

    public UserGoals measurement(String measurement) {
        this.setMeasurement(measurement);
        return this;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Integer getWeightage() {
        return this.weightage;
    }

    public UserGoals weightage(Integer weightage) {
        this.setWeightage(weightage);
        return this;
    }

    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }

    public Integer getProgress() {
        return this.progress;
    }

    public UserGoals progress(Integer progress) {
        this.setProgress(progress);
        return this;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return this.status;
    }

    public UserGoals status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public UserGoals dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public UserGoals createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UserGoals updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public UserGoals deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public UserGoals version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Employees getUser() {
        return this.user;
    }

    public void setUser(Employees employees) {
        this.user = employees;
    }

    public UserGoals user(Employees employees) {
        this.setUser(employees);
        return this;
    }

    public Goals getReferenceGoal() {
        return this.referenceGoal;
    }

    public void setReferenceGoal(Goals goals) {
        this.referenceGoal = goals;
    }

    public UserGoals referenceGoal(Goals goals) {
        this.setReferenceGoal(goals);
        return this;
    }

    public Set<UserGoalRaterAttributes> getUsergoalraterattributesUsergoals() {
        return this.usergoalraterattributesUsergoals;
    }

    public void setUsergoalraterattributesUsergoals(Set<UserGoalRaterAttributes> userGoalRaterAttributes) {
        if (this.usergoalraterattributesUsergoals != null) {
            this.usergoalraterattributesUsergoals.forEach(i -> i.setUsergoal(null));
        }
        if (userGoalRaterAttributes != null) {
            userGoalRaterAttributes.forEach(i -> i.setUsergoal(this));
        }
        this.usergoalraterattributesUsergoals = userGoalRaterAttributes;
    }

    public UserGoals usergoalraterattributesUsergoals(Set<UserGoalRaterAttributes> userGoalRaterAttributes) {
        this.setUsergoalraterattributesUsergoals(userGoalRaterAttributes);
        return this;
    }

    public UserGoals addUsergoalraterattributesUsergoal(UserGoalRaterAttributes userGoalRaterAttributes) {
        this.usergoalraterattributesUsergoals.add(userGoalRaterAttributes);
        userGoalRaterAttributes.setUsergoal(this);
        return this;
    }

    public UserGoals removeUsergoalraterattributesUsergoal(UserGoalRaterAttributes userGoalRaterAttributes) {
        this.usergoalraterattributesUsergoals.remove(userGoalRaterAttributes);
        userGoalRaterAttributes.setUsergoal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGoals)) {
            return false;
        }
        return id != null && id.equals(((UserGoals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGoals{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", measurement='" + getMeasurement() + "'" +
            ", weightage=" + getWeightage() +
            ", progress=" + getProgress() +
            ", status='" + getStatus() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
