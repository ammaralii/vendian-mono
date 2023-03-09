package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FeedbackRequests.
 */
@Entity
@Table(name = "feedback_requests")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isreportavailable")
    private Boolean isreportavailable;

    @Size(max = 255)
    @Column(name = "reportpath", length = 255)
    private String reportpath;

    @Column(name = "approvedat")
    private Instant approvedat;

    @Column(name = "expiredat")
    private Instant expiredat;

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
    private Employees linemanager;

    @OneToMany(mappedBy = "request")
    @JsonIgnoreProperties(
        value = { "employee", "request", "feedbackemailsRespondents", "feedbackresponsesRespondents" },
        allowSetters = true
    )
    private Set<FeedbackRespondents> feedbackrespondentsRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedbackRequests id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return this.status;
    }

    public FeedbackRequests status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsreportavailable() {
        return this.isreportavailable;
    }

    public FeedbackRequests isreportavailable(Boolean isreportavailable) {
        this.setIsreportavailable(isreportavailable);
        return this;
    }

    public void setIsreportavailable(Boolean isreportavailable) {
        this.isreportavailable = isreportavailable;
    }

    public String getReportpath() {
        return this.reportpath;
    }

    public FeedbackRequests reportpath(String reportpath) {
        this.setReportpath(reportpath);
        return this;
    }

    public void setReportpath(String reportpath) {
        this.reportpath = reportpath;
    }

    public Instant getApprovedat() {
        return this.approvedat;
    }

    public FeedbackRequests approvedat(Instant approvedat) {
        this.setApprovedat(approvedat);
        return this;
    }

    public void setApprovedat(Instant approvedat) {
        this.approvedat = approvedat;
    }

    public Instant getExpiredat() {
        return this.expiredat;
    }

    public FeedbackRequests expiredat(Instant expiredat) {
        this.setExpiredat(expiredat);
        return this;
    }

    public void setExpiredat(Instant expiredat) {
        this.expiredat = expiredat;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public FeedbackRequests createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public FeedbackRequests updatedat(Instant updatedat) {
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

    public FeedbackRequests employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Employees getLinemanager() {
        return this.linemanager;
    }

    public void setLinemanager(Employees employees) {
        this.linemanager = employees;
    }

    public FeedbackRequests linemanager(Employees employees) {
        this.setLinemanager(employees);
        return this;
    }

    public Set<FeedbackRespondents> getFeedbackrespondentsRequests() {
        return this.feedbackrespondentsRequests;
    }

    public void setFeedbackrespondentsRequests(Set<FeedbackRespondents> feedbackRespondents) {
        if (this.feedbackrespondentsRequests != null) {
            this.feedbackrespondentsRequests.forEach(i -> i.setRequest(null));
        }
        if (feedbackRespondents != null) {
            feedbackRespondents.forEach(i -> i.setRequest(this));
        }
        this.feedbackrespondentsRequests = feedbackRespondents;
    }

    public FeedbackRequests feedbackrespondentsRequests(Set<FeedbackRespondents> feedbackRespondents) {
        this.setFeedbackrespondentsRequests(feedbackRespondents);
        return this;
    }

    public FeedbackRequests addFeedbackrespondentsRequest(FeedbackRespondents feedbackRespondents) {
        this.feedbackrespondentsRequests.add(feedbackRespondents);
        feedbackRespondents.setRequest(this);
        return this;
    }

    public FeedbackRequests removeFeedbackrespondentsRequest(FeedbackRespondents feedbackRespondents) {
        this.feedbackrespondentsRequests.remove(feedbackRespondents);
        feedbackRespondents.setRequest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackRequests)) {
            return false;
        }
        return id != null && id.equals(((FeedbackRequests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackRequests{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", isreportavailable='" + getIsreportavailable() + "'" +
            ", reportpath='" + getReportpath() + "'" +
            ", approvedat='" + getApprovedat() + "'" +
            ", expiredat='" + getExpiredat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
