package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeProjects.
 */
@Entity
@Table(name = "employee_projects")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjects implements Serializable {

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

    @Column(name = "roleid")
    private Integer roleid;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @Column(name = "extendedenddate")
    private Instant extendedenddate;

    @Size(max = 65535)
    @Column(name = "probability", length = 65535)
    private String probability;

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

    @OneToMany(mappedBy = "employeeproject")
    @JsonIgnoreProperties(value = { "employeeproject", "projectrole" }, allowSetters = true)
    private Set<EmployeeProjectRoles> employeeprojectrolesEmployeeprojects = new HashSet<>();

    @OneToMany(mappedBy = "employeeproject")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsEmployeeprojects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeProjects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public EmployeeProjects status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public EmployeeProjects type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public EmployeeProjects startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public EmployeeProjects enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Boolean getAllocation() {
        return this.allocation;
    }

    public EmployeeProjects allocation(Boolean allocation) {
        this.setAllocation(allocation);
        return this;
    }

    public void setAllocation(Boolean allocation) {
        this.allocation = allocation;
    }

    public String getBilled() {
        return this.billed;
    }

    public EmployeeProjects billed(String billed) {
        this.setBilled(billed);
        return this;
    }

    public void setBilled(String billed) {
        this.billed = billed;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeProjects createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeProjects updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getRoleid() {
        return this.roleid;
    }

    public EmployeeProjects roleid(Integer roleid) {
        this.setRoleid(roleid);
        return this;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getNotes() {
        return this.notes;
    }

    public EmployeeProjects notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getExtendedenddate() {
        return this.extendedenddate;
    }

    public EmployeeProjects extendedenddate(Instant extendedenddate) {
        this.setExtendedenddate(extendedenddate);
        return this;
    }

    public void setExtendedenddate(Instant extendedenddate) {
        this.extendedenddate = extendedenddate;
    }

    public String getProbability() {
        return this.probability;
    }

    public EmployeeProjects probability(String probability) {
        this.setProbability(probability);
        return this;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeProjects employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public EmployeeProjects project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public Employees getAssignor() {
        return this.assignor;
    }

    public void setAssignor(Employees employees) {
        this.assignor = employees;
    }

    public EmployeeProjects assignor(Employees employees) {
        this.setAssignor(employees);
        return this;
    }

    public Set<EmployeeProjectRoles> getEmployeeprojectrolesEmployeeprojects() {
        return this.employeeprojectrolesEmployeeprojects;
    }

    public void setEmployeeprojectrolesEmployeeprojects(Set<EmployeeProjectRoles> employeeProjectRoles) {
        if (this.employeeprojectrolesEmployeeprojects != null) {
            this.employeeprojectrolesEmployeeprojects.forEach(i -> i.setEmployeeproject(null));
        }
        if (employeeProjectRoles != null) {
            employeeProjectRoles.forEach(i -> i.setEmployeeproject(this));
        }
        this.employeeprojectrolesEmployeeprojects = employeeProjectRoles;
    }

    public EmployeeProjects employeeprojectrolesEmployeeprojects(Set<EmployeeProjectRoles> employeeProjectRoles) {
        this.setEmployeeprojectrolesEmployeeprojects(employeeProjectRoles);
        return this;
    }

    public EmployeeProjects addEmployeeprojectrolesEmployeeproject(EmployeeProjectRoles employeeProjectRoles) {
        this.employeeprojectrolesEmployeeprojects.add(employeeProjectRoles);
        employeeProjectRoles.setEmployeeproject(this);
        return this;
    }

    public EmployeeProjects removeEmployeeprojectrolesEmployeeproject(EmployeeProjectRoles employeeProjectRoles) {
        this.employeeprojectrolesEmployeeprojects.remove(employeeProjectRoles);
        employeeProjectRoles.setEmployeeproject(null);
        return this;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsEmployeeprojects() {
        return this.zemployeeprojectsEmployeeprojects;
    }

    public void setZemployeeprojectsEmployeeprojects(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsEmployeeprojects != null) {
            this.zemployeeprojectsEmployeeprojects.forEach(i -> i.setEmployeeproject(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setEmployeeproject(this));
        }
        this.zemployeeprojectsEmployeeprojects = zEmployeeProjects;
    }

    public EmployeeProjects zemployeeprojectsEmployeeprojects(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsEmployeeprojects(zEmployeeProjects);
        return this;
    }

    public EmployeeProjects addZemployeeprojectsEmployeeproject(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEmployeeprojects.add(zEmployeeProjects);
        zEmployeeProjects.setEmployeeproject(this);
        return this;
    }

    public EmployeeProjects removeZemployeeprojectsEmployeeproject(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEmployeeprojects.remove(zEmployeeProjects);
        zEmployeeProjects.setEmployeeproject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProjects)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProjects) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjects{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", allocation='" + getAllocation() + "'" +
            ", billed='" + getBilled() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", roleid=" + getRoleid() +
            ", notes='" + getNotes() + "'" +
            ", extendedenddate='" + getExtendedenddate() + "'" +
            ", probability='" + getProbability() + "'" +
            "}";
    }
}
