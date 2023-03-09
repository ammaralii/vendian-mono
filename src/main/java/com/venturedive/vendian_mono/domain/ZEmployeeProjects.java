package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ZEmployeeProjects.
 */
@Entity
@Table(name = "z_employee_projects")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZEmployeeProjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @Size(max = 12)
    @Column(name = "type", length = 12)
    private String type;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "allocation")
    private Boolean allocation;

    @Size(max = 15)
    @Column(name = "billed", length = 15)
    private String billed;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @Column(name = "deliverymanagerid")
    private Integer deliverymanagerid;

    @Column(name = "architectid")
    private Integer architectid;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @Column(name = "previousenddate")
    private Instant previousenddate;

    @Column(name = "data")
    private String data;

    @Column(name = "extendedenddate")
    private Instant extendedenddate;

    @Size(max = 65535)
    @Column(name = "probability", length = 65535)
    private String probability;

    @ManyToOne
    @JsonIgnoreProperties(value = { "zemployeeprojectsEvents" }, allowSetters = true)
    private Events event;

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
        value = { "employee", "project", "assignor", "employeeprojectrolesEmployeeprojects", "zemployeeprojectsEmployeeprojects" },
        allowSetters = true
    )
    private EmployeeProjects employeeproject;

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
    private Employees assignor;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ZEmployeeProjects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public ZEmployeeProjects status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public ZEmployeeProjects type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public ZEmployeeProjects startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public ZEmployeeProjects enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getName() {
        return this.name;
    }

    public ZEmployeeProjects name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllocation() {
        return this.allocation;
    }

    public ZEmployeeProjects allocation(Boolean allocation) {
        this.setAllocation(allocation);
        return this;
    }

    public void setAllocation(Boolean allocation) {
        this.allocation = allocation;
    }

    public String getBilled() {
        return this.billed;
    }

    public ZEmployeeProjects billed(String billed) {
        this.setBilled(billed);
        return this;
    }

    public void setBilled(String billed) {
        this.billed = billed;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ZEmployeeProjects createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ZEmployeeProjects updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getDeliverymanagerid() {
        return this.deliverymanagerid;
    }

    public ZEmployeeProjects deliverymanagerid(Integer deliverymanagerid) {
        this.setDeliverymanagerid(deliverymanagerid);
        return this;
    }

    public void setDeliverymanagerid(Integer deliverymanagerid) {
        this.deliverymanagerid = deliverymanagerid;
    }

    public Integer getArchitectid() {
        return this.architectid;
    }

    public ZEmployeeProjects architectid(Integer architectid) {
        this.setArchitectid(architectid);
        return this;
    }

    public void setArchitectid(Integer architectid) {
        this.architectid = architectid;
    }

    public String getNotes() {
        return this.notes;
    }

    public ZEmployeeProjects notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getPreviousenddate() {
        return this.previousenddate;
    }

    public ZEmployeeProjects previousenddate(Instant previousenddate) {
        this.setPreviousenddate(previousenddate);
        return this;
    }

    public void setPreviousenddate(Instant previousenddate) {
        this.previousenddate = previousenddate;
    }

    public String getData() {
        return this.data;
    }

    public ZEmployeeProjects data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getExtendedenddate() {
        return this.extendedenddate;
    }

    public ZEmployeeProjects extendedenddate(Instant extendedenddate) {
        this.setExtendedenddate(extendedenddate);
        return this;
    }

    public void setExtendedenddate(Instant extendedenddate) {
        this.extendedenddate = extendedenddate;
    }

    public String getProbability() {
        return this.probability;
    }

    public ZEmployeeProjects probability(String probability) {
        this.setProbability(probability);
        return this;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public Events getEvent() {
        return this.event;
    }

    public void setEvent(Events events) {
        this.event = events;
    }

    public ZEmployeeProjects event(Events events) {
        this.setEvent(events);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public ZEmployeeProjects employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public ZEmployeeProjects project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public EmployeeProjects getEmployeeproject() {
        return this.employeeproject;
    }

    public void setEmployeeproject(EmployeeProjects employeeProjects) {
        this.employeeproject = employeeProjects;
    }

    public ZEmployeeProjects employeeproject(EmployeeProjects employeeProjects) {
        this.setEmployeeproject(employeeProjects);
        return this;
    }

    public Employees getAssignor() {
        return this.assignor;
    }

    public void setAssignor(Employees employees) {
        this.assignor = employees;
    }

    public ZEmployeeProjects assignor(Employees employees) {
        this.setAssignor(employees);
        return this;
    }

    public Employees getProjectmanager() {
        return this.projectmanager;
    }

    public void setProjectmanager(Employees employees) {
        this.projectmanager = employees;
    }

    public ZEmployeeProjects projectmanager(Employees employees) {
        this.setProjectmanager(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZEmployeeProjects)) {
            return false;
        }
        return id != null && id.equals(((ZEmployeeProjects) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZEmployeeProjects{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", name='" + getName() + "'" +
            ", allocation='" + getAllocation() + "'" +
            ", billed='" + getBilled() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deliverymanagerid=" + getDeliverymanagerid() +
            ", architectid=" + getArchitectid() +
            ", notes='" + getNotes() + "'" +
            ", previousenddate='" + getPreviousenddate() + "'" +
            ", data='" + getData() + "'" +
            ", extendedenddate='" + getExtendedenddate() + "'" +
            ", probability='" + getProbability() + "'" +
            "}";
    }
}
