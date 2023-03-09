package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeEducation.
 */
@Entity
@Table(name = "employee_education")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeEducation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "institute", length = 255, nullable = false)
    private String institute;

    @Size(max = 255)
    @Column(name = "major", length = 255)
    private String major;

    @Size(max = 255)
    @Column(name = "degree", length = 255)
    private String degree;

    @Size(max = 255)
    @Column(name = "value", length = 255)
    private String value;

    @Size(max = 255)
    @Column(name = "city", length = 255)
    private String city;

    @Size(max = 255)
    @Column(name = "province", length = 255)
    private String province;

    @Size(max = 255)
    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "datefrom")
    private Instant datefrom;

    @Column(name = "dateto")
    private Instant dateto;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employeeeducationQualificationtypes" }, allowSetters = true)
    private QualificationTypes qualificationtype;

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

    public EmployeeEducation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitute() {
        return this.institute;
    }

    public EmployeeEducation institute(String institute) {
        this.setInstitute(institute);
        return this;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getMajor() {
        return this.major;
    }

    public EmployeeEducation major(String major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return this.degree;
    }

    public EmployeeEducation degree(String degree) {
        this.setDegree(degree);
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getValue() {
        return this.value;
    }

    public EmployeeEducation value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCity() {
        return this.city;
    }

    public EmployeeEducation city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public EmployeeEducation province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return this.country;
    }

    public EmployeeEducation country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getDatefrom() {
        return this.datefrom;
    }

    public EmployeeEducation datefrom(Instant datefrom) {
        this.setDatefrom(datefrom);
        return this;
    }

    public void setDatefrom(Instant datefrom) {
        this.datefrom = datefrom;
    }

    public Instant getDateto() {
        return this.dateto;
    }

    public EmployeeEducation dateto(Instant dateto) {
        this.setDateto(dateto);
        return this;
    }

    public void setDateto(Instant dateto) {
        this.dateto = dateto;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeEducation createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeEducation updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public QualificationTypes getQualificationtype() {
        return this.qualificationtype;
    }

    public void setQualificationtype(QualificationTypes qualificationTypes) {
        this.qualificationtype = qualificationTypes;
    }

    public EmployeeEducation qualificationtype(QualificationTypes qualificationTypes) {
        this.setQualificationtype(qualificationTypes);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeEducation employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeEducation)) {
            return false;
        }
        return id != null && id.equals(((EmployeeEducation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeEducation{" +
            "id=" + getId() +
            ", institute='" + getInstitute() + "'" +
            ", major='" + getMajor() + "'" +
            ", degree='" + getDegree() + "'" +
            ", value='" + getValue() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", datefrom='" + getDatefrom() + "'" +
            ", dateto='" + getDateto() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
