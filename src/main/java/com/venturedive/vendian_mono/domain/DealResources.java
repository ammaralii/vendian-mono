package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealResources.
 */
@Entity
@Table(name = "deal_resources")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "firstname", length = 255)
    private String firstname;

    @Size(max = 255)
    @Column(name = "lastname", length = 255)
    private String lastname;

    @Column(name = "joiningdate")
    private Instant joiningdate;

    @Column(name = "externalexpyears")
    private Integer externalexpyears;

    @Column(name = "externalexpmonths")
    private Integer externalexpmonths;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @NotNull
    @Size(max = 14)
    @Column(name = "type", length = 14, nullable = false)
    private String type;

    @NotNull
    @Column(name = "isactive", nullable = false)
    private Boolean isactive;

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

    @OneToMany(mappedBy = "resource")
    @JsonIgnoreProperties(
        value = { "dealrequirement", "resource", "resourcestatus", "systemstatus", "dealresourceeventlogsMatchingresources" },
        allowSetters = true
    )
    private Set<DealRequirementMatchingResources> dealrequirementmatchingresourcesResources = new HashSet<>();

    @OneToMany(mappedBy = "resource")
    @JsonIgnoreProperties(value = { "resource", "skill" }, allowSetters = true)
    private Set<DealResourceSkills> dealresourceskillsResources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealResources id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public DealResources firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public DealResources lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Instant getJoiningdate() {
        return this.joiningdate;
    }

    public DealResources joiningdate(Instant joiningdate) {
        this.setJoiningdate(joiningdate);
        return this;
    }

    public void setJoiningdate(Instant joiningdate) {
        this.joiningdate = joiningdate;
    }

    public Integer getExternalexpyears() {
        return this.externalexpyears;
    }

    public DealResources externalexpyears(Integer externalexpyears) {
        this.setExternalexpyears(externalexpyears);
        return this;
    }

    public void setExternalexpyears(Integer externalexpyears) {
        this.externalexpyears = externalexpyears;
    }

    public Integer getExternalexpmonths() {
        return this.externalexpmonths;
    }

    public DealResources externalexpmonths(Integer externalexpmonths) {
        this.setExternalexpmonths(externalexpmonths);
        return this;
    }

    public void setExternalexpmonths(Integer externalexpmonths) {
        this.externalexpmonths = externalexpmonths;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealResources createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public DealResources updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getType() {
        return this.type;
    }

    public DealResources type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public DealResources isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public DealResources employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public Set<DealRequirementMatchingResources> getDealrequirementmatchingresourcesResources() {
        return this.dealrequirementmatchingresourcesResources;
    }

    public void setDealrequirementmatchingresourcesResources(Set<DealRequirementMatchingResources> dealRequirementMatchingResources) {
        if (this.dealrequirementmatchingresourcesResources != null) {
            this.dealrequirementmatchingresourcesResources.forEach(i -> i.setResource(null));
        }
        if (dealRequirementMatchingResources != null) {
            dealRequirementMatchingResources.forEach(i -> i.setResource(this));
        }
        this.dealrequirementmatchingresourcesResources = dealRequirementMatchingResources;
    }

    public DealResources dealrequirementmatchingresourcesResources(Set<DealRequirementMatchingResources> dealRequirementMatchingResources) {
        this.setDealrequirementmatchingresourcesResources(dealRequirementMatchingResources);
        return this;
    }

    public DealResources addDealrequirementmatchingresourcesResource(DealRequirementMatchingResources dealRequirementMatchingResources) {
        this.dealrequirementmatchingresourcesResources.add(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setResource(this);
        return this;
    }

    public DealResources removeDealrequirementmatchingresourcesResource(DealRequirementMatchingResources dealRequirementMatchingResources) {
        this.dealrequirementmatchingresourcesResources.remove(dealRequirementMatchingResources);
        dealRequirementMatchingResources.setResource(null);
        return this;
    }

    public Set<DealResourceSkills> getDealresourceskillsResources() {
        return this.dealresourceskillsResources;
    }

    public void setDealresourceskillsResources(Set<DealResourceSkills> dealResourceSkills) {
        if (this.dealresourceskillsResources != null) {
            this.dealresourceskillsResources.forEach(i -> i.setResource(null));
        }
        if (dealResourceSkills != null) {
            dealResourceSkills.forEach(i -> i.setResource(this));
        }
        this.dealresourceskillsResources = dealResourceSkills;
    }

    public DealResources dealresourceskillsResources(Set<DealResourceSkills> dealResourceSkills) {
        this.setDealresourceskillsResources(dealResourceSkills);
        return this;
    }

    public DealResources addDealresourceskillsResource(DealResourceSkills dealResourceSkills) {
        this.dealresourceskillsResources.add(dealResourceSkills);
        dealResourceSkills.setResource(this);
        return this;
    }

    public DealResources removeDealresourceskillsResource(DealResourceSkills dealResourceSkills) {
        this.dealresourceskillsResources.remove(dealResourceSkills);
        dealResourceSkills.setResource(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealResources)) {
            return false;
        }
        return id != null && id.equals(((DealResources) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResources{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", joiningdate='" + getJoiningdate() + "'" +
            ", externalexpyears=" + getExternalexpyears() +
            ", externalexpmonths=" + getExternalexpmonths() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", type='" + getType() + "'" +
            ", isactive='" + getIsactive() + "'" +
            "}";
    }
}
