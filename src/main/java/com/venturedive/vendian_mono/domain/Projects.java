package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "isdelete")
    private Boolean isdelete;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Size(max = 255)
    @Column(name = "colorcode", length = 255)
    private String colorcode;

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

    @Column(name = "isdeleted")
    private Integer isdeleted;

    @Column(name = "approvedresources")
    private Integer approvedresources;

    @Column(name = "releaseat")
    private Instant releaseat;

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

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employeejobinfoBusinessunits", "employeesBusinessunits", "projectsBusinessunits" },
        allowSetters = true
    )
    private BusinessUnits businessunit;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(
        value = { "employee", "project", "assignor", "employeeprojectrolesEmployeeprojects", "zemployeeprojectsEmployeeprojects" },
        allowSetters = true
    )
    private Set<EmployeeProjects> employeeprojectsProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "employee", "project", "track", "questionsInterviews" }, allowSetters = true)
    private Set<Interviews> interviewsProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(
        value = {
            "project",
            "allowedafterduedateby",
            "architect",
            "projectmanager",
            "ratings",
            "employeeprojectratingsProjectcycles",
            "performancecycles",
        },
        allowSetters = true
    )
    private Set<ProjectCycles> projectcyclesProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "projectrole", "project", "employee" }, allowSetters = true)
    private Set<ProjectLeadership> projectleadershipProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "interview", "project", "track" }, allowSetters = true)
    private Set<Questions> questionsProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "project", "track" }, allowSetters = true)
    private Set<QuestionsFrequencyPerClientTrack> questionsfrequencyperclienttrackProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "worklog", "project" }, allowSetters = true)
    private Set<WorkLogDetails> worklogdetailsProjects = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Projects name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public Projects isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getIsdelete() {
        return this.isdelete;
    }

    public Projects isdelete(Boolean isdelete) {
        this.setIsdelete(isdelete);
        return this;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public Projects startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public Projects enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public String getColorcode() {
        return this.colorcode;
    }

    public Projects colorcode(String colorcode) {
        this.setColorcode(colorcode);
        return this;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Projects createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Projects updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getDeliverymanagerid() {
        return this.deliverymanagerid;
    }

    public Projects deliverymanagerid(Integer deliverymanagerid) {
        this.setDeliverymanagerid(deliverymanagerid);
        return this;
    }

    public void setDeliverymanagerid(Integer deliverymanagerid) {
        this.deliverymanagerid = deliverymanagerid;
    }

    public Integer getArchitectid() {
        return this.architectid;
    }

    public Projects architectid(Integer architectid) {
        this.setArchitectid(architectid);
        return this;
    }

    public void setArchitectid(Integer architectid) {
        this.architectid = architectid;
    }

    public Integer getIsdeleted() {
        return this.isdeleted;
    }

    public Projects isdeleted(Integer isdeleted) {
        this.setIsdeleted(isdeleted);
        return this;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getApprovedresources() {
        return this.approvedresources;
    }

    public Projects approvedresources(Integer approvedresources) {
        this.setApprovedresources(approvedresources);
        return this;
    }

    public void setApprovedresources(Integer approvedresources) {
        this.approvedresources = approvedresources;
    }

    public Instant getReleaseat() {
        return this.releaseat;
    }

    public Projects releaseat(Instant releaseat) {
        this.setReleaseat(releaseat);
        return this;
    }

    public void setReleaseat(Instant releaseat) {
        this.releaseat = releaseat;
    }

    public Employees getProjectmanager() {
        return this.projectmanager;
    }

    public void setProjectmanager(Employees employees) {
        this.projectmanager = employees;
    }

    public Projects projectmanager(Employees employees) {
        this.setProjectmanager(employees);
        return this;
    }

    public BusinessUnits getBusinessunit() {
        return this.businessunit;
    }

    public void setBusinessunit(BusinessUnits businessUnits) {
        this.businessunit = businessUnits;
    }

    public Projects businessunit(BusinessUnits businessUnits) {
        this.setBusinessunit(businessUnits);
        return this;
    }

    public Set<EmployeeProjects> getEmployeeprojectsProjects() {
        return this.employeeprojectsProjects;
    }

    public void setEmployeeprojectsProjects(Set<EmployeeProjects> employeeProjects) {
        if (this.employeeprojectsProjects != null) {
            this.employeeprojectsProjects.forEach(i -> i.setProject(null));
        }
        if (employeeProjects != null) {
            employeeProjects.forEach(i -> i.setProject(this));
        }
        this.employeeprojectsProjects = employeeProjects;
    }

    public Projects employeeprojectsProjects(Set<EmployeeProjects> employeeProjects) {
        this.setEmployeeprojectsProjects(employeeProjects);
        return this;
    }

    public Projects addEmployeeprojectsProject(EmployeeProjects employeeProjects) {
        this.employeeprojectsProjects.add(employeeProjects);
        employeeProjects.setProject(this);
        return this;
    }

    public Projects removeEmployeeprojectsProject(EmployeeProjects employeeProjects) {
        this.employeeprojectsProjects.remove(employeeProjects);
        employeeProjects.setProject(null);
        return this;
    }

    public Set<Interviews> getInterviewsProjects() {
        return this.interviewsProjects;
    }

    public void setInterviewsProjects(Set<Interviews> interviews) {
        if (this.interviewsProjects != null) {
            this.interviewsProjects.forEach(i -> i.setProject(null));
        }
        if (interviews != null) {
            interviews.forEach(i -> i.setProject(this));
        }
        this.interviewsProjects = interviews;
    }

    public Projects interviewsProjects(Set<Interviews> interviews) {
        this.setInterviewsProjects(interviews);
        return this;
    }

    public Projects addInterviewsProject(Interviews interviews) {
        this.interviewsProjects.add(interviews);
        interviews.setProject(this);
        return this;
    }

    public Projects removeInterviewsProject(Interviews interviews) {
        this.interviewsProjects.remove(interviews);
        interviews.setProject(null);
        return this;
    }

    public Set<ProjectCycles> getProjectcyclesProjects() {
        return this.projectcyclesProjects;
    }

    public void setProjectcyclesProjects(Set<ProjectCycles> projectCycles) {
        if (this.projectcyclesProjects != null) {
            this.projectcyclesProjects.forEach(i -> i.setProject(null));
        }
        if (projectCycles != null) {
            projectCycles.forEach(i -> i.setProject(this));
        }
        this.projectcyclesProjects = projectCycles;
    }

    public Projects projectcyclesProjects(Set<ProjectCycles> projectCycles) {
        this.setProjectcyclesProjects(projectCycles);
        return this;
    }

    public Projects addProjectcyclesProject(ProjectCycles projectCycles) {
        this.projectcyclesProjects.add(projectCycles);
        projectCycles.setProject(this);
        return this;
    }

    public Projects removeProjectcyclesProject(ProjectCycles projectCycles) {
        this.projectcyclesProjects.remove(projectCycles);
        projectCycles.setProject(null);
        return this;
    }

    public Set<ProjectLeadership> getProjectleadershipProjects() {
        return this.projectleadershipProjects;
    }

    public void setProjectleadershipProjects(Set<ProjectLeadership> projectLeaderships) {
        if (this.projectleadershipProjects != null) {
            this.projectleadershipProjects.forEach(i -> i.setProject(null));
        }
        if (projectLeaderships != null) {
            projectLeaderships.forEach(i -> i.setProject(this));
        }
        this.projectleadershipProjects = projectLeaderships;
    }

    public Projects projectleadershipProjects(Set<ProjectLeadership> projectLeaderships) {
        this.setProjectleadershipProjects(projectLeaderships);
        return this;
    }

    public Projects addProjectleadershipProject(ProjectLeadership projectLeadership) {
        this.projectleadershipProjects.add(projectLeadership);
        projectLeadership.setProject(this);
        return this;
    }

    public Projects removeProjectleadershipProject(ProjectLeadership projectLeadership) {
        this.projectleadershipProjects.remove(projectLeadership);
        projectLeadership.setProject(null);
        return this;
    }

    public Set<Questions> getQuestionsProjects() {
        return this.questionsProjects;
    }

    public void setQuestionsProjects(Set<Questions> questions) {
        if (this.questionsProjects != null) {
            this.questionsProjects.forEach(i -> i.setProject(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setProject(this));
        }
        this.questionsProjects = questions;
    }

    public Projects questionsProjects(Set<Questions> questions) {
        this.setQuestionsProjects(questions);
        return this;
    }

    public Projects addQuestionsProject(Questions questions) {
        this.questionsProjects.add(questions);
        questions.setProject(this);
        return this;
    }

    public Projects removeQuestionsProject(Questions questions) {
        this.questionsProjects.remove(questions);
        questions.setProject(null);
        return this;
    }

    public Set<QuestionsFrequencyPerClientTrack> getQuestionsfrequencyperclienttrackProjects() {
        return this.questionsfrequencyperclienttrackProjects;
    }

    public void setQuestionsfrequencyperclienttrackProjects(Set<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTracks) {
        if (this.questionsfrequencyperclienttrackProjects != null) {
            this.questionsfrequencyperclienttrackProjects.forEach(i -> i.setProject(null));
        }
        if (questionsFrequencyPerClientTracks != null) {
            questionsFrequencyPerClientTracks.forEach(i -> i.setProject(this));
        }
        this.questionsfrequencyperclienttrackProjects = questionsFrequencyPerClientTracks;
    }

    public Projects questionsfrequencyperclienttrackProjects(Set<QuestionsFrequencyPerClientTrack> questionsFrequencyPerClientTracks) {
        this.setQuestionsfrequencyperclienttrackProjects(questionsFrequencyPerClientTracks);
        return this;
    }

    public Projects addQuestionsfrequencyperclienttrackProject(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        this.questionsfrequencyperclienttrackProjects.add(questionsFrequencyPerClientTrack);
        questionsFrequencyPerClientTrack.setProject(this);
        return this;
    }

    public Projects removeQuestionsfrequencyperclienttrackProject(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        this.questionsfrequencyperclienttrackProjects.remove(questionsFrequencyPerClientTrack);
        questionsFrequencyPerClientTrack.setProject(null);
        return this;
    }

    public Set<WorkLogDetails> getWorklogdetailsProjects() {
        return this.worklogdetailsProjects;
    }

    public void setWorklogdetailsProjects(Set<WorkLogDetails> workLogDetails) {
        if (this.worklogdetailsProjects != null) {
            this.worklogdetailsProjects.forEach(i -> i.setProject(null));
        }
        if (workLogDetails != null) {
            workLogDetails.forEach(i -> i.setProject(this));
        }
        this.worklogdetailsProjects = workLogDetails;
    }

    public Projects worklogdetailsProjects(Set<WorkLogDetails> workLogDetails) {
        this.setWorklogdetailsProjects(workLogDetails);
        return this;
    }

    public Projects addWorklogdetailsProject(WorkLogDetails workLogDetails) {
        this.worklogdetailsProjects.add(workLogDetails);
        workLogDetails.setProject(this);
        return this;
    }

    public Projects removeWorklogdetailsProject(WorkLogDetails workLogDetails) {
        this.worklogdetailsProjects.remove(workLogDetails);
        workLogDetails.setProject(null);
        return this;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsProjects() {
        return this.zemployeeprojectsProjects;
    }

    public void setZemployeeprojectsProjects(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsProjects != null) {
            this.zemployeeprojectsProjects.forEach(i -> i.setProject(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setProject(this));
        }
        this.zemployeeprojectsProjects = zEmployeeProjects;
    }

    public Projects zemployeeprojectsProjects(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsProjects(zEmployeeProjects);
        return this;
    }

    public Projects addZemployeeprojectsProject(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsProjects.add(zEmployeeProjects);
        zEmployeeProjects.setProject(this);
        return this;
    }

    public Projects removeZemployeeprojectsProject(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsProjects.remove(zEmployeeProjects);
        zEmployeeProjects.setProject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", isdelete='" + getIsdelete() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", colorcode='" + getColorcode() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deliverymanagerid=" + getDeliverymanagerid() +
            ", architectid=" + getArchitectid() +
            ", isdeleted=" + getIsdeleted() +
            ", approvedresources=" + getApprovedresources() +
            ", releaseat='" + getReleaseat() + "'" +
            "}";
    }
}
