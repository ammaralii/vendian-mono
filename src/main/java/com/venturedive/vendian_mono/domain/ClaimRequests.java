package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClaimRequests.
 */
@Entity
@Table(name = "claim_requests")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "projectid")
    private Integer projectid;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "amountreleased")
    private Integer amountreleased;

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

    @Column(name = "updatedat")
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

    @OneToMany(mappedBy = "claimrequest")
    @JsonIgnoreProperties(value = { "status", "claimrequest" }, allowSetters = true)
    private Set<ClaimApprovers> claimapproversClaimrequests = new HashSet<>();

    @OneToMany(mappedBy = "claimrequest")
    @JsonIgnoreProperties(value = { "claimrequest", "claimtype" }, allowSetters = true)
    private Set<ClaimDetails> claimdetailsClaimrequests = new HashSet<>();

    @OneToMany(mappedBy = "claimrequest")
    @JsonIgnoreProperties(value = { "claimrequest" }, allowSetters = true)
    private Set<ClaimImages> claimimagesClaimrequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClaimRequests id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return this.projectid;
    }

    public ClaimRequests projectid(Integer projectid) {
        this.setProjectid(projectid);
        return this;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getComments() {
        return this.comments;
    }

    public ClaimRequests comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getAmountreleased() {
        return this.amountreleased;
    }

    public ClaimRequests amountreleased(Integer amountreleased) {
        this.setAmountreleased(amountreleased);
        return this;
    }

    public void setAmountreleased(Integer amountreleased) {
        this.amountreleased = amountreleased;
    }

    public String getDesignation() {
        return this.designation;
    }

    public ClaimRequests designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return this.department;
    }

    public ClaimRequests department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return this.location;
    }

    public ClaimRequests location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManager() {
        return this.manager;
    }

    public ClaimRequests manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ClaimRequests createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ClaimRequests updatedat(Instant updatedat) {
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

    public ClaimRequests employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Set<ClaimApprovers> getClaimapproversClaimrequests() {
        return this.claimapproversClaimrequests;
    }

    public void setClaimapproversClaimrequests(Set<ClaimApprovers> claimApprovers) {
        if (this.claimapproversClaimrequests != null) {
            this.claimapproversClaimrequests.forEach(i -> i.setClaimrequest(null));
        }
        if (claimApprovers != null) {
            claimApprovers.forEach(i -> i.setClaimrequest(this));
        }
        this.claimapproversClaimrequests = claimApprovers;
    }

    public ClaimRequests claimapproversClaimrequests(Set<ClaimApprovers> claimApprovers) {
        this.setClaimapproversClaimrequests(claimApprovers);
        return this;
    }

    public ClaimRequests addClaimapproversClaimrequest(ClaimApprovers claimApprovers) {
        this.claimapproversClaimrequests.add(claimApprovers);
        claimApprovers.setClaimrequest(this);
        return this;
    }

    public ClaimRequests removeClaimapproversClaimrequest(ClaimApprovers claimApprovers) {
        this.claimapproversClaimrequests.remove(claimApprovers);
        claimApprovers.setClaimrequest(null);
        return this;
    }

    public Set<ClaimDetails> getClaimdetailsClaimrequests() {
        return this.claimdetailsClaimrequests;
    }

    public void setClaimdetailsClaimrequests(Set<ClaimDetails> claimDetails) {
        if (this.claimdetailsClaimrequests != null) {
            this.claimdetailsClaimrequests.forEach(i -> i.setClaimrequest(null));
        }
        if (claimDetails != null) {
            claimDetails.forEach(i -> i.setClaimrequest(this));
        }
        this.claimdetailsClaimrequests = claimDetails;
    }

    public ClaimRequests claimdetailsClaimrequests(Set<ClaimDetails> claimDetails) {
        this.setClaimdetailsClaimrequests(claimDetails);
        return this;
    }

    public ClaimRequests addClaimdetailsClaimrequest(ClaimDetails claimDetails) {
        this.claimdetailsClaimrequests.add(claimDetails);
        claimDetails.setClaimrequest(this);
        return this;
    }

    public ClaimRequests removeClaimdetailsClaimrequest(ClaimDetails claimDetails) {
        this.claimdetailsClaimrequests.remove(claimDetails);
        claimDetails.setClaimrequest(null);
        return this;
    }

    public Set<ClaimImages> getClaimimagesClaimrequests() {
        return this.claimimagesClaimrequests;
    }

    public void setClaimimagesClaimrequests(Set<ClaimImages> claimImages) {
        if (this.claimimagesClaimrequests != null) {
            this.claimimagesClaimrequests.forEach(i -> i.setClaimrequest(null));
        }
        if (claimImages != null) {
            claimImages.forEach(i -> i.setClaimrequest(this));
        }
        this.claimimagesClaimrequests = claimImages;
    }

    public ClaimRequests claimimagesClaimrequests(Set<ClaimImages> claimImages) {
        this.setClaimimagesClaimrequests(claimImages);
        return this;
    }

    public ClaimRequests addClaimimagesClaimrequest(ClaimImages claimImages) {
        this.claimimagesClaimrequests.add(claimImages);
        claimImages.setClaimrequest(this);
        return this;
    }

    public ClaimRequests removeClaimimagesClaimrequest(ClaimImages claimImages) {
        this.claimimagesClaimrequests.remove(claimImages);
        claimImages.setClaimrequest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimRequests)) {
            return false;
        }
        return id != null && id.equals(((ClaimRequests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimRequests{" +
            "id=" + getId() +
            ", projectid=" + getProjectid() +
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
