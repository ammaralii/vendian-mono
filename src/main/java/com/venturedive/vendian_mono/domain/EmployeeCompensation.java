package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeCompensation.
 */
@Entity
@Table(name = "employee_compensation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCompensation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "amount", nullable = false)
    private byte[] amount;

    @NotNull
    @Column(name = "amount_content_type", nullable = false)
    private String amountContentType;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Lob
    @Column(name = "ec_reason")
    private byte[] ecReason;

    @Column(name = "ec_reason_content_type")
    private String ecReasonContentType;

    @NotNull
    @Size(max = 5)
    @Column(name = "type", length = 5, nullable = false)
    private String type;

    @Column(name = "commitmentuntil")
    private Instant commitmentuntil;

    @Size(max = 65535)
    @Column(name = "comments", length = 65535)
    private String comments;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Size(max = 255)
    @Column(name = "reasoncomments", length = 255)
    private String reasoncomments;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeecompensationReasons" }, allowSetters = true)
    private Reasons reason;

    @OneToMany(mappedBy = "employeecompensation")
    @JsonIgnoreProperties(value = { "benefit", "employeecompensation" }, allowSetters = true)
    private Set<CompensationBenefits> compensationbenefitsEmployeecompensations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeCompensation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAmount() {
        return this.amount;
    }

    public EmployeeCompensation amount(byte[] amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(byte[] amount) {
        this.amount = amount;
    }

    public String getAmountContentType() {
        return this.amountContentType;
    }

    public EmployeeCompensation amountContentType(String amountContentType) {
        this.amountContentType = amountContentType;
        return this;
    }

    public void setAmountContentType(String amountContentType) {
        this.amountContentType = amountContentType;
    }

    public Instant getDate() {
        return this.date;
    }

    public EmployeeCompensation date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public byte[] getEcReason() {
        return this.ecReason;
    }

    public EmployeeCompensation ecReason(byte[] ecReason) {
        this.setEcReason(ecReason);
        return this;
    }

    public void setEcReason(byte[] ecReason) {
        this.ecReason = ecReason;
    }

    public String getEcReasonContentType() {
        return this.ecReasonContentType;
    }

    public EmployeeCompensation ecReasonContentType(String ecReasonContentType) {
        this.ecReasonContentType = ecReasonContentType;
        return this;
    }

    public void setEcReasonContentType(String ecReasonContentType) {
        this.ecReasonContentType = ecReasonContentType;
    }

    public String getType() {
        return this.type;
    }

    public EmployeeCompensation type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCommitmentuntil() {
        return this.commitmentuntil;
    }

    public EmployeeCompensation commitmentuntil(Instant commitmentuntil) {
        this.setCommitmentuntil(commitmentuntil);
        return this;
    }

    public void setCommitmentuntil(Instant commitmentuntil) {
        this.commitmentuntil = commitmentuntil;
    }

    public String getComments() {
        return this.comments;
    }

    public EmployeeCompensation comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeCompensation createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeCompensation updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getReasoncomments() {
        return this.reasoncomments;
    }

    public EmployeeCompensation reasoncomments(String reasoncomments) {
        this.setReasoncomments(reasoncomments);
        return this;
    }

    public void setReasoncomments(String reasoncomments) {
        this.reasoncomments = reasoncomments;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeCompensation employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Reasons getReason() {
        return this.reason;
    }

    public void setReason(Reasons reasons) {
        this.reason = reasons;
    }

    public EmployeeCompensation reason(Reasons reasons) {
        this.setReason(reasons);
        return this;
    }

    public Set<CompensationBenefits> getCompensationbenefitsEmployeecompensations() {
        return this.compensationbenefitsEmployeecompensations;
    }

    public void setCompensationbenefitsEmployeecompensations(Set<CompensationBenefits> compensationBenefits) {
        if (this.compensationbenefitsEmployeecompensations != null) {
            this.compensationbenefitsEmployeecompensations.forEach(i -> i.setEmployeecompensation(null));
        }
        if (compensationBenefits != null) {
            compensationBenefits.forEach(i -> i.setEmployeecompensation(this));
        }
        this.compensationbenefitsEmployeecompensations = compensationBenefits;
    }

    public EmployeeCompensation compensationbenefitsEmployeecompensations(Set<CompensationBenefits> compensationBenefits) {
        this.setCompensationbenefitsEmployeecompensations(compensationBenefits);
        return this;
    }

    public EmployeeCompensation addCompensationbenefitsEmployeecompensation(CompensationBenefits compensationBenefits) {
        this.compensationbenefitsEmployeecompensations.add(compensationBenefits);
        compensationBenefits.setEmployeecompensation(this);
        return this;
    }

    public EmployeeCompensation removeCompensationbenefitsEmployeecompensation(CompensationBenefits compensationBenefits) {
        this.compensationbenefitsEmployeecompensations.remove(compensationBenefits);
        compensationBenefits.setEmployeecompensation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeCompensation)) {
            return false;
        }
        return id != null && id.equals(((EmployeeCompensation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCompensation{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", amountContentType='" + getAmountContentType() + "'" +
            ", date='" + getDate() + "'" +
            ", ecReason='" + getEcReason() + "'" +
            ", ecReasonContentType='" + getEcReasonContentType() + "'" +
            ", type='" + getType() + "'" +
            ", commitmentuntil='" + getCommitmentuntil() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", reasoncomments='" + getReasoncomments() + "'" +
            "}";
    }
}
