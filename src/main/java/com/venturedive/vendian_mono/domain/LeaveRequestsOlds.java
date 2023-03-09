package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveRequestsOlds.
 */
@Entity
@Table(name = "leave_requests_olds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveRequestsOlds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Size(max = 65535)
    @Column(name = "requesternote", length = 65535)
    private String requesternote;

    @Size(max = 65535)
    @Column(name = "managernote", length = 65535)
    private String managernote;

    @Size(max = 255)
    @Column(name = "currentstatus", length = 255)
    private String currentstatus;

    @Column(name = "leavescanceled")
    private Boolean leavescanceled;

    @Column(name = "requestdate")
    private Instant requestdate;

    @Size(max = 255)
    @Column(name = "linkstring", length = 255)
    private String linkstring;

    @Column(name = "linkused")
    private Boolean linkused;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "ishalfday")
    private Boolean ishalfday;

    @Column(name = "actiondate")
    private Instant actiondate;

    @Size(max = 255)
    @Column(name = "lmstatus", length = 255)
    private String lmstatus;

    @Column(name = "pmstatus")
    private String pmstatus;

    @Column(name = "isbench")
    private Boolean isbench;

    @Column(name = "isescalated")
    private Boolean isescalated;

    @Column(name = "iscommented")
    private Boolean iscommented;

    @Column(name = "isreminded")
    private Boolean isreminded;

    @Column(name = "isnotified")
    private Boolean isnotified;

    @Column(name = "isremindedhr")
    private Boolean isremindedhr;

    @Column(name = "isdm")
    private Boolean isdm;

    @ManyToOne
    @JsonIgnoreProperties(value = { "leaverequestsoldsLeavetypes" }, allowSetters = true)
    private LeaveTypesOlds leavetype;

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
    private Employees manager;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveRequestsOlds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public LeaveRequestsOlds startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public LeaveRequestsOlds enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getRequesternote() {
        return this.requesternote;
    }

    public LeaveRequestsOlds requesternote(String requesternote) {
        this.setRequesternote(requesternote);
        return this;
    }

    public void setRequesternote(String requesternote) {
        this.requesternote = requesternote;
    }

    public String getManagernote() {
        return this.managernote;
    }

    public LeaveRequestsOlds managernote(String managernote) {
        this.setManagernote(managernote);
        return this;
    }

    public void setManagernote(String managernote) {
        this.managernote = managernote;
    }

    public String getCurrentstatus() {
        return this.currentstatus;
    }

    public LeaveRequestsOlds currentstatus(String currentstatus) {
        this.setCurrentstatus(currentstatus);
        return this;
    }

    public void setCurrentstatus(String currentstatus) {
        this.currentstatus = currentstatus;
    }

    public Boolean getLeavescanceled() {
        return this.leavescanceled;
    }

    public LeaveRequestsOlds leavescanceled(Boolean leavescanceled) {
        this.setLeavescanceled(leavescanceled);
        return this;
    }

    public void setLeavescanceled(Boolean leavescanceled) {
        this.leavescanceled = leavescanceled;
    }

    public Instant getRequestdate() {
        return this.requestdate;
    }

    public LeaveRequestsOlds requestdate(Instant requestdate) {
        this.setRequestdate(requestdate);
        return this;
    }

    public void setRequestdate(Instant requestdate) {
        this.requestdate = requestdate;
    }

    public String getLinkstring() {
        return this.linkstring;
    }

    public LeaveRequestsOlds linkstring(String linkstring) {
        this.setLinkstring(linkstring);
        return this;
    }

    public void setLinkstring(String linkstring) {
        this.linkstring = linkstring;
    }

    public Boolean getLinkused() {
        return this.linkused;
    }

    public LeaveRequestsOlds linkused(Boolean linkused) {
        this.setLinkused(linkused);
        return this;
    }

    public void setLinkused(Boolean linkused) {
        this.linkused = linkused;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public LeaveRequestsOlds createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public LeaveRequestsOlds updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Boolean getIshalfday() {
        return this.ishalfday;
    }

    public LeaveRequestsOlds ishalfday(Boolean ishalfday) {
        this.setIshalfday(ishalfday);
        return this;
    }

    public void setIshalfday(Boolean ishalfday) {
        this.ishalfday = ishalfday;
    }

    public Instant getActiondate() {
        return this.actiondate;
    }

    public LeaveRequestsOlds actiondate(Instant actiondate) {
        this.setActiondate(actiondate);
        return this;
    }

    public void setActiondate(Instant actiondate) {
        this.actiondate = actiondate;
    }

    public String getLmstatus() {
        return this.lmstatus;
    }

    public LeaveRequestsOlds lmstatus(String lmstatus) {
        this.setLmstatus(lmstatus);
        return this;
    }

    public void setLmstatus(String lmstatus) {
        this.lmstatus = lmstatus;
    }

    public String getPmstatus() {
        return this.pmstatus;
    }

    public LeaveRequestsOlds pmstatus(String pmstatus) {
        this.setPmstatus(pmstatus);
        return this;
    }

    public void setPmstatus(String pmstatus) {
        this.pmstatus = pmstatus;
    }

    public Boolean getIsbench() {
        return this.isbench;
    }

    public LeaveRequestsOlds isbench(Boolean isbench) {
        this.setIsbench(isbench);
        return this;
    }

    public void setIsbench(Boolean isbench) {
        this.isbench = isbench;
    }

    public Boolean getIsescalated() {
        return this.isescalated;
    }

    public LeaveRequestsOlds isescalated(Boolean isescalated) {
        this.setIsescalated(isescalated);
        return this;
    }

    public void setIsescalated(Boolean isescalated) {
        this.isescalated = isescalated;
    }

    public Boolean getIscommented() {
        return this.iscommented;
    }

    public LeaveRequestsOlds iscommented(Boolean iscommented) {
        this.setIscommented(iscommented);
        return this;
    }

    public void setIscommented(Boolean iscommented) {
        this.iscommented = iscommented;
    }

    public Boolean getIsreminded() {
        return this.isreminded;
    }

    public LeaveRequestsOlds isreminded(Boolean isreminded) {
        this.setIsreminded(isreminded);
        return this;
    }

    public void setIsreminded(Boolean isreminded) {
        this.isreminded = isreminded;
    }

    public Boolean getIsnotified() {
        return this.isnotified;
    }

    public LeaveRequestsOlds isnotified(Boolean isnotified) {
        this.setIsnotified(isnotified);
        return this;
    }

    public void setIsnotified(Boolean isnotified) {
        this.isnotified = isnotified;
    }

    public Boolean getIsremindedhr() {
        return this.isremindedhr;
    }

    public LeaveRequestsOlds isremindedhr(Boolean isremindedhr) {
        this.setIsremindedhr(isremindedhr);
        return this;
    }

    public void setIsremindedhr(Boolean isremindedhr) {
        this.isremindedhr = isremindedhr;
    }

    public Boolean getIsdm() {
        return this.isdm;
    }

    public LeaveRequestsOlds isdm(Boolean isdm) {
        this.setIsdm(isdm);
        return this;
    }

    public void setIsdm(Boolean isdm) {
        this.isdm = isdm;
    }

    public LeaveTypesOlds getLeavetype() {
        return this.leavetype;
    }

    public void setLeavetype(LeaveTypesOlds leaveTypesOlds) {
        this.leavetype = leaveTypesOlds;
    }

    public LeaveRequestsOlds leavetype(LeaveTypesOlds leaveTypesOlds) {
        this.setLeavetype(leaveTypesOlds);
        return this;
    }

    public Employees getManager() {
        return this.manager;
    }

    public void setManager(Employees employees) {
        this.manager = employees;
    }

    public LeaveRequestsOlds manager(Employees employees) {
        this.setManager(employees);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public LeaveRequestsOlds employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveRequestsOlds)) {
            return false;
        }
        return id != null && id.equals(((LeaveRequestsOlds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequestsOlds{" +
            "id=" + getId() +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", requesternote='" + getRequesternote() + "'" +
            ", managernote='" + getManagernote() + "'" +
            ", currentstatus='" + getCurrentstatus() + "'" +
            ", leavescanceled='" + getLeavescanceled() + "'" +
            ", requestdate='" + getRequestdate() + "'" +
            ", linkstring='" + getLinkstring() + "'" +
            ", linkused='" + getLinkused() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", ishalfday='" + getIshalfday() + "'" +
            ", actiondate='" + getActiondate() + "'" +
            ", lmstatus='" + getLmstatus() + "'" +
            ", pmstatus='" + getPmstatus() + "'" +
            ", isbench='" + getIsbench() + "'" +
            ", isescalated='" + getIsescalated() + "'" +
            ", iscommented='" + getIscommented() + "'" +
            ", isreminded='" + getIsreminded() + "'" +
            ", isnotified='" + getIsnotified() + "'" +
            ", isremindedhr='" + getIsremindedhr() + "'" +
            ", isdm='" + getIsdm() + "'" +
            "}";
    }
}
