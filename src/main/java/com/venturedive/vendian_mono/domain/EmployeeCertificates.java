package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeCertificates.
 */
@Entity
@Table(name = "employee_certificates")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCertificates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "certificateno", length = 255)
    private String certificateno;

    @Size(max = 255)
    @Column(name = "issuingbody", length = 255)
    private String issuingbody;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "validtill")
    private Instant validtill;

    @Size(max = 255)
    @Column(name = "certificatecompetency", length = 255)
    private String certificatecompetency;

    @Column(name = "deletedat")
    private Instant deletedat;

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

    public EmployeeCertificates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EmployeeCertificates name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateno() {
        return this.certificateno;
    }

    public EmployeeCertificates certificateno(String certificateno) {
        this.setCertificateno(certificateno);
        return this;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public String getIssuingbody() {
        return this.issuingbody;
    }

    public EmployeeCertificates issuingbody(String issuingbody) {
        this.setIssuingbody(issuingbody);
        return this;
    }

    public void setIssuingbody(String issuingbody) {
        this.issuingbody = issuingbody;
    }

    public Instant getDate() {
        return this.date;
    }

    public EmployeeCertificates date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeCertificates createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeCertificates updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getValidtill() {
        return this.validtill;
    }

    public EmployeeCertificates validtill(Instant validtill) {
        this.setValidtill(validtill);
        return this;
    }

    public void setValidtill(Instant validtill) {
        this.validtill = validtill;
    }

    public String getCertificatecompetency() {
        return this.certificatecompetency;
    }

    public EmployeeCertificates certificatecompetency(String certificatecompetency) {
        this.setCertificatecompetency(certificatecompetency);
        return this;
    }

    public void setCertificatecompetency(String certificatecompetency) {
        this.certificatecompetency = certificatecompetency;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public EmployeeCertificates deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeCertificates employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeCertificates)) {
            return false;
        }
        return id != null && id.equals(((EmployeeCertificates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCertificates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", certificateno='" + getCertificateno() + "'" +
            ", issuingbody='" + getIssuingbody() + "'" +
            ", date='" + getDate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", validtill='" + getValidtill() + "'" +
            ", certificatecompetency='" + getCertificatecompetency() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
