package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Designations.
 */
@Entity
@Table(name = "designations")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Designations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "deletedat")
    private Instant deletedat;

    @OneToMany(mappedBy = "designation")
    @JsonIgnoreProperties(value = { "document", "designation", "employeejobinfoJobdescriptions" }, allowSetters = true)
    private Set<DesignationJobDescriptions> designationjobdescriptionsDesignations = new HashSet<>();

    @OneToMany(mappedBy = "designation")
    @JsonIgnoreProperties(
        value = {
            "employee",
            "designation",
            "reviewmanager",
            "manager",
            "department",
            "employmenttype",
            "jobdescription",
            "division",
            "businessunit",
            "competency",
        },
        allowSetters = true
    )
    private Set<EmployeeJobInfo> employeejobinfoDesignations = new HashSet<>();

    @OneToMany(mappedBy = "designation")
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
    private Set<Employees> employeesDesignations = new HashSet<>();

    @OneToMany(mappedBy = "designationAfterPromotion")
    @JsonIgnoreProperties(
        value = { "designationAfterPromotion", "designationAfterRedesignation", "rater", "pcEmployeeRating" },
        allowSetters = true
    )
    private Set<UserRatingsRemarks> userratingsremarksDesignationafterpromotions = new HashSet<>();

    @OneToMany(mappedBy = "designationAfterRedesignation")
    @JsonIgnoreProperties(
        value = { "designationAfterPromotion", "designationAfterRedesignation", "rater", "pcEmployeeRating" },
        allowSetters = true
    )
    private Set<UserRatingsRemarks> userratingsremarksDesignationafterredesignations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Designations id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Designations name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Designations createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Designations updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Instant getDeletedat() {
        return this.deletedat;
    }

    public Designations deletedat(Instant deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(Instant deletedat) {
        this.deletedat = deletedat;
    }

    public Set<DesignationJobDescriptions> getDesignationjobdescriptionsDesignations() {
        return this.designationjobdescriptionsDesignations;
    }

    public void setDesignationjobdescriptionsDesignations(Set<DesignationJobDescriptions> designationJobDescriptions) {
        if (this.designationjobdescriptionsDesignations != null) {
            this.designationjobdescriptionsDesignations.forEach(i -> i.setDesignation(null));
        }
        if (designationJobDescriptions != null) {
            designationJobDescriptions.forEach(i -> i.setDesignation(this));
        }
        this.designationjobdescriptionsDesignations = designationJobDescriptions;
    }

    public Designations designationjobdescriptionsDesignations(Set<DesignationJobDescriptions> designationJobDescriptions) {
        this.setDesignationjobdescriptionsDesignations(designationJobDescriptions);
        return this;
    }

    public Designations addDesignationjobdescriptionsDesignation(DesignationJobDescriptions designationJobDescriptions) {
        this.designationjobdescriptionsDesignations.add(designationJobDescriptions);
        designationJobDescriptions.setDesignation(this);
        return this;
    }

    public Designations removeDesignationjobdescriptionsDesignation(DesignationJobDescriptions designationJobDescriptions) {
        this.designationjobdescriptionsDesignations.remove(designationJobDescriptions);
        designationJobDescriptions.setDesignation(null);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoDesignations() {
        return this.employeejobinfoDesignations;
    }

    public void setEmployeejobinfoDesignations(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoDesignations != null) {
            this.employeejobinfoDesignations.forEach(i -> i.setDesignation(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setDesignation(this));
        }
        this.employeejobinfoDesignations = employeeJobInfos;
    }

    public Designations employeejobinfoDesignations(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoDesignations(employeeJobInfos);
        return this;
    }

    public Designations addEmployeejobinfoDesignation(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDesignations.add(employeeJobInfo);
        employeeJobInfo.setDesignation(this);
        return this;
    }

    public Designations removeEmployeejobinfoDesignation(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoDesignations.remove(employeeJobInfo);
        employeeJobInfo.setDesignation(null);
        return this;
    }

    public Set<Employees> getEmployeesDesignations() {
        return this.employeesDesignations;
    }

    public void setEmployeesDesignations(Set<Employees> employees) {
        if (this.employeesDesignations != null) {
            this.employeesDesignations.forEach(i -> i.setDesignation(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setDesignation(this));
        }
        this.employeesDesignations = employees;
    }

    public Designations employeesDesignations(Set<Employees> employees) {
        this.setEmployeesDesignations(employees);
        return this;
    }

    public Designations addEmployeesDesignation(Employees employees) {
        this.employeesDesignations.add(employees);
        employees.setDesignation(this);
        return this;
    }

    public Designations removeEmployeesDesignation(Employees employees) {
        this.employeesDesignations.remove(employees);
        employees.setDesignation(null);
        return this;
    }

    public Set<UserRatingsRemarks> getUserratingsremarksDesignationafterpromotions() {
        return this.userratingsremarksDesignationafterpromotions;
    }

    public void setUserratingsremarksDesignationafterpromotions(Set<UserRatingsRemarks> userRatingsRemarks) {
        if (this.userratingsremarksDesignationafterpromotions != null) {
            this.userratingsremarksDesignationafterpromotions.forEach(i -> i.setDesignationAfterPromotion(null));
        }
        if (userRatingsRemarks != null) {
            userRatingsRemarks.forEach(i -> i.setDesignationAfterPromotion(this));
        }
        this.userratingsremarksDesignationafterpromotions = userRatingsRemarks;
    }

    public Designations userratingsremarksDesignationafterpromotions(Set<UserRatingsRemarks> userRatingsRemarks) {
        this.setUserratingsremarksDesignationafterpromotions(userRatingsRemarks);
        return this;
    }

    public Designations addUserratingsremarksDesignationafterpromotion(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksDesignationafterpromotions.add(userRatingsRemarks);
        userRatingsRemarks.setDesignationAfterPromotion(this);
        return this;
    }

    public Designations removeUserratingsremarksDesignationafterpromotion(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksDesignationafterpromotions.remove(userRatingsRemarks);
        userRatingsRemarks.setDesignationAfterPromotion(null);
        return this;
    }

    public Set<UserRatingsRemarks> getUserratingsremarksDesignationafterredesignations() {
        return this.userratingsremarksDesignationafterredesignations;
    }

    public void setUserratingsremarksDesignationafterredesignations(Set<UserRatingsRemarks> userRatingsRemarks) {
        if (this.userratingsremarksDesignationafterredesignations != null) {
            this.userratingsremarksDesignationafterredesignations.forEach(i -> i.setDesignationAfterRedesignation(null));
        }
        if (userRatingsRemarks != null) {
            userRatingsRemarks.forEach(i -> i.setDesignationAfterRedesignation(this));
        }
        this.userratingsremarksDesignationafterredesignations = userRatingsRemarks;
    }

    public Designations userratingsremarksDesignationafterredesignations(Set<UserRatingsRemarks> userRatingsRemarks) {
        this.setUserratingsremarksDesignationafterredesignations(userRatingsRemarks);
        return this;
    }

    public Designations addUserratingsremarksDesignationafterredesignation(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksDesignationafterredesignations.add(userRatingsRemarks);
        userRatingsRemarks.setDesignationAfterRedesignation(this);
        return this;
    }

    public Designations removeUserratingsremarksDesignationafterredesignation(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksDesignationafterredesignations.remove(userRatingsRemarks);
        userRatingsRemarks.setDesignationAfterRedesignation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Designations)) {
            return false;
        }
        return id != null && id.equals(((Designations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Designations{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}
