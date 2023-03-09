package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ProjectCycles.
 */
@Entity
@Table(name = "project_cycles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCycles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "allowedafterduedateat")
    private Instant allowedafterduedateat;

    @Column(name = "duedate")
    private Instant duedate;

    @Column(name = "auditlogs")
    private String auditlogs;

    @Column(name = "deletedat")
    private Instant deletedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "projectmanager",
            "businessunit",
            "employeeprojectsProjects",
            "interviewsProjects",
            "projectcyclesProjects",
            "projectleadershipProjects",
            "questionsProjects",
            "questionsfrequencyperclienttrackProjects",
            "worklogdetailsProjects",
            "zemployeeprojectsProjects",
        },
        allowSetters = true
    )
    private Projects project;

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
    private Employees allowedafterduedateby;

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
    private Employees architect;

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
    private Employees projectmanager;

    @ManyToMany
    @JoinTable(
        name = "rel_project_cycles__rating",
        joinColumns = @JoinColumn(name = "project_cycles_id"),
        inverseJoinColumns = @JoinColumn(name = "rating_id")
    )
    @JsonIgnoreProperties(value = { "rater", "ratingattributesRatings", "performancecycles", "projectcycles" }, allowSetters = true)
    private Set<Ratings> ratings = new HashSet<>();

    @OneToMany(mappedBy = "projectcycle")
    @JsonIgnoreProperties(value = { "projectarchitect", "projectmanager", "employee", "projectcycle" }, allowSetters = true)
    private Set<EmployeeProjectRatings> employeeprojectratingsProjectcycles = new HashSet<>();

    @ManyToMany(mappedBy = "projectcycles")
    @JsonIgnoreProperties(value = { "projectcycles", "employeeratings" }, allowSetters = true)
    private Set<PerformanceCycles> performancecycles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectCycles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public ProjectCycles isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ProjectCycles createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ProjectCycles updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getAllowedafterduedateat() {
        return this.allowedafterduedateat;
    }

    public ProjectCycles allowedafterduedateat(Instant allowedafterduedateat) {
        this.setAllowedafterduedateat(allowedafterduedateat);
        return this;
    }

    public void setAllowedafterduedateat(Instant allowedafterduedateat) {
        this.allowedafterduedateat = allowedafterduedateat;
    }

    public Instant getDuedate() {
        return this.duedate;
    }

    public ProjectCycles duedate(Instant duedate) {
        this.setDuedate(duedate);
        return this;
    }

    public void setDuedate(Instant duedate) {
        this.duedate = duedate;
    }

    public String getAuditlogs() {
        return this.auditlogs;
    }

    public ProjectCycles auditlogs(String auditlogs) {
        this.setAuditlogs(auditlogs);
        return this;
    }

    public void setAuditlogs(String auditlogs) {
        this.auditlogs = auditlogs;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public ProjectCycles deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public ProjectCycles project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public Employees getAllowedafterduedateby() {
        return this.allowedafterduedateby;
    }

    public void setAllowedafterduedateby(Employees employees) {
        this.allowedafterduedateby = employees;
    }

    public ProjectCycles allowedafterduedateby(Employees employees) {
        this.setAllowedafterduedateby(employees);
        return this;
    }

    public Employees getArchitect() {
        return this.architect;
    }

    public void setArchitect(Employees employees) {
        this.architect = employees;
    }

    public ProjectCycles architect(Employees employees) {
        this.setArchitect(employees);
        return this;
    }

    public Employees getProjectmanager() {
        return this.projectmanager;
    }

    public void setProjectmanager(Employees employees) {
        this.projectmanager = employees;
    }

    public ProjectCycles projectmanager(Employees employees) {
        this.setProjectmanager(employees);
        return this;
    }

    public Set<Ratings> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Ratings> ratings) {
        this.ratings = ratings;
    }

    public ProjectCycles ratings(Set<Ratings> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public ProjectCycles addRating(Ratings ratings) {
        this.ratings.add(ratings);
        ratings.getProjectcycles().add(this);
        return this;
    }

    public ProjectCycles removeRating(Ratings ratings) {
        this.ratings.remove(ratings);
        ratings.getProjectcycles().remove(this);
        return this;
    }

    public Set<EmployeeProjectRatings> getEmployeeprojectratingsProjectcycles() {
        return this.employeeprojectratingsProjectcycles;
    }

    public void setEmployeeprojectratingsProjectcycles(Set<EmployeeProjectRatings> employeeProjectRatings) {
        if (this.employeeprojectratingsProjectcycles != null) {
            this.employeeprojectratingsProjectcycles.forEach(i -> i.setProjectcycle(null));
        }
        if (employeeProjectRatings != null) {
            employeeProjectRatings.forEach(i -> i.setProjectcycle(this));
        }
        this.employeeprojectratingsProjectcycles = employeeProjectRatings;
    }

    public ProjectCycles employeeprojectratingsProjectcycles(Set<EmployeeProjectRatings> employeeProjectRatings) {
        this.setEmployeeprojectratingsProjectcycles(employeeProjectRatings);
        return this;
    }

    public ProjectCycles addEmployeeprojectratingsProjectcycle(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectcycles.add(employeeProjectRatings);
        employeeProjectRatings.setProjectcycle(this);
        return this;
    }

    public ProjectCycles removeEmployeeprojectratingsProjectcycle(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectcycles.remove(employeeProjectRatings);
        employeeProjectRatings.setProjectcycle(null);
        return this;
    }

    public Set<PerformanceCycles> getPerformancecycles() {
        return this.performancecycles;
    }

    public void setPerformancecycles(Set<PerformanceCycles> performanceCycles) {
        if (this.performancecycles != null) {
            this.performancecycles.forEach(i -> i.removeProjectcycle(this));
        }
        if (performanceCycles != null) {
            performanceCycles.forEach(i -> i.addProjectcycle(this));
        }
        this.performancecycles = performanceCycles;
    }

    public ProjectCycles performancecycles(Set<PerformanceCycles> performanceCycles) {
        this.setPerformancecycles(performanceCycles);
        return this;
    }

    public ProjectCycles addPerformancecycle(PerformanceCycles performanceCycles) {
        this.performancecycles.add(performanceCycles);
        performanceCycles.getProjectcycles().add(this);
        return this;
    }

    public ProjectCycles removePerformancecycle(PerformanceCycles performanceCycles) {
        this.performancecycles.remove(performanceCycles);
        performanceCycles.getProjectcycles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectCycles)) {
            return false;
        }
        return id != null && id.equals(((ProjectCycles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCycles{" +
            "id=" + getId() +
            ", isactive='" + getIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", allowedafterduedateat='" + getAllowedafterduedateat() + "'" +
            ", duedate='" + getDuedate() + "'" +
            ", auditlogs='" + getAuditlogs() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
