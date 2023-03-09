package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeFamilyInfo.
 */
@Entity
@Table(name = "employee_family_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeFamilyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "fullname", length = 255, nullable = false)
    private String fullname;

    @NotNull
    @Size(max = 255)
    @Column(name = "relationship", length = 255, nullable = false)
    private String relationship;

    @Size(max = 255)
    @Column(name = "contactno", length = 255)
    private String contactno;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "registeredinmedical")
    private Boolean registeredinmedical;

    @Lob
    @Column(name = "cnic")
    private byte[] cnic;

    @Column(name = "cnic_content_type")
    private String cnicContentType;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Size(max = 255)
    @Column(name = "medicalpolicyno", length = 255)
    private String medicalpolicyno;

    @NotNull
    @Size(max = 6)
    @Column(name = "gender", length = 6, nullable = false)
    private String gender;

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

    public EmployeeFamilyInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public EmployeeFamilyInfo fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public EmployeeFamilyInfo relationship(String relationship) {
        this.setRelationship(relationship);
        return this;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getContactno() {
        return this.contactno;
    }

    public EmployeeFamilyInfo contactno(String contactno) {
        this.setContactno(contactno);
        return this;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmail() {
        return this.email;
    }

    public EmployeeFamilyInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDob() {
        return this.dob;
    }

    public EmployeeFamilyInfo dob(Instant dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Boolean getRegisteredinmedical() {
        return this.registeredinmedical;
    }

    public EmployeeFamilyInfo registeredinmedical(Boolean registeredinmedical) {
        this.setRegisteredinmedical(registeredinmedical);
        return this;
    }

    public void setRegisteredinmedical(Boolean registeredinmedical) {
        this.registeredinmedical = registeredinmedical;
    }

    public byte[] getCnic() {
        return this.cnic;
    }

    public EmployeeFamilyInfo cnic(byte[] cnic) {
        this.setCnic(cnic);
        return this;
    }

    public void setCnic(byte[] cnic) {
        this.cnic = cnic;
    }

    public String getCnicContentType() {
        return this.cnicContentType;
    }

    public EmployeeFamilyInfo cnicContentType(String cnicContentType) {
        this.cnicContentType = cnicContentType;
        return this;
    }

    public void setCnicContentType(String cnicContentType) {
        this.cnicContentType = cnicContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeFamilyInfo createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeFamilyInfo updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getMedicalpolicyno() {
        return this.medicalpolicyno;
    }

    public EmployeeFamilyInfo medicalpolicyno(String medicalpolicyno) {
        this.setMedicalpolicyno(medicalpolicyno);
        return this;
    }

    public void setMedicalpolicyno(String medicalpolicyno) {
        this.medicalpolicyno = medicalpolicyno;
    }

    public String getGender() {
        return this.gender;
    }

    public EmployeeFamilyInfo gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeFamilyInfo employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeFamilyInfo)) {
            return false;
        }
        return id != null && id.equals(((EmployeeFamilyInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeFamilyInfo{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", relationship='" + getRelationship() + "'" +
            ", contactno='" + getContactno() + "'" +
            ", email='" + getEmail() + "'" +
            ", dob='" + getDob() + "'" +
            ", registeredinmedical='" + getRegisteredinmedical() + "'" +
            ", cnic='" + getCnic() + "'" +
            ", cnicContentType='" + getCnicContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", medicalpolicyno='" + getMedicalpolicyno() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
