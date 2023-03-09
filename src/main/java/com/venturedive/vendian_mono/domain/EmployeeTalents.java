package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeTalents.
 */
@Entity
@Table(name = "employee_talents")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeTalents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "criticalposition")
    private Boolean criticalposition;

    @Column(name = "highpotential")
    private Boolean highpotential;

    @Size(max = 255)
    @Column(name = "successorfor", length = 255)
    private String successorfor;

    @Column(name = "criticalexperience")
    private Boolean criticalexperience;

    @Size(max = 65535)
    @Column(name = "promotionreadiness", length = 65535)
    private String promotionreadiness;

    @Size(max = 65535)
    @Column(name = "leadershipqualities", length = 65535)
    private String leadershipqualities;

    @Size(max = 65535)
    @Column(name = "careerambitions", length = 65535)
    private String careerambitions;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

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

    public EmployeeTalents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCriticalposition() {
        return this.criticalposition;
    }

    public EmployeeTalents criticalposition(Boolean criticalposition) {
        this.setCriticalposition(criticalposition);
        return this;
    }

    public void setCriticalposition(Boolean criticalposition) {
        this.criticalposition = criticalposition;
    }

    public Boolean getHighpotential() {
        return this.highpotential;
    }

    public EmployeeTalents highpotential(Boolean highpotential) {
        this.setHighpotential(highpotential);
        return this;
    }

    public void setHighpotential(Boolean highpotential) {
        this.highpotential = highpotential;
    }

    public String getSuccessorfor() {
        return this.successorfor;
    }

    public EmployeeTalents successorfor(String successorfor) {
        this.setSuccessorfor(successorfor);
        return this;
    }

    public void setSuccessorfor(String successorfor) {
        this.successorfor = successorfor;
    }

    public Boolean getCriticalexperience() {
        return this.criticalexperience;
    }

    public EmployeeTalents criticalexperience(Boolean criticalexperience) {
        this.setCriticalexperience(criticalexperience);
        return this;
    }

    public void setCriticalexperience(Boolean criticalexperience) {
        this.criticalexperience = criticalexperience;
    }

    public String getPromotionreadiness() {
        return this.promotionreadiness;
    }

    public EmployeeTalents promotionreadiness(String promotionreadiness) {
        this.setPromotionreadiness(promotionreadiness);
        return this;
    }

    public void setPromotionreadiness(String promotionreadiness) {
        this.promotionreadiness = promotionreadiness;
    }

    public String getLeadershipqualities() {
        return this.leadershipqualities;
    }

    public EmployeeTalents leadershipqualities(String leadershipqualities) {
        this.setLeadershipqualities(leadershipqualities);
        return this;
    }

    public void setLeadershipqualities(String leadershipqualities) {
        this.leadershipqualities = leadershipqualities;
    }

    public String getCareerambitions() {
        return this.careerambitions;
    }

    public EmployeeTalents careerambitions(String careerambitions) {
        this.setCareerambitions(careerambitions);
        return this;
    }

    public void setCareerambitions(String careerambitions) {
        this.careerambitions = careerambitions;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeTalents createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeTalents updatedat(Instant updatedat) {
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

    public EmployeeTalents employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeTalents)) {
            return false;
        }
        return id != null && id.equals(((EmployeeTalents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeTalents{" +
            "id=" + getId() +
            ", criticalposition='" + getCriticalposition() + "'" +
            ", highpotential='" + getHighpotential() + "'" +
            ", successorfor='" + getSuccessorfor() + "'" +
            ", criticalexperience='" + getCriticalexperience() + "'" +
            ", promotionreadiness='" + getPromotionreadiness() + "'" +
            ", leadershipqualities='" + getLeadershipqualities() + "'" +
            ", careerambitions='" + getCareerambitions() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
