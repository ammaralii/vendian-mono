package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimRequestViews.
 */
@Entity
@Table(name = "claim_request_views")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimRequestViews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "costcenter", length = 255)
    private String costcenter;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "amountreleased", precision = 21, scale = 2)
    private BigDecimal amountreleased;

    @Size(max = 255)
    @Column(name = "designation", length = 255)
    private String designation;

    @Size(max = 255)
    @Column(name = "department", length = 255)
    private String department;

    @Size(max = 255)
    @Column(name = "location", length = 255)
    private String location;

    @Size(max = 255)
    @Column(name = "manager", length = 255)
    private String manager;

    @Column(name = "createdat")
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimRequestViews id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCostcenter() {
        return this.costcenter;
    }

    public ClaimRequestViews costcenter(String costcenter) {
        this.setCostcenter(costcenter);
        return this;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

    public String getComments() {
        return this.comments;
    }

    public ClaimRequestViews comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAmountreleased() {
        return this.amountreleased;
    }

    public ClaimRequestViews amountreleased(BigDecimal amountreleased) {
        this.setAmountreleased(amountreleased);
        return this;
    }

    public void setAmountreleased(BigDecimal amountreleased) {
        this.amountreleased = amountreleased;
    }

    public String getDesignation() {
        return this.designation;
    }

    public ClaimRequestViews designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return this.department;
    }

    public ClaimRequestViews department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return this.location;
    }

    public ClaimRequestViews location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManager() {
        return this.manager;
    }

    public ClaimRequestViews manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimRequestViews createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimRequestViews updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public ClaimRequestViews employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimRequestViews)) {
            return false;
        }
        return id != null && id.equals(((ClaimRequestViews) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimRequestViews{" +
            "id=" + getId() +
            ", costcenter='" + getCostcenter() + "'" +
            ", comments='" + getComments() + "'" +
            ", amountreleased=" + getAmountreleased() +
            ", designation='" + getDesignation() + "'" +
            ", department='" + getDepartment() + "'" +
            ", location='" + getLocation() + "'" +
            ", manager='" + getManager() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
