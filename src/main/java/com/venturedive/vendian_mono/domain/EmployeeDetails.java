package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeDetails.
 */
@Entity
@Table(name = "employee_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 9)
    @Column(name = "religion", length = 9)
    private String religion;

    @Size(max = 8)
    @Column(name = "maritalstatus", length = 8)
    private String maritalstatus;

    @Lob
    @Column(name = "cnic")
    private byte[] cnic;

    @Column(name = "cnic_content_type")
    private String cnicContentType;

    @Lob
    @Column(name = "cnicexpiry")
    private byte[] cnicexpiry;

    @Column(name = "cnicexpiry_content_type")
    private String cnicexpiryContentType;

    @Size(max = 3)
    @Column(name = "bloodgroup", length = 3)
    private String bloodgroup;

    @Lob
    @Column(name = "taxreturnfiler")
    private byte[] taxreturnfiler;

    @Column(name = "taxreturnfiler_content_type")
    private String taxreturnfilerContentType;

    @Lob
    @Column(name = "passportno")
    private byte[] passportno;

    @Column(name = "passportno_content_type")
    private String passportnoContentType;

    @Lob
    @Column(name = "passportexpiry")
    private byte[] passportexpiry;

    @Column(name = "passportexpiry_content_type")
    private String passportexpiryContentType;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Size(max = 255)
    @Column(name = "totaltenure", length = 255)
    private String totaltenure;

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

    public EmployeeDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReligion() {
        return this.religion;
    }

    public EmployeeDetails religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMaritalstatus() {
        return this.maritalstatus;
    }

    public EmployeeDetails maritalstatus(String maritalstatus) {
        this.setMaritalstatus(maritalstatus);
        return this;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public byte[] getCnic() {
        return this.cnic;
    }

    public EmployeeDetails cnic(byte[] cnic) {
        this.setCnic(cnic);
        return this;
    }

    public void setCnic(byte[] cnic) {
        this.cnic = cnic;
    }

    public String getCnicContentType() {
        return this.cnicContentType;
    }

    public EmployeeDetails cnicContentType(String cnicContentType) {
        this.cnicContentType = cnicContentType;
        return this;
    }

    public void setCnicContentType(String cnicContentType) {
        this.cnicContentType = cnicContentType;
    }

    public byte[] getCnicexpiry() {
        return this.cnicexpiry;
    }

    public EmployeeDetails cnicexpiry(byte[] cnicexpiry) {
        this.setCnicexpiry(cnicexpiry);
        return this;
    }

    public void setCnicexpiry(byte[] cnicexpiry) {
        this.cnicexpiry = cnicexpiry;
    }

    public String getCnicexpiryContentType() {
        return this.cnicexpiryContentType;
    }

    public EmployeeDetails cnicexpiryContentType(String cnicexpiryContentType) {
        this.cnicexpiryContentType = cnicexpiryContentType;
        return this;
    }

    public void setCnicexpiryContentType(String cnicexpiryContentType) {
        this.cnicexpiryContentType = cnicexpiryContentType;
    }

    public String getBloodgroup() {
        return this.bloodgroup;
    }

    public EmployeeDetails bloodgroup(String bloodgroup) {
        this.setBloodgroup(bloodgroup);
        return this;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public byte[] getTaxreturnfiler() {
        return this.taxreturnfiler;
    }

    public EmployeeDetails taxreturnfiler(byte[] taxreturnfiler) {
        this.setTaxreturnfiler(taxreturnfiler);
        return this;
    }

    public void setTaxreturnfiler(byte[] taxreturnfiler) {
        this.taxreturnfiler = taxreturnfiler;
    }

    public String getTaxreturnfilerContentType() {
        return this.taxreturnfilerContentType;
    }

    public EmployeeDetails taxreturnfilerContentType(String taxreturnfilerContentType) {
        this.taxreturnfilerContentType = taxreturnfilerContentType;
        return this;
    }

    public void setTaxreturnfilerContentType(String taxreturnfilerContentType) {
        this.taxreturnfilerContentType = taxreturnfilerContentType;
    }

    public byte[] getPassportno() {
        return this.passportno;
    }

    public EmployeeDetails passportno(byte[] passportno) {
        this.setPassportno(passportno);
        return this;
    }

    public void setPassportno(byte[] passportno) {
        this.passportno = passportno;
    }

    public String getPassportnoContentType() {
        return this.passportnoContentType;
    }

    public EmployeeDetails passportnoContentType(String passportnoContentType) {
        this.passportnoContentType = passportnoContentType;
        return this;
    }

    public void setPassportnoContentType(String passportnoContentType) {
        this.passportnoContentType = passportnoContentType;
    }

    public byte[] getPassportexpiry() {
        return this.passportexpiry;
    }

    public EmployeeDetails passportexpiry(byte[] passportexpiry) {
        this.setPassportexpiry(passportexpiry);
        return this;
    }

    public void setPassportexpiry(byte[] passportexpiry) {
        this.passportexpiry = passportexpiry;
    }

    public String getPassportexpiryContentType() {
        return this.passportexpiryContentType;
    }

    public EmployeeDetails passportexpiryContentType(String passportexpiryContentType) {
        this.passportexpiryContentType = passportexpiryContentType;
        return this;
    }

    public void setPassportexpiryContentType(String passportexpiryContentType) {
        this.passportexpiryContentType = passportexpiryContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeDetails createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeDetails updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getTotaltenure() {
        return this.totaltenure;
    }

    public EmployeeDetails totaltenure(String totaltenure) {
        this.setTotaltenure(totaltenure);
        return this;
    }

    public void setTotaltenure(String totaltenure) {
        this.totaltenure = totaltenure;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeDetails employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDetails)) {
            return false;
        }
        return id != null && id.equals(((EmployeeDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetails{" +
            "id=" + getId() +
            ", religion='" + getReligion() + "'" +
            ", maritalstatus='" + getMaritalstatus() + "'" +
            ", cnic='" + getCnic() + "'" +
            ", cnicContentType='" + getCnicContentType() + "'" +
            ", cnicexpiry='" + getCnicexpiry() + "'" +
            ", cnicexpiryContentType='" + getCnicexpiryContentType() + "'" +
            ", bloodgroup='" + getBloodgroup() + "'" +
            ", taxreturnfiler='" + getTaxreturnfiler() + "'" +
            ", taxreturnfilerContentType='" + getTaxreturnfilerContentType() + "'" +
            ", passportno='" + getPassportno() + "'" +
            ", passportnoContentType='" + getPassportnoContentType() + "'" +
            ", passportexpiry='" + getPassportexpiry() + "'" +
            ", passportexpiryContentType='" + getPassportexpiryContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", totaltenure='" + getTotaltenure() + "'" +
            "}";
    }
}
