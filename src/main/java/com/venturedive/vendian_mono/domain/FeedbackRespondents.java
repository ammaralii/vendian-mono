package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FeedbackRespondents.
 */
@Entity
@Table(name = "feedback_respondents")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackRespondents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "category", nullable = false)
    private Integer category;

    @Column(name = "hasresponded")
    private Boolean hasresponded;

    @Column(name = "respondedat")
    private Instant respondedat;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

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
    private Employees employee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employee", "linemanager", "feedbackrespondentsRequests" }, allowSetters = true)
    private FeedbackRequests request;

    @OneToMany(mappedBy = "respondent")
    @JsonIgnoreProperties(value = { "respondent" }, allowSetters = true)
    private Set<FeedbackEmails> feedbackemailsRespondents = new HashSet<>();

    @OneToMany(mappedBy = "respondent")
    @JsonIgnoreProperties(value = { "respondent", "question" }, allowSetters = true)
    private Set<FeedbackResponses> feedbackresponsesRespondents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedbackRespondents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategory() {
        return this.category;
    }

    public FeedbackRespondents category(Integer category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getHasresponded() {
        return this.hasresponded;
    }

    public FeedbackRespondents hasresponded(Boolean hasresponded) {
        this.setHasresponded(hasresponded);
        return this;
    }

    public void setHasresponded(Boolean hasresponded) {
        this.hasresponded = hasresponded;
    }

    public Instant getRespondedat() {
        return this.respondedat;
    }

    public FeedbackRespondents respondedat(Instant respondedat) {
        this.setRespondedat(respondedat);
        return this;
    }

    public void setRespondedat(Instant respondedat) {
        this.respondedat = respondedat;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public FeedbackRespondents createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public FeedbackRespondents updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public FeedbackRespondents employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public FeedbackRequests getRequest() {
        return this.request;
    }

    public void setRequest(FeedbackRequests feedbackRequests) {
        this.request = feedbackRequests;
    }

    public FeedbackRespondents request(FeedbackRequests feedbackRequests) {
        this.setRequest(feedbackRequests);
        return this;
    }

    public Set<FeedbackEmails> getFeedbackemailsRespondents() {
        return this.feedbackemailsRespondents;
    }

    public void setFeedbackemailsRespondents(Set<FeedbackEmails> feedbackEmails) {
        if (this.feedbackemailsRespondents != null) {
            this.feedbackemailsRespondents.forEach(i -> i.setRespondent(null));
        }
        if (feedbackEmails != null) {
            feedbackEmails.forEach(i -> i.setRespondent(this));
        }
        this.feedbackemailsRespondents = feedbackEmails;
    }

    public FeedbackRespondents feedbackemailsRespondents(Set<FeedbackEmails> feedbackEmails) {
        this.setFeedbackemailsRespondents(feedbackEmails);
        return this;
    }

    public FeedbackRespondents addFeedbackemailsRespondent(FeedbackEmails feedbackEmails) {
        this.feedbackemailsRespondents.add(feedbackEmails);
        feedbackEmails.setRespondent(this);
        return this;
    }

    public FeedbackRespondents removeFeedbackemailsRespondent(FeedbackEmails feedbackEmails) {
        this.feedbackemailsRespondents.remove(feedbackEmails);
        feedbackEmails.setRespondent(null);
        return this;
    }

    public Set<FeedbackResponses> getFeedbackresponsesRespondents() {
        return this.feedbackresponsesRespondents;
    }

    public void setFeedbackresponsesRespondents(Set<FeedbackResponses> feedbackResponses) {
        if (this.feedbackresponsesRespondents != null) {
            this.feedbackresponsesRespondents.forEach(i -> i.setRespondent(null));
        }
        if (feedbackResponses != null) {
            feedbackResponses.forEach(i -> i.setRespondent(this));
        }
        this.feedbackresponsesRespondents = feedbackResponses;
    }

    public FeedbackRespondents feedbackresponsesRespondents(Set<FeedbackResponses> feedbackResponses) {
        this.setFeedbackresponsesRespondents(feedbackResponses);
        return this;
    }

    public FeedbackRespondents addFeedbackresponsesRespondent(FeedbackResponses feedbackResponses) {
        this.feedbackresponsesRespondents.add(feedbackResponses);
        feedbackResponses.setRespondent(this);
        return this;
    }

    public FeedbackRespondents removeFeedbackresponsesRespondent(FeedbackResponses feedbackResponses) {
        this.feedbackresponsesRespondents.remove(feedbackResponses);
        feedbackResponses.setRespondent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackRespondents)) {
            return false;
        }
        return id != null && id.equals(((FeedbackRespondents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackRespondents{" +
            "id=" + getId() +
            ", category=" + getCategory() +
            ", hasresponded='" + getHasresponded() + "'" +
            ", respondedat='" + getRespondedat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
