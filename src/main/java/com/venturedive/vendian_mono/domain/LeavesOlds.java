package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeavesOlds.
 */
@Entity
@Table(name = "leaves_olds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavesOlds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "annualtotal", precision = 21, scale = 2)
    private BigDecimal annualtotal;

    @Column(name = "annualused", precision = 21, scale = 2)
    private BigDecimal annualused;

    @Column(name = "annualadjustments", precision = 21, scale = 2)
    private BigDecimal annualadjustments;

    @Column(name = "casualtotal", precision = 21, scale = 2)
    private BigDecimal casualtotal;

    @Column(name = "casualused", precision = 21, scale = 2)
    private BigDecimal casualused;

    @Column(name = "sicktotal", precision = 21, scale = 2)
    private BigDecimal sicktotal;

    @Column(name = "sickused", precision = 21, scale = 2)
    private BigDecimal sickused;

    @Column(name = "annualtotalnextyear", precision = 21, scale = 2)
    private BigDecimal annualtotalnextyear;

    @Column(name = "annualusednextyear", precision = 21, scale = 2)
    private BigDecimal annualusednextyear;

    @Column(name = "casualtotalnextyear", precision = 21, scale = 2)
    private BigDecimal casualtotalnextyear;

    @Column(name = "casualusednextyear", precision = 21, scale = 2)
    private BigDecimal casualusednextyear;

    @Column(name = "sicktotalnextyear", precision = 21, scale = 2)
    private BigDecimal sicktotalnextyear;

    @Column(name = "sickusednextyear", precision = 21, scale = 2)
    private BigDecimal sickusednextyear;

    @Column(name = "carryforward", precision = 21, scale = 2)
    private BigDecimal carryforward;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "leave")
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
    private Set<Employees> employeesLeaves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeavesOlds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAnnualtotal() {
        return this.annualtotal;
    }

    public LeavesOlds annualtotal(BigDecimal annualtotal) {
        this.setAnnualtotal(annualtotal);
        return this;
    }

    public void setAnnualtotal(BigDecimal annualtotal) {
        this.annualtotal = annualtotal;
    }

    public BigDecimal getAnnualused() {
        return this.annualused;
    }

    public LeavesOlds annualused(BigDecimal annualused) {
        this.setAnnualused(annualused);
        return this;
    }

    public void setAnnualused(BigDecimal annualused) {
        this.annualused = annualused;
    }

    public BigDecimal getAnnualadjustments() {
        return this.annualadjustments;
    }

    public LeavesOlds annualadjustments(BigDecimal annualadjustments) {
        this.setAnnualadjustments(annualadjustments);
        return this;
    }

    public void setAnnualadjustments(BigDecimal annualadjustments) {
        this.annualadjustments = annualadjustments;
    }

    public BigDecimal getCasualtotal() {
        return this.casualtotal;
    }

    public LeavesOlds casualtotal(BigDecimal casualtotal) {
        this.setCasualtotal(casualtotal);
        return this;
    }

    public void setCasualtotal(BigDecimal casualtotal) {
        this.casualtotal = casualtotal;
    }

    public BigDecimal getCasualused() {
        return this.casualused;
    }

    public LeavesOlds casualused(BigDecimal casualused) {
        this.setCasualused(casualused);
        return this;
    }

    public void setCasualused(BigDecimal casualused) {
        this.casualused = casualused;
    }

    public BigDecimal getSicktotal() {
        return this.sicktotal;
    }

    public LeavesOlds sicktotal(BigDecimal sicktotal) {
        this.setSicktotal(sicktotal);
        return this;
    }

    public void setSicktotal(BigDecimal sicktotal) {
        this.sicktotal = sicktotal;
    }

    public BigDecimal getSickused() {
        return this.sickused;
    }

    public LeavesOlds sickused(BigDecimal sickused) {
        this.setSickused(sickused);
        return this;
    }

    public void setSickused(BigDecimal sickused) {
        this.sickused = sickused;
    }

    public BigDecimal getAnnualtotalnextyear() {
        return this.annualtotalnextyear;
    }

    public LeavesOlds annualtotalnextyear(BigDecimal annualtotalnextyear) {
        this.setAnnualtotalnextyear(annualtotalnextyear);
        return this;
    }

    public void setAnnualtotalnextyear(BigDecimal annualtotalnextyear) {
        this.annualtotalnextyear = annualtotalnextyear;
    }

    public BigDecimal getAnnualusednextyear() {
        return this.annualusednextyear;
    }

    public LeavesOlds annualusednextyear(BigDecimal annualusednextyear) {
        this.setAnnualusednextyear(annualusednextyear);
        return this;
    }

    public void setAnnualusednextyear(BigDecimal annualusednextyear) {
        this.annualusednextyear = annualusednextyear;
    }

    public BigDecimal getCasualtotalnextyear() {
        return this.casualtotalnextyear;
    }

    public LeavesOlds casualtotalnextyear(BigDecimal casualtotalnextyear) {
        this.setCasualtotalnextyear(casualtotalnextyear);
        return this;
    }

    public void setCasualtotalnextyear(BigDecimal casualtotalnextyear) {
        this.casualtotalnextyear = casualtotalnextyear;
    }

    public BigDecimal getCasualusednextyear() {
        return this.casualusednextyear;
    }

    public LeavesOlds casualusednextyear(BigDecimal casualusednextyear) {
        this.setCasualusednextyear(casualusednextyear);
        return this;
    }

    public void setCasualusednextyear(BigDecimal casualusednextyear) {
        this.casualusednextyear = casualusednextyear;
    }

    public BigDecimal getSicktotalnextyear() {
        return this.sicktotalnextyear;
    }

    public LeavesOlds sicktotalnextyear(BigDecimal sicktotalnextyear) {
        this.setSicktotalnextyear(sicktotalnextyear);
        return this;
    }

    public void setSicktotalnextyear(BigDecimal sicktotalnextyear) {
        this.sicktotalnextyear = sicktotalnextyear;
    }

    public BigDecimal getSickusednextyear() {
        return this.sickusednextyear;
    }

    public LeavesOlds sickusednextyear(BigDecimal sickusednextyear) {
        this.setSickusednextyear(sickusednextyear);
        return this;
    }

    public void setSickusednextyear(BigDecimal sickusednextyear) {
        this.sickusednextyear = sickusednextyear;
    }

    public BigDecimal getCarryforward() {
        return this.carryforward;
    }

    public LeavesOlds carryforward(BigDecimal carryforward) {
        this.setCarryforward(carryforward);
        return this;
    }

    public void setCarryforward(BigDecimal carryforward) {
        this.carryforward = carryforward;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public LeavesOlds createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public LeavesOlds updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<Employees> getEmployeesLeaves() {
        return this.employeesLeaves;
    }

    public void setEmployeesLeaves(Set<Employees> employees) {
        if (this.employeesLeaves != null) {
            this.employeesLeaves.forEach(i -> i.setLeave(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setLeave(this));
        }
        this.employeesLeaves = employees;
    }

    public LeavesOlds employeesLeaves(Set<Employees> employees) {
        this.setEmployeesLeaves(employees);
        return this;
    }

    public LeavesOlds addEmployeesLeave(Employees employees) {
        this.employeesLeaves.add(employees);
        employees.setLeave(this);
        return this;
    }

    public LeavesOlds removeEmployeesLeave(Employees employees) {
        this.employeesLeaves.remove(employees);
        employees.setLeave(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeavesOlds)) {
            return false;
        }
        return id != null && id.equals(((LeavesOlds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavesOlds{" +
            "id=" + getId() +
            ", annualtotal=" + getAnnualtotal() +
            ", annualused=" + getAnnualused() +
            ", annualadjustments=" + getAnnualadjustments() +
            ", casualtotal=" + getCasualtotal() +
            ", casualused=" + getCasualused() +
            ", sicktotal=" + getSicktotal() +
            ", sickused=" + getSickused() +
            ", annualtotalnextyear=" + getAnnualtotalnextyear() +
            ", annualusednextyear=" + getAnnualusednextyear() +
            ", casualtotalnextyear=" + getCasualtotalnextyear() +
            ", casualusednextyear=" + getCasualusednextyear() +
            ", sicktotalnextyear=" + getSicktotalnextyear() +
            ", sickusednextyear=" + getSickusednextyear() +
            ", carryforward=" + getCarryforward() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
