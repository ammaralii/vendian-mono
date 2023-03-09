package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmploymentHistory.
 */
@Entity
@Table(name = "employment_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmploymentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "positiontitle", length = 255, nullable = false)
    private String positiontitle;

    @NotNull
    @Size(max = 255)
    @Column(name = "companyname", length = 255, nullable = false)
    private String companyname;

    @Size(max = 255)
    @Column(name = "grade", length = 255)
    private String grade;

    @Size(max = 65535)
    @Column(name = "jobdescription", length = 65535)
    private String jobdescription;

    @Size(max = 255)
    @Column(name = "city", length = 255)
    private String city;

    @Size(max = 255)
    @Column(name = "country", length = 255)
    private String country;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private Instant startdate;

    @NotNull
    @Column(name = "enddate", nullable = false)
    private Instant enddate;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Size(max = 65535)
    @Column(name = "reasonforleaving", length = 65535)
    private String reasonforleaving;

    @Lob
    @Column(name = "grosssalary")
    private byte[] grosssalary;

    @Column(name = "grosssalary_content_type")
    private String grosssalaryContentType;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmploymentHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositiontitle() {
        return this.positiontitle;
    }

    public EmploymentHistory positiontitle(String positiontitle) {
        this.setPositiontitle(positiontitle);
        return this;
    }

    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    public String getCompanyname() {
        return this.companyname;
    }

    public EmploymentHistory companyname(String companyname) {
        this.setCompanyname(companyname);
        return this;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getGrade() {
        return this.grade;
    }

    public EmploymentHistory grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getJobdescription() {
        return this.jobdescription;
    }

    public EmploymentHistory jobdescription(String jobdescription) {
        this.setJobdescription(jobdescription);
        return this;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getCity() {
        return this.city;
    }

    public EmploymentHistory city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public EmploymentHistory country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public EmploymentHistory startdate(Instant startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public EmploymentHistory enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmploymentHistory createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmploymentHistory updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getReasonforleaving() {
        return this.reasonforleaving;
    }

    public EmploymentHistory reasonforleaving(String reasonforleaving) {
        this.setReasonforleaving(reasonforleaving);
        return this;
    }

    public void setReasonforleaving(String reasonforleaving) {
        this.reasonforleaving = reasonforleaving;
    }

    public byte[] getGrosssalary() {
        return this.grosssalary;
    }

    public EmploymentHistory grosssalary(byte[] grosssalary) {
        this.setGrosssalary(grosssalary);
        return this;
    }

    public void setGrosssalary(byte[] grosssalary) {
        this.grosssalary = grosssalary;
    }

    public String getGrosssalaryContentType() {
        return this.grosssalaryContentType;
    }

    public EmploymentHistory grosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
        return this;
    }

    public void setGrosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmploymentHistory employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmploymentHistory)) {
            return false;
        }
        return id != null && id.equals(((EmploymentHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmploymentHistory{" +
            "id=" + getId() +
            ", positiontitle='" + getPositiontitle() + "'" +
            ", companyname='" + getCompanyname() + "'" +
            ", grade='" + getGrade() + "'" +
            ", jobdescription='" + getJobdescription() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", reasonforleaving='" + getReasonforleaving() + "'" +
            ", grosssalary='" + getGrosssalary() + "'" +
            ", grosssalaryContentType='" + getGrosssalaryContentType() + "'" +
            "}";
    }
}
