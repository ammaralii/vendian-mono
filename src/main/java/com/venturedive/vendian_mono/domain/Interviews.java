package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Interviews.
 */
@Entity
@Table(name = "interviews")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "result", length = 255)
    private String result;

    @Size(max = 1000)
    @Column(name = "clientcomments", length = 1000)
    private String clientcomments;

    @Size(max = 1000)
    @Column(name = "lmcomments", length = 1000)
    private String lmcomments;

    @Column(name = "scheduledat")
    private Instant scheduledat;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "competency",
            "interviewsTracks",
            "questionsTracks",
            "questionsfrequencyperclienttrackTracks",
            "questionsfrequencypertrackTracks",
        },
        allowSetters = true
    )
    private Tracks track;

    @OneToMany(mappedBy = "interview")
    @JsonIgnoreProperties(value = { "interview", "project", "track" }, allowSetters = true)
    private Set<Questions> questionsInterviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Interviews id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return this.result;
    }

    public Interviews result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getClientcomments() {
        return this.clientcomments;
    }

    public Interviews clientcomments(String clientcomments) {
        this.setClientcomments(clientcomments);
        return this;
    }

    public void setClientcomments(String clientcomments) {
        this.clientcomments = clientcomments;
    }

    public String getLmcomments() {
        return this.lmcomments;
    }

    public Interviews lmcomments(String lmcomments) {
        this.setLmcomments(lmcomments);
        return this;
    }

    public void setLmcomments(String lmcomments) {
        this.lmcomments = lmcomments;
    }

    public Instant getScheduledat() {
        return this.scheduledat;
    }

    public Interviews scheduledat(Instant scheduledat) {
        this.setScheduledat(scheduledat);
        return this;
    }

    public void setScheduledat(Instant scheduledat) {
        this.scheduledat = scheduledat;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Interviews createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Interviews updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Interviews deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public Interviews employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public Interviews project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public Tracks getTrack() {
        return this.track;
    }

    public void setTrack(Tracks tracks) {
        this.track = tracks;
    }

    public Interviews track(Tracks tracks) {
        this.setTrack(tracks);
        return this;
    }

    public Set<Questions> getQuestionsInterviews() {
        return this.questionsInterviews;
    }

    public void setQuestionsInterviews(Set<Questions> questions) {
        if (this.questionsInterviews != null) {
            this.questionsInterviews.forEach(i -> i.setInterview(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setInterview(this));
        }
        this.questionsInterviews = questions;
    }

    public Interviews questionsInterviews(Set<Questions> questions) {
        this.setQuestionsInterviews(questions);
        return this;
    }

    public Interviews addQuestionsInterview(Questions questions) {
        this.questionsInterviews.add(questions);
        questions.setInterview(this);
        return this;
    }

    public Interviews removeQuestionsInterview(Questions questions) {
        this.questionsInterviews.remove(questions);
        questions.setInterview(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interviews)) {
            return false;
        }
        return id != null && id.equals(((Interviews) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interviews{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", clientcomments='" + getClientcomments() + "'" +
            ", lmcomments='" + getLmcomments() + "'" +
            ", scheduledat='" + getScheduledat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
