package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeJobInfo.
 */
@Entity
@Table(name = "employee_job_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeJobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotNull
    @Size(max = 255)
    @Column(name = "grade", length = 255, nullable = false)
    private String grade;

    @NotNull
    @Size(max = 255)
    @Column(name = "subgrade", length = 255, nullable = false)
    private String subgrade;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "location")
    private Integer location;

    @Lob
    @Column(name = "grosssalary")
    private byte[] grosssalary;

    @Column(name = "grosssalary_content_type")
    private String grosssalaryContentType;

    @Lob
    @Column(name = "fuelallowance")
    private byte[] fuelallowance;

    @Column(name = "fuelallowance_content_type")
    private String fuelallowanceContentType;

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
            "designationjobdescriptionsDesignations",
            "employeejobinfoDesignations",
            "employeesDesignations",
            "userratingsremarksDesignationafterpromotions",
            "userratingsremarksDesignationafterredesignations",
        },
        allowSetters = true
    )
    private Designations designation;

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
    private Employees reviewmanager;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "division", "employeejobinfoDepartments", "employeesDepartments" }, allowSetters = true)
    private Departments department;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeejobinfoEmploymenttypes", "employeesEmploymenttypes" }, allowSetters = true)
    private EmploymentTypes employmenttype;

    @ManyToOne
    @JsonIgnoreProperties(value = { "document", "designation", "employeejobinfoJobdescriptions" }, allowSetters = true)
    private DesignationJobDescriptions jobdescription;

    @ManyToOne
    @JsonIgnoreProperties(value = { "departmentsDivisions", "employeejobinfoDivisions", "employeesDivisions" }, allowSetters = true)
    private Divisions division;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employeejobinfoBusinessunits", "employeesBusinessunits", "projectsBusinessunits" },
        allowSetters = true
    )
    private BusinessUnits businessunit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeejobinfoCompetencies", "employeesCompetencies", "tracksCompetencies" }, allowSetters = true)
    private Competencies competency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeJobInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public EmployeeJobInfo title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return this.grade;
    }

    public EmployeeJobInfo grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubgrade() {
        return this.subgrade;
    }

    public EmployeeJobInfo subgrade(String subgrade) {
        this.setSubgrade(subgrade);
        return this;
    }

    public void setSubgrade(String subgrade) {
        this.subgrade = subgrade;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public EmployeeJobInfo startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public EmployeeJobInfo enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeJobInfo createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeJobInfo updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getLocation() {
        return this.location;
    }

    public EmployeeJobInfo location(Integer location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public byte[] getGrosssalary() {
        return this.grosssalary;
    }

    public EmployeeJobInfo grosssalary(byte[] grosssalary) {
        this.setGrosssalary(grosssalary);
        return this;
    }

    public void setGrosssalary(byte[] grosssalary) {
        this.grosssalary = grosssalary;
    }

    public String getGrosssalaryContentType() {
        return this.grosssalaryContentType;
    }

    public EmployeeJobInfo grosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
        return this;
    }

    public void setGrosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
    }

    public byte[] getFuelallowance() {
        return this.fuelallowance;
    }

    public EmployeeJobInfo fuelallowance(byte[] fuelallowance) {
        this.setFuelallowance(fuelallowance);
        return this;
    }

    public void setFuelallowance(byte[] fuelallowance) {
        this.fuelallowance = fuelallowance;
    }

    public String getFuelallowanceContentType() {
        return this.fuelallowanceContentType;
    }

    public EmployeeJobInfo fuelallowanceContentType(String fuelallowanceContentType) {
        this.fuelallowanceContentType = fuelallowanceContentType;
        return this;
    }

    public void setFuelallowanceContentType(String fuelallowanceContentType) {
        this.fuelallowanceContentType = fuelallowanceContentType;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeJobInfo employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Designations getDesignation() {
        return this.designation;
    }

    public void setDesignation(Designations designations) {
        this.designation = designations;
    }

    public EmployeeJobInfo designation(Designations designations) {
        this.setDesignation(designations);
        return this;
    }

    public Employees getReviewmanager() {
        return this.reviewmanager;
    }

    public void setReviewmanager(Employees employees) {
        this.reviewmanager = employees;
    }

    public EmployeeJobInfo reviewmanager(Employees employees) {
        this.setReviewmanager(employees);
        return this;
    }

    public Employees getManager() {
        return this.manager;
    }

    public void setManager(Employees employees) {
        this.manager = employees;
    }

    public EmployeeJobInfo manager(Employees employees) {
        this.setManager(employees);
        return this;
    }

    public Departments getDepartment() {
        return this.department;
    }

    public void setDepartment(Departments departments) {
        this.department = departments;
    }

    public EmployeeJobInfo department(Departments departments) {
        this.setDepartment(departments);
        return this;
    }

    public EmploymentTypes getEmploymenttype() {
        return this.employmenttype;
    }

    public void setEmploymenttype(EmploymentTypes employmentTypes) {
        this.employmenttype = employmentTypes;
    }

    public EmployeeJobInfo employmenttype(EmploymentTypes employmentTypes) {
        this.setEmploymenttype(employmentTypes);
        return this;
    }

    public DesignationJobDescriptions getJobdescription() {
        return this.jobdescription;
    }

    public void setJobdescription(DesignationJobDescriptions designationJobDescriptions) {
        this.jobdescription = designationJobDescriptions;
    }

    public EmployeeJobInfo jobdescription(DesignationJobDescriptions designationJobDescriptions) {
        this.setJobdescription(designationJobDescriptions);
        return this;
    }

    public Divisions getDivision() {
        return this.division;
    }

    public void setDivision(Divisions divisions) {
        this.division = divisions;
    }

    public EmployeeJobInfo division(Divisions divisions) {
        this.setDivision(divisions);
        return this;
    }

    public BusinessUnits getBusinessunit() {
        return this.businessunit;
    }

    public void setBusinessunit(BusinessUnits businessUnits) {
        this.businessunit = businessUnits;
    }

    public EmployeeJobInfo businessunit(BusinessUnits businessUnits) {
        this.setBusinessunit(businessUnits);
        return this;
    }

    public Competencies getCompetency() {
        return this.competency;
    }

    public void setCompetency(Competencies competencies) {
        this.competency = competencies;
    }

    public EmployeeJobInfo competency(Competencies competencies) {
        this.setCompetency(competencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeJobInfo)) {
            return false;
        }
        return id != null && id.equals(((EmployeeJobInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeJobInfo{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", grade='" + getGrade() + "'" +
            ", subgrade='" + getSubgrade() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", location=" + getLocation() +
            ", grosssalary='" + getGrosssalary() + "'" +
            ", grosssalaryContentType='" + getGrosssalaryContentType() + "'" +
            ", fuelallowance='" + getFuelallowance() + "'" +
            ", fuelallowanceContentType='" + getFuelallowanceContentType() + "'" +
            "}";
    }
}
