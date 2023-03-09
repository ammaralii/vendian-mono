package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FeedbackQuestions.
 */
@Entity
@Table(name = "feedback_questions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackQuestions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 65535)
    @Column(name = "question", length = 65535)
    private String question;

    @Column(name = "isdefault")
    private Boolean isdefault;

    @Size(max = 255)
    @Column(name = "area", length = 255)
    private String area;

    @Size(max = 255)
    @Column(name = "competency", length = 255)
    private String competency;

    @Column(name = "category")
    private Integer category;

    @Column(name = "isskill")
    private Boolean isskill;

    @Column(name = "skilltype")
    private Integer skilltype;

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

    @OneToMany(mappedBy = "question")
    @JsonIgnoreProperties(value = { "respondent", "question" }, allowSetters = true)
    private Set<FeedbackResponses> feedbackresponsesQuestions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedbackQuestions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public FeedbackQuestions question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getIsdefault() {
        return this.isdefault;
    }

    public FeedbackQuestions isdefault(Boolean isdefault) {
        this.setIsdefault(isdefault);
        return this;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getArea() {
        return this.area;
    }

    public FeedbackQuestions area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompetency() {
        return this.competency;
    }

    public FeedbackQuestions competency(String competency) {
        this.setCompetency(competency);
        return this;
    }

    public void setCompetency(String competency) {
        this.competency = competency;
    }

    public Integer getCategory() {
        return this.category;
    }

    public FeedbackQuestions category(Integer category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getIsskill() {
        return this.isskill;
    }

    public FeedbackQuestions isskill(Boolean isskill) {
        this.setIsskill(isskill);
        return this;
    }

    public void setIsskill(Boolean isskill) {
        this.isskill = isskill;
    }

    public Integer getSkilltype() {
        return this.skilltype;
    }

    public FeedbackQuestions skilltype(Integer skilltype) {
        this.setSkilltype(skilltype);
        return this;
    }

    public void setSkilltype(Integer skilltype) {
        this.skilltype = skilltype;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public FeedbackQuestions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public FeedbackQuestions updatedat(Instant updatedat) {
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

    public FeedbackQuestions employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Set<FeedbackResponses> getFeedbackresponsesQuestions() {
        return this.feedbackresponsesQuestions;
    }

    public void setFeedbackresponsesQuestions(Set<FeedbackResponses> feedbackResponses) {
        if (this.feedbackresponsesQuestions != null) {
            this.feedbackresponsesQuestions.forEach(i -> i.setQuestion(null));
        }
        if (feedbackResponses != null) {
            feedbackResponses.forEach(i -> i.setQuestion(this));
        }
        this.feedbackresponsesQuestions = feedbackResponses;
    }

    public FeedbackQuestions feedbackresponsesQuestions(Set<FeedbackResponses> feedbackResponses) {
        this.setFeedbackresponsesQuestions(feedbackResponses);
        return this;
    }

    public FeedbackQuestions addFeedbackresponsesQuestion(FeedbackResponses feedbackResponses) {
        this.feedbackresponsesQuestions.add(feedbackResponses);
        feedbackResponses.setQuestion(this);
        return this;
    }

    public FeedbackQuestions removeFeedbackresponsesQuestion(FeedbackResponses feedbackResponses) {
        this.feedbackresponsesQuestions.remove(feedbackResponses);
        feedbackResponses.setQuestion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackQuestions)) {
            return false;
        }
        return id != null && id.equals(((FeedbackQuestions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackQuestions{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", isdefault='" + getIsdefault() + "'" +
            ", area='" + getArea() + "'" +
            ", competency='" + getCompetency() + "'" +
            ", category=" + getCategory() +
            ", isskill='" + getIsskill() + "'" +
            ", skilltype=" + getSkilltype() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
